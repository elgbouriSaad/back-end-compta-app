package dot.compta.backend.controllers.quotation;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.RequestTransformQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.services.quotation.QuotationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.QUOTATIONS_URL)
@AllArgsConstructor
public class QuotationController {

    private final QuotationService quotationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    @Transactional
    public void createQuotation(@RequestBody @Validated RequestQuotationDto quotation) {
        quotationService.createQuotation(quotation);
    }
    
    @PutMapping(APIConstants.QUOTATION_VALIDATE_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_VALIDATE_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_VALIDATE_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    @Transactional
    public void validateQuotation(@PathVariable int id) {
         quotationService.validateQuotation(id);
    }
    
    @PutMapping(APIConstants.QUOTATION_UPDATE_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_UPDATE_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_UPDATE_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    @Transactional
    public void updateQuotation(@RequestBody @Validated UpdateQuotationDto quotation,@PathVariable int id) {
         quotationService.updateQuotation(quotation, id);
    }

    @PutMapping(APIConstants.QUOTATION_TRANSFORM_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TRANSFORM_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TRANSFORM_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    @Transactional
    public void transformQuotation(@RequestBody @Validated RequestTransformQuotationDto requestTransformQuotationDto, @PathVariable int id) {
        quotationService.transformToInvoice(id, requestTransformQuotationDto.getPaymentDelay());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    public List<ResponseQuotationDto> getQuotations() {
        return quotationService.getQuotations();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_IDGET_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_IDGET_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    public ResponseQuotationDto getQuotationById(@PathVariable int id) {
        return quotationService.getQuotationById(id);
    }
	
	@GetMapping(APIConstants.CUSTOMER_QUOTATIONS_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_CUSTOMERGET_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_CUSTOMERGET_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    public List<ResponseQuotationDto> getQuotationsByCustomerId(@PathVariable int id) {
        return quotationService.getQuotationsByCustomerId(id);
    }
	
	@GetMapping(APIConstants.CLIENT_QUOTATIONS_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_CLIENTGET_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_CLIENTGET_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    public List<ResponseQuotationDto> getQuotationsByClientId(@PathVariable int id) {
        return quotationService.getQuotationsByClientId(id);
    }
	
	@DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_QUOTATION_CONTROLLER_DELETE_SUM_DESC,
            description = DocumentationConstants.DOC_QUOTATION_CONTROLLER_DELETE_SUM_DESC,
            tags = DocumentationConstants.DOC_QUOTATION_CONTROLLER_TAGS)
    @Transactional
    public void deleteQuotation(@PathVariable int id) {
    	quotationService.deleteQuotation(id);
    }
	
}
