package co.isatd.mobilebankingapi.features.init;

import co.isatd.mobilebankingapi.domain.Account_Type;
import co.isatd.mobilebankingapi.domain.Role;
import co.isatd.mobilebankingapi.features.Account_Type.AccountTypeRepository;
import co.isatd.mobilebankingapi.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;

    @PostConstruct
    void init() {
// Auto generate role (USER, CUSTOMER, STAFF, ADMIN)
        if (roleRepository.count() < 1) {
            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role staff = new Role();
            staff.setName("STAFF");

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.saveAll(
                    List.of(user, customer, staff, admin)
            );
        }
    }

    @PostConstruct
    void initAccountType(){
        if(accountTypeRepository.count() < 1){
            Account_Type payroll = new Account_Type();
            payroll.setName("Payroll");
            payroll.setAlias("payroll");
            payroll.setIsDeleted(false);
            payroll.setDescription("this is payroll account type");

            Account_Type saving = new Account_Type();
            saving.setName("Saving");
            saving.setAlias("saving");
            saving.setIsDeleted(false);
            saving.setDescription("this is saving account type");

            accountTypeRepository.saveAll(List.of(payroll,saving));
        }
    }

}
