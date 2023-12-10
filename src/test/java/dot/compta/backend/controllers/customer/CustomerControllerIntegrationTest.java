package dot.compta.backend.controllers.customer;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;
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
import dot.compta.backend.repositories.expenseReport.ExpenseReportRepository;
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
public class CustomerControllerIntegrationTest extends BaseCustomerControllerTest implements BaseControllerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ExpenseReportRepository expenseReportRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private QuotationProductRepository quotationProductRepository;
    @Autowired
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        assertDbState();
        mvc = standaloneSetup(customerController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }

    @Test
    void successful_customer_creation_process_test() throws Exception {

        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel savedModel = createAccountantInDb(city.get());
        createCustomerUsingEndpoint(savedModel, "");

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());

        List<CustomerModel> customers = customerRepository.findAll();
        CustomerModel customerModel = customers.get(0);
        assertFalse(customerModel.isDeleted());
        assertEquals(CUSTOMER_EMAIL_TEST, customerModel.getEmail());
        assertEquals(CUSTOMER_RC_TEST__101, customerModel.getRc());
        assertEquals(CUSTOMER_SOCIAL_PURPOSE_TEST, customerModel.getCompanyName());
        assertEquals(CUSTOMER_MOBILE_PHONE_TEST, customerModel.getMobilePhone());
        assertEquals(CUSTOMER_PHONE_TEST, customerModel.getPhone());
        assertEquals(CUSTOMER_FAX_TEST, customerModel.getFax());
        AddressModel address = customerModel.getAddress();
        assertEquals(CUSTOMER_POSTAL_CODE_TEST__201, address.getPostalCode());
        assertEquals(CUSTOMER_PRIMARY_ADDRESS_TEST, address.getPrimaryAddress());
        assertEquals(CUSTOMER_SECONDARY_ADDRESS_TEST, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__CASABLANCA, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());

        removeAllAddedElements(customerModel);
    }

    @Test
    void successful_multiple_customer_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel savedModel = createAccountantInDb(city.get());

        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            createCustomerUsingEndpoint(savedModel, suffix);
        }

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(4, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(3, customerRepository.count());

        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            Optional<CustomerModel> optionalCustomer = customerRepository.findOneByCompanyNameIgnoreCase(CUSTOMER_SOCIAL_PURPOSE_TEST + suffix);
            assertTrue(optionalCustomer.isPresent());
            removeAddedElement(optionalCustomer.get());
        }

        Optional<AccountantModel> optionalAccountant = accountantRepository.findOneByCompanyNameIgnoreCase(ACCOUNTANT_SOCIAL_PURPOSE_TEST);
        assertTrue(optionalAccountant.isPresent());
        removeAddedElement(optionalAccountant.get());
    }

    @Test
    void successful_get_customers_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel savedModel = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), savedModel);
        mvc.perform(get(APIConstants.CUSTOMERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CustomerControllerIntegration_getCustomers.json"), false));

        removeAllAddedElements(customer);
    }

    private CustomerModel createCustomerInDb(City city, AccountantModel savedModel) {
        return TestUtils.createCustomerInDb(customerRepository, city, savedModel);
    }

    @Test
    void successful_get_customer_byId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel savedModel = createCustomerInDb(city.get(), accountant);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.CUSTOMERS_URL + "/" + savedModel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CustomerControllerIntegration_getCustomerById.json"), false));

        removeAllAddedElements(savedModel);
    }

    @Test
    void successful_get_customers_byAccountantId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel savedModel = createCustomerInDb(city.get(), accountant);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.CUSTOMERS_URL + APIConstants.ACCOUNTANT_CUSTOMERS_URL + savedModel.getAccountant().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CustomerControllerIntegration_getCustomersByAccountantId.json"), false));

        removeAllAddedElements(savedModel);
    }

    @Test
    public void successful_update_customer_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel savedModel = createCustomerInDb(city.get(), accountant);
        String updateSuffix = "Up";
        UpdateCustomerDto updateCustomerDto = buildUpdateCustomerDto(updateSuffix);
        int updatedRc = 301;
        updateCustomerDto.setRc(updatedRc);
        updateCustomerDto.getAddress().setCity(CITY_NAME_TEST__RABAT);
        String content = objectMapper.writeValueAsString(updateCustomerDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.CUSTOMERS_URL + "/" + savedModel.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());

        List<CustomerModel> customers = customerRepository.findAll();
        CustomerModel customerModel = customers.get(0);
        assertFalse(customerModel.isDeleted());
        assertEquals(CUSTOMER_EMAIL_TEST + updateSuffix, customerModel.getEmail());
        assertEquals(updatedRc, customerModel.getRc());
        assertEquals(CUSTOMER_SOCIAL_PURPOSE_TEST + updateSuffix, customerModel.getCompanyName());
        assertEquals(CUSTOMER_MOBILE_PHONE_TEST + updateSuffix, customerModel.getMobilePhone());
        assertEquals(CUSTOMER_PHONE_TEST + updateSuffix, customerModel.getPhone());
        assertEquals(CUSTOMER_FAX_TEST + updateSuffix, customerModel.getFax());
        AddressModel address = customerModel.getAddress();
        assertEquals(CUSTOMER_POSTAL_CODE_TEST__201, address.getPostalCode());
        assertEquals(CUSTOMER_PRIMARY_ADDRESS_TEST + updateSuffix, address.getPrimaryAddress());
        assertEquals(CUSTOMER_SECONDARY_ADDRESS_TEST + updateSuffix, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__RABAT, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());

        removeAllAddedElements(savedModel);
    }
    
    @Test
    public void successful_delete_customer_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel savedModel = createCustomerInDb(city.get(), accountant);
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.CUSTOMERS_URL + "/" + savedModel.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        List<CustomerModel> customers = customerRepository.findAll();
        CustomerModel customerModel = customers.get(0);
        assertTrue(customerModel.isDeleted());
        assertEquals(CUSTOMER_EMAIL_TEST, customerModel.getEmail());
        assertEquals(CUSTOMER_RC_TEST__101, customerModel.getRc());
        assertEquals(CUSTOMER_SOCIAL_PURPOSE_TEST, customerModel.getCompanyName());
        assertEquals(CUSTOMER_MOBILE_PHONE_TEST, customerModel.getMobilePhone());
        assertEquals(CUSTOMER_PHONE_TEST, customerModel.getPhone());
        assertEquals(CUSTOMER_FAX_TEST, customerModel.getFax());
        AddressModel address = customerModel.getAddress();
        assertEquals(CUSTOMER_POSTAL_CODE_TEST__201, address.getPostalCode());
        assertEquals(CUSTOMER_PRIMARY_ADDRESS_TEST, address.getPrimaryAddress());
        assertEquals(CUSTOMER_SECONDARY_ADDRESS_TEST, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__CASABLANCA, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());

        removeAllAddedElements(savedModel);
    }

    @Test
    public void successful_delete_customer_with_relations_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ExpenseReportModel expenseReport = createExpenseReportInDb(customer);
        ClientModel client = createClientInDb(city.get(), customer);
        ProductModel product = createProductInDb(customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        QuotationModel quotation = createQuotationInDb(customer, client);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.CUSTOMERS_URL + "/" + customer.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, expenseReportRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(1, invoiceProductRepository.count());
        assertEquals(1, quotationRepository.count());
        assertEquals(1, quotationProductRepository.count());
        List<CustomerModel> customers = customerRepository.findAll();
        CustomerModel customerModel = customers.get(0);
        assertTrue(customerModel.isDeleted());
        assertEquals(CUSTOMER_EMAIL_TEST, customerModel.getEmail());
        assertEquals(CUSTOMER_RC_TEST__101, customerModel.getRc());
        assertEquals(CUSTOMER_SOCIAL_PURPOSE_TEST, customerModel.getCompanyName());
        assertEquals(CUSTOMER_MOBILE_PHONE_TEST, customerModel.getMobilePhone());
        assertEquals(CUSTOMER_PHONE_TEST, customerModel.getPhone());
        assertEquals(CUSTOMER_FAX_TEST, customerModel.getFax());
        AddressModel address = customerModel.getAddress();
        assertEquals(CUSTOMER_POSTAL_CODE_TEST__201, address.getPostalCode());
        assertEquals(CUSTOMER_PRIMARY_ADDRESS_TEST, address.getPrimaryAddress());
        assertEquals(CUSTOMER_SECONDARY_ADDRESS_TEST, address.getSecondaryAddress());
        assertEquals(CITY_NAME_TEST__CASABLANCA, address.getCity().getName());
        assertEquals(COUNTRY_NAME_TEST__MAROC, address.getCity().getCountry().getName());
        
        List<ClientModel> clients = clientRepository.findAll();
        ClientModel storedClient = clients.get(0);
        assertTrue(storedClient.isDeleted());
        
        List<ProductModel> products = productRepository.findAll();
        ProductModel storedProduct = products.get(0);
        assertTrue(storedProduct.isDeleted());
        
        List<ExpenseReportModel> expenseReports = expenseReportRepository.findAll();
        ExpenseReportModel storedExpenseReport = expenseReports.get(0);
        assertTrue(storedExpenseReport.isDeleted());
        
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        InvoiceModel getInvoice = invoices.get(0);
        assertTrue(getInvoice.isDeleted());
        
        List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAll();
        InvoiceProductModel getInvoiceProduct = invoiceProducts.get(0);
        assertTrue(getInvoiceProduct.isDeleted());
        
        List<QuotationModel> quotations = quotationRepository.findAll();
        QuotationModel getQuotation = quotations.get(0);
        assertTrue(getQuotation.isDeleted());
      
        List<QuotationProductModel> quotationProducts = quotationProductRepository.findAll();
        QuotationProductModel getQuotationProduct = quotationProducts.get(0);
        assertTrue(getQuotationProduct.isDeleted());
        
        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(invoiceProduct);
        removeAddedElement(invoice);
        removeAddedElement(product);
        removeAddedElement(expenseReport);
        removeAllAddedElements(client);
    }

    private void createCustomerUsingEndpoint(AccountantModel accountant, String suffix) throws Exception {
        RequestCustomerDto requestCustomerDto = buildRequestCustomerDto(accountant.getId(), suffix);
        String content = objectMapper.writeValueAsString(requestCustomerDto);
        mvc.perform(post(APIConstants.CUSTOMERS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    private AccountantModel createAccountantInDb(City city) {
        return TestUtils.createAccountantInDb(accountantRepository, city);
    }
    
    private ExpenseReportModel createExpenseReportInDb(CustomerModel customer) {
        return TestUtils.createExpenseReportInDb(expenseReportRepository, customer);
    }
    
    private ClientModel createClientInDb(City city, CustomerModel customer) {
        return TestUtils.createClientInDb(clientRepository, city, customer);
    }
    
    private ProductModel createProductInDb(CustomerModel customer) {
        return TestUtils.createProductInDb(productRepository, customer);
    }
    
    private InvoiceProductModel createInvoiceProductInDb(InvoiceModel invoice, ProductModel product) {
        return TestUtils.createInvoiceProductInDb(invoiceProductRepository, invoice, product);
    }
    
    private InvoiceModel createInvoiceInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createInvoiceInDb(invoiceRepository, customer, client);
    }
    
    private QuotationModel createQuotationInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createQuotationInDb(quotationRepository, customer, client);
    }
    
    private QuotationProductModel createQuotationProductInDb(QuotationModel quotation, ProductModel product) {
        return TestUtils.createQuotationProductInDb(quotationProductRepository, quotation, product);
    }
    
    private void removeAddedElement(QuotationModel quotation) {
        TestUtils.removeAddedElement(quotationRepository, quotation.getId());
    }

    private void removeAddedElement(QuotationProductModel quotationProduct) {
        TestUtils.removeAddedElement(quotationProductRepository, quotationProduct.getId());
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
    
    private void removeAddedElement(ExpenseReportModel expenseReport) {
        TestUtils.removeAddedElement(expenseReportRepository, expenseReport.getId());
    }
    
    private void removeAllAddedElements(ClientModel client) {
        TestUtils.removeAddedElement(clientRepository, addressRepository, client);
        removeAllAddedElements(client.getCustomer());
    }

    private void removeAllAddedElements(CustomerModel customerModel) {
        removeAddedElement(customerModel);
        removeAddedElement(customerModel.getAccountant());
    }

    private void removeAddedElement(AccountantModel accountantModel) {
        TestUtils.removeAddedElement(accountantRepository, addressRepository, accountantModel);
    }

    private void removeAddedElement(CustomerModel customerModel) {
        TestUtils.removeAddedElement(customerRepository, addressRepository, customerModel);
    }

    @Override
    public void assertDbState() {
        assertCountryTableInitialState(countryRepository);
        assertCityTableInitialState(cityRepository);
        assertAddressTableInitialState(addressRepository);
        assertAccountantTableInitialState(accountantRepository);
        assertCustomerTableInitialState(customerRepository);
        assertClientTableInitialState(clientRepository);
        assertExpenseReportTableInitialState(expenseReportRepository);
        assertProductTableInitialState(productRepository);
        assertInvoiceTableInitialState(invoiceRepository);
        assertInvoiceProductTableInitialState(invoiceProductRepository);
        assertQuotationTableInitialState(quotationRepository);
        assertQuotationProductTableInitialState(quotationProductRepository);
    }
}
