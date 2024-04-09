package co.isatd.mobilebankingapi.features.Account;

import co.isatd.mobilebankingapi.features.Account.dto.AccountCreateRequest;
import co.isatd.mobilebankingapi.features.Account.dto.AccountRenameRequest;
import co.isatd.mobilebankingapi.features.Account.dto.AccountResponse;
import co.isatd.mobilebankingapi.util.BigDecimalUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor

public class AccountController {
    private final AccountService accountService;

    @GetMapping
    Page<AccountResponse> findList(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size
    ) {
        return accountService.findList(page, size);
    }

    @PutMapping("/{actNo}/hide")
    void hideAccountByActNo(@PathVariable String actNo) {
        accountService.hideAccount(actNo);
    }

    @PutMapping("/{actNo}/rename")
    AccountResponse renameByActNo(@PathVariable String actNo,
                                  @Valid @RequestBody AccountRenameRequest accountRenameRequest) {
        return accountService.renameByActNo(actNo, accountRenameRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.createNew(accountCreateRequest);
    }

    @GetMapping("/{actNo}")
    AccountResponse findByActNo(@PathVariable String actNo) {
        return accountService.findByActNo(actNo);
    }


    @PutMapping("/{actNo}/transfer-limit")
    AccountResponse setTransferLimit(@PathVariable String actNo,
                                     @RequestBody BigDecimalUtil transferLimitAmount){
        BigDecimal amount = transferLimitAmount.getTransferLimitAmount();
        return accountService.setLimitTransfer(actNo,amount);
    }

}
