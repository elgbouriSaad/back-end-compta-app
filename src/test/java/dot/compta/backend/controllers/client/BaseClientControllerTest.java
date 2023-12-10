package dot.compta.backend.controllers.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.utils.TestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static dot.compta.backend.utils.TestUtils.*;

class BaseClientControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected RequestClientDto buildRequestClientDto() {
        return buildRequestClientDto("");
    }


    protected RequestClientDto buildRequestClientDto(String updateSuffix) {
        return RequestClientDto.builder()
                .rc(CLIENT_RC_TEST__102)
                .companyName(CLIENT_SOCIAL_PURPOSE_TEST + updateSuffix)
                .email(CLIENT_EMAIL_TEST + updateSuffix)
                .mobilePhone(CLIENT_MOBILE_PHONE_TEST + updateSuffix)
                .phone(CLIENT_PHONE_TEST + updateSuffix)
                .fax(CLIENT_FAX_TEST + updateSuffix)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(CLIENT_POSTAL_CODE_TEST__202)
                        .primaryAddress(CLIENT_PRIMARY_ADDRESS_TEST + updateSuffix)
                        .secondaryAddress(CLIENT_SECONDARY_ADDRESS_TEST + updateSuffix)
                        .build())
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
    }
    
    protected UpdateClientDto buildUpdateClientDto() {
        return buildUpdateClientDto("");
    }


    protected UpdateClientDto buildUpdateClientDto(String updateSuffix) {
        return UpdateClientDto.builder()
                .rc(CLIENT_RC_TEST__102)
                .companyName(CLIENT_SOCIAL_PURPOSE_TEST + updateSuffix)
                .email(CLIENT_EMAIL_TEST + updateSuffix)
                .mobilePhone(CLIENT_MOBILE_PHONE_TEST + updateSuffix)
                .phone(CLIENT_PHONE_TEST + updateSuffix)
                .fax(CLIENT_FAX_TEST + updateSuffix)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(CLIENT_POSTAL_CODE_TEST__202)
                        .primaryAddress(CLIENT_PRIMARY_ADDRESS_TEST + updateSuffix)
                        .secondaryAddress(CLIENT_SECONDARY_ADDRESS_TEST + updateSuffix)
                        .build())
                .build();
    }

    protected ResponseClientDto buildResponseClientDto() {
        return ResponseClientDto.builder()
                .id(CLIENT_ID_TEST__1005)
                .rc(CLIENT_RC_TEST__102)
                .companyName(TestUtils.CLIENT_SOCIAL_PURPOSE_TEST)
                .email(TestUtils.CLIENT_EMAIL_TEST)
                .mobilePhone(TestUtils.CLIENT_MOBILE_PHONE_TEST)
                .phone(TestUtils.CLIENT_PHONE_TEST)
                .fax(TestUtils.CLIENT_FAX_TEST)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(CLIENT_POSTAL_CODE_TEST__202)
                        .primaryAddress(TestUtils.CLIENT_PRIMARY_ADDRESS_TEST)
                        .secondaryAddress(TestUtils.CLIENT_SECONDARY_ADDRESS_TEST)
                        .build())
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
    }

}
