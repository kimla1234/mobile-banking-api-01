package co.isatd.mobilebankingapi.features.transaction;

import co.isatd.mobilebankingapi.domain.Account;
import co.isatd.mobilebankingapi.domain.Transaction;
import co.isatd.mobilebankingapi.features.Account.AccountRepository;
import co.isatd.mobilebankingapi.features.transaction.dto.TransactionCreateRequest;
import co.isatd.mobilebankingapi.features.transaction.dto.TransactionResponse;
import co.isatd.mobilebankingapi.maper.TransactionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    @Override
    @Transactional
    public TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest) {
        //validate account
        Account owner = accountRepository.findByActNo(transactionCreateRequest.ownerActNo())
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account owner has not fount"
                ));

        Account transferReceiver = accountRepository.findByActNo(transactionCreateRequest.transferReceiverActNo())
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account owner has not fount"
                ));

        if(owner.getBalance().doubleValue() < transactionCreateRequest.amount().doubleValue()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient balance"
            );
        }

        if (transactionCreateRequest.amount().doubleValue() >= owner.getTransferLimit().doubleValue()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transfer has been over the transfer limit"
            );
        }

        owner.setBalance(owner.getBalance().subtract(transactionCreateRequest.amount()));
        transferReceiver.setBalance(transferReceiver.getBalance().add(transactionCreateRequest.amount()));

        Transaction transaction = transactionMapper.fromTransactionCreateRequest(transactionCreateRequest);
        transaction.setOwner(owner);
        transaction.setTransferReceiver(transferReceiver);
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionAt(LocalDateTime.now());
        transaction.setStatus(true);
        transaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(transaction);
    }


    @Override
    public Page<TransactionResponse> findAll(int page, int size, String transactionType, String sortDirection) {
        if(page < 0 ){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "page number must be greater than 0"
            );
        }

        if(size < 1){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Size must be greater than or equal to 1!"
            );
        }

        Sort.Direction direction = Sort.Direction.DESC;

        if(sortDirection.equals("ASC")){
            direction = Sort.Direction.ASC;
        }

        Sort sortByDateTime = Sort.by(direction, "transactionAt");

        PageRequest pageRequest = PageRequest.of(page,size, sortByDateTime);

        Page<Transaction> transactions;

        if(transactionType.equals("TRANSFER")){
            transactions = transactionRepository.findAllByTransactionType(transactionType, pageRequest);
        } else if (transactionType.equals("PAYMENT")) {
            transactions = transactionRepository.findAllByTransactionType(transactionType, pageRequest);
        }else {
            transactions = transactionRepository.findAll(pageRequest);
        }

        return transactions.map(transactionMapper::toTransactionResponse);
    }


}
