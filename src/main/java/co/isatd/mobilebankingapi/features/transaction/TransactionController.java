package co.isatd.mobilebankingapi.features.transaction;

import co.isatd.mobilebankingapi.domain.Account;
import co.isatd.mobilebankingapi.features.transaction.dto.TransactionCreateRequest;
import co.isatd.mobilebankingapi.features.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    TransactionResponse transfer ( @Valid @RequestBody TransactionCreateRequest transactionCreateRequest){
      return transactionService.transfer(transactionCreateRequest);
    }

    @GetMapping
    public Page<TransactionResponse> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "25") int page_size,
                                             @RequestParam(required = false, defaultValue = "") String transaction_type,
                                             @RequestParam(required = false, defaultValue = "DESC") String sort_date){

        return transactionService.findAll(page,page_size,transaction_type,sort_date);

    }
}
