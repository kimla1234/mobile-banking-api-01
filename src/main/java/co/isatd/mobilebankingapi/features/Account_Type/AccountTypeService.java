package co.isatd.mobilebankingapi.features.Account_Type;

import co.isatd.mobilebankingapi.features.Account_Type.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {

    List<AccountTypeResponse> findAll();

    AccountTypeResponse findByAlias(String alias);
}
