package co.isatd.mobilebankingapi.features.Account_Type;

import co.isatd.mobilebankingapi.features.Account_Type.dto.AccountTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/accountTypes")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountTypeResponse> findAccountTypes(){
        return accountTypeService.findAll();
    }

    @GetMapping("/{alias}")
    @ResponseStatus(HttpStatus.OK)
    public AccountTypeResponse findByAlias(@PathVariable String alias){
        return accountTypeService.findByAlias(alias);
    }
}
