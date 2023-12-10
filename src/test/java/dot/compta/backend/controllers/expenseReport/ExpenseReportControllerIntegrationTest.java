package dot.compta.backend.controllers.expenseReport;

import static dot.compta.backend.utils.TestUtils.CITY_NAME_TEST__CASABLANCA;
import static dot.compta.backend.utils.TestUtils.CUSTOMER_SOCIAL_PURPOSE_TEST;
import static dot.compta.backend.utils.TestUtils.EXPENSE_LABEL_TEST;
import static dot.compta.backend.utils.TestUtils.EXPENSE_PRICE_TEST;
import static dot.compta.backend.utils.TestUtils.EXPENSE_QUALIFICATION_TEST;
import static dot.compta.backend.utils.TestUtils.EXPENSE_TAX_TEST;
import static dot.compta.backend.utils.TestUtils.MULTIPLE_LIST_ELEMENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.controllers.BaseControllerIntegrationTest;
import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import dot.compta.backend.repositories.address.AddressRepository;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.expenseReport.ExpenseReportRepository;
import dot.compta.backend.utils.TestUtils;

@SpringBootTest
@ActiveProfiles("dev-h2")
public class ExpenseReportControllerIntegrationTest extends BaseExpenseReportControllerTest implements BaseControllerIntegrationTest {

    @Autowired
    private AccountantRepository accountantRepository;
    @Autowired
    private ExpenseReportRepository expenseReportRepository;
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
    private ExpenseReportController expenseReportController;

    @BeforeEach
    void setUp() {
        assertDbState();
        mvc = standaloneSetup(expenseReportController).build();
    }

    @AfterEach
    void tearDown() {
        assertDbState();
    }

    @Test
    void successful_expense_report_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        createExpenseReportUsingEndpoint(customer, "");

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(0, clientRepository.count());
        assertEquals(1, expenseReportRepository.count());

        List<ExpenseReportModel> expenseReports = expenseReportRepository.findAll();
        ExpenseReportModel expenseReportModel = expenseReports.get(0);
        assertEquals(ExpenseReportStatus.SAVED, expenseReportModel.getStatus());
        assertEquals(EXPENSE_LABEL_TEST, expenseReportModel.getLabel());
        assertEquals(EXPENSE_PRICE_TEST, expenseReportModel.getPriceExclTax());
        assertEquals(EXPENSE_QUALIFICATION_TEST, expenseReportModel.getQualification());
        assertEquals(EXPENSE_TAX_TEST, expenseReportModel.getTax());

