package co.isatd.mobilebankingapi.features.transaction.dto;

import co.isatd.mobilebankingapi.features.Account.dto.AccountResponse;
import co.isatd.mobilebankingapi.features.Account.dto.AccountSnippetResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        AccountSnippetResponse owner,

        AccountSnippetResponse transferReceiver,

        BigDecimal amount,

        String remark,

        String transactionType,

        Boolean status,

        LocalDateTime transactionAt


) {
}
