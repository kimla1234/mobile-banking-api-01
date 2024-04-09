package co.isatd.mobilebankingapi.features.Account.dto;

import co.isatd.mobilebankingapi.features.Account_Type.dto.AccountTypeResponse;
import co.isatd.mobilebankingapi.features.user.dto.UserDetailResponse;
import co.isatd.mobilebankingapi.features.user.dto.UserResponse;

import java.math.BigDecimal;

public record AccountResponse(
        String alias,
        String actNo,
        String actName,
        BigDecimal balance,
        BigDecimal transferLimit,
        AccountTypeResponse accountTypeResponse,
        UserResponse user
) {
}
