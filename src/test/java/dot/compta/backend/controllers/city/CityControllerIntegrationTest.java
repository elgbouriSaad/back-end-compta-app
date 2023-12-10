package dot.compta.backend.controllers.city;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.models.city.City;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static dot.compta.backend.utils.TestUtils.COUNTRY_NAME_TEST__MAROC;
import static dot.compta.backend.utils.TestUtils.MULTIPLE_LIST_ELEMENTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ActiveProfiles("dev-h2")
public class CityControllerIntegrationTest extends BaseCityControllerTest implements BaseControllerIntegrationTest {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityController cityController;

    @BeforeEach
    void setUp() {
        assertDbState();
        assertFalse(cityRepository.existsByNameIgnoreCase(CITY_NAME_TEST__HOCEIMA));
        mvc = standaloneSetup(cityController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }

    @Test
    void successful_city_creation_process_test() throws Exception {
        createCityUsingEndpoint("");

        assertEquals(INITIAL_CITIES_COUNT + 1, cityRepository.count());
        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());

        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__HOCEIMA);
        assertEquals(CITY_NAME_TEST__HOCEIMA, city.get().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, city.get().getCountry().getName());

        removeAddedElement(city.get());
    }

    @Test
    void successful_multiple_city_creation_process_test() throws Exception {
        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            createCityUsingEndpoint(suffix);
        }

        assertEquals(INITIAL_CITIES_COUNT + 3, cityRepository.count());
        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());

        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            Optional<City> optionalCity = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__HOCEIMA + suffix);
            assertTrue(optionalCity.isPresent());
            removeAddedElement(optionalCity.get());
        }
    }

    @Test
    void successful_get_cities_process_test() throws Exception {
        mvc.perform(get(APIConstants.CITIES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CityControllerIntegration_getCities.json"), true));
    }

    private void createCityUsingEndpoint(String suffix) throws Exception {
        RequestCityDto requestCityDto = buildRequestCityDto(suffix);
        String content = objectMapper.writeValueAsString(requestCityDto);
        mvc.perform(post(APIConstants.CITIES_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    private void removeAddedElement(City city) {
        TestUtils.removeAddedElement(cityRepository, city.getId());
    }

    @Override
    public void assertDbState() {
        assertCountryTableInitialState(countryRepository);
        assertCityTableInitialState(cityRepository);
    }
}