        removeAllAddedElements(expenseReportModel);
    }

    @Test
    void successful_multiple_expense_reports_creation_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            createExpenseReportUsingEndpoint(customer, suffix);
        }

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(0, clientRepository.count());
        assertEquals(3, expenseReportRepository.count());

        for (String suffix : MULTIPLE_LIST_ELEMENTS) {
            Optional<ExpenseReportModel> optionalExpenseReport = expenseReportRepository.findOneByLabelIgnoreCase(EXPENSE_LABEL_TEST + suffix);
            assertTrue(optionalExpenseReport.isPresent());
            removeAddedElement(optionalExpenseReport.get());
        }

        Optional<CustomerModel> optionalCustomer = customerRepository.findOneByCompanyNameIgnoreCase(CUSTOMER_SOCIAL_PURPOSE_TEST);
        assertTrue(optionalCustomer.isPresent());
        removeAllAddedElements(optionalCustomer.get());
    }

    @Test
    void successful_get_expense_reports_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ExpenseReportModel savedModel = createExpenseReportInDb(customer);
        ExpenseReportModel savedModel2 = createExpenseReportInDb(customer);
        mvc.perform(get(APIConstants.EXPENSE_REPORTS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ExpenseReportControllerIntegration_getExpenseReports.json"), false));

        removeAddedElement(savedModel2);
        removeAllAddedElements(savedModel);
    }

    private ExpenseReportModel createExpenseReportInDb(CustomerModel customer) {
        return TestUtils.createExpenseReportInDb(expenseReportRepository, customer);
    }

    @Test
    void successful_get_expense_report_byId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ExpenseReportModel savedModel = createExpenseReportInDb(customer);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.EXPENSE_REPORTS_URL + "/" + savedModel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ExpenseReportControllerIntegration_getExpenseReportById.json"), false));

        removeAllAddedElements(savedModel);
    }

    @Test
    void successful_get_expense_report_byCustomerId_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ExpenseReportModel savedModel = createExpenseReportInDb(customer);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.EXPENSE_REPORTS_URL + APIConstants.CUSTOMER_EXPENSE_REPORTS_URL + savedModel.getCustomer().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("ExpenseReportControllerIntegration_getExpenseReportsByCustomerId.json"), false));

        removeAllAddedElements(savedModel);
    }
    
    @Test
    void successful_validate_expense_report_process_test() throws Exception {
    	Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ExpenseReportModel savedModel = createExpenseReportInDb(customer);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.EXPENSE_REPORTS_URL + APIConstants.EXPENSE_REPORT_VALIDATE_URL + savedModel.getId()))
        					.andExpect(status().isOk());
        
        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(0, clientRepository.count());
        assertEquals(1, expenseReportRepository.count());
        
        List<ExpenseReportModel> expenseReports = expenseReportRepository.findAll();
        ExpenseReportModel expenseReportModel = expenseReports.get(0);
        assertEquals(ExpenseReportStatus.VALIDATED, expenseReportModel.getStatus());
        
        removeAllAddedElements(savedModel);

    }
    
    @Test
    public void successful_update_expense_report_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ExpenseReportModel savedModel = createExpenseReportInDb(customer);
        String updateSuffix = "Up";
        UpdateExpenseReportDto updateExpenseReportDto = buildUpdateExpenseReportDto(updateSuffix);
        double updatedPrice = 301;
        updateExpenseReportDto.setPriceExclTax(updatedPrice);
        String content = objectMapper.writeValueAsString(updateExpenseReportDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.EXPENSE_REPORTS_URL + APIConstants.EXPENSE_REPORT_UPDATE_URL + savedModel.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, expenseReportRepository.count());

        List<ExpenseReportModel> expenseReports = expenseReportRepository.findAll();
        ExpenseReportModel expenseReport = expenseReports.get(0);
        assertFalse(expenseReport.isDeleted());
        assertEquals(ExpenseReportStatus.SAVED, expenseReport.getStatus());
        assertEquals(customer.getId(), expenseReport.getCustomer().getId());
        assertEquals(EXPENSE_LABEL_TEST + updateSuffix, expenseReport.getLabel());
        assertEquals(updatedPrice, expenseReport.getPriceExclTax());
        assertEquals(EXPENSE_QUALIFICATION_TEST + updateSuffix, expenseReport.getQualification());
        assertEquals(EXPENSE_TAX_TEST, expenseReport.getTax());

        removeAllAddedElements(savedModel);
    }
    
    @Test
    public void successful_delete_expense_report_process_test() throws Exception {
        Optional<City> city = cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        AccountantModel accountant = createAccountantInDb(city.get());
        CustomerModel customer = createCustomerInDb(city.get(), accountant);
        ExpenseReportModel savedModel = createExpenseReportInDb(customer);

        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.EXPENSE_REPORTS_URL + "/" + savedModel.getId()))
                .andExpect(status().isOk());

        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
        assertEquals(2, addressRepository.count());
        assertEquals(1, accountantRepository.count());
        assertEquals(1, customerRepository.count());
        assertEquals(1, expenseReportRepository.count());
        List<ExpenseReportModel> expenseReports = expenseReportRepository.findAll();
        ExpenseReportModel expenseReport = expenseReports.get(0);
        assertTrue(expenseReport.isDeleted());
        assertEquals(ExpenseReportStatus.SAVED, expenseReport.getStatus());
        assertEquals(EXPENSE_LABEL_TEST, expenseReport.getLabel());
        assertEquals(EXPENSE_PRICE_TEST, expenseReport.getPriceExclTax());
        assertEquals(EXPENSE_QUALIFICATION_TEST, expenseReport.getQualification());
        assertEquals(EXPENSE_TAX_TEST, expenseReport.getTax());

        removeAllAddedElements(savedModel);
    }

    private void removeAddedElement(ExpenseReportModel expenseReport) {
        TestUtils.removeAddedElement(expenseReportRepository, expenseReport.getId());
    }

    private void removeAllAddedElements(ExpenseReportModel expenseReport) {
        removeAddedElement(expenseReport);
        removeAllAddedElements(expenseReport.getCustomer());
    }

    private void removeAllAddedElements(CustomerModel customer) {
        TestUtils.removeAddedElement(customerRepository, addressRepository, customer);
        TestUtils.removeAddedElement(accountantRepository, addressRepository, customer.getAccountant());
    }

    private void createExpenseReportUsingEndpoint(CustomerModel customer, String suffix) throws Exception {
        RequestExpenseReportDto requestExpenseReportDto = buildRequestExpenseReportDto(suffix);
        requestExpenseReportDto.setCustomerId(customer.getId());
        String content = objectMapper.writeValueAsString(requestExpenseReportDto);
        mvc.perform(post(APIConstants.EXPENSE_REPORTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    private CustomerModel createCustomerInDb(City city, AccountantModel accountant) {
        return TestUtils.createCustomerInDb(customerRepository, city, accountant);
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
        assertExpenseReportTableInitialState(expenseReportRepository);
    }

}
