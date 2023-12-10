package dot.compta.backend.validators.invoice;

import dot.compta.backend.validators.CustomValidator;

public interface InvoiceValidator extends CustomValidator {
	void validateSavedStatus(int id);
}
