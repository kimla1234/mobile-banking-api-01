package co.isatd.mobilebankingapi.features.card_type;

import co.isatd.mobilebankingapi.domain.Card_Type;
import co.isatd.mobilebankingapi.features.card_type.dto.CardTypeResponse;
import co.isatd.mobilebankingapi.maper.CardTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypeServiceImpl implements CardTypeService{
    private final CardTypeRepository cardTypeRepository;
    private final CardTypeMapper cardTypeMapper;

    @Override
    public List<CardTypeResponse> findAll() {

        List<Card_Type> cardTypes = cardTypeRepository.findAll();

        return cardTypeMapper.toListCardTypeResponse(cardTypes);
    }

    @Override
    public CardTypeResponse findByName(String name) {

        Card_Type cardType = cardTypeRepository.findByNameIgnoreCase(name)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "CardType does not exist!"
                ));

        return cardTypeMapper.toCardTypeResponse(cardType);
    }
}