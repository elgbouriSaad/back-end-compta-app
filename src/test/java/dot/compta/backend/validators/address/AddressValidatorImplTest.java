package dot.compta.backend.validators.address;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;
import dot.compta.backend.repositories.city.CityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

class AddressValidatorImplTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private AddressValidatorImpl addressValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    void when_city_not_in_db_throw_CustomBadInputException_validateTest() {
        // GIVEN
        AddressDto addressDto = mock(AddressDto.class);
        when(addressDto.getCity()).thenReturn(CITY_NAME_TEST__CASABLANCA);
        when(cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA)).thenReturn(Optional.empty());

        // WHEN
        CustomBadInputException result = assertThrows(CustomBadInputException.class,
                () -> addressValidator.validate(addressDto));

        // THEN
        assertEquals(String.format(ValidationConstants.CITY_NOT_CORRECT_OR_NOT_SUPPORTED_MSG, CITY_NAME_TEST__CASABLANCA), result.getMessage());
        verify(cityRepository).findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
    }

    @Test
    void when_city_in_db_but_country_does_not_match_throw_CustomBadInputException_validateTest() {
        // GIVEN
        AddressDto addressDto = mock(AddressDto.class);
        when(addressDto.getCity()).thenReturn(CITY_NAME_TEST__CASABLANCA);
        when(addressDto.getCountry()).thenReturn(COUNTRY_NAME_TEST__MAROC);
        City city = mock(City.class);
        Country country = mock(Country.class);
        when(city.getCountry()).thenReturn(country);
        when(country.getName()).thenReturn(COUNTRY_NAME_TEST__FRANCE);
        when(cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA)).thenReturn(Optional.of(city));

        // WHEN
        CustomBadInputException result = assertThrows(CustomBadInputException.class,
                () -> addressValidator.validate(addressDto));

        // THEN
        assertEquals(String.format(ValidationConstants.COUNTRY_AND_CITY_DONT_MATCH_MSG, COUNTRY_NAME_TEST__MAROC, CITY_NAME_TEST__CASABLANCA), result.getMessage());
        verify(cityRepository).findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
    }

    @Test
    void when_city_in_db_country_match_no_exception_thrown_validateTest() {
        // GIVEN
        AddressDto addressDto = mock(AddressDto.class);
        when(addressDto.getCity()).thenReturn(CITY_NAME_TEST__CASABLANCA);
        when(addressDto.getCountry()).thenReturn(COUNTRY_NAME_TEST__MAROC);
        City city = mock(City.class);
        Country country = mock(Country.class);
        when(city.getCountry()).thenReturn(country);
        when(country.getName()).thenReturn(COUNTRY_NAME_TEST__MAROC);
        when(cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA)).thenReturn(Optional.of(city));

        // WHEN
        assertDoesNotThrow(() -> addressValidator.validate(addressDto));

        // THEN
        verify(cityRepository).findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
    }
}