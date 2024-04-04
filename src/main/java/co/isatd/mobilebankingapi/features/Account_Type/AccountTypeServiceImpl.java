package co.isatd.mobilebankingapi.features.Account_Type;

import co.isatd.mobilebankingapi.domain.Account_Type;
import co.isatd.mobilebankingapi.features.Account_Type.dto.AccountTypeResponse;
import co.isatd.mobilebankingapi.maper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountTypeResponse> findAll() {

        List<Account_Type> account_types = accountTypeRepository.findAll();

        return accountTypeMapper.toListAccountTypeResponse(account_types);
    }

    @Override
    public AccountTypeResponse findByAlias(String alias) {

        Account_Type account_type = accountTypeRepository.findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "account type does not exist!"
                ));

        return accountTypeMapper.toAccountTypeResponse(account_type);
    }
}