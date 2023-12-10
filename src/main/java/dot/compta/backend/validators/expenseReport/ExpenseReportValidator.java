package dot.compta.backend.validators.expenseReport;

import dot.compta.backend.validators.CustomValidator;

public interface ExpenseReportValidator extends CustomValidator {
	void validateSavedStatus(int id);
}
