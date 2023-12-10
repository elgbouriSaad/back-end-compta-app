package dot.compta.backend.controllers.invoice;

import static dot.compta.backend.utils.TestUtils.CITY_NAME_TEST__CASABLANCA;
import static dot.compta.backend.utils.TestUtils.INVOICE_PAYMENT_DELAY_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotation.QuotationModel;
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
import dot.compta.backend.utils.TestUtils;

@SpringBootTest
@ActiveProfiles("dev-h2")
public class InvoiceControllerIntegrationTest extends BaseInvoiceControllerTest implements BaseControllerIntegrationTest{
	
	@Autowired
	private QuotationRepository quotationRepository;
	@Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private InvoiceController invoiceController;
    
    @BeforeEach
    void setUp() {
        assertDbState();
        mvc = standaloneSetup(invoiceController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }
    
    @Test
    void successful_invoice_without_quotation_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        ProductModel product = createProductInDb(customer);
        ProductModel product2 = createProductInDb(customer);
        List<Integer> productIds = Arrays.asList(product.getId(), product2.getId());
        createInvoiceUsingEndpoint(customer, client, productIds);

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(2, productRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(2, invoiceProductRepository.count());

        List<InvoiceModel> invoices = invoiceRepository.findAll();
        InvoiceModel invoiceModel = invoices.get(0);
        assertEquals(InvoiceStatus.SAVED, invoiceModel.getStatus());
        assertEquals(INVOICE_PAYMENT_DELAY_TEST, invoiceModel.getPaymentDelay());

        removeAllAddedElements(invoiceModel, Arrays.asList(product, product2));
    }
    
    @Test
    void unsuccessful_invoice_without_products_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        createInvoiceWithoutProductsUsingEndpoint(customer, client);

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(0, invoiceRepository.count());

        removeAllAddedElements(client);
    }
    
    @Test
    void successful_get_invoices_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        QuotationModel quotation = createQuotationInDb(customer,client);
        InvoiceModel invoice2 = createInvoiceInDb(customer, client, quotation);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        InvoiceProductModel invoiceProduct2 = createInvoiceProductInDb(invoice2, product);
        mvc.perform(get(APIConstants.INVOICES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("InvoiceControllerIntegration_getInvoices.json"), false));

        removeAddedElement(invoiceProduct);
        removeAddedElement(invoiceProduct2);
        removeAddedElement(invoice);
        removeAddedElement(invoice2);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_get_invoice_without_quotation_byId_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        mvc.perform(get(APIConstants.INVOICES_URL + "/" + invoice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("InvoiceControllerIntegration_getInvoiceWithoutQById.json"), false));

