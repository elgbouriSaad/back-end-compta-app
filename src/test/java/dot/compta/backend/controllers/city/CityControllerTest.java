package dot.compta.backend.controllers.city;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import dot.compta.backend.services.city.CityService;
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


public class CityControllerTest extends BaseCityControllerTest {

	@Mock
	private CityService cityService;
	@InjectMocks
	private CityController cityController;

	@BeforeEach
	void setUp() {
		openMocks(this);
		mvc = standaloneSetup(cityController).build();
	}

	@AfterEach
	void tearDown() {
		verifyNoMoreInteractions(cityService);
	}

	@Test
	public void testGetCities() throws Exception {
		ResponseCityDto responseCityDto = buildResponseCityDto();
		List<ResponseCityDto> cities = Collections.singletonList(responseCityDto);
		given(cityService.getCities()).willReturn(cities);
		mvc.perform(MockMvcRequestBuilders.get(APIConstants.CITIES_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().json(TestUtils.getJsonContent("CityController_getCities.json"), true));
		verify(cityService).getCities();
	}

	@Test
	public void testCreateCity() throws Exception {
		RequestCityDto requestCityDto = buildRequestCityDto("");
		String content = objectMapper.writeValueAsString(requestCityDto);
		mvc.perform(MockMvcRequestBuilders.post(APIConstants.CITIES_URL)
						.content(content)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(MockMvcResultMatchers.status().isCreated());
		verify(cityService).createCity(requestCityDto);
	}

}
