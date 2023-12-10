package dot.compta.backend.services.city;

import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import dot.compta.backend.mappers.city.CityMapper;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.validators.city.CityValidator;
import dot.compta.backend.validators.country.CountryValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

public class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @Mock
    private CountryValidator countryValidator;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CityValidator cityValidator;

    @InjectMocks
    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                cityRepository,
                cityMapper,
                countryValidator,
                countryRepository
        );
    }

    @Test
    public void testGetCities() {
        // GIVEN
        City city1 = mock(City.class);
        City city2 = mock(City.class);
        List<City> mockCities = Arrays.asList(city1, city2);
        when(cityRepository.findAll()).thenReturn(mockCities);
        ResponseCityDto cityDto1 = mock(ResponseCityDto.class);
        ResponseCityDto cityDto2 = mock(ResponseCityDto.class);
        when(cityMapper.mapToResponseCityDto(city1)).thenReturn(cityDto1);
        when(cityMapper.mapToResponseCityDto(city2)).thenReturn(cityDto2);

        // WHEN
        List<ResponseCityDto> result = cityService.getCities();

        // THEN
        assertEquals(2, result.size());
        assertEquals(cityDto1, result.get(0));
        assertEquals(cityDto2, result.get(1));

        verify(cityRepository).findAll();
        verify(cityMapper).mapToResponseCityDto(city1);
        verify(cityMapper).mapToResponseCityDto(city2);
    }

    @Test
    public void testCreateCity() {
        // GIVEN
        RequestCityDto cityDto = RequestCityDto.builder()
                .name(CITY_NAME_TEST__CASABLANCA)
                .country(COUNTRY_NAME_TEST__MAROC)
                .build();
        Country country = mock(Country.class);
        City city = mock(City.class);
        when(countryRepository.findOneByNameIgnoreCase(COUNTRY_NAME_TEST__MAROC)).thenReturn(Optional.of(country));
        when(cityMapper.mapToCity(cityDto, country)).thenReturn(city);

        // WHEN
        cityService.createCity(cityDto);

        // THEN
        verify(countryValidator).validateExist(COUNTRY_NAME_TEST__MAROC);
        verify(countryRepository).findOneByNameIgnoreCase(COUNTRY_NAME_TEST__MAROC);
        verify(cityMapper).mapToCity(cityDto, country);
        verify(cityRepository).save(city);
        verify(cityValidator).validateNotExist(CITY_NAME_TEST__CASABLANCA);
    }

}
