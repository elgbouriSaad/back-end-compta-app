package dot.compta.backend.services.client;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.mappers.client.ClientMapper;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.services.invoice.InvoiceService;
import dot.compta.backend.services.quotation.QuotationService;
import dot.compta.backend.validators.address.AddressValidator;
import dot.compta.backend.validators.client.ClientValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dot.compta.backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerValidator customerValidator;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private AddressValidator addressValidator;

    @Mock
    private ClientValidator clientValidator;
    
    @Mock
    private QuotationService quotationService;
    
    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(customerRepository,
                clientRepository,
                cityRepository,
                clientMapper,
                addressValidator,
                customerValidator,
                clientValidator,
                quotationService,
                invoiceService
        );
    }

    @Test
    void createClientTest() {
        // GIVEN
        RequestClientDto requestClientDto = mock(RequestClientDto.class);
        AddressDto addressDto = mock(AddressDto.class);
        when(requestClientDto.getAddress()).thenReturn(addressDto);
        when(addressDto.getCity()).thenReturn(CITY_NAME_TEST__RABAT);
        City city = mock(City.class);
        when(cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__RABAT)).thenReturn(Optional.of(city));
        ClientModel clientModel = mock(ClientModel.class);
        when(requestClientDto.getCustomerId()).thenReturn(CUSTOMER_ID_TEST__1004);
        CustomerModel customer = mock(CustomerModel.class);
        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customer));
        when(clientMapper.mapToClientModel(requestClientDto, city, customer)).thenReturn(clientModel);


        // WHEN
        clientService.createClient(requestClientDto);

        // THEN
        verify(addressValidator).validate(addressDto);
        verify(cityRepository).findOneByNameIgnoreCase(CITY_NAME_TEST__RABAT);
        verify(clientMapper).mapToClientModel(requestClientDto, city, customer);
        verify(clientRepository).save(clientModel);
        verify(customerRepository).findById(CUSTOMER_ID_TEST__1004);
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);

    }

    @Test
    public void testGetClients() {
        // GIVEN
        ClientModel client1 = mock(ClientModel.class);
        ClientModel client2 = mock(ClientModel.class);
        List<ClientModel> mockClients = Arrays.asList(client1, client2);
        when(clientRepository.findAllByDeletedFalse()).thenReturn(mockClients);
        ResponseClientDto clientDto1 = mock(ResponseClientDto.class);
        ResponseClientDto clientDto2 = mock(ResponseClientDto.class);
        when(clientMapper.mapToResponseClientDto(client1)).thenReturn(clientDto1);
        when(clientMapper.mapToResponseClientDto(client2)).thenReturn(clientDto2);

        // WHEN
        List<ResponseClientDto> result = clientService.getClients();

        // THEN
        assertEquals(2, result.size());
        assertEquals(clientDto1, result.get(0));
        assertEquals(clientDto2, result.get(1));

        verify(clientRepository).findAllByDeletedFalse();
        verify(clientMapper).mapToResponseClientDto(client1);
        verify(clientMapper).mapToResponseClientDto(client2);
    }

    @Test
    public void getClientByIdTest() {
        // GIVEN
        ClientModel client1 = mock(ClientModel.class);
        when(clientRepository.findById(CLIENT_ID_TEST__1005)).thenReturn(Optional.of(client1));
        ResponseClientDto clientDto1 = mock(ResponseClientDto.class);
        when(clientMapper.mapToResponseClientDto(client1)).thenReturn(clientDto1);

        // WHEN
        ResponseClientDto result = clientService.getClientById(CLIENT_ID_TEST__1005);

        // THEN
        assertNotNull(result);
        assertEquals(clientDto1, result);
        verify(clientRepository).findById(CLIENT_ID_TEST__1005);
        verify(clientMapper).mapToResponseClientDto(client1);
        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
    }

    @Test
    public void testGetClientsByCustomerId() {
        // GIVEN
        ClientModel client1 = mock(ClientModel.class);
        ClientModel client2 = mock(ClientModel.class);
        List<ClientModel> mockClients = Arrays.asList(client1, client2);
        when(clientRepository.findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004)).thenReturn(mockClients);
        ResponseClientDto clientDto1 = mock(ResponseClientDto.class);
        ResponseClientDto clientDto2 = mock(ResponseClientDto.class);
        when(clientMapper.mapToResponseClientDto(client1)).thenReturn(clientDto1);
        when(clientMapper.mapToResponseClientDto(client2)).thenReturn(clientDto2);

        // WHEN
        List<ResponseClientDto> result = clientService.getClientsByCustomerId(CUSTOMER_ID_TEST__1004);

        // THEN
        assertEquals(2, result.size());
        assertEquals(clientDto1, result.get(0));
        assertEquals(clientDto2, result.get(1));

        verify(clientRepository).findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004);
        verify(clientMapper).mapToResponseClientDto(client1);
        verify(clientMapper).mapToResponseClientDto(client2);
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
    }

    @Test
    public void updateClientTest() {
        // GIVEN
        AddressModel addressModel = mock(AddressModel.class);
        when(addressModel.getId()).thenReturn(ADDRESS_ID_TEST__1002);
        CustomerModel customerModel = CustomerModel.builder()
                .id(CUSTOMER_ID_TEST__1004)
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST)
                .email(CUSTOMER_EMAIL_TEST)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST)
                .phone(CUSTOMER_PHONE_TEST)
                .fax(CUSTOMER_FAX_TEST)
                .address(addressModel)
                .build();
        ClientModel clientModel = ClientModel.builder()
                .id(CLIENT_ID_TEST__1005)
                .rc(CLIENT_RC_TEST__102)
                .companyName(CLIENT_SOCIAL_PURPOSE_TEST)
                .email(CLIENT_EMAIL_TEST)
                .mobilePhone(CLIENT_MOBILE_PHONE_TEST)
                .phone(CLIENT_PHONE_TEST)
                .fax(CLIENT_FAX_TEST)
                .address(addressModel)
                .customer(customerModel)
                .build();
        UpdateClientDto updateClientDto = mock(UpdateClientDto.class);
        AddressDto addressDto = mock(AddressDto.class);
        when(updateClientDto.getAddress()).thenReturn(addressDto);
        String cityNameTest = "cityNameTest";
        when(addressDto.getCity()).thenReturn(cityNameTest);
        City city = mock(City.class);
        when(cityRepository.findOneByNameIgnoreCase(cityNameTest)).thenReturn(Optional.of(city));
        when(clientMapper.mapToClientModel(updateClientDto, city, customerModel, ADDRESS_ID_TEST__1002)).thenReturn(clientModel);
        when(clientRepository.findById(CLIENT_ID_TEST__1005)).thenReturn(Optional.of(clientModel));

        // WHEN
        clientService.updateClient(updateClientDto, CLIENT_ID_TEST__1005);

        // THEN
        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
        verify(addressValidator).validate(addressDto);
        verify(clientMapper).mapToClientModel(updateClientDto, city, customerModel, ADDRESS_ID_TEST__1002);
        verify(cityRepository).findOneByNameIgnoreCase(cityNameTest);
        verify(clientRepository).save(clientModel);
        verify(clientRepository).findById(CLIENT_ID_TEST__1005);
    }

    @Test
    public void deleteClientTest() {
        // WHEN
        clientService.deleteClient(CLIENT_ID_TEST__1005);

        // THEN
        verify(clientValidator).validateExistsAndNotDeleted(CLIENT_ID_TEST__1005);
        verify(clientRepository).logicalDeleteById(CLIENT_ID_TEST__1005);
        verify(quotationService).deleteClientQuotations(CLIENT_ID_TEST__1005);
        verify(invoiceService).deleteClientInvoices(CLIENT_ID_TEST__1005);
    }

}
