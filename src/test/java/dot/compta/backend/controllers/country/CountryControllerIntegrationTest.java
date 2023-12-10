package dot.compta.backend.controllers.country;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.models.country.Country;
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

import static dot.compta.backend.utils.TestUtils.MULTIPLE_LIST_ELEMENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ActiveProfiles("dev-h2")
public class CountryControllerIntegrationTest extends BaseCountryControllerTest implements BaseControllerIntegrationTest {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        assertDbState();
        mvc = standaloneSetup(countryController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }

    @Test
    void successful_country_creation_process_test() throws Exception {
        createCountryUsingEndpoint("");

        assertEquals(2, countryRepository.count());
        Optional<Country> country = countryRepository.findOneByNameIgnoreCase(COUNTRY_NAME_TEST__FRANCE);
        assertEquals(COUNTRY_NAME_TEST__FRANCE, country.get().getName());

        removeAddedElement(country.get());
    }

    @Test
    void successful_multiple_country_creation_process_test() throws Exception {
        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            createCountryUsingEndpoint(suffix);
        }

        assertEquals(4, countryRepository.count());

        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            Optional<Country> optionalCountry = countryRepository.findOneByNameIgnoreCase(COUNTRY_NAME_TEST__FRANCE + suffix);
            assertTrue(optionalCountry.isPresent());
            removeAddedElement(optionalCountry.get());
        }
    }

    @Test
    void successful_get_countries_process_test() throws Exception {
        mvc.perform(get(APIConstants.COUNTRIES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CountryControllerIntegration_getCountries.json"), true));
    }

    private void createCountryUsingEndpoint(String suffix) throws Exception {
        RequestCountryDto requestCountryDto = buildRequestCountryDto(suffix);
        String content = objectMapper.writeValueAsString(requestCountryDto);
        mvc.perform(post(APIConstants.COUNTRIES_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    private void removeAddedElement(Country country) {
        TestUtils.removeAddedElement(countryRepository, country.getId());
    }


    @Override
    public void assertDbState() {
        assertCountryTableInitialState(countryRepository);
    }
}
