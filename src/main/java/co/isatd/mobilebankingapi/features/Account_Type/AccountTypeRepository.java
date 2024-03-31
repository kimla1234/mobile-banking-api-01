package co.isatd.mobilebankingapi.features.Account_Type;

import co.isatd.mobilebankingapi.domain.Account_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AccountTypeRepository extends JpaRepository<Account_Type, Integer> {
    Optional<Account_Type> findByAliasIgnoreCase(String alias);

}