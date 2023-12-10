package dot.compta.backend.controllers.quotation;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.models.accountant.AccountantModel;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dot.compta.backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ActiveProfiles("dev-h2")
public class QuotationControllerIntegrationTest extends BaseQuotationControllerTest implements BaseControllerIntegrationTest {

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
    private QuotationRepository quotationRepository;
    @Autowired
    private QuotationProductRepository quotationProductRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private QuotationController quotationController;

    @BeforeEach
    void setUp() {
        assertDbState();
        mvc = standaloneSetup(quotationController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }

    @Test
    void successful_quotation_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        ProductModel product = createProductInDb(customer);
        ProductModel product2 = createProductInDb(customer);
        List<Integer> productIds = Arrays.asList(product.getId(), product2.getId());
        createQuotationUsingEndpoint(customer, client, productIds);

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(2, productRepository.count());
        assertEquals(1, quotationRepository.count());
        assertEquals(2, quotationProductRepository.count());

        List<QuotationModel> quotations = quotationRepository.findAll();
        QuotationModel quotationModel = quotations.get(0);
        assertEquals(QuotationStatus.SAVED, quotationModel.getStatus());
        assertEquals(QUOTATION_VALIDATION_DELAY_TEST, quotationModel.getValidationDelay());

        removeAllAddedElements(quotationModel, Arrays.asList(product, product2));
    }
    
    @Test
    void unsuccessful_quotation_without_products_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        createQuotationWithoutProductsUsingEndpoint(customer, client);

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(0, quotationRepository.count());

