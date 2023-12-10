package dot.compta.backend.controllers.product;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.product.RequestProductDto;
import dot.compta.backend.dtos.product.UpdateProductDto;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import dot.compta.backend.repositories.address.AddressRepository;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.product.ProductRepository;
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
public class ProductControllerIntegrationTest extends BaseProductControllerTest implements BaseControllerIntegrationTest {

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
    private ProductController productController;

    @BeforeEach
    void setUp() {
        assertDbState();
        mvc = standaloneSetup(productController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }

    @Test
    void successful_product_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        createProductUsingEndpoint(customer, "");

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(0, clientRepository.count());
        assertEquals(1, productRepository.count());

        List<ProductModel> products = productRepository.findAll();
        ProductModel productModel = products.get(0);
        assertEquals(PRODUCT_LABEL_TEST, productModel.getLabel());
        assertEquals(PRODUCT_REFERENCE_TEST, productModel.getReference());
        assertEquals(PRODUCT_PRICE_TEST, productModel.getPriceExclTax());
        assertEquals(PRODUCT_UNITY_TEST, productModel.getUnity());
        assertEquals(PRODUCT_QUALIFICATION_TEST, productModel.getQualification());
        assertEquals(PRODUCT_TAX_TEST, productModel.getTax());

        removeAllAddedElements(productModel);
    }

    @Test
    void successful_multiple_product_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            createProductUsingEndpoint(customer, suffix);
        }

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(0, clientRepository.count());
        assertEquals(3, productRepository.count());

        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            Optional<ProductModel> optionalProduct = productRepository.findOneByReferenceIgnoreCase(PRODUCT_REFERENCE_TEST + suffix);
            assertTrue(optionalProduct.isPresent());
            removeAddedElement(optionalProduct.get());
        }

        Optional<CustomerModel> optionalCustomer = customerRepository.findOneByCompanyNameIgnoreCase(CUSTOMER_SOCIAL_PURPOSE_TEST);
        assertTrue(optionalCustomer.isPresent());
        removeAllAddedElements(optionalCustomer.get());
    }

    @Test
    void successful_get_products_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ProductModel savedModel = createProductInDb(customer);
        mvc.perform(get(APIConstants.PRODUCTS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ProductControllerIntegration_getProducts.json"), false));

        removeAllAddedElements(savedModel);
    }

    @Test
    void successful_get_product_byId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ProductModel savedModel = createProductInDb(customer);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.PRODUCTS_URL + "/" + savedModel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ProductControllerIntegration_getProductById.json"), false));

        removeAllAddedElements(savedModel);
    }

    @Test
    void successful_get_product_byCustomerId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ProductModel savedModel = createProductInDb(customer);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.PRODUCTS_URL + APIConstants.CUSTOMER_PRODUCTS_URL + savedModel.getCustomer().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ProductControllerIntegration_getProductsByCustomerId.json"), false));

        removeAllAddedElements(savedModel);
    }

    @Test
    public void successful_delete_product_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ProductModel savedModel = createProductInDb(customer);

        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.PRODUCTS_URL + "/" + savedModel.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, productRepository.count());
        List<ProductModel> products = productRepository.findAll();
        ProductModel product = products.get(0);
        assertTrue(product.isDeleted());
        assertEquals(PRODUCT_LABEL_TEST, product.getLabel());
        assertEquals(PRODUCT_REFERENCE_TEST, product.getReference());
        assertEquals(PRODUCT_PRICE_TEST, product.getPriceExclTax());
        assertEquals(PRODUCT_UNITY_TEST, product.getUnity());
        assertEquals(PRODUCT_QUALIFICATION_TEST, product.getQualification());
        assertEquals(PRODUCT_TAX_TEST, product.getTax());

        removeAllAddedElements(savedModel);
    }

    @Test
    public void successful_update_product_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ProductModel savedModel = createProductInDb(customer);
        String updateSuffix = "Up";
        UpdateProductDto updateProductDto = buildUpdateProductDto(updateSuffix);
        double updatedPrice = 301;
        updateProductDto.setPriceExclTax(updatedPrice);
        String content = objectMapper.writeValueAsString(updateProductDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.PRODUCTS_URL + "/" + savedModel.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, productRepository.count());

        List<ProductModel> products = productRepository.findAll();
        ProductModel product = products.get(0);
        assertFalse(product.isDeleted());
        assertEquals(customer.getId(), product.getCustomer().getId());
        assertEquals(PRODUCT_LABEL_TEST + updateSuffix, product.getLabel());
        assertEquals(PRODUCT_REFERENCE_TEST + updateSuffix, product.getReference());
        assertEquals(updatedPrice, product.getPriceExclTax());
        assertEquals(PRODUCT_UNITY_TEST + updateSuffix, product.getUnity());
        assertEquals(PRODUCT_QUALIFICATION_TEST + updateSuffix, product.getQualification());
        assertEquals(PRODUCT_TAX_TEST, product.getTax());

        removeAllAddedElements(savedModel);
    }

    private void createProductUsingEndpoint(CustomerModel customer, String suffix) throws Exception {
        RequestProductDto requestProductDto = buildRequestProductDto(suffix);
        requestProductDto.setCustomerId(customer.getId());
        String content = objectMapper.writeValueAsString(requestProductDto);
        mvc.perform(post(APIConstants.PRODUCTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    private ProductModel createProductInDb(CustomerModel customer) {
        return TestUtils.createProductInDb(productRepository, customer);
    }

    private CustomerModel createCustomerInDb(City city, AccountantModel accountant) {
        return TestUtils.createCustomerInDb(customerRepository, city, accountant);
    }

    private void removeAddedElement(ProductModel product) {
        TestUtils.removeAddedElement(productRepository, product.getId());
    }

    private void removeAllAddedElements(ProductModel product) {
        removeAddedElement(product);
        removeAllAddedElements(product.getCustomer());
    }

    private void removeAllAddedElements(CustomerModel customer) {
        TestUtils.removeAddedElement(customerRepository, addressRepository, customer);
        TestUtils.removeAddedElement(accountantRepository, addressRepository, customer.getAccountant());
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
    }
}
