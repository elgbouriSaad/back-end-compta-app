package dot.compta.backend.mappers.address;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static dot.compta.backend.utils.TestUtils.*;

class AddressMapperImplTest {

    private AddressMapperImpl addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = new AddressMapperImpl(new ModelMapper());
    }

    @Test
    void mapToAddressTest() {
        // GIVEN
        AddressDto addressDto = AddressDto.builder()
                .primaryAddress(ADDRESS_PRIMARY_ADDRESS_TEST)
                .secondaryAddress(ADDRESS_SECONDARY_ADDRESS_TEST)
                .city(CITY_NAME_TEST__CASABLANCA)
                .country(COUNTRY_NAME_TEST__MAROC)
                .postalCode(ADDRESS_POSTAL_CODE_TEST__204)
                .build();
        City city = mock(City.class);

        // WHEN
        AddressModel result = addressMapper.mapToAddressModel(addressDto, city);

        // THEN
        assertNotNull(result);
        assertEquals(ADDRESS_PRIMARY_ADDRESS_TEST, result.getPrimaryAddress());
        assertEquals(ADDRESS_SECONDARY_ADDRESS_TEST, result.getSecondaryAddress());
        assertEquals(ADDRESS_POSTAL_CODE_TEST__204, result.getPostalCode());
        assertEquals(city, result.getCity());
    }

    @Test
    void mapToAddressDtoTest() {
        // GIVEN
        Country country = new Country(
                1,
                COUNTRY_NAME_TEST__MAROC);
        City city = new City(
                1,
                CITY_NAME_TEST__CASABLANCA,
                country);
        AddressModel addressModel = AddressModel.builder()
                .id(ADDRESS_ID_TEST__1002)
                .primaryAddress(ADDRESS_PRIMARY_ADDRESS_TEST)
                .secondaryAddress(ADDRESS_SECONDARY_ADDRESS_TEST)
                .postalCode(ADDRESS_POSTAL_CODE_TEST__204)
                .city(city)
                .build();

        // WHEN
        AddressDto result = addressMapper.mapToAddressDto(addressModel);

        // THEN
        assertNotNull(result);
        assertEquals(ADDRESS_PRIMARY_ADDRESS_TEST, result.getPrimaryAddress());
        assertEquals(ADDRESS_SECONDARY_ADDRESS_TEST, result.getSecondaryAddress());
        assertEquals(ADDRESS_POSTAL_CODE_TEST__204, result.getPostalCode());
        assertEquals(CITY_NAME_TEST__CASABLANCA, result.getCity());
        assertEquals(COUNTRY_NAME_TEST__MAROC, result.getCountry());
    }
}