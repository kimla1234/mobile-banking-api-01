package co.isatd.mobilebankingapi.features.card_type;


import co.isatd.mobilebankingapi.features.card_type.dto.CardTypeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cardTypes")
@RequiredArgsConstructor
@Slf4j
public class CardTypeController {

    private final CardTypeService cardTypeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CardTypeResponse> findCardTypes(){

        List<CardTypeResponse> cardTypeResponses = cardTypeService.findAll();
        log.info(cardTypeResponses.toString());

        return cardTypeResponses;
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CardTypeResponse findCardTypeByName(@PathVariable String name){
        return cardTypeService.findByName(name);
    }

}