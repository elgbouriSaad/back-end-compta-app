package dot.compta.backend.controllers.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import org.springframework.test.web.servlet.MockMvc;

class BaseCountryControllerTest {

    protected static final int COUNTRY_ID_TEST__1001 = 1001;
    protected static final String COUNTRY_NAME_TEST__FRANCE = "France";
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected ResponseCountryDto buildResponseCountryDto() {
        return ResponseCountryDto.builder()
                .id(COUNTRY_ID_TEST__1001)
                .name(COUNTRY_NAME_TEST__FRANCE)
                .build();
    }

    protected RequestCountryDto buildRequestCountryDto(String suffix) {
        return RequestCountryDto.builder()
                .name(COUNTRY_NAME_TEST__FRANCE + suffix)
                .build();
    }

}
