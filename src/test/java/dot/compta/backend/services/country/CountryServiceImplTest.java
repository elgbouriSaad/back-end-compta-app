package dot.compta.backend.services.country;

import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import dot.compta.backend.mappers.country.CountryMapper;
import dot.compta.backend.models.country.Country;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.validators.country.CountryValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @Mock
    private CountryValidator countryValidator;

    @InjectMocks
    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                countryRepository,
                countryMapper,
                countryValidator
        );
    }

    @Test
    public void testGetCountries() {
        // GIVEN
        Country country1 = mock(Country.class);
        Country country2 = mock(Country.class);
        List<Country> mockCountries = Arrays.asList(country1, country2);
        when(countryRepository.findAll()).thenReturn(mockCountries);
        ResponseCountryDto countryDto1 = mock(ResponseCountryDto.class);
        ResponseCountryDto countryDto2 = mock(ResponseCountryDto.class);
        when(countryMapper.mapToResponseCountryDto(country1)).thenReturn(countryDto1);
        when(countryMapper.mapToResponseCountryDto(country2)).thenReturn(countryDto2);

        // WHEN
        List<ResponseCountryDto> result = countryService.getCountries();

        // THEN
        assertEquals(2, result.size());
        assertEquals(countryDto1, result.get(0));
        assertEquals(countryDto2, result.get(1));

        verify(countryRepository).findAll();
        verify(countryMapper).mapToResponseCountryDto(country1);
        verify(countryMapper).mapToResponseCountryDto(country2);
    }

    @Test
    public void testCreateCountry() {
        // GIVEN
        RequestCountryDto countryDto = mock(RequestCountryDto.class);
        Country country = Country.builder()
                .id(1)
                .name(COUNTRY_NAME_TEST__MAROC)
                .build();
        when(countryMapper.mapToCountry(countryDto)).thenReturn(country);

        // WHEN
        countryService.createCountry(countryDto);

        // THEN
        verify(countryRepository).save(country);
        verify(countryMapper).mapToCountry(countryDto);
        verify(countryValidator).validateNotExist(null);
    }

}
