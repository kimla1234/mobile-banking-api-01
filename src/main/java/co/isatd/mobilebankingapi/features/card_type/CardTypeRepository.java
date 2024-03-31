package co.isatd.mobilebankingapi.features.card_type;

import co.isatd.mobilebankingapi.domain.Card_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardTypeRepository extends JpaRepository<Card_Type,Integer> {

    Optional<Card_Type> findByNameIgnoreCase(String name);
}