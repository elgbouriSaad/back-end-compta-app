package dot.compta.backend.services.quotation;

import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.mappers.invoice.InvoiceMapper;
import dot.compta.backend.mappers.quotation.QuotationMapper;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import dot.compta.backend.repositories.quotation.QuotationRepository;
import dot.compta.backend.services.quotationProduct.QuotationProductServiceImpl;
import dot.compta.backend.validators.client.ClientValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
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


public class QuotationServiceImplTest {

    @Mock
    private QuotationMapper quotationMapper;

    @Mock
    private QuotationRepository quotationRepository;

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
    private InvoiceMapper invoiceMapper;
    
    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private QuotationProductServiceImpl quotationProductService;

    @InjectMocks
    private QuotationServiceImpl quotationService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                quotationMapper,
                quotationRepository,
                clientValidator,
                clientRepository,
                customerValidator,
                customerRepository,
                productValidator,
                quotationProductService,
                quotationValidator,
                invoiceMapper,
                invoiceRepository
        );
    }

    @Test
    void testcreateQuotation() {
        // GIVEN
        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
                .productId(PRODUCT_ID_TEST__1006)
                .quantity(PRODUCT_QUANTITY_TEST)
                .build();
        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
        RequestQuotationDto requestQuotationDto = RequestQuotationDto.builder()
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .productQuantities(mockproductQuantityDtoRequests)
                .build();
        CustomerModel customer = mock(CustomerModel.class);
        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customer));
        ClientModel client = mock(ClientModel.class);
        when(clientRepository.findById(CLIENT_ID_TEST__1005)).thenReturn(Optional.of(client));
        QuotationModel quotation = mock(QuotationModel.class);
        when(quotationMapper.mapToQuotationModel(requestQuotationDto, customer, client)).thenReturn(quotation);
        when(quotationRepository.save(quotation)).thenReturn(quotation);

        // WHEN
        quotationService.createQuotation(requestQuotationDto);

        // THEN
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
        verify(productValidator).validateExistsAndNotDeleted(PRODUCT_ID_TEST__1006);
        verify(customerRepository).findById(CUSTOMER_ID_TEST__1004);
        verify(clientRepository).findById(CLIENT_ID_TEST__1005);
        verify(quotationMapper).mapToQuotationModel(requestQuotationDto, customer, client);
        verify(quotationRepository).save(quotation);
        verify(quotationProductService).createQuotationProducts(quotation, mockproductQuantityDtoRequests);
    }

    @Test
    public void testGetQuotations() {
        // GIVEN
        QuotationModel quotation = mock(QuotationModel.class);
        List<QuotationModel> mockQuotations = Collections.singletonList(quotation);
        when(quotationRepository.findAllByDeletedFalse()).thenReturn(mockQuotations);
        ResponseQuotationDto quotationDto = mock(ResponseQuotationDto.class);
        when(quotationMapper.mapToResponseQuotationDto(quotation)).thenReturn(quotationDto);
        when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
        ResponseQuotationProductDto responseQuotationProduct = mock(ResponseQuotationProductDto.class);
        List<ResponseQuotationProductDto> quotationProducts = Collections.singletonList(responseQuotationProduct);
        when(quotationProductService.getQuotationProducts(QUOTATION_ID_TEST__1007)).thenReturn(quotationProducts);

        // WHEN
        List<ResponseQuotationDto> result = quotationService.getQuotations();

        // THEN
        assertEquals(1, result.size());
        assertEquals(quotationDto, result.get(0));

        verify(quotationRepository).findAllByDeletedFalse();
        verify(quotationMapper).mapToResponseQuotationDto(quotation);
        verify(quotationProductService).getQuotationProducts(QUOTATION_ID_TEST__1007);
    }

    @Test
    public void testGetQuotationById() {
        // GIVEN
        QuotationModel quotation = mock(QuotationModel.class);
        when(quotationRepository.findById(QUOTATION_ID_TEST__1007)).thenReturn(Optional.of(quotation));
        ResponseQuotationDto quotationDto = mock(ResponseQuotationDto.class);
        when(quotationMapper.mapToResponseQuotationDto(quotation)).thenReturn(quotationDto);
        when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
        ResponseQuotationProductDto responseQuotationProduct = mock(ResponseQuotationProductDto.class);
        List<ResponseQuotationProductDto> quotationProducts = Collections.singletonList(responseQuotationProduct);
        when(quotationProductService.getQuotationProducts(QUOTATION_ID_TEST__1007)).thenReturn(quotationProducts);

        // WHEN
        ResponseQuotationDto result = quotationService.getQuotationById(QUOTATION_ID_TEST__1007);

        // THEN
        assertNotNull(result);
        assertEquals(quotationDto, result);

        verify(quotationRepository).findById(QUOTATION_ID_TEST__1007);
        verify(quotationMapper).mapToResponseQuotationDto(quotation);
        verify(quotationProductService).getQuotationProducts(QUOTATION_ID_TEST__1007);
        verify(quotationValidator).validateExistsAndNotDeleted(QUOTATION_ID_TEST__1007);
    }
	
	  @Test
	  public void testGetQuotationsByCustomerId() {
		    // GIVEN
		    QuotationModel quotation = mock(QuotationModel.class);
          List<QuotationModel> mockQuotations = Collections.singletonList(quotation);
	    	when(quotationRepository.findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004)).thenReturn(mockQuotations);
	    	ResponseQuotationDto quotationDto = mock(ResponseQuotationDto.class);
		    when(quotationMapper.mapToResponseQuotationDto(quotation)).thenReturn(quotationDto);
	    	when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
          ResponseQuotationProductDto responseQuotationProduct = mock(ResponseQuotationProductDto.class);
          List<ResponseQuotationProductDto> quotationProducts = Collections.singletonList(responseQuotationProduct);
	    	when(quotationProductService.getQuotationProducts(QUOTATION_ID_TEST__1007)).thenReturn(quotationProducts);

	    	// WHEN
		    List<ResponseQuotationDto> result = quotationService.getQuotationsByCustomerId(CUSTOMER_ID_TEST__1004);

        // THEN
        assertEquals(1, result.size());
        assertEquals(quotationDto, result.get(0));

          verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
          verify(quotationRepository).findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004);
          verify(quotationMapper).mapToResponseQuotationDto(quotation);
        verify(quotationProductService).getQuotationProducts(QUOTATION_ID_TEST__1007);
    }
	  
	  @Test
	  public void testGetQuotationsByClientId() {
		    // GIVEN
		    QuotationModel quotation = mock(QuotationModel.class);
		    List<QuotationModel> mockQuotations = Collections.singletonList(quotation);
          when(quotationRepository.findAllByClientIdAndDeletedFalse(CLIENT_ID_TEST__1005)).thenReturn(mockQuotations);
	    	ResponseQuotationDto quotationDto = mock(ResponseQuotationDto.class);
		    when(quotationMapper.mapToResponseQuotationDto(quotation)).thenReturn(quotationDto);
	    	when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
	    	ResponseQuotationProductDto responseQuotationProduct = mock(ResponseQuotationProductDto.class);
	    	List<ResponseQuotationProductDto> quotationProducts = Collections.singletonList(responseQuotationProduct);
	    	when(quotationProductService.getQuotationProducts(QUOTATION_ID_TEST__1007)).thenReturn(quotationProducts);

	    	// WHEN
		    List<ResponseQuotationDto> result = quotationService.getQuotationsByClientId(CLIENT_ID_TEST__1005);

		    // THEN
		    assertEquals(1, result.size());
		    assertEquals(quotationDto, result.get(0));

          verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
          verify(quotationRepository).findAllByClientIdAndDeletedFalse(CLIENT_ID_TEST__1005);
          verify(quotationMapper).mapToResponseQuotationDto(quotation);
		    verify(quotationProductService).getQuotationProducts(QUOTATION_ID_TEST__1007);
    }

	  @Test
	  public void testValidateQuotation() {
          // WHEN
          quotationService.validateQuotation(QUOTATION_ID_TEST__1007);

          // THEN
          verify(quotationValidator).validateExistsAndNotDeleted(QUOTATION_ID_TEST__1007);
          verify(quotationValidator).validateStatus(QUOTATION_ID_TEST__1007, QuotationStatus.SAVED);
          verify(quotationRepository).updateStatusById(QuotationStatus.VALIDATED, QUOTATION_ID_TEST__1007);
      }
	  
	  @Test
	  public void testUpdateQuotation() {
		// GIVEN
	        RequestProductQuantityDto requestProductQuantityDto = RequestProductQuantityDto.builder()
	                .productId(PRODUCT_ID_TEST__1006)
	                .quantity(PRODUCT_QUANTITY_TEST)
	                .build();
	        List<RequestProductQuantityDto> mockproductQuantityDtoRequests = Collections.singletonList(requestProductQuantityDto);
	        UpdateQuotationDto updateQuotationDto = UpdateQuotationDto.builder()
	                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
	                .clientId(CLIENT_ID_TEST__1005)
	                .productQuantities(mockproductQuantityDtoRequests)
	                .build();
	        CustomerModel customer = mock(CustomerModel.class);
	        ClientModel client = mock(ClientModel.class);
	        when(clientRepository.findById(CLIENT_ID_TEST__1005)).thenReturn(Optional.of(client));
	        QuotationModel quotation = QuotationModel.builder()
	        		.customer(customer)
	        		.build();
	        when(quotationRepository.findById(QUOTATION_ID_TEST__1007)).thenReturn(Optional.of(quotation));
	        when(quotationMapper.mapToQuotationModel(updateQuotationDto, customer, client)).thenReturn(quotation);
	        when(quotationRepository.save(quotation)).thenReturn(quotation);

	        // WHEN
	        quotationService.updateQuotation(updateQuotationDto,QUOTATION_ID_TEST__1007);

	        // THEN
	        verify(quotationValidator).validateExistsAndNotDeleted(QUOTATION_ID_TEST__1007);
	        verify(quotationValidator).validateStatus(QUOTATION_ID_TEST__1007, QuotationStatus.SAVED);
	        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
	        verify(productValidator).validateExistsAndNotDeleted(PRODUCT_ID_TEST__1006);
	        verify(clientRepository).findById(CLIENT_ID_TEST__1005);
	        verify(quotationMapper).mapToQuotationModel(updateQuotationDto, customer, client);
	        verify(quotationRepository).save(quotation);
	        verify(quotationRepository).findById(QUOTATION_ID_TEST__1007);
	        verify(quotationProductService).updateQuotationProducts(quotation, mockproductQuantityDtoRequests);
	  }
	  
	  @Test
	  public void testTransformQuotation() {
		  // GIVEN
		  QuotationModel quotation = mock(QuotationModel.class);
	      when(quotationRepository.findById(QUOTATION_ID_TEST__1007)).thenReturn(Optional.of(quotation));
	      InvoiceModel invoice = mock(InvoiceModel.class);
	      when(invoiceMapper.mapToInvoiceModel(quotation, INVOICE_PAYMENT_DELAY_TEST)).thenReturn(invoice);
	      when(invoiceRepository.save(invoice)).thenReturn(invoice);
	      
	      // WHEN
	      quotationService.transformToInvoice(QUOTATION_ID_TEST__1007, INVOICE_PAYMENT_DELAY_TEST);
	      
	      // THEN
	      verify(quotationValidator).validateExistsAndNotDeleted(QUOTATION_ID_TEST__1007);
	      verify(quotationValidator).validateStatus(QUOTATION_ID_TEST__1007, QuotationStatus.VALIDATED);
	      verify(quotationValidator).validateNotLinkedToInvoice(QUOTATION_ID_TEST__1007);
	      verify(quotationRepository).findById(QUOTATION_ID_TEST__1007);
	      verify(quotationRepository).updateStatusById(QuotationStatus.TRANSFORMED, QUOTATION_ID_TEST__1007);
	      verify(invoiceMapper).mapToInvoiceModel(quotation, INVOICE_PAYMENT_DELAY_TEST);
	      verify(invoiceRepository).save(invoice);
	      verify(quotationProductService).transformQuotationProducts(quotation, invoice);
	  }
	  
	  @Test
	    public void testDeleteQuotation() {
	    	// WHEN
		  	quotationService.deleteQuotation(QUOTATION_ID_TEST__1007);
	    	
	    	// THEN
	    	verify(quotationValidator).validateExistsAndNotDeleted(QUOTATION_ID_TEST__1007);
	        verify(quotationValidator).validateStatus(QUOTATION_ID_TEST__1007, QuotationStatus.SAVED);
	        verify(quotationRepository).logicalDeleteById(QUOTATION_ID_TEST__1007);
	        verify(quotationProductService).deleteQuotationProducts(QUOTATION_ID_TEST__1007);
	    }
	  
	  @Test
	    public void testDeleteClientQuotations() {
		  	// GIVEN
		  	QuotationModel quotation = mock(QuotationModel.class);
		    List<QuotationModel> mockQuotations = Collections.singletonList(quotation);
		    when(quotationRepository.findAllByClientIdAndStatusAndDeletedFalse(CLIENT_ID_TEST__1005,QuotationStatus.SAVED)).thenReturn(mockQuotations);
		    when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
		    when(quotation.getStatus()).thenReturn(QuotationStatus.SAVED);
		  
	    	// WHEN
		  	quotationService.deleteClientQuotations(CLIENT_ID_TEST__1005);
	    	
	    	// THEN
		  	verify(quotationRepository).findAllByClientIdAndStatusAndDeletedFalse(CLIENT_ID_TEST__1005, QuotationStatus.SAVED);
		  	verify(quotationRepository).logicalDeleteById(QUOTATION_ID_TEST__1007);
		  	verify(quotationProductService).deleteQuotationProducts(QUOTATION_ID_TEST__1007);
	    }
}
