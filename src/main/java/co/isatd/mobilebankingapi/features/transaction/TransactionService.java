package co.isatd.mobilebankingapi.features.transaction;

import co.isatd.mobilebankingapi.features.transaction.dto.TransactionCreateRequest;
import co.isatd.mobilebankingapi.features.transaction.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface TransactionService {
    TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest);


}
