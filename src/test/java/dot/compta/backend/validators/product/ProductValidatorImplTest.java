package dot.compta.backend.validators.product;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.product.ProductRepository;
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

public class ProductValidatorImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductValidatorImpl productValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void when_product_in_db_throw_CustomBadInputException_validateNotExistByReferenceAndCustomerIdTest() {
        // GIVEN
        when(productRepository.existsByReferenceIgnoreCaseAndCustomerIdAndDeletedFalse(PRODUCT_REFERENCE_TEST, CUSTOMER_ID_TEST__1004)).thenReturn(true);

        // WHEN
        CustomBadInputException result = assertThrows(CustomBadInputException.class,
                () -> productValidator.validateNotExistByReferenceAndCustomerId(PRODUCT_REFERENCE_TEST, CUSTOMER_ID_TEST__1004));

        // THEN
        assertEquals(String.format(ValidationConstants.PRODUCT_ALREADY_EXISTS, PRODUCT_REFERENCE_TEST, CUSTOMER_ID_TEST__1004), result.getMessage());
        verify(productRepository).existsByReferenceIgnoreCaseAndCustomerIdAndDeletedFalse(PRODUCT_REFERENCE_TEST, CUSTOMER_ID_TEST__1004);
    }

    @Test
    void when_product_not_in_db_throw_CustomNotFoundException_validateExistsTest() {
        // GIVEN
        when(productRepository.existsById(PRODUCT_ID_TEST__1006)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> productValidator.validateExists(PRODUCT_ID_TEST__1006));

        // THEN
        assertEquals(String.format(ValidationConstants.PRODUCT_NOT_FOUND, PRODUCT_ID_TEST__1006), result.getMessage());
        verify(productRepository).existsById(PRODUCT_ID_TEST__1006);
    }

    @Test
    void when_product_deleted_throw_CustomDeletedException_validateNotDeletedTest() {
        // GIVEN
        when(productRepository.existsByIdAndDeletedTrue(PRODUCT_ID_TEST__1006)).thenReturn(true);

        // WHEN
        CustomDeletedException result = assertThrows(CustomDeletedException.class,
                () -> productValidator.validateNotDeleted(PRODUCT_ID_TEST__1006));

        // THEN
        assertEquals(String.format(ValidationConstants.PRODUCT_DELETED, PRODUCT_ID_TEST__1006), result.getMessage());
        verify(productRepository).existsByIdAndDeletedTrue(PRODUCT_ID_TEST__1006);
    }

}
