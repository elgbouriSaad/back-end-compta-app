package dot.compta.backend.services.invoice;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.mappers.invoice.InvoiceMapper;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import dot.compta.backend.services.invoiceProduct.InvoiceProductServiceImpl;
import dot.compta.backend.validators.client.ClientValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
import dot.compta.backend.validators.invoice.InvoiceValidator;
import dot.compta.backend.validators.product.ProductValidator;
import dot.compta.backend.validators.quotation.QuotationValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static dot.compta.backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class InvoiceServiceImplTest {
	
	@Mock
    private InvoiceMapper invoiceMapper;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private CustomerValidator customerValidator;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ClientValidator clientValidator;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private QuotationValidator quotationValidator;
    
    @Mock
    private InvoiceValidator invoiceValidator;

    @Mock
    private InvoiceProductServiceImpl invoiceProductService;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
        		invoiceMapper,
        		invoiceRepository,
                clientValidator,
                clientRepository,
                customerValidator,
                customerRepository,
                productValidator,
                invoiceProductService,
                quotationValidator,
                invoiceValidator
        );
    }

    @Test
    void testCreateInvoice() {
        // GIVEN
        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(INVOICE_PRODUCT_QUANTITY_TEST)
                .build();
        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
        RequestInvoiceDto requestInvoiceDto = RequestInvoiceDto.builder()
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .productQuantities(mockproductQuantityDtoRequests)
                .build();
        CustomerModel customer = mock(CustomerModel.class);
        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customer));
        ClientModel client = mock(ClientModel.class);
        when(clientRepository.findById(CLIENT_ID_TEST__1005)).thenReturn(Optional.of(client));
        InvoiceModel invoice = mock(InvoiceModel.class);
        when(invoiceMapper.mapToInvoiceModel(requestInvoiceDto, customer, client, null)).thenReturn(invoice);
        when(invoiceRepository.save(invoice)).thenReturn(invoice);

        // WHEN
        invoiceService.createInvoice(requestInvoiceDto);

        // THEN
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
        verify(productValidator).validateExistsAndNotDeleted(PRODUCT_ID_TEST__1006);
        verify(customerRepository).findById(CUSTOMER_ID_TEST__1004);
        verify(clientRepository).findById(CLIENT_ID_TEST__1005);
        verify(invoiceMapper).mapToInvoiceModel(requestInvoiceDto, customer, client, null);
        verify(invoiceRepository).save(invoice);
        verify(invoiceProductService).createInvoiceProducts(invoice, mockproductQuantityDtoRequests);
    }
    
    @Test
    public void testGetInvoices() {
        // GIVEN
    	InvoiceModel invoice = mock(InvoiceModel.class);
        List<InvoiceModel> mockInvoices = Collections.singletonList(invoice);
        when(invoiceRepository.findAllByDeletedFalse()).thenReturn(mockInvoices);
        ResponseInvoiceDto invoiceDto = mock(ResponseInvoiceDto.class);
        when(invoiceMapper.mapToResponseInvoiceDto(invoice)).thenReturn(invoiceDto);
        when(invoice.getId()).thenReturn(INVOICE_ID_TEST__1010);
        ResponseInvoiceProductDto responseInvoiceProduct = mock(ResponseInvoiceProductDto.class);
        List<ResponseInvoiceProductDto> invoiceProducts = Collections.singletonList(responseInvoiceProduct);
        when(invoiceProductService.getInvoiceProducts(INVOICE_ID_TEST__1010)).thenReturn(invoiceProducts);

        // WHEN
        List<ResponseInvoiceDto> result = invoiceService.getInvoices();

        // THEN
        assertEquals(1, result.size());
        assertEquals(invoiceDto, result.get(0));

        verify(invoiceRepository).findAllByDeletedFalse();
        verify(invoiceMapper).mapToResponseInvoiceDto(invoice);
        verify(invoiceProductService).getInvoiceProducts(INVOICE_ID_TEST__1010);
    }
    
    @Test
    public void testGetInvoiceById() {
        // GIVEN
    	InvoiceModel invoice = mock(InvoiceModel.class);
        when(invoiceRepository.findById(INVOICE_ID_TEST__1010)).thenReturn(Optional.of(invoice));
        ResponseInvoiceDto invoiceDto = mock(ResponseInvoiceDto.class);
        when(invoiceMapper.mapToResponseInvoiceDto(invoice)).thenReturn(invoiceDto);
        when(invoice.getId()).thenReturn(INVOICE_ID_TEST__1010);
        ResponseInvoiceProductDto responseInvoiceProduct = mock(ResponseInvoiceProductDto.class);
        List<ResponseInvoiceProductDto> invoiceProducts = Collections.singletonList(responseInvoiceProduct);
        when(invoiceProductService.getInvoiceProducts(INVOICE_ID_TEST__1010)).thenReturn(invoiceProducts);

        // WHEN
        ResponseInvoiceDto result = invoiceService.getInvoiceById(INVOICE_ID_TEST__1010);

        // THEN
        assertNotNull(result);
        assertEquals(invoiceDto, result);

        verify(invoiceRepository).findById(INVOICE_ID_TEST__1010);
        verify(invoiceMapper).mapToResponseInvoiceDto(invoice);
        verify(invoiceProductService).getInvoiceProducts(INVOICE_ID_TEST__1010);
        verify(invoiceValidator).validateExistsAndNotDeleted(INVOICE_ID_TEST__1010);
    }
    
    @Test
	  public void testGetInvoicesByCustomerId() {
		    // GIVEN
    		InvoiceModel invoice = mock(InvoiceModel.class);
    		List<InvoiceModel> mockInvoices = Collections.singletonList(invoice);
	    	when(invoiceRepository.findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004)).thenReturn(mockInvoices);
	    	ResponseInvoiceDto invoiceDto = mock(ResponseInvoiceDto.class);
		    when(invoiceMapper.mapToResponseInvoiceDto(invoice)).thenReturn(invoiceDto);
	    	when(invoice.getId()).thenReturn(INVOICE_ID_TEST__1010);
	    	ResponseInvoiceProductDto responseInvoiceProduct = mock(ResponseInvoiceProductDto.class);
	    	List<ResponseInvoiceProductDto> invoiceProducts = Collections.singletonList(responseInvoiceProduct);
	    	when(invoiceProductService.getInvoiceProducts(INVOICE_ID_TEST__1010)).thenReturn(invoiceProducts);

	    	// WHEN
		    List<ResponseInvoiceDto> result = invoiceService.getInvoicesByCustomerId(CUSTOMER_ID_TEST__1004);

		    // THEN
		    assertEquals(1, result.size());
		    assertEquals(invoiceDto, result.get(0));

        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
        verify(invoiceRepository).findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004);
        verify(invoiceMapper).mapToResponseInvoiceDto(invoice);
		    verify(invoiceProductService).getInvoiceProducts(INVOICE_ID_TEST__1010);
  }
    
    @Test
	  public void testGetInvoicesByClientId() {
		    // GIVEN
  		InvoiceModel invoice = mock(InvoiceModel.class);
  		List<InvoiceModel> mockInvoices = Collections.singletonList(invoice);
        when(invoiceRepository.findAllByClientIdAndDeletedFalse(CLIENT_ID_TEST__1005)).thenReturn(mockInvoices);
	    	ResponseInvoiceDto invoiceDto = mock(ResponseInvoiceDto.class);
		    when(invoiceMapper.mapToResponseInvoiceDto(invoice)).thenReturn(invoiceDto);
	    	when(invoice.getId()).thenReturn(INVOICE_ID_TEST__1010);
	    	ResponseInvoiceProductDto responseInvoiceProduct = mock(ResponseInvoiceProductDto.class);
	    	List<ResponseInvoiceProductDto> invoiceProducts = Collections.singletonList(responseInvoiceProduct);
	    	when(invoiceProductService.getInvoiceProducts(INVOICE_ID_TEST__1010)).thenReturn(invoiceProducts);

	    	// WHEN
		    List<ResponseInvoiceDto> result = invoiceService.getInvoicesByClientId(CLIENT_ID_TEST__1005);

		    // THEN
		    assertEquals(1, result.size());
		    assertEquals(invoiceDto, result.get(0));

        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
        verify(invoiceRepository).findAllByClientIdAndDeletedFalse(CLIENT_ID_TEST__1005);
        verify(invoiceMapper).mapToResponseInvoiceDto(invoice);
		    verify(invoiceProductService).getInvoiceProducts(INVOICE_ID_TEST__1010);
}
    
    @Test
	  public void testValidateInvoice() {
        // WHEN
    	invoiceService.validateInvoice(INVOICE_ID_TEST__1010);

        // THEN
        verify(invoiceValidator).validateExistsAndNotDeleted(INVOICE_ID_TEST__1010);
        verify(invoiceValidator).validateSavedStatus(INVOICE_ID_TEST__1010);
        verify(invoiceRepository).updateStatusById(InvoiceStatus.VALIDATED, INVOICE_ID_TEST__1010);
    }
    
    @Test
	  public void testUpdateInvoice() {
		// GIVEN
	        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
	                .productId(PRODUCT_ID_TEST__1006)
	                .quantity(PRODUCT_QUANTITY_TEST)
	                .build();
	        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
	        UpdateInvoiceDto updateInvoiceDto = UpdateInvoiceDto.builder()
	                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
	                .clientId(CLIENT_ID_TEST__1005)
	                .productQuantities(mockproductQuantityDtoRequests)
	                .build();
	        CustomerModel customer = mock(CustomerModel.class);
	        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customer));
	        ClientModel client = mock(ClientModel.class);
	        when(clientRepository.findById(CLIENT_ID_TEST__1005)).thenReturn(Optional.of(client));
	        InvoiceModel invoice = InvoiceModel.builder()
	        		.customer(customer)
	        		.build();
	        when(invoiceRepository.findById(INVOICE_ID_TEST__1010)).thenReturn(Optional.of(invoice));
	        when(invoiceMapper.mapToInvoiceModel(updateInvoiceDto, customer, client, null)).thenReturn(invoice);
	        when(invoiceRepository.save(invoice)).thenReturn(invoice);

	        // WHEN
	        invoiceService.updateInvoice(updateInvoiceDto,INVOICE_ID_TEST__1010);

	        // THEN
	        verify(invoiceValidator).validateExistsAndNotDeleted(INVOICE_ID_TEST__1010);
	        verify(invoiceValidator).validateSavedStatus(INVOICE_ID_TEST__1010);
	        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
	        verify(productValidator).validateExistsAndNotDeleted(PRODUCT_ID_TEST__1006);
	        verify(clientRepository).findById(CLIENT_ID_TEST__1005);
	        verify(invoiceMapper).mapToInvoiceModel(updateInvoiceDto, customer, client, null);
	        verify(invoiceRepository).save(invoice);
	        verify(invoiceRepository).findById(INVOICE_ID_TEST__1010);
	        verify(invoiceProductService).updateInvoiceProducts(invoice, mockproductQuantityDtoRequests);
	  }
    
    @Test
    public void testDeleteInvoice() {
    	// WHEN
    	invoiceService.deleteInvoice(INVOICE_ID_TEST__1010);
    	
    	// THEN
    	verify(invoiceValidator).validateExistsAndNotDeleted(INVOICE_ID_TEST__1010);
        verify(invoiceValidator).validateSavedStatus(INVOICE_ID_TEST__1010);
        verify(invoiceRepository).logicalDeleteById(INVOICE_ID_TEST__1010);
        verify(invoiceProductService).deleteInvoiceProducts(INVOICE_ID_TEST__1010);
    }
    
    @Test
    public void testDeleteClientInvoices() {
	  	// GIVEN
    	InvoiceModel invoice = mock(InvoiceModel.class);
	    List<InvoiceModel> mockInvoices = Collections.singletonList(invoice);
	    when(invoiceRepository.findAllByClientIdAndStatusAndDeletedFalse(CLIENT_ID_TEST__1005, InvoiceStatus.SAVED)).thenReturn(mockInvoices);
	    when(invoice.getId()).thenReturn(INVOICE_ID_TEST__1010);
	    when(invoice.getStatus()).thenReturn(InvoiceStatus.SAVED);
	  
    	// WHEN
	    invoiceService.deleteClientInvoices(CLIENT_ID_TEST__1005);
    	
    	// THEN
	  	verify(invoiceRepository).findAllByClientIdAndStatusAndDeletedFalse(CLIENT_ID_TEST__1005, InvoiceStatus.SAVED);
	  	verify(invoiceRepository).logicalDeleteById(INVOICE_ID_TEST__1010);
	  	verify(invoiceProductService).deleteInvoiceProducts(INVOICE_ID_TEST__1010);
    }

}
