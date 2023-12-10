package dot.compta.backend.validators.expenseReport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomInvalidStatusException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.expenseReport.ExpenseReportRepository;

public class ExpenseReportValidatorImplTest {

    @Mock
    private ExpenseReportRepository expenseReportRepository;

    @InjectMocks
    private ExpenseReportValidatorImpl expenseReportValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(expenseReportRepository);
    }

    @Test
    void when_expense_report_not_in_db_throw_CustomNotFoundException_validateExistsTest() {
        // GIVEN
        when(expenseReportRepository.existsById(EXPENSE_ID_TEST__1009)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> expenseReportValidator.validateExists(EXPENSE_ID_TEST__1009));

        // THEN
        assertEquals(String.format(ValidationConstants.EXPENSE_REPORT_NOT_FOUND, EXPENSE_ID_TEST__1009), result.getMessage());
        verify(expenseReportRepository).existsById(EXPENSE_ID_TEST__1009);
    }

    @Test
    void when_expense_report_deleted_throw_CustomDeletedException_validateNotDeletedTest() {
        // GIVEN
        when(expenseReportRepository.existsByIdAndDeletedTrue(EXPENSE_ID_TEST__1009)).thenReturn(true);

        // WHEN
        CustomDeletedException result = assertThrows(CustomDeletedException.class,
                () -> expenseReportValidator.validateNotDeleted(EXPENSE_ID_TEST__1009));

        // THEN
        assertEquals(String.format(ValidationConstants.EXPENSE_REPORT_DELETED, EXPENSE_ID_TEST__1009), result.getMessage());
        verify(expenseReportRepository).existsByIdAndDeletedTrue(EXPENSE_ID_TEST__1009);
    }
    
    @Test
    void when_expense_report_stats_notSaved_throw_CustomInvalidStatusException_validateSavedStatusTest() {
        // GIVEN
        when(expenseReportRepository.existsByIdAndStatus(EXPENSE_ID_TEST__1009, ExpenseReportStatus.SAVED)).thenReturn(false);

        // WHEN
        CustomInvalidStatusException result = assertThrows(CustomInvalidStatusException.class,
                () -> expenseReportValidator.validateSavedStatus(EXPENSE_ID_TEST__1009));

        // THEN
        assertEquals(String.format(ValidationConstants.EXPENSE_REPORT_STATUS_INVALID, EXPENSE_ID_TEST__1009), result.getMessage());
        verify(expenseReportRepository).existsByIdAndStatus(EXPENSE_ID_TEST__1009, ExpenseReportStatus.SAVED);
    }

}
