package dot.compta.backend.services.invoiceProduct;

import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.mappers.invoiceProduct.InvoiceProductMapper;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.repositories.invoiceProduct.InvoiceProductRepository;
import dot.compta.backend.repositories.product.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static dot.compta.backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class InvoiceProductServiceImplTest {
	
	@Mock
    private InvoiceProductRepository invoiceProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InvoiceProductMapper invoiceProductMapper;

    @InjectMocks
    private InvoiceProductServiceImpl invoiceProductService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                productRepository,
                invoiceProductRepository,
                invoiceProductMapper
        );
    }

    @Test
    public void testCreateInvoiceProducts() {
        // GIVEN
        InvoiceModel invoice = mock(InvoiceModel.class);
        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(INVOICE_PRODUCT_QUANTITY_TEST)
                .build();
        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
        ProductModel product = mock(ProductModel.class);
        when(productRepository.findById(PRODUCT_ID_TEST__1006)).thenReturn(Optional.of(product));
        InvoiceProductModel invoiceProductModel = mock(InvoiceProductModel.class);
        when(invoiceProductMapper.mapToInvoiceProductModel(product)).thenReturn(invoiceProductModel);
        List<InvoiceProductModel> invoiceProductModels = Collections.singletonList(invoiceProductModel);

        // WHEN
        invoiceProductService.createInvoiceProducts(invoice, mockproductQuantityDtoRequests);

        // THEN
        verify(invoiceProductRepository).saveAll(invoiceProductModels);
        verify(productRepository).findById(PRODUCT_ID_TEST__1006);
        verify(invoiceProductMapper).mapToInvoiceProductModel(product);
    }
    
    @Test
    public void testGetInvoiceProducts() {
        // GIVEN
        InvoiceProductModel invoiceProduct1 = mock(InvoiceProductModel.class);
        InvoiceProductModel invoiceProduct2 = mock(InvoiceProductModel.class);
        List<InvoiceProductModel> mockInvoiceProducts = Arrays.asList(invoiceProduct1, invoiceProduct2);
        when(invoiceProductRepository.findAllByInvoiceIdAndDeletedFalse(INVOICE_ID_TEST__1010)).thenReturn(mockInvoiceProducts);
        ResponseInvoiceProductDto invoiceProductDto1 = mock(ResponseInvoiceProductDto.class);
        ResponseInvoiceProductDto invoiceProductDto2 = mock(ResponseInvoiceProductDto.class);
        when(invoiceProductMapper.mapToResponseInvoiceProductDto(invoiceProduct1)).thenReturn(invoiceProductDto1);
        when(invoiceProductMapper.mapToResponseInvoiceProductDto(invoiceProduct2)).thenReturn(invoiceProductDto2);

        // WHEN
        List<ResponseInvoiceProductDto> result = invoiceProductService.getInvoiceProducts(INVOICE_ID_TEST__1010);

        // THEN
        assertEquals(2, result.size());
        assertEquals(invoiceProductDto1, result.get(0));
        assertEquals(invoiceProductDto2, result.get(1));

        verify(invoiceProductRepository).findAllByInvoiceIdAndDeletedFalse(INVOICE_ID_TEST__1010);
        verify(invoiceProductMapper).mapToResponseInvoiceProductDto(invoiceProduct1);
        verify(invoiceProductMapper).mapToResponseInvoiceProductDto(invoiceProduct2);
    }
    
    @Test
    public void testUpdateInvoiceProducts() {
        // GIVEN
    	InvoiceModel invoice = mock(InvoiceModel.class);
        when(invoice.getId()).thenReturn(INVOICE_ID_TEST__1010);
        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(PRODUCT_QUANTITY_TEST)
                .build();
        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
        InvoiceProductModel invoiceProductModel = mock (InvoiceProductModel.class);
        ProductModel product = mock(ProductModel.class);
        when(product.getId()).thenReturn(PRODUCT_ID_TEST__1006);
        when(invoiceProductModel.getProduct()).thenReturn(product);
        List<InvoiceProductModel> invoiceProducts = Collections.singletonList(invoiceProductModel);
        when(invoiceProductRepository.findAllByInvoiceIdAndDeletedFalse(INVOICE_ID_TEST__1010)).thenReturn(invoiceProducts);

        // WHEN
        invoiceProductService.updateInvoiceProducts(invoice, mockproductQuantityDtoRequests);

        // THEN
        verify(invoiceProductRepository).findAllByInvoiceIdAndDeletedFalse(INVOICE_ID_TEST__1010);
        verify(invoiceProductRepository).updateQuantityByInvoiceIdAndProductId(PRODUCT_QUANTITY_TEST, INVOICE_ID_TEST__1010, PRODUCT_ID_TEST__1006);
        verify(invoiceProductRepository).saveAll(Collections.emptyList());
    }
    
    @Test
    public void testDeleteInvoiceProducts() {
    	// GIVEN
    	InvoiceProductModel invoiceProductModel = mock (InvoiceProductModel.class);
    	when(invoiceProductModel.getId()).thenReturn(INVOICE_PRODUCT_ID_TEST__1011);
    	List<InvoiceProductModel> invoiceProducts = Collections.singletonList(invoiceProductModel);
    	 when(invoiceProductRepository.findAllByInvoiceIdAndDeletedFalse(INVOICE_ID_TEST__1010)).thenReturn(invoiceProducts);
    	
    	// WHEN
    	invoiceProductService.deleteInvoiceProducts(INVOICE_ID_TEST__1010);
    	
    	// THEN
    	verify(invoiceProductRepository).findAllByInvoiceIdAndDeletedFalse(INVOICE_ID_TEST__1010);
    	verify(invoiceProductRepository).logicalDeleteById(INVOICE_PRODUCT_ID_TEST__1011);
    }

}
