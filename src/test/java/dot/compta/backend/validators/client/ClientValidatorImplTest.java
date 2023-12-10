package dot.compta.backend.validators.client;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomDeletedException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.client.ClientRepository;
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

public class ClientValidatorImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientValidatorImpl clientValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(clientRepository);
    }

    @Test
    void when_client_not_in_db_throw_CustomNotFoundException_validateExistsTest() {
        // GIVEN
        when(clientRepository.existsById(CLIENT_ID_TEST__1005)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> clientValidator.validateExists(CLIENT_ID_TEST__1005));

        // THEN
        assertEquals(String.format(ValidationConstants.CLIENT_NOT_FOUND, CLIENT_ID_TEST__1005), result.getMessage());
        verify(clientRepository).existsById(CLIENT_ID_TEST__1005);
    }

    @Test
    void when_client_deleted_throw_CustomDeletedException_validateNotDeletedTest() {
        // GIVEN
        when(clientRepository.existsByIdAndDeletedTrue(CLIENT_ID_TEST__1005)).thenReturn(true);

        // WHEN
        CustomDeletedException result = assertThrows(CustomDeletedException.class,
                () -> clientValidator.validateNotDeleted(CLIENT_ID_TEST__1005));

        // THEN
        assertEquals(String.format(ValidationConstants.CLIENT_DELETED, CLIENT_ID_TEST__1005), result.getMessage());
        verify(clientRepository).existsByIdAndDeletedTrue(CLIENT_ID_TEST__1005);
    }

}
