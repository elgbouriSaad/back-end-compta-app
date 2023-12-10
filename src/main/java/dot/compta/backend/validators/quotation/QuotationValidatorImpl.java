package dot.compta.backend.validators.quotation;

import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomInvalidStatusException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import dot.compta.backend.repositories.quotation.QuotationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuotationValidatorImpl implements QuotationValidator {

    private final QuotationRepository quotationRepository;
    
    private final InvoiceRepository invoiceRepository;

    @Override
    public void validateExists(int id) {
        if (!quotationRepository.existsById(id)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.QUOTATION_NOT_FOUND, id));
        }
    }

    @Override
    public void validateNotDeleted(int id) {
        if (quotationRepository.existsByIdAndDeletedTrue(id)) {
            throw new CustomDeletedException(String.format(ValidationConstants.QUOTATION_DELETED, id));
        }
    }
    
    @Override
    public void validateStatus(int id, QuotationStatus status) {
        if (!quotationRepository.existsByIdAndStatus(id, status)) {
            throw new CustomInvalidStatusException(String.format(ValidationConstants.QUOTATION_STATUS_INVALID, id, status));
        }
    }
    
    @Override
    public void validateNotLinkedToInvoice(int id) {
    	if(invoiceRepository.existsByQuotationId(id)) {
    		throw new CustomBadInputException(String.format(ValidationConstants.QUOTATION_LINKED_TO_INVOICE, id));
    	}
    }

}
