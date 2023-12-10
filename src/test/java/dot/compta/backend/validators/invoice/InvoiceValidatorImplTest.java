package dot.compta.backend.validators.invoice;

import static dot.compta.backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomInvalidStatusException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.invoice.InvoiceRepository;

public class InvoiceValidatorImplTest {
	
	@Mock
	private InvoiceRepository invoiceRepository;
	
	@InjectMocks
	private InvoiceValidatorImpl invoiceValidator;
	
	@BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(invoiceRepository);
    }
    
    @Test
    void when_invoice_not_in_db_throw_CustomNotFoundException_validateExistsTest() {
        // GIVEN
        when(invoiceRepository.existsById(INVOICE_ID_TEST__1010)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> invoiceValidator.validateExists(INVOICE_ID_TEST__1010));

        // THEN
        assertEquals(String.format(ValidationConstants.INVOICE_NOT_FOUND, INVOICE_ID_TEST__1010), result.getMessage());
        verify(invoiceRepository).existsById(INVOICE_ID_TEST__1010);
    }

    @Test
    void when_invoice_deleted_throw_CustomDeletedException_validateNotDeletedTest() {
        // GIVEN
        when(invoiceRepository.existsByIdAndDeletedTrue(INVOICE_ID_TEST__1010)).thenReturn(true);

        // WHEN
        CustomDeletedException result = assertThrows(CustomDeletedException.class,
                () -> invoiceValidator.validateNotDeleted(INVOICE_ID_TEST__1010));

        // THEN
        assertEquals(String.format(ValidationConstants.INVOICE_DELETED, INVOICE_ID_TEST__1010), result.getMessage());
        verify(invoiceRepository).existsByIdAndDeletedTrue(INVOICE_ID_TEST__1010);
    }
    
    @Test
    void when_invoice_status_notSaved_throw_CustomInvalidStatusException_validateSavedStatusTest() {
        // GIVEN
        when(invoiceRepository.existsByIdAndStatus(INVOICE_ID_TEST__1010, InvoiceStatus.SAVED)).thenReturn(false);

        // WHEN
        CustomInvalidStatusException result = assertThrows(CustomInvalidStatusException.class,
                () -> invoiceValidator.validateSavedStatus(INVOICE_ID_TEST__1010));

        // THEN
        assertEquals(String.format(ValidationConstants.INVOICE_STATUS_INVALID, INVOICE_ID_TEST__1010), result.getMessage());
        verify(invoiceRepository).existsByIdAndStatus(INVOICE_ID_TEST__1010, InvoiceStatus.SAVED);
    }

}
