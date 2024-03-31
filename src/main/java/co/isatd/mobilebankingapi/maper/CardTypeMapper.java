package co.isatd.mobilebankingapi.maper;

import co.isatd.mobilebankingapi.domain.Card_Type;
import co.isatd.mobilebankingapi.features.card_type.dto.CardTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {

    Card_Type fromCardTypeResponse(CardTypeResponse cardTypeResponse);

    CardTypeResponse toCardTypeResponse(Card_Type cardType);

    List<CardTypeResponse> toListCardTypeResponse(List<Card_Type> cardTypes);

}