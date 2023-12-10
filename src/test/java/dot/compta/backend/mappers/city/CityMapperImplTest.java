package dot.compta.backend.mappers.city;

import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static dot.compta.backend.utils.TestUtils.*;

public class CityMapperImplTest {

    private CityMapperImpl cityMapper;

    @BeforeEach
    void setUp() {
        cityMapper = new CityMapperImpl(new ModelMapper());
    }

    @Test
    void mapToCityDtoTest() {
        // GIVEN
        Country country = Country.builder()
                .id(COUNTRY_ID_TEST__1000)
                .name(COUNTRY_NAME_TEST__MAROC)
                .build();
        City city = City.builder()
                .id(CITY_ID_TEST__1001)
                .name(CITY_NAME_TEST__RABAT)
                .country(country)
                .build();

        // WHEN
        ResponseCityDto result = cityMapper.mapToResponseCityDto(city);

        // THEN
        assertNotNull(result);
        assertEquals(CITY_ID_TEST__1001, result.getId());
        assertEquals(CITY_NAME_TEST__RABAT, result.getName());
        assertEquals(country.getName(), result.getCountry());
    }

    @Test
    void mapToCityTest() {
        // GIVEN
        Country country = Country.builder()
        		.id(COUNTRY_ID_TEST__1000)
        		.name(COUNTRY_NAME_TEST__MAROC)
        		.build();
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name(CITY_NAME_TEST__RABAT)
                .country(COUNTRY_NAME_TEST__MAROC)
                .build();

        // WHEN
        City result = cityMapper.mapToCity(requestCityDto, country);

        // THEN
        assertNotNull(result);
        assertEquals(CITY_NAME_TEST__RABAT, result.getName());
        assertEquals(country, result.getCountry());
    }
}
