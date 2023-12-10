package dot.compta.backend.validators.city;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.repositories.city.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static dot.compta.backend.utils.TestUtils.CITY_NAME_TEST__CASABLANCA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CityValidatorImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityValidatorImpl cityValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    void when_city_in_db_throw_CustomBadInputException_validateNotExistTest() {
        // GIVEN
        when(cityRepository.existsByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA)).thenReturn(true);

        // WHEN
        CustomBadInputException result = assertThrows(CustomBadInputException.class,
                () -> cityValidator.validateNotExist(CITY_NAME_TEST__CASABLANCA));

        // THEN
        assertEquals(String.format(ValidationConstants.CITY_ALREADY_EXISTS, CITY_NAME_TEST__CASABLANCA), result.getMessage());
        verify(cityRepository).existsByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
    }

}
