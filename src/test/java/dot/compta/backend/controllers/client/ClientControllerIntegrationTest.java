package dot.compta.backend.controllers.client;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import dot.compta.backend.repositories.address.AddressRepository;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import dot.compta.backend.repositories.invoiceProduct.InvoiceProductRepository;
import dot.compta.backend.repositories.product.ProductRepository;
import dot.compta.backend.repositories.quotation.QuotationRepository;
import dot.compta.backend.repositories.quotationProduct.QuotationProductRepository;
import dot.compta.backend.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static dot.compta.backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ActiveProfiles("dev-h2")
public class ClientControllerIntegrationTest extends BaseClientControllerTest implements BaseControllerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private QuotationProductRepository quotationProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        assertDbState();
        mvc = standaloneSetup(clientController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }

    @Test
    void successful_client_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel savedModel = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), savedModel);
        createClientUsingEndpoint(customer, "");

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());

        List<ClientModel> clients = clientRepository.findAll();
        ClientModel clientModel = clients.get(0);
        assertFalse(clientModel.isDeleted());
        assertEquals(customer.getId(), clientModel.getCustomer().getId());
        assertEquals(CLIENT_EMAIL_TEST, clientModel.getEmail());
        assertEquals(CLIENT_RC_TEST__102, clientModel.getRc());
        assertEquals(CLIENT_SOCIAL_PURPOSE_TEST, clientModel.getCompanyName());
        assertEquals(CLIENT_MOBILE_PHONE_TEST, clientModel.getMobilePhone());
        assertEquals(CLIENT_PHONE_TEST, clientModel.getPhone());
        assertEquals(CLIENT_FAX_TEST, clientModel.getFax());
        AddressModel address = clientModel.getAddress();
        assertEquals(CLIENT_POSTAL_CODE_TEST__202, address.getPostalCode());
        assertEquals(CLIENT_PRIMARY_ADDRESS_TEST, address.getPrimaryAddress());
        assertEquals(CLIENT_SECONDARY_ADDRESS_TEST, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__CASABLANCA, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());

        removeAddedElement(clientModel);
    }

    @Test
    void successful_multiple_client_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel savedModel = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), savedModel);
        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            createClientUsingEndpoint(customer, suffix);
        }

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(5, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(3, clientRepository.count());

        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            Optional<ClientModel> optionalClient = clientRepository.findOneByCompanyNameIgnoreCase(CLIENT_SOCIAL_PURPOSE_TEST + suffix);
            assertTrue(optionalClient.isPresent());
            removeAddedElements(optionalClient.get());
        }

        Optional<CustomerModel> optionalCustomer = customerRepository.findOneByCompanyNameIgnoreCase(CUSTOMER_SOCIAL_PURPOSE_TEST);
        assertTrue(optionalCustomer.isPresent());
        removeAddedElement(optionalCustomer.get());
    }

    @Test
    void successful_get_clients_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel savedModel = createClientInDb(city.get(), customer);
        mvc.perform(get(APIConstants.CLIENTS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ClientControllerIntegration_getClients.json"), false));

        removeAddedElement(savedModel);
    }

    private ClientModel createClientInDb(City city, CustomerModel customer) {
        return TestUtils.createClientInDb(clientRepository, city, customer);
    }

    @Test
    void successful_get_client_byId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel savedModel = createClientInDb(city.get(), customer);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.CLIENTS_URL + "/" + savedModel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ClientControllerIntegration_getClientById.json"), false));

        removeAddedElement(savedModel);
    }

    @Test
    void successful_get_client_byCustomerId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel savedModel = createClientInDb(city.get(), customer);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.CLIENTS_URL + APIConstants.CUSTOMER_CLIENTS_URL + savedModel.getCustomer().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ClientControllerIntegration_getClientByCustomerId.json"), false));

        removeAddedElement(savedModel);
    }

    @Test
    public void successful_update_client_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel savedModel = createClientInDb(city.get(), customer);
        String updateSuffix = "Updated";
        UpdateClientDto updateClientDto = buildUpdateClientDto(updateSuffix);
        int updatedRc = 301;
        updateClientDto.setRc(updatedRc);
        updateClientDto.getAddress().setCity(CITY_NAME_TEST__RABAT);
        String content = objectMapper.writeValueAsString(updateClientDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.CLIENTS_URL + "/" + savedModel.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());

        List<ClientModel> clients = clientRepository.findAll();
        ClientModel client = clients.get(0);
        assertFalse(client.isDeleted());
        assertEquals(customer.getId(), client.getCustomer().getId());
        assertEquals(CLIENT_EMAIL_TEST + updateSuffix, client.getEmail());
        assertEquals(updatedRc, client.getRc());
        assertEquals(CLIENT_SOCIAL_PURPOSE_TEST + updateSuffix, client.getCompanyName());
        assertEquals(CLIENT_MOBILE_PHONE_TEST + updateSuffix, client.getMobilePhone());
        assertEquals(CLIENT_PHONE_TEST + updateSuffix, client.getPhone());
        assertEquals(CLIENT_FAX_TEST + updateSuffix, client.getFax());
        AddressModel address = client.getAddress();
        assertEquals(CLIENT_POSTAL_CODE_TEST__202, address.getPostalCode());
        assertEquals(CLIENT_PRIMARY_ADDRESS_TEST + updateSuffix, address.getPrimaryAddress());
        assertEquals(CLIENT_SECONDARY_ADDRESS_TEST + updateSuffix, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__RABAT, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());

        removeAddedElement(savedModel);
    }

    @Test
    public void successful_delete_client_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel savedModel = createClientInDb(city.get(), customer);
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.CLIENTS_URL + "/" + savedModel.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        List<ClientModel> clients = clientRepository.findAll();
        ClientModel client = clients.get(0);
        assertTrue(client.isDeleted());
        assertEquals(CLIENT_EMAIL_TEST, client.getEmail());
        assertEquals(CLIENT_RC_TEST__102, client.getRc());
        assertEquals(CLIENT_SOCIAL_PURPOSE_TEST, client.getCompanyName());
        assertEquals(CLIENT_MOBILE_PHONE_TEST, client.getMobilePhone());
        assertEquals(CLIENT_PHONE_TEST, client.getPhone());
        assertEquals(CLIENT_FAX_TEST, client.getFax());
        AddressModel address = client.getAddress();
        assertEquals(CLIENT_POSTAL_CODE_TEST__202, address.getPostalCode());
        assertEquals(CLIENT_PRIMARY_ADDRESS_TEST, address.getPrimaryAddress());
        assertEquals(CLIENT_SECONDARY_ADDRESS_TEST, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__CASABLANCA, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());

        removeAddedElement(savedModel);
    }
    
    @Test
    public void successful_delete_client_with_quotation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        createQuotationProductInDb(quotation, product);
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.CLIENTS_URL + "/" + client.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, quotationRepository.count());
        assertEquals(1, quotationProductRepository.count());
        List<ClientModel> clients = clientRepository.findAll();
        ClientModel storedClient = clients.get(0);
        assertTrue(storedClient.isDeleted());
        assertEquals(CLIENT_EMAIL_TEST, storedClient.getEmail());
        assertEquals(CLIENT_RC_TEST__102, storedClient.getRc());
        assertEquals(CLIENT_SOCIAL_PURPOSE_TEST, storedClient.getCompanyName());
        assertEquals(CLIENT_MOBILE_PHONE_TEST, storedClient.getMobilePhone());
        assertEquals(CLIENT_PHONE_TEST, storedClient.getPhone());
        assertEquals(CLIENT_FAX_TEST, storedClient.getFax());
        AddressModel address = storedClient.getAddress();
        assertEquals(CLIENT_POSTAL_CODE_TEST__202, address.getPostalCode());
        assertEquals(CLIENT_PRIMARY_ADDRESS_TEST, address.getPrimaryAddress());
        assertEquals(CLIENT_SECONDARY_ADDRESS_TEST, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__CASABLANCA, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());
        List<QuotationModel> quotations = quotationRepository.findAll();
        QuotationModel storedQuotation = quotations.get(0);
        assertTrue(storedQuotation.isDeleted());
        List<QuotationProductModel> quotationProducts = quotationProductRepository.findAll();
        QuotationProductModel storedQuotationProduct = quotationProducts.get(0);
        assertTrue(storedQuotationProduct.isDeleted());

        removeAddedElement(storedQuotationProduct);
        removeAddedElement(storedQuotation);
        removeAddedElement(product);
        removeAddedElement(storedClient);
    }
    
    @Test
    public void successful_delete_client_with_invoice_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        createInvoiceProductInDb(invoice, product);
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.CLIENTS_URL + "/" + client.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(1, invoiceProductRepository.count());
        List<ClientModel> clients = clientRepository.findAll();
        ClientModel storedClient = clients.get(0);
        assertTrue(storedClient.isDeleted());
        assertEquals(CLIENT_EMAIL_TEST, storedClient.getEmail());
        assertEquals(CLIENT_RC_TEST__102, storedClient.getRc());
        assertEquals(CLIENT_SOCIAL_PURPOSE_TEST, storedClient.getCompanyName());
        assertEquals(CLIENT_MOBILE_PHONE_TEST, storedClient.getMobilePhone());
        assertEquals(CLIENT_PHONE_TEST, storedClient.getPhone());
        assertEquals(CLIENT_FAX_TEST, storedClient.getFax());
        AddressModel address = storedClient.getAddress();
        assertEquals(CLIENT_POSTAL_CODE_TEST__202, address.getPostalCode());
        assertEquals(CLIENT_PRIMARY_ADDRESS_TEST, address.getPrimaryAddress());
        assertEquals(CLIENT_SECONDARY_ADDRESS_TEST, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__CASABLANCA, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        InvoiceModel storedInvoice = invoices.get(0);
        assertTrue(storedInvoice.isDeleted());
        List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAll();
        InvoiceProductModel storedInvoiceProduct = invoiceProducts.get(0);
        assertTrue(storedInvoiceProduct.isDeleted());

        removeAddedElement(storedInvoiceProduct);
        removeAddedElement(storedInvoice);
        removeAddedElement(product);
        removeAddedElement(storedClient);
    }
    
    private void removeAddedElement(InvoiceModel invoice) {
        TestUtils.removeAddedElement(invoiceRepository, invoice.getId());
    }
    
    private void removeAddedElement(InvoiceProductModel invoiceProduct) {
        TestUtils.removeAddedElement(invoiceProductRepository, invoiceProduct.getId());
    }
    
    private void removeAddedElement(ProductModel product) {
        TestUtils.removeAddedElement(productRepository, product.getId());
    }
    
    private void removeAddedElement(QuotationModel quotation) {
        TestUtils.removeAddedElement(quotationRepository, quotation.getId());
    }
    
    private void removeAddedElement(QuotationProductModel quotationProduct) {
        TestUtils.removeAddedElement(quotationProductRepository, quotationProduct.getId());
    }

    private void removeAddedElements(ClientModel clientModel) {
        clientRepository.deleteById(clientModel.getId());
        addressRepository.deleteById(clientModel.getAddress().getId());
    }

    private void removeAddedElement(ClientModel clientModel) {
        TestUtils.removeAddedElement(clientRepository, addressRepository, clientModel);
        removeAddedElement(clientModel.getCustomer());
    }

    private void removeAddedElement(CustomerModel customerModel) {
        TestUtils.removeAddedElement(customerRepository, addressRepository, customerModel);
        TestUtils.removeAddedElement(accountantRepository, addressRepository, customerModel.getAccountant());
    }
    
    private InvoiceModel createInvoiceInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createInvoiceInDb(invoiceRepository, customer, client);
    }
    
    private InvoiceProductModel createInvoiceProductInDb(InvoiceModel invoice, ProductModel product) {
        return TestUtils.createInvoiceProductInDb(invoiceProductRepository, invoice, product);
    }
    
    private QuotationModel createQuotationInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createQuotationInDb(quotationRepository, customer, client);
    }
    
    private ProductModel createProductInDb(CustomerModel customer) {
        return TestUtils.createProductInDb(productRepository, customer);
    }
    
    private QuotationProductModel createQuotationProductInDb(QuotationModel quotation, ProductModel product) {
        return TestUtils.createQuotationProductInDb(quotationProductRepository, quotation, product);
    }

    private AccountantModel createAccountantInDb(City city) {
        return TestUtils.createAccountantInDb(accountantRepository, city);
    }

    private CustomerModel createCustomerInDb(City city, AccountantModel savedModel) {
        return TestUtils.createCustomerInDb(customerRepository, city, savedModel);
    }

    private void createClientUsingEndpoint(CustomerModel customer, String suffix) throws Exception {
        RequestClientDto requestClientDto = buildRequestClientDto(suffix);
        requestClientDto.setCustomerId(customer.getId());
        String content = objectMapper.writeValueAsString(requestClientDto);
        mvc.perform(post(APIConstants.CLIENTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    @Override
    public void assertDbState() {
        assertCountryTableInitialState(countryRepository);
        assertCityTableInitialState(cityRepository);
        assertAddressTableInitialState(addressRepository);
        assertAccountantTableInitialState(accountantRepository);
        assertCustomerTableInitialState(customerRepository);
        assertClientTableInitialState(clientRepository);
        assertQuotationTableInitialState(quotationRepository);
        assertQuotationProductTableInitialState(quotationProductRepository);
    }
}
