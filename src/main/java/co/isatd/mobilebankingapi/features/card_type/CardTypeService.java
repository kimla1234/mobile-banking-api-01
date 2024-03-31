package co.isatd.mobilebankingapi.features.card_type;

import co.isatd.mobilebankingapi.features.card_type.dto.CardTypeResponse;

import java.util.List;

public interface CardTypeService {
    List<CardTypeResponse> findAll();

    CardTypeResponse findByName(String name);
}
