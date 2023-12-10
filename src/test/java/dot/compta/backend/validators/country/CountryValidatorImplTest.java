package dot.compta.backend.validators.country;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.country.CountryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static dot.compta.backend.utils.TestUtils.COUNTRY_NAME_TEST__FRANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CountryValidatorImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryValidatorImpl countryValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(countryRepository);
    }

    @Test
    void when_country_in_db_throw_CustomBadInputException_validateNotExistTest() {
        // GIVEN
        when(countryRepository.existsByNameIgnoreCase(COUNTRY_NAME_TEST__FRANCE)).thenReturn(true);

        // WHEN
        CustomBadInputException result = assertThrows(CustomBadInputException.class,
                () -> countryValidator.validateNotExist(COUNTRY_NAME_TEST__FRANCE));

        // THEN
        assertEquals(String.format(ValidationConstants.COUNTRY_ALREADY_EXISTS, COUNTRY_NAME_TEST__FRANCE), result.getMessage());
        verify(countryRepository).existsByNameIgnoreCase(COUNTRY_NAME_TEST__FRANCE);
    }

    @Test
    void when_country_not_in_db_throw_CustomNotFoundException_validateExistTest() {
        // GIVEN
        when(countryRepository.existsByNameIgnoreCase(COUNTRY_NAME_TEST__FRANCE)).thenReturn(false);

        // WHEN
        CustomNotFoundException result = assertThrows(CustomNotFoundException.class,
                () -> countryValidator.validateExist(COUNTRY_NAME_TEST__FRANCE));

        // THEN
        assertEquals(String.format(ValidationConstants.COUNTRY_NOT_EXISTS, COUNTRY_NAME_TEST__FRANCE), result.getMessage());
        verify(countryRepository).existsByNameIgnoreCase(COUNTRY_NAME_TEST__FRANCE);
    }
}
