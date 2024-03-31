package co.isatd.mobilebankingapi.maper;

import co.isatd.mobilebankingapi.domain.Account_Type;
import co.isatd.mobilebankingapi.features.Account_Type.dto.AccountTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    List<AccountTypeResponse> toListAccountTypeResponse(List<Account_Type> accountTypes);

    AccountTypeResponse toAccountTypeResponse(Account_Type accountType);
}
