package dot.compta.backend.controllers.invoice;

import org.springframework.test.web.servlet.MockMvc;
import static dot.compta.backend.utils.TestUtils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;

class BaseInvoiceControllerTest {
	
	protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;
    
    protected List<RequestProductQuantityDto> buildProductQuantities() {
        RequestProductQuantityDto productQuantity = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(INVOICE_PRODUCT_QUANTITY_TEST)
                .build();
        return Collections.singletonList(productQuantity);
    }

    protected List<RequestProductQuantityDto> buildProductQuantities(List<Integer> productIds) {
        List<RequestProductQuantityDto> productQuantities = new ArrayList<>();
        for (int productId : productIds) {
            RequestProductQuantityDto productQuantity = RequestProductQuantityDto.builder()
                    .productId(productId)
                    .quantity(INVOICE_PRODUCT_QUANTITY_TEST)
                    .build();
            productQuantities.add(productQuantity);
        }
        return productQuantities;
    }

    protected RequestInvoiceDto buildRequestInvoiceDto(List<RequestProductQuantityDto> productQuantities) {
        return RequestInvoiceDto.builder()
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .productQuantities(productQuantities)
                .build();
    }
    
    protected UpdateInvoiceDto buildUpdateInvoiceDto(List<RequestProductQuantityDto> productQuantities) {
        return UpdateInvoiceDto.builder()
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .clientId(CLIENT_ID_TEST__1005)
                .productQuantities(productQuantities)
                .build();
    }
    
    protected RequestInvoiceDto buildRequestInvoiceDto() {
        return buildRequestInvoiceDto(Collections.emptyList());
    }
    
    protected ResponseInvoiceDto buildResponseInvoiceDto(List<ResponseInvoiceProductDto> invoiceProducts) {
        return ResponseInvoiceDto.builder()
                .id(INVOICE_ID_TEST__1010)
                .status(InvoiceStatus.SAVED)
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .quotationId(QUOTATION_ID_TEST__1007)
                .invoiceProducts(invoiceProducts)
                .build();
    }

    protected ResponseInvoiceProductDto buildResponseInvoiceProductDto() {
        return ResponseInvoiceProductDto.builder()
                .id(INVOICE_PRODUCT_ID_TEST__1011)
                .productId(PRODUCT_ID_TEST__1006)
                .label(INVOICE_PRODUCT_LABEL_TEST)
                .reference(INVOICE_PRODUCT_REFERENCE_TEST)
                .priceExclTax(INVOICE_PRODUCT_PRICE_TEST)
                .unity(INVOICE_PRODUCT_UNITY_TEST)
                .qualification(INVOICE_PRODUCT_QUALIFICATION_TEST)
                .tax(INVOICE_PRODUCT_TAX_TEST)
                .quantity(INVOICE_PRODUCT_QUANTITY_TEST)
                .build();
    }

}
