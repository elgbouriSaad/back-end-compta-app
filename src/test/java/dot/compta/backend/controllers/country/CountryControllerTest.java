package dot.compta.backend.controllers.country;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import dot.compta.backend.services.country.CountryService;
import dot.compta.backend.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CountryControllerTest extends BaseCountryControllerTest {

    @Mock
    private CountryService countryService;
    @InjectMocks
    private CountryController countryController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(countryController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(countryService);
    }

    @Test
    public void testGetCountries() throws Exception {
        ResponseCountryDto responseCountryDto = buildResponseCountryDto();
        List<ResponseCountryDto> countries = Collections.singletonList(responseCountryDto);
        given(countryService.getCountries()).willReturn(countries);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.COUNTRIES_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CountryController_getCountries.json"), true));
        verify(countryService).getCountries();
    }

    @Test
    public void testCreateCountry() throws Exception {
        RequestCountryDto requestCountryDto = buildRequestCountryDto("");
        String content = objectMapper.writeValueAsString(requestCountryDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.COUNTRIES_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(countryService).createCountry(requestCountryDto);
    }

}
