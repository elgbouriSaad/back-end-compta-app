package dot.compta.backend.controllers.accountant;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.services.accountant.AccountantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.ACCOUNTANTS_URL)
@AllArgsConstructor
public class AccountantController {

    private final AccountantService accountantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_TAGS)
    @Transactional
    public void createAccountant(@RequestBody @Validated RequestAccountantDto accountant) {
        accountantService.createAccountant(accountant);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_TAGS)
    public List<ResponseAccountantDto> getAccountants() {
        return accountantService.getAccountants();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_IDGET_SUM_DESC,
            description = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_IDGET_SUM_DESC,
            tags = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_TAGS)
    public ResponseAccountantDto getAccountantById(@PathVariable int id) {
        return accountantService.getAccountantById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_TAGS)
    @Transactional
    public void updateAccountant(@RequestBody @Validated RequestAccountantDto accountant, @PathVariable int id) {
        accountantService.updateAccountant(accountant, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_DELETE_SUM_DESC,
            description = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_DELETE_SUM_DESC,
            tags = DocumentationConstants.DOC_ACCOUNTANT_CONTROLLER_TAGS)
    @Transactional
    public void deleteAccountant(@PathVariable int id) {
        accountantService.deleteAccountant(id);
    }

}

