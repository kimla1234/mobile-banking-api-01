package co.isatd.mobilebankingapi.features.transaction;

import co.isatd.mobilebankingapi.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction ,Long> {
}
