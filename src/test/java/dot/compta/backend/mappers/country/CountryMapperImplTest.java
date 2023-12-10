package dot.compta.backend.mappers.country;

import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import dot.compta.backend.models.country.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static dot.compta.backend.utils.TestUtils.*;

public class CountryMapperImplTest {

    private CountryMapperImpl countryMapper;

    @BeforeEach
    void setUp() {
        countryMapper = new CountryMapperImpl(new ModelMapper());
    }

    @Test
    void mapToCountryDtoTest() {
        // GIVEN
        Country country = Country.builder()
                .id(COUNTRY_ID_TEST__1000)
                .name(COUNTRY_NAME_TEST__MAROC)
                .build();

        // WHEN
        ResponseCountryDto result = countryMapper.mapToResponseCountryDto(country);

        // THEN
        assertNotNull(result);
        assertEquals(COUNTRY_ID_TEST__1000, result.getId());
        assertEquals(COUNTRY_NAME_TEST__MAROC, result.getName());
    }

    @Test
    void mapToCountry() {
        // GIVEN
        RequestCountryDto country = RequestCountryDto.builder()
                .name(COUNTRY_NAME_TEST__MAROC)
                .build();

        // WHEN
        Country result = countryMapper.mapToCountry(country);

        // THEN
        assertNotNull(result);
        assertEquals(COUNTRY_NAME_TEST__MAROC, result.getName());
    }

}
