package dot.compta.backend.mappers.quotationProduct;

import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;
import dot.compta.backend.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static dot.compta.backend.utils.TestUtils.*;

public class QuotationProductMapperImplTest {

    private QuotationProductMapperImpl quotationProductMapper;

    @BeforeEach
    void setUp() {
        quotationProductMapper = new QuotationProductMapperImpl(TestUtils.getModelMapper());
    }

    @Test
    void mapToQuotationProductTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ProductModel product = ProductModel.builder()
                .id(PRODUCT_ID_TEST__1006)
                .label(PRODUCT_LABEL_TEST)
                .reference(PRODUCT_REFERENCE_TEST)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(PRODUCT_UNITY_TEST)
                .qualification(PRODUCT_QUALIFICATION_TEST)
                .tax(PRODUCT_TAX_TEST)
                .customer(customer)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);

        // WHEN
        QuotationProductModel result = quotationProductMapper.mapToQuotationProductModel(product);

        // THEN
        assertNotNull(result);
        assertEquals(PRODUCT_LABEL_TEST, result.getLabel());
        assertEquals(PRODUCT_REFERENCE_TEST, result.getReference());
        assertEquals(PRODUCT_PRICE_TEST, result.getPriceExclTax());
        assertEquals(PRODUCT_UNITY_TEST, result.getUnity());
        assertEquals(PRODUCT_QUALIFICATION_TEST, result.getQualification());
        assertEquals(PRODUCT_TAX_TEST, result.getTax());
    }

    @Test
    void mapToQuotationProductDtoTest() {
        // GIVEN
        QuotationModel quotation = mock(QuotationModel.class);
        ProductModel product = mock(ProductModel.class);
        QuotationProductModel quotationProduct = QuotationProductModel.builder()
                .id(QUOTATION_PRODUCT_ID_TEST__1008)
                .product(product)
                .label(QUOTATION_PRODUCT_LABEL_TEST)
                .reference(QUOTATION_PRODUCT_REFERENCE_TEST)
                .priceExclTax(QUOTATION_PRODUCT_PRICE_TEST)
                .unity(QUOTATION_PRODUCT_UNITY_TEST)
                .qualification(QUOTATION_PRODUCT_QUALIFICATION_TEST)
                .tax(QUOTATION_PRODUCT_TAX_TEST)
                .quantity(QUOTATION_PRODUCT_QUANTITY_TEST)
                .quotation(quotation)
                .build();
        when(product.getId()).thenReturn(PRODUCT_ID_TEST__1006);

        // WHEN
        ResponseQuotationProductDto result = quotationProductMapper.mapToResponseQuotationProductDto(quotationProduct);

        // THEN
        assertNotNull(result);
        assertEquals(QUOTATION_PRODUCT_ID_TEST__1008, result.getId());
        assertEquals(QUOTATION_PRODUCT_LABEL_TEST, result.getLabel());
        assertEquals(QUOTATION_PRODUCT_REFERENCE_TEST, result.getReference());
        assertEquals(QUOTATION_PRODUCT_PRICE_TEST, result.getPriceExclTax());
        assertEquals(QUOTATION_PRODUCT_UNITY_TEST, result.getUnity());
        assertEquals(QUOTATION_PRODUCT_QUALIFICATION_TEST, result.getQualification());
        assertEquals(QUOTATION_PRODUCT_TAX_TEST, result.getTax());
        assertEquals(PRODUCT_ID_TEST__1006, result.getProductId());
        assertEquals(QUOTATION_PRODUCT_QUANTITY_TEST, result.getQuantity());
    }

}
