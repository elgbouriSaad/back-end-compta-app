package dot.compta.backend.services.customer;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;
import dot.compta.backend.mappers.customer.CustomerMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.services.client.ClientService;
import dot.compta.backend.services.expenseReport.ExpenseReportService;
import dot.compta.backend.services.invoice.InvoiceService;
import dot.compta.backend.services.product.ProductService;
import dot.compta.backend.services.quotation.QuotationService;
import dot.compta.backend.validators.accountant.AccountantValidator;
import dot.compta.backend.validators.address.AddressValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AccountantRepository accountantRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private AddressValidator addressValidator;

    @Mock
    private CustomerValidator customerValidator;

    @Mock
    private AccountantValidator accountantValidator;
    
    @Mock
	private InvoiceService invoiceService;
	
    @Mock
	private QuotationService quotationService;
	
    @Mock
	private ProductService productService;
	
    @Mock
	private ExpenseReportService expenseReportService;
	
    @Mock
	private ClientService clientService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(customerRepository,
                cityRepository,
                customerMapper,
                addressValidator,
                customerValidator,
                accountantRepository,
                accountantValidator,
                invoiceService,
                quotationService,
                productService,
                expenseReportService,
                clientService
        );
    }

    @Test
    void createCustomerTest() {
        // GIVEN
        RequestCustomerDto requestCustomerDto = mock(RequestCustomerDto.class);
        AddressDto addressDto = mock(AddressDto.class);
        when(requestCustomerDto.getAddress()).thenReturn(addressDto);
        when(requestCustomerDto.getAccountantId()).thenReturn(ACCOUNTANT_ID_TEST__1003);
        when(addressDto.getCity()).thenReturn(CITY_NAME_TEST__CASABLANCA);
        City city = mock(City.class);
        when(cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA)).thenReturn(Optional.of(city));
        AccountantModel accountantModel = mock(AccountantModel.class);
        when(accountantRepository.findById(ACCOUNTANT_ID_TEST__1003)).thenReturn(Optional.of(accountantModel));
        CustomerModel customerModel = mock(CustomerModel.class);
        when(customerMapper.mapToCustomerModel(requestCustomerDto, city, accountantModel)).thenReturn(customerModel);


        // WHEN
        customerService.createCustomer(requestCustomerDto);

        // THEN
        verify(addressValidator).validate(addressDto);
        verify(accountantValidator).validateExistsAndNotDeleted(ACCOUNTANT_ID_TEST__1003);
        verify(cityRepository).findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        verify(customerMapper).mapToCustomerModel(requestCustomerDto, city, accountantModel);
        verify(accountantRepository).findById(ACCOUNTANT_ID_TEST__1003);
        verify(customerRepository).save(customerModel);

    }

    @Test
    public void testGetCustomers() {
        // GIVEN
        CustomerModel customer1 = mock(CustomerModel.class);
        CustomerModel customer2 = mock(CustomerModel.class);
        List<CustomerModel> mockCustomers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAllByDeletedFalse()).thenReturn(mockCustomers);
        ResponseCustomerDto customerDto1 = mock(ResponseCustomerDto.class);
        ResponseCustomerDto customerDto2 = mock(ResponseCustomerDto.class);
        when(customerMapper.mapToResponseCustomerDto(customer1)).thenReturn(customerDto1);
        when(customerMapper.mapToResponseCustomerDto(customer2)).thenReturn(customerDto2);

        // WHEN
        List<ResponseCustomerDto> result = customerService.getCustomers();

        // THEN
        assertEquals(2, result.size());
        assertEquals(customerDto1, result.get(0));
        assertEquals(customerDto2, result.get(1));

        verify(customerRepository).findAllByDeletedFalse();
        verify(customerMapper).mapToResponseCustomerDto(customer1);
        verify(customerMapper).mapToResponseCustomerDto(customer2);
    }

    @Test
    public void getCustomerByIdTest() {
        // GIVEN
        CustomerModel customer1 = mock(CustomerModel.class);
        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customer1));
        ResponseCustomerDto customerDto1 = mock(ResponseCustomerDto.class);
        when(customerMapper.mapToResponseCustomerDto(customer1)).thenReturn(customerDto1);

        // WHEN
        ResponseCustomerDto result = customerService.getCustomerById(CUSTOMER_ID_TEST__1004);

        // THEN
        assertNotNull(result);
        assertEquals(customerDto1, result);
        verify(customerRepository).findById(CUSTOMER_ID_TEST__1004);
        verify(customerMapper).mapToResponseCustomerDto(customer1);
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
    }

    @Test
    public void testGetCustomersByAccountantId() {
        // GIVEN
        CustomerModel customer1 = mock(CustomerModel.class);
        CustomerModel customer2 = mock(CustomerModel.class);
        List<CustomerModel> mockCustomers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAllByAccountantIdAndDeletedFalse(ACCOUNTANT_ID_TEST__1003)).thenReturn(mockCustomers);
        ResponseCustomerDto customerDto1 = mock(ResponseCustomerDto.class);
        ResponseCustomerDto customerDto2 = mock(ResponseCustomerDto.class);
        when(customerMapper.mapToResponseCustomerDto(customer1)).thenReturn(customerDto1);
        when(customerMapper.mapToResponseCustomerDto(customer2)).thenReturn(customerDto2);

        // WHEN
        List<ResponseCustomerDto> result = customerService.getCustomersByAccountantId(ACCOUNTANT_ID_TEST__1003);

        // THEN
        assertEquals(2, result.size());
        assertEquals(customerDto1, result.get(0));
        assertEquals(customerDto2, result.get(1));

        verify(customerRepository).findAllByAccountantIdAndDeletedFalse(ACCOUNTANT_ID_TEST__1003);
        verify(customerMapper).mapToResponseCustomerDto(customer1);
        verify(customerMapper).mapToResponseCustomerDto(customer2);
        verify(accountantValidator).validateExistsAndNotDeleted(ACCOUNTANT_ID_TEST__1003);
    }

    @Test
    public void updateCustomerTest() {
        // GIVEN
        AddressModel addressModel = mock(AddressModel.class);
        when(addressModel.getId()).thenReturn(ADDRESS_ID_TEST__1002);
        AccountantModel accountantModel = mock(AccountantModel.class);
        CustomerModel customerModel = CustomerModel.builder()
                .id(CUSTOMER_ID_TEST__1004)
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST)
                .email(CUSTOMER_EMAIL_TEST)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST)
                .phone(CUSTOMER_PHONE_TEST)
                .fax(CUSTOMER_FAX_TEST)
                .address(addressModel)
                .accountant(accountantModel)
                .build();
        UpdateCustomerDto updateCustomerDto = mock(UpdateCustomerDto.class);
        AddressDto addressDto = mock(AddressDto.class);
        when(updateCustomerDto.getAddress()).thenReturn(addressDto);
        String cityNameTest = "cityNameTest";
        when(addressDto.getCity()).thenReturn(cityNameTest);
        City city = mock(City.class);
        when(cityRepository.findOneByNameIgnoreCase(cityNameTest)).thenReturn(Optional.of(city));
        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customerModel));

        when(customerMapper.mapToCustomerModel(updateCustomerDto, city, ADDRESS_ID_TEST__1002,accountantModel)).thenReturn(customerModel);

        // WHEN
        customerService.updateCustomer(updateCustomerDto, CUSTOMER_ID_TEST__1004);

        // THEN
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
        verify(customerMapper).mapToCustomerModel(updateCustomerDto, city, ADDRESS_ID_TEST__1002,accountantModel);
        verify(addressValidator).validate(addressDto);
        verify(cityRepository).findOneByNameIgnoreCase(cityNameTest);
        verify(customerRepository).save(customerModel);
        verify(customerRepository).findById(CUSTOMER_ID_TEST__1004);

    }

    @Test
    public void deleteCustomerTest() {
        // WHEN
        customerService.deleteCustomer(CUSTOMER_ID_TEST__1004);

        // THEN
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
        verify(customerRepository).logicalDeleteById(CUSTOMER_ID_TEST__1004);
        verify(invoiceService).deleteCustomerInvoices(CUSTOMER_ID_TEST__1004);
        verify(quotationService).deleteCustomerQuotations(CUSTOMER_ID_TEST__1004);
        verify(productService).deleteCustomerProducts(CUSTOMER_ID_TEST__1004);
        verify(expenseReportService).deleteCustomerExpenseReports(CUSTOMER_ID_TEST__1004);
        verify(clientService).deleteCustomerClients(CUSTOMER_ID_TEST__1004);
    }

}
