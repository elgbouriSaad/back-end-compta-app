package dot.compta.backend.validators.invoice;

import org.springframework.stereotype.Service;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomInvalidStatusException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceValidatorImpl implements InvoiceValidator {
	
	private final InvoiceRepository invoiceRepository;
	
	@Override
    public void validateExists(int id) {
        if (!invoiceRepository.existsById(id)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.INVOICE_NOT_FOUND, id));
        }
    }

    @Override
    public void validateNotDeleted(int id) {
        if (invoiceRepository.existsByIdAndDeletedTrue(id)) {
            throw new CustomDeletedException(String.format(ValidationConstants.INVOICE_DELETED, id));
        }
    }
    
    @Override
    public void validateSavedStatus(int id) {
        if (!invoiceRepository.existsByIdAndStatus(id, InvoiceStatus.SAVED)) {
            throw new CustomInvalidStatusException(String.format(ValidationConstants.INVOICE_STATUS_INVALID, id));
        }
    }

}
