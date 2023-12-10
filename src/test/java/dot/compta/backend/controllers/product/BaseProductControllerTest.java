package dot.compta.backend.controllers.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;
import dot.compta.backend.utils.TestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static dot.compta.backend.utils.TestUtils.*;

class BaseProductControllerTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected RequestProductDto buildRequestProductDto() {
        return RequestProductDto.builder()
                .label(PRODUCT_LABEL_TEST)
                .reference(PRODUCT_REFERENCE_TEST)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(PRODUCT_UNITY_TEST)
                .qualification(PRODUCT_QUALIFICATION_TEST)
                .tax(PRODUCT_TAX_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
    }
    
    protected RequestProductDto buildRequestProductDto(String suffix) {
        return RequestProductDto.builder()
                .label(PRODUCT_LABEL_TEST + suffix)
                .reference(PRODUCT_REFERENCE_TEST + suffix)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(PRODUCT_UNITY_TEST + suffix)
                .qualification(PRODUCT_QUALIFICATION_TEST + suffix)
                .tax(PRODUCT_TAX_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
    }
    
    protected UpdateProductDto buildUpdateProductDto(String suffix) {
        return UpdateProductDto.builder()
                .label(PRODUCT_LABEL_TEST + suffix)
                .reference(PRODUCT_REFERENCE_TEST + suffix)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(PRODUCT_UNITY_TEST + suffix)
                .qualification(PRODUCT_QUALIFICATION_TEST + suffix)
                .tax(PRODUCT_TAX_TEST)
                .build();
    }

    protected ResponseProductDto buildResponseProductDto() {
        return ResponseProductDto.builder()
                .id(PRODUCT_ID_TEST__1006)
                .label(PRODUCT_LABEL_TEST)
                .reference(PRODUCT_REFERENCE_TEST)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(TestUtils.PRODUCT_UNITY_TEST)
                .qualification(TestUtils.PRODUCT_QUALIFICATION_TEST)
                .tax(TestUtils.PRODUCT_TAX_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
    }

}
