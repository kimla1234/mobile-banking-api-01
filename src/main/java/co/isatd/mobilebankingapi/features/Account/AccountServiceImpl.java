package co.isatd.mobilebankingapi.features.Account;
import co.isatd.mobilebankingapi.domain.Account;
import co.isatd.mobilebankingapi.domain.Account_Type;
import co.isatd.mobilebankingapi.domain.User;
import co.isatd.mobilebankingapi.domain.UserAccount;
import co.isatd.mobilebankingapi.features.Account.dto.AccountCreateRequest;
import co.isatd.mobilebankingapi.features.Account.dto.AccountRenameRequest;
import co.isatd.mobilebankingapi.features.Account.dto.AccountResponse;
import co.isatd.mobilebankingapi.features.Account_Type.AccountTypeRepository;
import co.isatd.mobilebankingapi.features.user.UserRepository;
import co.isatd.mobilebankingapi.maper.AccountMapper;
import co.isatd.mobilebankingapi.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final UserAccountRepository userAccountRepository;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;


    @Override
    public void createNew(AccountCreateRequest accountCreateRequest) {

        // check account type
        Account_Type accountType = accountTypeRepository.findByAlias(accountCreateRequest.accountTypeAlias())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Invalid account type"));

        // check user by UUID
        User user = userRepository.findByUuid(accountCreateRequest.userUuid())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User has not been found"));

        // map account dto to account entity
        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo(RandomUtil.generate9Digits());
        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setIsHidden(false);

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setUser(user);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreatedAt(LocalDateTime.now());

        userAccountRepository.save(userAccount);

    }

    @Override
    public AccountResponse findByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account no is invalid!"
                ));

        return accountMapper.toAccountResponse(account);

    }


    @Override
    public Page<AccountResponse> findList(int page, int size) {

        // validate page and size
        if (page < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Page number must be greater than or equal to zero");
        }

        if (size < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Size must be greater than or equal to one");
        }

        Sort sortByActName = Sort.by(Sort.Direction.ASC, "actName");
        PageRequest pageRequest = PageRequest.of(page, size, sortByActName);

        Page<Account> accounts = accountRepository.findAll(pageRequest);

        return accounts.map(accountMapper::toAccountResponse);

    }



    @Override
    public AccountResponse renameByActNo(String actNo, AccountRenameRequest accountRenameRequest) {
        // check actNo if exists
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account has not been found"
                ));

        // check old alias and new alias
        if (account.getAlias() != null && account.getAlias().equals(accountRenameRequest.newName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "New name must not the same as old name");
        }

        account.setAlias(accountRenameRequest.newName());
        account = accountRepository.save(account);

        return accountMapper.toAccountResponse(account);

    }

    @Override
    public void hideAccount(String actNo) {
        if (!accountRepository.existsByActNo(actNo)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account has not been found"
            );
        }

        try {
            accountRepository.hideAccountByActNo(actNo);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong, please contact teacher (ABA-123456789)"
            );

    }
    }

    @Override
    public AccountResponse setLimitTransfer(String actNo,BigDecimal amount) {

        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "account has not been found!"
                ));

        account.setTransferLimit(amount);

        account = accountRepository.save(account);

        return accountMapper.toAccountResponse(account);

    }


}


