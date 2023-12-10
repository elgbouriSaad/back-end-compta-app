package dot.compta.backend.controllers.city;

import com.fasterxml.jackson.databind.ObjectMapper;
import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import org.springframework.test.web.servlet.MockMvc;

import static dot.compta.backend.utils.TestUtils.COUNTRY_NAME_TEST__MAROC;

class BaseCityControllerTest {

    protected static final int CITY_ID_TEST__1002 = 1002;
    protected static final String CITY_NAME_TEST__HOCEIMA = "Hoceima";
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected RequestCityDto buildRequestCityDto(String suffix) {
        return RequestCityDto.builder()
                .name(CITY_NAME_TEST__HOCEIMA + suffix)
                .country(COUNTRY_NAME_TEST__MAROC)
                .build();
    }

    protected ResponseCityDto buildResponseCityDto() {
        return ResponseCityDto.builder()
                .id(CITY_ID_TEST__1002)
                .name(CITY_NAME_TEST__HOCEIMA)
                .country(COUNTRY_NAME_TEST__MAROC)
                .build();
    }

}
