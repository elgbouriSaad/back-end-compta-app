package dot.compta.backend.controllers.quotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.RequestTransformQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dot.compta.backend.utils.TestUtils.*;

class BaseQuotationControllerTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected List<RequestProductQuantityDto> buildProductQuantities() {
        RequestProductQuantityDto productQuantity = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(QUOTATION_PRODUCT_QUANTITY_TEST)
                .build();
        return Collections.singletonList(productQuantity);
    }

    protected List<RequestProductQuantityDto> buildProductQuantities(List<Integer> productIds) {
        List<RequestProductQuantityDto> productQuantities = new ArrayList<>();
        for (int productId : productIds) {
            RequestProductQuantityDto productQuantity = RequestProductQuantityDto.builder()
                    .productId(productId)
                    .quantity(QUOTATION_PRODUCT_QUANTITY_TEST)
                    .build();
            productQuantities.add(productQuantity);
        }
        return productQuantities;
    }

    protected RequestQuotationDto buildRequestQuotationDto(List<RequestProductQuantityDto> productQuantities) {
        return RequestQuotationDto.builder()
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .productQuantities(productQuantities)
                .build();
    }
    
    protected UpdateQuotationDto buildUpdateQuotationDto(List<RequestProductQuantityDto> productQuantities) {
        return UpdateQuotationDto.builder()
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .clientId(CLIENT_ID_TEST__1005)
                .productQuantities(productQuantities)
                .build();
    }
    
    protected RequestQuotationDto buildRequestQuotationDto() {
        return buildRequestQuotationDto(Collections.emptyList());
    }

    protected ResponseQuotationDto buildResponseQuotationDto(List<ResponseQuotationProductDto> quotationProducts) {
        return ResponseQuotationDto.builder()
                .id(QUOTATION_ID_TEST__1007)
                .status(QuotationStatus.SAVED)
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .quotationProducts(quotationProducts)
                .build();
    }

    protected ResponseQuotationProductDto buildResponseQuotationProductDto() {
        return ResponseQuotationProductDto.builder()
                .id(QUOTATION_PRODUCT_ID_TEST__1008)
                .productId(PRODUCT_ID_TEST__1006)
                .label(QUOTATION_PRODUCT_LABEL_TEST)
                .reference(QUOTATION_PRODUCT_REFERENCE_TEST)
                .priceExclTax(QUOTATION_PRODUCT_PRICE_TEST)
                .unity(QUOTATION_PRODUCT_UNITY_TEST)
                .qualification(QUOTATION_PRODUCT_QUALIFICATION_TEST)
                .tax(QUOTATION_PRODUCT_TAX_TEST)
                .quantity(QUOTATION_PRODUCT_QUANTITY_TEST)
                .build();
    }

    protected RequestTransformQuotationDto buildRequestTransformQuotationDto() {
        return RequestTransformQuotationDto.builder()
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .build();
    }

}
