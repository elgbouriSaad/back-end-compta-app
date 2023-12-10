package dot.compta.backend.controllers.invoice;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.services.invoice.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.INVOICES_URL)
@AllArgsConstructor
public class InvoiceController {
	
	private final InvoiceService invoiceService;
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    @Transactional
    public void createInvoice(@RequestBody @Validated RequestInvoiceDto requestInvoiceDto) {
		invoiceService.createInvoice(requestInvoiceDto);
    }
	
	@PutMapping(APIConstants.INVOICE_VALIDATE_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_VALIDATE_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_VALIDATE_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    @Transactional
    public void validateInvoice(@PathVariable int id) {
		invoiceService.validateInvoice(id);
    }
	
	@PutMapping(APIConstants.INVOICE_UPDATE_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_UPDATE_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_UPDATE_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    @Transactional
    public void updateInvoice(@RequestBody @Validated UpdateInvoiceDto invoice,@PathVariable int id) {
         invoiceService.updateInvoice(invoice, id);
    }
	
	@GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    public List<ResponseInvoiceDto> getInvoices() {
        return invoiceService.getInvoices();
    }
	
	@GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_IDGET_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_IDGET_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    public ResponseInvoiceDto getInvoiceById(@PathVariable int id) {
        return invoiceService.getInvoiceById(id);
    }
	
	@GetMapping(APIConstants.CUSTOMER_INVOICES_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_CUSTOMERGET_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_CUSTOMERGET_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    public List<ResponseInvoiceDto> getInvoicesByCustomerId(@PathVariable int id) {
        return invoiceService.getInvoicesByCustomerId(id);
    }
	
	@GetMapping(APIConstants.CLIENT_INVOICES_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_CUSTOMERGET_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_CUSTOMERGET_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    public List<ResponseInvoiceDto> getInvoicesByClientId(@PathVariable int id) {
        return invoiceService.getInvoicesByClientId(id);
    }
	
	@DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_INVOICE_CONTROLLER_DELETE_SUM_DESC,
            description = DocumentationConstants.DOC_INVOICE_CONTROLLER_DELETE_SUM_DESC,
            tags = DocumentationConstants.DOC_INVOICE_CONTROLLER_TAGS)
    @Transactional
    public void deleteInvoice(@PathVariable int id) {
    	invoiceService.deleteInvoice(id);
    }

}
