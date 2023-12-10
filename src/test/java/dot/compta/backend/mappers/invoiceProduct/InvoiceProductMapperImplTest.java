package dot.compta.backend.mappers.invoiceProduct;

import static dot.compta.backend.utils.TestUtils.*;
import static dot.compta.backend.utils.TestUtils.getModelMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;

public class InvoiceProductMapperImplTest {
	
	private InvoiceProductMapperImpl invoiceProductMapper;
	
	@BeforeEach
    void setUp() {
		invoiceProductMapper = new InvoiceProductMapperImpl(getModelMapper());
    }
	
	@Test
    void mapToInvoiceProductTest() {
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
        InvoiceProductModel result = invoiceProductMapper.mapToInvoiceProductModel(product);

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
    void mapToInvoiceProductDtoTest() {
        // GIVEN
        InvoiceModel invoice = mock(InvoiceModel.class);
        ProductModel product = mock(ProductModel.class);
        InvoiceProductModel invoiceProduct = InvoiceProductModel.builder()
                .id(INVOICE_PRODUCT_ID_TEST__1011)
                .product(product)
                .label(INVOICE_PRODUCT_LABEL_TEST)
                .reference(INVOICE_PRODUCT_REFERENCE_TEST)
                .priceExclTax(INVOICE_PRODUCT_PRICE_TEST)
                .unity(INVOICE_PRODUCT_UNITY_TEST)
                .qualification(INVOICE_PRODUCT_QUALIFICATION_TEST)
                .tax(INVOICE_PRODUCT_TAX_TEST)
                .quantity(INVOICE_PRODUCT_QUANTITY_TEST)
                .invoice(invoice)
                .build();
        when(product.getId()).thenReturn(PRODUCT_ID_TEST__1006);

        // WHEN
        ResponseInvoiceProductDto result = invoiceProductMapper.mapToResponseInvoiceProductDto(invoiceProduct);

        // THEN
        assertNotNull(result);
        assertEquals(INVOICE_PRODUCT_LABEL_TEST, result.getLabel());
        assertEquals(PRODUCT_ID_TEST__1006, result.getProductId());
        assertEquals(INVOICE_PRODUCT_REFERENCE_TEST, result.getReference());
        assertEquals(INVOICE_PRODUCT_PRICE_TEST, result.getPriceExclTax());
        assertEquals(INVOICE_PRODUCT_UNITY_TEST, result.getUnity());
        assertEquals(INVOICE_PRODUCT_QUALIFICATION_TEST, result.getQualification());
        assertEquals(INVOICE_PRODUCT_TAX_TEST, result.getTax());
        assertEquals(INVOICE_PRODUCT_QUANTITY_TEST, result.getQuantity());
    }
	
	@Test
    void mapToInvoiceProductModelTest() {
        // GIVEN
        QuotationModel quotation = mock(QuotationModel.class);
        ProductModel product = mock(ProductModel.class);
        InvoiceModel invoice = mock(InvoiceModel.class);
        QuotationProductModel quotationProduct = QuotationProductModel.builder()
                .id(PRODUCT_ID_TEST__1006)
                .quotation(quotation)
                .product(product)
                .label(PRODUCT_LABEL_TEST)
                .reference(PRODUCT_REFERENCE_TEST)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(PRODUCT_UNITY_TEST)
                .qualification(PRODUCT_QUALIFICATION_TEST)
                .tax(PRODUCT_TAX_TEST)
                .build();
        when(product.getId()).thenReturn(PRODUCT_ID_TEST__1006);
        when(invoice.getId()).thenReturn(INVOICE_ID_TEST__1010);

        // WHEN
        InvoiceProductModel result = invoiceProductMapper.mapToInvoiceProductModel(quotationProduct,invoice);

        // THEN
        assertNotNull(result);
        assertEquals(PRODUCT_LABEL_TEST, result.getLabel());
        assertEquals(PRODUCT_ID_TEST__1006, result.getProduct().getId());
        assertEquals(INVOICE_ID_TEST__1010, result.getInvoice().getId());
        assertEquals(PRODUCT_REFERENCE_TEST, result.getReference());
        assertEquals(PRODUCT_PRICE_TEST, result.getPriceExclTax());
        assertEquals(PRODUCT_UNITY_TEST, result.getUnity());
        assertEquals(PRODUCT_QUALIFICATION_TEST, result.getQualification());
        assertEquals(PRODUCT_TAX_TEST, result.getTax());
    }

}
