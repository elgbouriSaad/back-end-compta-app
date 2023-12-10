package dot.compta.backend.validators.accountant;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.accountant.AccountantRepository;
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

public class AccountantValidatorImplTest {

    @Mock
    private AccountantRepository accountantRepository;

    @InjectMocks
    private AccountantValidatorImpl accountantValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(accountantRepository);
    }

    @Test
    void when_accountant_not_in_db_throw_CustomNotFoundException_validateExistsTest() {
        // GIVEN
        when(accountantRepository.existsById(ACCOUNTANT_ID_TEST__1003)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> accountantValidator.validateExists(ACCOUNTANT_ID_TEST__1003));

        // THEN
        assertEquals(String.format(ValidationConstants.ACCOUNTANT_NOT_FOUND, ACCOUNTANT_ID_TEST__1003), result.getMessage());
        verify(accountantRepository).existsById(ACCOUNTANT_ID_TEST__1003);
    }

    @Test
    void when_accountant_deleted_throw_CustomDeletedException_validateNotDeletedTest() {
        // GIVEN
        when(accountantRepository.existsByIdAndDeletedTrue(ACCOUNTANT_ID_TEST__1003)).thenReturn(true);

        // WHEN
        CustomDeletedException result = assertThrows(CustomDeletedException.class,
                () -> accountantValidator.validateNotDeleted(ACCOUNTANT_ID_TEST__1003));

        // THEN
        assertEquals(String.format(ValidationConstants.ACCOUNTANT_DELETED, ACCOUNTANT_ID_TEST__1003), result.getMessage());
        verify(accountantRepository).existsByIdAndDeletedTrue(ACCOUNTANT_ID_TEST__1003);
    }

}
