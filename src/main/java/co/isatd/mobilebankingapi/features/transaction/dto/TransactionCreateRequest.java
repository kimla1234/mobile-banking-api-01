package co.isatd.mobilebankingapi.features.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionCreateRequest(
        @NotBlank(message = "Account no is required")
        String ownerActNo,
        @NotBlank(message = "Transfer Account no is required")
        String transferReceiverActNo,
        @NotNull
        @Positive
        BigDecimal amount,
        String remark
) {
}
