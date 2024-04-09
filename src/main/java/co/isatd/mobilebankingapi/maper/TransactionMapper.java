package co.isatd.mobilebankingapi.maper;

import co.isatd.mobilebankingapi.domain.Transaction;
import co.isatd.mobilebankingapi.features.transaction.dto.TransactionCreateRequest;
import co.isatd.mobilebankingapi.features.transaction.dto.TransactionResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface TransactionMapper {
    TransactionResponse toTransactionResponse(Transaction transaction);

    Transaction fromTransactionCreateRequest(TransactionCreateRequest transactionCreateRequest);

    List<TransactionResponse> ToListTransactionResponse(List<Transaction> transaction);


}
