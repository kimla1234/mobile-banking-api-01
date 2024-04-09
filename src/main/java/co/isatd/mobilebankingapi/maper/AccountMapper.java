package co.isatd.mobilebankingapi.maper;

import co.isatd.mobilebankingapi.domain.Account;
import co.isatd.mobilebankingapi.features.Account.dto.AccountCreateRequest;
import co.isatd.mobilebankingapi.features.Account.dto.AccountResponse;

import co.isatd.mobilebankingapi.features.Account.dto.AccountSnippetResponse;
import co.isatd.mobilebankingapi.features.Account.dto.AccountSnippetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class,
        AccountTypeMapper.class
})
public interface AccountMapper {

    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);

    @Named("mapUserResponse")
    AccountResponse toAccountResponse(Account account);

    @Mapping(source = "userAccountList", target = "user",
            qualifiedByName = "mapUserDetailResponse")
    List<AccountResponse> toListAccountResponse(List<Account> accounts);

    AccountSnippetResponse toAccountSnippetResponse(Account account);
}


