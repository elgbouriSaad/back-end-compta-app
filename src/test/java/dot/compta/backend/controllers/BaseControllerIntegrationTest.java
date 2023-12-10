package dot.compta.backend.controllers;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface BaseControllerIntegrationTest {

    int INITIAL_CITIES_COUNT = 12;
    int INITIAL_COUNTRIES_COUNT = 1;
    int INITITIAL_ZERO_COUNT = 0;

    void assertDbState();

    default void assertCountryTableInitialState(CountryRepository countryRepository) {
        assertEquals(INITIAL_COUNTRIES_COUNT, countryRepository.count());
    }

    default void assertCityTableInitialState(CityRepository cityRepository) {
        assertEquals(INITIAL_CITIES_COUNT, cityRepository.count());
    }

    default void assertAddressTableInitialState(AddressRepository addressRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, addressRepository.count());
    }

    default void assertAccountantTableInitialState(AccountantRepository accountantRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, accountantRepository.count());
    }

    default void assertCustomerTableInitialState(CustomerRepository customerRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, customerRepository.count());
    }

    default void assertClientTableInitialState(ClientRepository clientRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, clientRepository.count());
    }

    default void assertProductTableInitialState(ProductRepository productRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, productRepository.count());
    }

    default void assertQuotationTableInitialState(QuotationRepository quotationRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, quotationRepository.count());
    }

    default void assertQuotationProductTableInitialState(QuotationProductRepository quotationProductRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, quotationProductRepository.count());
    }

    default void assertExpenseReportTableInitialState(ExpenseReportRepository expenseReportRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, expenseReportRepository.count());
    }
    
    default void assertInvoiceTableInitialState(InvoiceRepository invoiceRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, invoiceRepository.count());
    }
    
    default void assertInvoiceProductTableInitialState(InvoiceProductRepository invoiceProductRepository) {
        assertEquals(INITITIAL_ZERO_COUNT, invoiceProductRepository.count());
    }

}