        removeAllAddedElements(client);
    }

    @Test
    void successful_multiple_quotation_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        ProductModel product = createProductInDb(customer);
        ProductModel product2 = createProductInDb(customer);
        List<Integer> productIds = Arrays.asList(product.getId(), product2.getId());
        for (int i = 0; i < 3; i++) {
            createQuotationUsingEndpoint(customer, client, productIds);
        }

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(2, productRepository.count());
        assertEquals(3, quotationRepository.count());
        assertEquals(6, quotationProductRepository.count());

        List<QuotationModel> optionalQuotations = quotationRepository.findAll();
        assertFalse(optionalQuotations.isEmpty());

        for (QuotationModel quotation : optionalQuotations) {
            removeAllAddedElements(quotation);
        }


        removeAddedElement(product);
        removeAddedElement(product2);

        Optional<ClientModel> optionalClient = clientRepository.findOneByCompanyNameIgnoreCase(CLIENT_SOCIAL_PURPOSE_TEST);
        assertTrue(optionalClient.isPresent());
        removeAllAddedElements(optionalClient.get());
    }

    @Test
    void successful_get_quotations_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
        mvc.perform(get(APIConstants.QUOTATIONS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("QuotationControllerIntegration_getQuotations.json"), false));

        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }

    @Test
    void successful_get_quotation_byId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
        mvc.perform(get(APIConstants.QUOTATIONS_URL + "/" + quotation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("QuotationControllerIntegration_getQuotationById.json"), false));

        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_get_quotations_byCustomerId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
        mvc.perform(get(APIConstants.QUOTATIONS_URL + APIConstants.CUSTOMER_QUOTATIONS_URL + quotation.getCustomer().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("QuotationControllerIntegration_getQuotationsByCustomerId.json"), false));

        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_get_quotations_byClientId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
        mvc.perform(get(APIConstants.QUOTATIONS_URL + APIConstants.CLIENT_QUOTATIONS_URL + quotation.getClient().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("QuotationControllerIntegration_getQuotationsByClientId.json"), false));

        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_validate_quotation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);

        mvc.perform(MockMvcRequestBuilders.put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_VALIDATE_URL + quotation.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, quotationRepository.count());
        assertEquals(1, quotationProductRepository.count());
        List<QuotationModel> quotations = quotationRepository.findAll();
        QuotationModel getQuotation = quotations.get(0);
        assertEquals(QuotationStatus.VALIDATED, getQuotation.getStatus());

        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);

    }

    @Test
    void successful_update_quotation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        ProductModel product = createProductInDb(customer);
        ProductModel product2 = createProductInDb(customer);
        ProductModel product3 = createProductInDb(customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        createQuotationProductInDb(quotation, product);
        createQuotationProductInDb(quotation, product2);
        List<Integer> newProductIds = Arrays.asList(product.getId(), product3.getId());
        List<RequestProductQuantityDto> productQuantities = buildProductQuantities(newProductIds);
        UpdateQuotationDto updateQuotationDto = buildUpdateQuotationDto(productQuantities);
        updateQuotationDto.setClientId(client.getId());
        String content = objectMapper.writeValueAsString(updateQuotationDto);

        mvc.perform(put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_UPDATE_URL + quotation.getId())
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
        assertEquals(1, quotationRepository.count());
        assertEquals(3, quotationProductRepository.count());

        removeAllAddedElements(quotation, Arrays.asList(product, product2, product3));
    }
    
    @Test
    void successful_transform_quotation_without_existing_invoice_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
        validateQuotationUsingEndpoint(quotation.getId());
        
        String content = objectMapper.writeValueAsString(buildRequestTransformQuotationDto());

        mvc.perform(put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_TRANSFORM_URL + quotation.getId())
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
        assertEquals(1, productRepository.count());
        assertEquals(1, quotationRepository.count());
        assertEquals(1, quotationProductRepository.count());
        assertEquals(1, invoiceRepository.count());
        assertEquals(1, invoiceProductRepository.count());
        List<QuotationModel> quotations = quotationRepository.findAll();
        QuotationModel getQuotation = quotations.get(0);
        assertEquals(QuotationStatus.TRANSFORMED, getQuotation.getStatus());
        
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        InvoiceModel getInvoice = invoices.get(0);
        assertEquals(INVOICE_PAYMENT_DELAY_TEST, getInvoice.getPaymentDelay());
        assertEquals(getQuotation.getCustomer().getId(), getInvoice.getCustomer().getId());
        assertEquals(getQuotation.getClient().getId(), getInvoice.getClient().getId());
        assertEquals(getQuotation.getId(), getInvoice.getQuotation().getId());
        assertEquals(InvoiceStatus.VALIDATED, getInvoice.getStatus());
        
        List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAll();
        InvoiceProductModel getInvoiceProduct = invoiceProducts.get(0);
        assertEquals(getInvoiceProduct.getInvoice().getId(), getInvoice.getId());
        
        removeAddedElement(getInvoiceProduct);
        removeAddedElement(getInvoice);
        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
  
    @Test
    void successful_delete_quotation_process_test() throws Exception{
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
      
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.QUOTATIONS_URL + "/" + quotation.getId()))
                .andExpect(status().isOk());
      
        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(3, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, clientRepository.count());
        assertEquals(1, productRepository.count());
        assertEquals(1, quotationRepository.count());
        assertEquals(1, quotationProductRepository.count());
      
        List<QuotationModel> quotations = quotationRepository.findAll();
        QuotationModel getQuotation = quotations.get(0);
        assertTrue(getQuotation.isDeleted());
      
        List<QuotationProductModel> quotationProducts = quotationProductRepository.findAll();
        QuotationProductModel getQuotationProduct = quotationProducts.get(0);
        assertTrue(getQuotationProduct.isDeleted());
        
        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }
    
    @Test
    void successful_transform_quotation_with_existing_invoice_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ClientModel client = createClientInDb(city.get(), customer);
        QuotationModel quotation = createQuotationInDb(customer, client);
        InvoiceModel invoice = createInvoiceInDb(customer, client);
        ProductModel product = createProductInDb(customer);
        QuotationProductModel quotationProduct = createQuotationProductInDb(quotation, product);
        InvoiceProductModel invoiceProduct = createInvoiceProductInDb(invoice,product);
        validateQuotationUsingEndpoint(quotation.getId());

        String content = objectMapper.writeValueAsString(buildRequestTransformQuotationDto());

        mvc.perform(put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_TRANSFORM_URL + quotation.getId())
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
        assertEquals(1, productRepository.count());
        assertEquals(1, quotationRepository.count());
        assertEquals(1, quotationProductRepository.count());
        assertEquals(2, invoiceRepository.count());
        assertEquals(2, invoiceProductRepository.count());
        List<QuotationModel> quotations = quotationRepository.findAll();
        QuotationModel getQuotation = quotations.get(0);
        assertEquals(QuotationStatus.TRANSFORMED, getQuotation.getStatus());
        InvoiceModel transformedInvoice = null;
        List<InvoiceModel> invoices = invoiceRepository.findAll();
        for (InvoiceModel getInvoice : invoices) {
        	if(getInvoice.getId() != invoice.getId()) {
        		transformedInvoice = getInvoice;
        	}
        }
        assertEquals(INVOICE_PAYMENT_DELAY_TEST, transformedInvoice.getPaymentDelay());
        assertEquals(getQuotation.getCustomer().getId(), transformedInvoice.getCustomer().getId());
        assertEquals(getQuotation.getClient().getId(), transformedInvoice.getClient().getId());
        assertEquals(getQuotation.getId(), transformedInvoice.getQuotation().getId());
        assertEquals(InvoiceStatus.VALIDATED, transformedInvoice.getStatus());
        assertNotEquals(invoice.getId(),transformedInvoice.getId());
        
        List<InvoiceProductModel> invoiceProducts = invoiceProductRepository.findAll();
        InvoiceProductModel transformedInvoiceProduct = null;
        for (InvoiceProductModel getInvoiceProduct : invoiceProducts) {
        	if(getInvoiceProduct.getId() != invoiceProduct.getId()) {
        		transformedInvoiceProduct = getInvoiceProduct;
        	}
        }
        assertEquals(transformedInvoiceProduct.getInvoice().getId(), transformedInvoice.getId());
        
        removeAddedElement(transformedInvoiceProduct);
        removeAddedElement(transformedInvoice);
        removeAddedElement(invoiceProduct);
        removeAddedElement(invoice);
        removeAddedElement(quotationProduct);
        removeAddedElement(quotation);
        removeAddedElement(product);
        removeAllAddedElements(client);
    }

    private void removeAllAddedElements(QuotationModel quotation, List<ProductModel> products) {
        List<QuotationProductModel> quotationProducts = quotationProductRepository.findAllByQuotationId(quotation.getId());
        quotationProducts.forEach(this::removeAddedElement);
        removeAddedElement(quotation);
        products.forEach(this::removeAddedElement);
        removeAllAddedElements(quotation.getClient());
    }

    private void removeAllAddedElements(QuotationModel quotation) {
        List<QuotationProductModel> quotationProducts = quotationProductRepository.findAllByQuotationIdAndDeletedFalse(quotation.getId());
        quotationProducts.forEach(this::removeAddedElement);
        removeAddedElement(quotation);
    }
    
    private void removeAddedElement(InvoiceModel invoice) {
        TestUtils.removeAddedElement(invoiceRepository, invoice.getId());
    }
    
    private void removeAddedElement(InvoiceProductModel invoiceProduct) {
        TestUtils.removeAddedElement(invoiceProductRepository, invoiceProduct.getId());
    }

    private void removeAddedElement(QuotationModel quotation) {
        TestUtils.removeAddedElement(quotationRepository, quotation.getId());
    }

    private void removeAddedElement(QuotationProductModel quotationProduct) {
        TestUtils.removeAddedElement(quotationProductRepository, quotationProduct.getId());
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

    private void createQuotationUsingEndpoint(CustomerModel customer, ClientModel client, List<Integer> productIds) throws Exception {
        List<RequestProductQuantityDto> productQuantities = buildProductQuantities(productIds);
        RequestQuotationDto requestQuotationDto = buildRequestQuotationDto(productQuantities);
        requestQuotationDto.setCustomerId(customer.getId());
        requestQuotationDto.setClientId(client.getId());
        String content = objectMapper.writeValueAsString(requestQuotationDto);
        mvc.perform(post(APIConstants.QUOTATIONS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }
    
    private void createQuotationWithoutProductsUsingEndpoint(CustomerModel customer, ClientModel client) throws Exception {
        RequestQuotationDto requestQuotationDto = buildRequestQuotationDto();
        requestQuotationDto.setCustomerId(customer.getId());
        requestQuotationDto.setClientId(client.getId());
        String content = objectMapper.writeValueAsString(requestQuotationDto);
        mvc.perform(post(APIConstants.QUOTATIONS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
        .andExpect(status().isBadRequest());
    }
    
    private void validateQuotationUsingEndpoint(int id) throws Exception {
    	mvc.perform(MockMvcRequestBuilders.put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_VALIDATE_URL + id))
        .andExpect(status().isOk());
    }
    
    private InvoiceModel createInvoiceInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createInvoiceInDb(invoiceRepository, customer, client);
    }
    
    private InvoiceProductModel createInvoiceProductInDb(InvoiceModel invoice, ProductModel product) {
        return TestUtils.createInvoiceProductInDb(invoiceProductRepository, invoice, product);
    }

    private QuotationProductModel createQuotationProductInDb(QuotationModel quotation, ProductModel product) {
        return TestUtils.createQuotationProductInDb(quotationProductRepository, quotation, product);
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

    private QuotationModel createQuotationInDb(CustomerModel customer, ClientModel client) {
        return TestUtils.createQuotationInDb(quotationRepository, customer, client);
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
        assertQuotationProductTableInitialState(quotationProductRepository);
        assertInvoiceTableInitialState(invoiceRepository);
        assertInvoiceProductTableInitialState(invoiceProductRepository);
    }

}
