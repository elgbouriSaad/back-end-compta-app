package dot.compta.backend.mappers.product;

import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.ResponseProductDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.product.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static dot.compta.backend.utils.TestUtils.*;

public class ProductMapperImplTest {

    private ProductMapperImpl productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapperImpl(new ModelMapper());
    }

    @Test
    void mapToProduct() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        RequestProductDto product = RequestProductDto.builder()
                .label(PRODUCT_LABEL_TEST)
                .reference(PRODUCT_REFERENCE_TEST)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(PRODUCT_UNITY_TEST)
                .qualification(PRODUCT_QUALIFICATION_TEST)
                .tax(PRODUCT_TAX_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);

        // WHEN
        ProductModel result = productMapper.mapToProductModel(product, customer);

        // THEN
        assertNotNull(result);
        assertEquals(PRODUCT_LABEL_TEST, result.getLabel());
        assertEquals(PRODUCT_REFERENCE_TEST, result.getReference());
        assertEquals(PRODUCT_PRICE_TEST, result.getPriceExclTax());
        assertEquals(PRODUCT_UNITY_TEST, result.getUnity());
        assertEquals(PRODUCT_QUALIFICATION_TEST, result.getQualification());
        assertEquals(PRODUCT_TAX_TEST, result.getTax());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomer().getId());
    }

    @Test
    public void mapToProductDtoTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ProductModel productModel = ProductModel.builder()
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
        ResponseProductDto result = productMapper.mapToResponseProductDto(productModel);

        // THEN
        assertNotNull(result);
        assertEquals(PRODUCT_ID_TEST__1006, result.getId());
        assertEquals(PRODUCT_LABEL_TEST, result.getLabel());
        assertEquals(PRODUCT_REFERENCE_TEST, result.getReference());
        assertEquals(PRODUCT_PRICE_TEST, result.getPriceExclTax());
        assertEquals(PRODUCT_UNITY_TEST, result.getUnity());
        assertEquals(PRODUCT_QUALIFICATION_TEST, result.getQualification());
        assertEquals(PRODUCT_TAX_TEST, result.getTax());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomerId());

    }

}
