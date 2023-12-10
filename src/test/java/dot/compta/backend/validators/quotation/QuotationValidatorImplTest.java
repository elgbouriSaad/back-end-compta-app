package dot.compta.backend.validators.quotation;

import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomInvalidStatusException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import dot.compta.backend.repositories.quotation.QuotationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

public class QuotationValidatorImplTest {

    @Mock
    private QuotationRepository quotationRepository;
    
    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private QuotationValidatorImpl quotationValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(quotationRepository,invoiceRepository);
    }

    @Test
    void when_quotation_not_in_db_throw_CustomNotFoundException_validateExistsTest() {
        // GIVEN
        when(quotationRepository.existsById(QUOTATION_ID_TEST__1007)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> quotationValidator.validateExists(QUOTATION_ID_TEST__1007));

        // THEN
        assertEquals(String.format(ValidationConstants.QUOTATION_NOT_FOUND, QUOTATION_ID_TEST__1007), result.getMessage());
        verify(quotationRepository).existsById(QUOTATION_ID_TEST__1007);
    }

    @Test
    void when_quotation_deleted_throw_CustomDeletedException_validateNotDeletedTest() {
        // GIVEN
        when(quotationRepository.existsByIdAndDeletedTrue(QUOTATION_ID_TEST__1007)).thenReturn(true);

        // WHEN
        CustomDeletedException result = assertThrows(CustomDeletedException.class,
                () -> quotationValidator.validateNotDeleted(QUOTATION_ID_TEST__1007));

        // THEN
        assertEquals(String.format(ValidationConstants.QUOTATION_DELETED, QUOTATION_ID_TEST__1007), result.getMessage());
        verify(quotationRepository).existsByIdAndDeletedTrue(QUOTATION_ID_TEST__1007);
    }
    
    @Test
    void when_quotation_status_not_matching_throw_CustomInvalidStatusException_validateStatusTest() {
        // GIVEN
        when(quotationRepository.existsByIdAndStatus(QUOTATION_ID_TEST__1007, QuotationStatus.SAVED)).thenReturn(false);

        // WHEN
        CustomInvalidStatusException result = assertThrows(CustomInvalidStatusException.class,
                () -> quotationValidator.validateStatus(QUOTATION_ID_TEST__1007,QuotationStatus.SAVED));

        // THEN
        assertEquals(String.format(ValidationConstants.QUOTATION_STATUS_INVALID, QUOTATION_ID_TEST__1007, QuotationStatus.SAVED), result.getMessage());
        verify(quotationRepository).existsByIdAndStatus(QUOTATION_ID_TEST__1007, QuotationStatus.SAVED);
    }
    
    @Test
    void when_quotation_linked_to_invoice_throw_CustomBadInputException_validateNotLinkedToInvoiceTest() {
        // GIVEN
        when(invoiceRepository.existsByQuotationId(QUOTATION_ID_TEST__1007)).thenReturn(true);

        // WHEN
        CustomBadInputException result = assertThrows(CustomBadInputException.class,
                () -> quotationValidator.validateNotLinkedToInvoice(QUOTATION_ID_TEST__1007));

        // THEN
        assertEquals(String.format(ValidationConstants.QUOTATION_LINKED_TO_INVOICE, QUOTATION_ID_TEST__1007), result.getMessage());
        verify(invoiceRepository).existsByQuotationId(QUOTATION_ID_TEST__1007);
    }

}
