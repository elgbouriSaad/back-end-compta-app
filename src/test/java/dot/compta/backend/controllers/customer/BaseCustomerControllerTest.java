package dot.compta.backend.controllers.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;

import org.springframework.test.web.servlet.MockMvc;

import static dot.compta.backend.utils.TestUtils.*;

class BaseCustomerControllerTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected RequestCustomerDto buildRequestCustomerDto() {
        return buildRequestCustomerDto(ACCOUNTANT_ID_TEST__1003, "");
    }

    protected RequestCustomerDto buildRequestCustomerDto(int accountantId, String updateSuffix) {
        return RequestCustomerDto.builder()
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST + updateSuffix)
                .email(CUSTOMER_EMAIL_TEST + updateSuffix)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST + updateSuffix)
                .phone(CUSTOMER_PHONE_TEST + updateSuffix)
                .fax(CUSTOMER_FAX_TEST + updateSuffix)
                .accountantId(accountantId)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(CUSTOMER_POSTAL_CODE_TEST__201)
                        .primaryAddress(CUSTOMER_PRIMARY_ADDRESS_TEST + updateSuffix)
                        .secondaryAddress(CUSTOMER_SECONDARY_ADDRESS_TEST + updateSuffix)
                        .build())
                .build();
    }
    
    protected UpdateCustomerDto buildUpdateCustomerDto() {
        return buildUpdateCustomerDto("");
    }
    
    protected UpdateCustomerDto buildUpdateCustomerDto(String updateSuffix) {
        return UpdateCustomerDto.builder()
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST + updateSuffix)
                .email(CUSTOMER_EMAIL_TEST + updateSuffix)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST + updateSuffix)
                .phone(CUSTOMER_PHONE_TEST + updateSuffix)
                .fax(CUSTOMER_FAX_TEST + updateSuffix)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(CUSTOMER_POSTAL_CODE_TEST__201)
                        .primaryAddress(CUSTOMER_PRIMARY_ADDRESS_TEST + updateSuffix)
                        .secondaryAddress(CUSTOMER_SECONDARY_ADDRESS_TEST + updateSuffix)
                        .build())
                .build();
    }

    protected ResponseCustomerDto buildResponseCustomerDto() {
        return ResponseCustomerDto.builder()
                .id(CUSTOMER_ID_TEST__1004)
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST)
                .email(CUSTOMER_EMAIL_TEST)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST)
                .phone(CUSTOMER_PHONE_TEST)
                .fax(CUSTOMER_FAX_TEST)
                .accountantId(ACCOUNTANT_ID_TEST__1003)
                .address(AddressDto.builder()
                        .country(COUNTRY_NAME_TEST__MAROC)
                        .city(CITY_NAME_TEST__CASABLANCA)
                        .postalCode(CUSTOMER_POSTAL_CODE_TEST__201)
                        .primaryAddress(CUSTOMER_PRIMARY_ADDRESS_TEST)
                        .secondaryAddress(CUSTOMER_SECONDARY_ADDRESS_TEST)
                        .build())
                .build();
    }

}
