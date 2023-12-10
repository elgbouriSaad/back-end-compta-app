package dot.compta.backend.controllers.accountant;

import com.fasterxml.jackson.databind.ObjectMapper;
import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.dtos.address.AddressDto;
import org.springframework.test.web.servlet.MockMvc;

import static dot.compta.backend.utils.TestUtils.*;

class BaseAccountantControllerTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected RequestAccountantDto buildRequestAccountantDto() {
        return buildRequestAccountantDto("");
    }

    protected RequestAccountantDto buildRequestAccountantDto(String updateSuffix) {
        return RequestAccountantDto.builder()
                .rc(ACCOUNTANT_RC_TEST__100)
                .companyName(ACCOUNTANT_SOCIAL_PURPOSE_TEST + updateSuffix)
                .email(ACCOUNTANT_EMAIL_TEST + updateSuffix)
                .mobilePhone(ACCOUNTANT_MOBILE_PHONE_TEST + updateSuffix)
                .phone(ACCOUNTANT_PHONE_TEST + updateSuffix)
                .fax(ACCOUNTANT_FAX_TEST + updateSuffix)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(ACCOUNTANT_POSTAL_CODE_TEST__200)
                        .primaryAddress(ACCOUNTANT_PRIMARY_ADDRESS_TEST + updateSuffix)
                        .secondaryAddress(ACCOUNTANT_SECONDARY_ADDRESS_TEST + updateSuffix)
                        .build())
                .build();
    }

    protected ResponseAccountantDto buildResponseAccountantDto() {
        return ResponseAccountantDto.builder()
                .id(ACCOUNTANT_ID_TEST__1003)
                .rc(ACCOUNTANT_RC_TEST__100)
                .companyName(ACCOUNTANT_SOCIAL_PURPOSE_TEST)
                .email(ACCOUNTANT_EMAIL_TEST)
                .mobilePhone(ACCOUNTANT_MOBILE_PHONE_TEST)
                .phone(ACCOUNTANT_PHONE_TEST)
                .fax(ACCOUNTANT_FAX_TEST)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(ACCOUNTANT_POSTAL_CODE_TEST__200)
                        .primaryAddress(ACCOUNTANT_PRIMARY_ADDRESS_TEST)
                        .secondaryAddress(ACCOUNTANT_SECONDARY_ADDRESS_TEST)
                        .build())
                .build();
    }
}