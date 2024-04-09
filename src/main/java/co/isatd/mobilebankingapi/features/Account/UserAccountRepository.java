package co.isatd.mobilebankingapi.features.Account;

import co.isatd.mobilebankingapi.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}

