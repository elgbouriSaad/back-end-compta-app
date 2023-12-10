package dot.compta.backend.validators.expenseReport;

import org.springframework.stereotype.Service;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomInvalidStatusException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.expenseReport.ExpenseReportRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpenseReportValidatorImpl implements ExpenseReportValidator {

    private final ExpenseReportRepository expenseReportRepository;

    @Override
    public void validateExists(int id) {
        if (!expenseReportRepository.existsById(id)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.EXPENSE_REPORT_NOT_FOUND, id));
        }
    }

    @Override
    public void validateNotDeleted(int id) {
        if (expenseReportRepository.existsByIdAndDeletedTrue(id)) {
            throw new CustomDeletedException(String.format(ValidationConstants.EXPENSE_REPORT_DELETED, id));
        }
    }
    
    @Override
    public void validateSavedStatus(int id) {
        if (!expenseReportRepository.existsByIdAndStatus(id, ExpenseReportStatus.SAVED)) {
            throw new CustomInvalidStatusException(String.format(ValidationConstants.EXPENSE_REPORT_STATUS_INVALID, id));
        }
    }

}
