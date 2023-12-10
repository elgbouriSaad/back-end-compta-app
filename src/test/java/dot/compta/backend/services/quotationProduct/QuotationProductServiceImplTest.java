package dot.compta.backend.services.quotationProduct;

import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.mappers.invoiceProduct.InvoiceProductMapper;
import dot.compta.backend.mappers.quotationProduct.QuotationProductMapper;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;
import dot.compta.backend.repositories.invoiceProduct.InvoiceProductRepository;
import dot.compta.backend.repositories.product.ProductRepository;
import dot.compta.backend.repositories.quotationProduct.QuotationProductRepository;
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

public class QuotationProductServiceImplTest {

    @Mock
    private QuotationProductRepository quotationProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private QuotationProductMapper quotationProductMapper;
    
    @Mock
    private InvoiceProductMapper invoiceProductMapper;
    
    @Mock
    private InvoiceProductRepository invoiceProductRepository;

    @InjectMocks
    private QuotationProductServiceImpl quotationProductService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                productRepository,
                quotationProductRepository,
                quotationProductMapper,
                invoiceProductMapper,
                invoiceProductRepository
        );
    }

    @Test
    public void testCreateQuotationProducts() {
        // GIVEN
        QuotationModel quotation = mock(QuotationModel.class);
        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(PRODUCT_QUANTITY_TEST)
                .build();
        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
        ProductModel product = mock(ProductModel.class);
        when(productRepository.findById(PRODUCT_ID_TEST__1006)).thenReturn(Optional.of(product));
        QuotationProductModel quotationProductModel = mock(QuotationProductModel.class);
        when(quotationProductMapper.mapToQuotationProductModel(product)).thenReturn(quotationProductModel);
        List<QuotationProductModel> quotationProductModels = Collections.singletonList(quotationProductModel);

        // WHEN
        quotationProductService.createQuotationProducts(quotation, mockproductQuantityDtoRequests);

        // THEN
        verify(quotationProductRepository).saveAll(quotationProductModels);
        verify(productRepository).findById(PRODUCT_ID_TEST__1006);
        verify(quotationProductMapper).mapToQuotationProductModel(product);
    }

    @Test
    public void testUpdateQuotationProducts() {
        // GIVEN
        QuotationModel quotation = mock(QuotationModel.class);
        when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(PRODUCT_QUANTITY_TEST)
                .build();
        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
        QuotationProductModel quotationProductModel = mock (QuotationProductModel.class);
        ProductModel product = mock(ProductModel.class);
        when(product.getId()).thenReturn(PRODUCT_ID_TEST__1006);
        when(quotationProductModel.getProduct()).thenReturn(product);
        List<QuotationProductModel> quotationProducts = Collections.singletonList(quotationProductModel);
        when(quotationProductRepository.findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007)).thenReturn(quotationProducts);

        // WHEN
        quotationProductService.updateQuotationProducts(quotation, mockproductQuantityDtoRequests);

        // THEN
        verify(quotationProductRepository).findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007);
        verify(quotationProductRepository).updateQuantityByQuotationIdAndProductId(PRODUCT_QUANTITY_TEST, QUOTATION_ID_TEST__1007, PRODUCT_ID_TEST__1006);
        verify(quotationProductRepository).saveAll(Collections.emptyList());
    }

    @Test
    public void testGetQuotationProducts() {
        // GIVEN
        QuotationProductModel quotationProduct1 = mock(QuotationProductModel.class);
        QuotationProductModel quotationProduct2 = mock(QuotationProductModel.class);
        List<QuotationProductModel> mockQuotationProducts = Arrays.asList(quotationProduct1, quotationProduct2);
        when(quotationProductRepository.findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007)).thenReturn(mockQuotationProducts);
        ResponseQuotationProductDto quotationProductDto1 = mock(ResponseQuotationProductDto.class);
        ResponseQuotationProductDto quotationProductDto2 = mock(ResponseQuotationProductDto.class);
        when(quotationProductMapper.mapToResponseQuotationProductDto(quotationProduct1)).thenReturn(quotationProductDto1);
        when(quotationProductMapper.mapToResponseQuotationProductDto(quotationProduct2)).thenReturn(quotationProductDto2);

        // WHEN
        List<ResponseQuotationProductDto> result = quotationProductService.getQuotationProducts(QUOTATION_ID_TEST__1007);

        // THEN
        assertEquals(2, result.size());
        assertEquals(quotationProductDto1, result.get(0));
        assertEquals(quotationProductDto2, result.get(1));

        verify(quotationProductRepository).findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007);
        verify(quotationProductMapper).mapToResponseQuotationProductDto(quotationProduct1);
        verify(quotationProductMapper).mapToResponseQuotationProductDto(quotationProduct2);
    }
    
    @Test
    public void testTransformQuotationProducts() {
    	// GIVEN
    	QuotationModel quotation = mock(QuotationModel.class);
    	when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
    	InvoiceModel invoice = mock(InvoiceModel.class);
    	QuotationProductModel quotationProduct = mock(QuotationProductModel.class);
        List<QuotationProductModel> mockQuotationProducts = Collections.singletonList(quotationProduct);
        when(quotationProductRepository.findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007)).thenReturn(mockQuotationProducts);
        InvoiceProductModel invoiceProduct = mock(InvoiceProductModel.class);
        when(invoiceProductMapper.mapToInvoiceProductModel(quotationProduct, invoice)).thenReturn(invoiceProduct);
        List<InvoiceProductModel> mockInvoiceProducts = Collections.singletonList(invoiceProduct);
        
        // WHEN
        quotationProductService.transformQuotationProducts(quotation, invoice);
        
        // THEN
        verify(quotationProductRepository).findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007);
        verify(invoiceProductMapper).mapToInvoiceProductModel(quotationProduct, invoice);
        verify(invoiceProductRepository).saveAll(mockInvoiceProducts);
        
    }
    
    @Test
    public void testDeleteQuotationProducts() {
    	// GIVEN
    	QuotationProductModel quotationProductModel = mock (QuotationProductModel.class);
    	when(quotationProductModel.getId()).thenReturn(QUOTATION_PRODUCT_ID_TEST__1008);
    	List<QuotationProductModel> quotationProducts = Collections.singletonList(quotationProductModel);
    	 when(quotationProductRepository.findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007)).thenReturn(quotationProducts);
    	
    	// WHEN
    	quotationProductService.deleteQuotationProducts(QUOTATION_ID_TEST__1007);
    	
    	// THEN
    	verify(quotationProductRepository).findAllByQuotationIdAndDeletedFalse(QUOTATION_ID_TEST__1007);
    	verify(quotationProductRepository).logicalDeleteById(QUOTATION_PRODUCT_ID_TEST__1008);
    }

}
