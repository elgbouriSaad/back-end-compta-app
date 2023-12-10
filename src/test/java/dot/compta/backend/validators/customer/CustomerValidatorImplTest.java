package dot.compta.backend.validators.customer;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.customer.CustomerRepository;
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

public class CustomerValidatorImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerValidatorImpl customerValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void when_customer_not_in_db_throw_CustomNotFoundException_validateExistsTest() {
        // GIVEN
        when(customerRepository.existsById(CUSTOMER_ID_TEST__1004)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> customerValidator.validateExists(CUSTOMER_ID_TEST__1004));

        // THEN
        assertEquals(String.format(ValidationConstants.CUSTOMER_NOT_FOUND, CUSTOMER_ID_TEST__1004), result.getMessage());
        verify(customerRepository).existsById(CUSTOMER_ID_TEST__1004);
    }

    @Test
    void when_customer_deleted_throw_CustomDeletedException_validateNotDeletedTest() {
        // GIVEN
        when(customerRepository.existsByIdAndDeletedTrue(CUSTOMER_ID_TEST__1004)).thenReturn(true);

        // WHEN
        CustomDeletedException result = assertThrows(CustomDeletedException.class,
                () -> customerValidator.validateNotDeleted(CUSTOMER_ID_TEST__1004));

        // THEN
        assertEquals(String.format(ValidationConstants.CUSTOMER_DELETED, CUSTOMER_ID_TEST__1004), result.getMessage());
        verify(customerRepository).existsByIdAndDeletedTrue(CUSTOMER_ID_TEST__1004);
    }

}