        removeAddedElement(invoiceProduct);
        removeAddedElement(invoice);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_get_invoice_with_quotation_byId_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer,client);
        InvoiceModel invoice = createInvoiceInDb(customer, client, quotation);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        mvc.perform(get(APIConstants.INVOICES_URL + "/" + invoice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("InvoiceControllerIntegration_getInvoiceWithQById.json"), false));

        removeAddedElement(invoiceProduct);
        removeAddedElement(invoice);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_get_invoices_byCustomerId_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        QuotationModel quotation = createQuotationInDb(customer,client);
        InvoiceModel invoice2 = createInvoiceInDb(customer, client, quotation);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        InvoiceProductModel invoiceProduct2 = createInvoiceProductInDb(invoice2, product);
        mvc.perform(get(APIConstants.INVOICES_URL + APIConstants.CUSTOMER_INVOICES_URL + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("InvoiceControllerIntegration_getInvoicesByCustomerId.json"), false));

        removeAddedElement(invoiceProduct);
        removeAddedElement(invoiceProduct2);
        removeAddedElement(invoice);
        removeAddedElement(invoice2);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_get_invoices_byClientId_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        QuotationModel quotation = createQuotationInDb(customer,client);
        InvoiceModel invoice2 = createInvoiceInDb(customer, client, quotation);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        InvoiceProductModel invoiceProduct2 = createInvoiceProductInDb(invoice2, product);
        mvc.perform(get(APIConstants.INVOICES_URL + APIConstants.CLIENT_INVOICES_URL + client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("InvoiceControllerIntegration_getInvoicesByClientId.json"), false));

        removeAddedElement(invoiceProduct);
        removeAddedElement(invoiceProduct2);
        removeAddedElement(invoice);
        removeAddedElement(invoice2);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_validate_invoice_without_quotation_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.INVOICES_URL + APIConstants.INVOICE_VALIDATE_URL + invoice.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(1, invoiceProductRepository.count());
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        InvoiceModel getInvoice = invoices.get(0);
        assertEquals(InvoiceStatus.VALIDATED, getInvoice.getStatus());

        removeAddedElement(invoiceProduct);
        removeAddedElement(invoice);
        removeAddedElement(product);
        removeAllAddedElements(client);

    }
    
    @Test
    void successful_validate_invoice_with_quotation_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer,client);
        InvoiceModel invoice = createInvoiceInDb(customer, client, quotation);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.INVOICES_URL + APIConstants.INVOICE_VALIDATE_URL + invoice.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(1, invoiceProductRepository.count());
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        InvoiceModel getInvoice = invoices.get(0);
        assertEquals(InvoiceStatus.VALIDATED, getInvoice.getStatus());

        removeAddedElement(invoiceProduct);
        removeAddedElement(invoice);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);

    }
    
    @Test
    void successful_update_invoice_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        ProductModel product = createProductInDb(customer);
        ProductModel product2 = createProductInDb(customer);
        ProductModel product3 = createProductInDb(customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        createInvoiceProductInDb(invoice, product);
        createInvoiceProductInDb(invoice, product2);
        List<Integer> newProductIds = Arrays.asList(product.getId(), product3.getId());
        List<RequestProductQuantityDto> productQuantities = buildProductQuantities(newProductIds);
        UpdateInvoiceDto updateInvoiceDto = buildUpdateInvoiceDto(productQuantities);
        updateInvoiceDto.setClientId(client.getId());
        String content = objectMapper.writeValueAsString(updateInvoiceDto);

        mvc.perform(put(APIConstants.INVOICES_URL + APIConstants.INVOICE_UPDATE_URL + invoice.getId())
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
        assertEquals(3, productRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(3, invoiceProductRepository.count());

        removeAllAddedElements(invoice, Arrays.asList(product, product2, product3));
    }
    
    @Test
    void successful_delete_invoice_process_test() throws Exception{
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice, product);
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.INVOICES_URL + "/" + invoice.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(1, invoiceProductRepository.count());
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        InvoiceModel getInvoice = invoices.get(0);
        assertTrue(getInvoice.isDeleted());
        List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAll();
        InvoiceProductModel getInvoiceProduct = invoiceProducts.get(0);
        assertTrue(getInvoiceProduct.isDeleted());
        removeAddedElement(invoiceProduct);
        removeAddedElement(invoice);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    private void removeAllAddedElements(InvoiceModel invoice, List<ProductModel> products) {
        List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoice.getId());
        invoiceProducts.forEach(this::removeAddedElement);
        removeAddedElement(invoice);
        products.forEach(this::removeAddedElement);
        removeAllAddedElements(invoice.getClient());
    }
    
    private void removeAddedElement(QuotationModel quotation) {
        TestUtils.removeAddedElement(quotationRepository, quotation.getId());
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
    
    private void removeAllAddedElements(ClientModel client) {
        TestUtils.removeAddedElement(clientRepository, addressRepository, client);
        removeAllAddedElements(client.getCustomer());
    }
    
    private void removeAllAddedElements(CustomerModel customer) {
        TestUtils.removeAddedElement(customerRepository, addressRepository, customer);
        TestUtils.removeAddedElement(accountantRepository, addressRepository, customer.getAccountant());
    }
    
    private void createInvoiceUsingEndpoint(CustomerModel customer, ClientModel client, List<Integer> productIds) throws Exception {
        List<RequestProductQuantityDto> productQuantities = buildProductQuantities(productIds);
        RequestInvoiceDto requestInvoiceDto = buildRequestInvoiceDto(productQuantities);
        requestInvoiceDto.setCustomerId(customer.getId());
        requestInvoiceDto.setClientId(client.getId());
        String content = objectMapper.writeValueAsString(requestInvoiceDto);
        mvc.perform(post(APIConstants.INVOICES_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }
    
    private void createInvoiceWithoutProductsUsingEndpoint(CustomerModel customer, ClientModel client) throws Exception {
        RequestInvoiceDto requestInvoiceDto = buildRequestInvoiceDto();
        requestInvoiceDto.setCustomerId(customer.getId());
        requestInvoiceDto.setClientId(client.getId());
        String content = objectMapper.writeValueAsString(requestInvoiceDto);
        mvc.perform(post(APIConstants.INVOICES_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
    
    private InvoiceProductModel createInvoiceProductInDb(InvoiceModel invoice, ProductModel product) {
        return TestUtils.createInvoiceProductInDb(invoiceProductRepository, invoice, product);
    }
    
    private InvoiceModel createInvoiceInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createInvoiceInDb(invoiceRepository, customer, client);
    }
    
    private InvoiceModel createInvoiceInDb(CustomerModel customer, ClientModel client, QuotationModel quotation) {
        return TestUtils.createInvoiceInDb(invoiceRepository, customer, client, quotation);
    }
    
    private QuotationModel createQuotationInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createQuotationInDb(quotationRepository, customer, client);
    }
    
    private ProductModel createProductInDb(CustomerModel customer) {
        return TestUtils.createProductInDb(productRepository, customer);
    }

    private ClientModel createClientInDb(City city, CustomerModel customer) {
        return TestUtils.createClientInDb(clientRepository, city, customer);
    }

    private CustomerModel createCustomerInDb(City city, AccountantModel savedModel) {
        return TestUtils.createCustomerInDb(customerRepository, city, savedModel);
    }

    private AccountantModel createAccountantInDb(City city) {
        return TestUtils.createAccountantInDb(accountantRepository, city);
    }
    
    @Override
    public void assertDbState() {
        assertCountryTableInitialState(countryRepository);
        assertCityTableInitialState(cityRepository);
        assertAddressTableInitialState(addressRepository);
        assertAccountantTableInitialState(accountantRepository);
        assertCustomerTableInitialState(customerRepository);
        assertClientTableInitialState(clientRepository);
        assertProductTableInitialState(productRepository);
        assertQuotationTableInitialState(quotationRepository);
        assertInvoiceTableInitialState(invoiceRepository);
        assertInvoiceProductTableInitialState(invoiceProductRepository);
    }

}
