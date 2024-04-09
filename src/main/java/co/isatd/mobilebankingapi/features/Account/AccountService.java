package co.isatd.mobilebankingapi.features.Account;

import co.isatd.mobilebankingapi.features.Account.dto.AccountCreateRequest;
import co.isatd.mobilebankingapi.features.Account.dto.AccountRenameRequest;
import co.isatd.mobilebankingapi.features.Account.dto.AccountResponse;
import co.isatd.mobilebankingapi.features.user.dto.UserCreateRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface AccountService {

    Page<AccountResponse> findList(int page, int size);

    void hideAccount(String actNo);

    AccountResponse renameByActNo(String actNo,
                                  AccountRenameRequest accountRenameRequest);

    void createNew(AccountCreateRequest accountCreateRequest);

    AccountResponse findByActNo(String actNo);

    AccountResponse setLimitTransfer(String actNo, BigDecimal amount);

}
