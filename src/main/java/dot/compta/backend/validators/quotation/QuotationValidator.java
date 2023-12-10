package dot.compta.backend.validators.quotation;

import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.validators.CustomValidator;

public interface QuotationValidator extends CustomValidator {
	void validateStatus(int id, QuotationStatus status);
	
	void validateNotLinkedToInvoice(int id);
}
