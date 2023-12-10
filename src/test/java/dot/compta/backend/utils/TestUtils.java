package dot.compta.backend.utils;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.QuotationStatus;
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
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static final List<String> MULTIPLE_LIST_ELEMENTS = Arrays.asList("", "1", "2");
    public static final String COUNTRY_NAME_TEST__MAROC = "Maroc";
    public static final String COUNTRY_NAME_TEST__FRANCE = "France";
    public static final String CITY_NAME_TEST__CASABLANCA = "Casablanca";
    public static final String CITY_NAME_TEST__RABAT = "Rabat";
    public static final int COUNTRY_ID_TEST__1000 = 1000;
    public static final int CITY_ID_TEST__1001 = 1001;
    public static final int ADDRESS_ID_TEST__1002 = 1002;
    public static final int ACCOUNTANT_ID_TEST__1003 = 1003;
    public static final int CUSTOMER_ID_TEST__1004 = 1004;
    public static final int CLIENT_ID_TEST__1005 = 1005;
    public static final int PRODUCT_ID_TEST__1006 = 1006;
    public static final int QUOTATION_ID_TEST__1007 = 1007;
    public static final int QUOTATION_PRODUCT_ID_TEST__1008 = 1008;
    public static final int EXPENSE_ID_TEST__1009 = 1009;
    public static final int INVOICE_ID_TEST__1010 = 1010;
    public static final int INVOICE_PRODUCT_ID_TEST__1011 = 1011;
    public static final String ACCOUNTANT_SOCIAL_PURPOSE_TEST = "accountantSocialPurposeTest";
    public static final int ACCOUNTANT_RC_TEST__100 = 100;
    public static final int ACCOUNTANT_POSTAL_CODE_TEST__200 = 200;
    public static final int ADDRESS_POSTAL_CODE_TEST__204 = 204;
    public static final String ACCOUNTANT_EMAIL_TEST = "accountant@email.com";
    public static final String ACCOUNTANT_MOBILE_PHONE_TEST = "accountantMobileT";
    public static final String ACCOUNTANT_PHONE_TEST = "accountantPhoneT";
    public static final String ACCOUNTANT_FAX_TEST = "accountantFaxTest";
    public static final String ACCOUNTANT_PRIMARY_ADDRESS_TEST = "accountantPrimaryAddressTest";
    public static final String ACCOUNTANT_SECONDARY_ADDRESS_TEST = "accountantSecondaryAddressTest";
    public static final String ADDRESS_PRIMARY_ADDRESS_TEST = "primaryAddressTest";
    public static final String ADDRESS_SECONDARY_ADDRESS_TEST = "secondaryAddressTest";
    public static final String CUSTOMER_SOCIAL_PURPOSE_TEST = "customerSocialPurposeTest";
    public static final String CUSTOMER_MOBILE_PHONE_TEST = "customerMobileT";
    public static final String CUSTOMER_PHONE_TEST = "customerPhoneT";
    public static final String CUSTOMER_FAX_TEST = "customerFaxTest";
    public static final String CUSTOMER_PRIMARY_ADDRESS_TEST = "customerPrimaryAddressTest";
    public static final String CUSTOMER_SECONDARY_ADDRESS_TEST = "customerSecondaryAddressTest";
    public static final int CUSTOMER_POSTAL_CODE_TEST__201 = 201;
    public static final int CUSTOMER_RC_TEST__101 = 101;
    public static final String CUSTOMER_EMAIL_TEST = "customer@email.com";
    public static final String CLIENT_SOCIAL_PURPOSE_TEST = "clientSocialPurposeTest";
    public static final String CLIENT_MOBILE_PHONE_TEST = "clientMobileT";
    public static final String CLIENT_PHONE_TEST = "clientPhoneT";
    public static final String CLIENT_FAX_TEST = "clientFaxTest";
    public static final String CLIENT_PRIMARY_ADDRESS_TEST = "clientPrimaryAddressTest";
    public static final String CLIENT_SECONDARY_ADDRESS_TEST = "clientSecondaryAddressTest";
    public static final int CLIENT_POSTAL_CODE_TEST__202 = 202;
    public static final int CLIENT_RC_TEST__102 = 102;
    public static final String CLIENT_EMAIL_TEST = "client@email.com";
    public static final String PRODUCT_LABEL_TEST = "productLabelTest";
    public static final String PRODUCT_REFERENCE_TEST = "productReferenceTest";
    public static final double PRODUCT_PRICE_TEST = 300.50;
    public static final String PRODUCT_UNITY_TEST = "productUnityTest";
    public static final String PRODUCT_QUALIFICATION_TEST = "productQualificationTest";
    public static final double PRODUCT_TAX_TEST = 5;
    public static final int PRODUCT_QUANTITY_TEST = 230;
    public static final String EXPENSE_LABEL_TEST = "expenseLabelTest";
    public static final double EXPENSE_PRICE_TEST = 302.70;
    public static final String EXPENSE_QUALIFICATION_TEST = "expenseQualificationTest";
    public static final double EXPENSE_TAX_TEST = 10;
    public static final String QUOTATION_PRODUCT_LABEL_TEST = "quotationProductLabelTest";
    public static final int QUOTATION_VALIDATION_DELAY_TEST = 50;
    public static final int QUOTATION_PRODUCT_QUANTITY_TEST = 400;
    public static final String QUOTATION_PRODUCT_REFERENCE_TEST = "quotationProductReferenceTest";
    public static final double QUOTATION_PRODUCT_PRICE_TEST = 301.60;
    public static final String QUOTATION_PRODUCT_UNITY_TEST = "quotationProductUnityTest";
    public static final String QUOTATION_PRODUCT_QUALIFICATION_TEST = "quotationProductQualificationTest";
    public static final double QUOTATION_PRODUCT_TAX_TEST = 15;
    public static final int INVOICE_PAYMENT_DELAY_TEST = 60;
    public static final int INVOICE_PRODUCT_QUANTITY_TEST = 500;
    public static final String INVOICE_PRODUCT_LABEL_TEST = "invoiceProductLabelTest";
    public static final String INVOICE_PRODUCT_REFERENCE_TEST = "invoiceProductReferenceTest";
    public static final double INVOICE_PRODUCT_PRICE_TEST = 345.60;
    public static final String INVOICE_PRODUCT_UNITY_TEST = "invoiceProductUnityTest";
    public static final String INVOICE_PRODUCT_QUALIFICATION_TEST = "invoiceProductQualificationTest";
    public static final double INVOICE_PRODUCT_TAX_TEST = 20;

    public static String getJsonContent(String fileName) throws IOException {
        return new String(Files.readAllBytes(new ClassPathResource(fileName).getFile().toPath()));
    }

    public static ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public static AccountantModel createAccountantInDb(AccountantRepository accountantRepository, City city) {
        AccountantModel accountantModelToSave = buildAccountantModel(city);
        return accountantRepository.save(accountantModelToSave);
    }

    public static CustomerModel createCustomerInDb(CustomerRepository customerRepository, City city, AccountantModel accountant) {
        CustomerModel customerModel = buildCustomerModel(city, accountant);
        return customerRepository.save(customerModel);
    }

    public static ClientModel createClientInDb(ClientRepository clientRepository, City city, CustomerModel customer) {
        ClientModel clientModel = buildClientModel(city, customer);
        return clientRepository.save(clientModel);
    }

    public static ProductModel createProductInDb(ProductRepository productRepository, CustomerModel customer) {
        ProductModel productModel = buildProductModel(customer);
        return productRepository.save(productModel);
    }

    public static ExpenseReportModel createExpenseReportInDb(ExpenseReportRepository expenseReportRepository, CustomerModel customer) {
        ExpenseReportModel expenseReportModel = buildExpenseReportModel(customer);
        return expenseReportRepository.save(expenseReportModel);
    }

    public static QuotationModel createQuotationInDb(QuotationRepository quotationRepository, CustomerModel customer, ClientModel client) {
        QuotationModel quotation = buildQuotationModel(customer, client);
        return quotationRepository.save(quotation);
    }

    public static QuotationProductModel createQuotationProductInDb(QuotationProductRepository quotationProductRepository, QuotationModel quotation, ProductModel product) {
        QuotationProductModel quotationProduct = buildQuotationProductModel(quotation, product);
        return quotationProductRepository.save(quotationProduct);
    }
    
    public static InvoiceModel createInvoiceInDb(InvoiceRepository invoiceRepository, CustomerModel customer, ClientModel client) {
    	InvoiceModel invoice = buildInvoiceModel(customer, client);
        return invoiceRepository.save(invoice);
    }
    
    public static InvoiceModel createInvoiceInDb(InvoiceRepository invoiceRepository, CustomerModel customer, ClientModel client, QuotationModel quotation) {
    	InvoiceModel invoice = buildInvoiceModel(customer, client, quotation);
        return invoiceRepository.save(invoice);
    }
    
    public static InvoiceProductModel createInvoiceProductInDb(InvoiceProductRepository invoiceProductRepository, InvoiceModel invoice, ProductModel product) {
    	InvoiceProductModel invoiceProduct = buildInvoiceProductModel(invoice, product);
        return invoiceProductRepository.save(invoiceProduct);
    }

    public static void removeAddedElement(CountryRepository countryRepository, int id) {
        countryRepository.deleteById(id);
    }

    public static void removeAddedElement(CityRepository cityRepository, int id) {
        cityRepository.deleteById(id);
    }

    public static void removeAddedElement(AccountantRepository accountantRepository,
                                          AddressRepository addressRepository,
                                          AccountantModel accountantModel) {
        accountantRepository.deleteById(accountantModel.getId());
        addressRepository.deleteById(accountantModel.getAddress().getId());
    }

    public static void removeAddedElement(CustomerRepository customerRepository,
                                          AddressRepository addressRepository,
                                          CustomerModel customerModel) {
        customerRepository.deleteById(customerModel.getId());
        addressRepository.deleteById(customerModel.getAddress().getId());
    }

    public static void removeAddedElement(ClientRepository clientRepository,
                                          AddressRepository addressRepository,
                                          ClientModel clientModel) {
        clientRepository.deleteById(clientModel.getId());
        addressRepository.deleteById(clientModel.getAddress().getId());
    }

    public static void removeAddedElement(ExpenseReportRepository expenseReportRepository, int id) {
        expenseReportRepository.deleteById(id);
    }

    public static void removeAddedElement(ProductRepository productRepository, int id) {
        productRepository.deleteById(id);
    }

    public static void removeAddedElement(QuotationRepository quotationRepository, int id) {
        quotationRepository.deleteById(id);
    }

    public static void removeAddedElement(QuotationProductRepository quotationProductRepository, int id) {
        quotationProductRepository.deleteById(id);
    }
    
    public static void removeAddedElement(InvoiceRepository invoiceRepository, int id) {
    	invoiceRepository.deleteById(id);
    }
    
    public static void removeAddedElement(InvoiceProductRepository invoiceProductRepository, int id) {
    	invoiceProductRepository.deleteById(id);
    }

    private static AccountantModel buildAccountantModel(City city) {
        return AccountantModel.builder()
                .rc(ACCOUNTANT_RC_TEST__100)
                .companyName(ACCOUNTANT_SOCIAL_PURPOSE_TEST)
                .email(ACCOUNTANT_EMAIL_TEST)
                .mobilePhone(ACCOUNTANT_MOBILE_PHONE_TEST)
                .phone(ACCOUNTANT_PHONE_TEST)
                .fax(ACCOUNTANT_FAX_TEST)
                .address(AddressModel.builder()
                        .city(city)
                        .postalCode(ACCOUNTANT_POSTAL_CODE_TEST__200)
                        .primaryAddress(ACCOUNTANT_PRIMARY_ADDRESS_TEST)
                        .secondaryAddress(ACCOUNTANT_SECONDARY_ADDRESS_TEST)
                        .build())
                .build();
    }

    private static CustomerModel buildCustomerModel(City city, AccountantModel accountant) {
        return CustomerModel.builder()
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST)
                .email(CUSTOMER_EMAIL_TEST)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST)
                .phone(CUSTOMER_PHONE_TEST)
                .fax(CUSTOMER_FAX_TEST)
                .accountant(accountant)
                .address(AddressModel.builder()
                        .city(city)
                        .postalCode(CUSTOMER_POSTAL_CODE_TEST__201)
                        .primaryAddress(CUSTOMER_PRIMARY_ADDRESS_TEST)
                        .secondaryAddress(CUSTOMER_SECONDARY_ADDRESS_TEST)
                        .build())
                .build();
    }

    private static ClientModel buildClientModel(City city, CustomerModel customer) {
        return ClientModel.builder()
                .rc(CLIENT_RC_TEST__102)
                .companyName(CLIENT_SOCIAL_PURPOSE_TEST)
                .email(CLIENT_EMAIL_TEST)
                .mobilePhone(CLIENT_MOBILE_PHONE_TEST)
                .phone(CLIENT_PHONE_TEST)
                .fax(CLIENT_FAX_TEST)
                .address(AddressModel.builder()
                        .city(city)
                        .postalCode(CLIENT_POSTAL_CODE_TEST__202)
                        .primaryAddress(CLIENT_PRIMARY_ADDRESS_TEST)
                        .secondaryAddress(CLIENT_SECONDARY_ADDRESS_TEST)
                        .build())
                .customer(customer)
                .build();
    }

    private static ProductModel buildProductModel(CustomerModel customer) {
        return ProductModel.builder()
                .label(PRODUCT_LABEL_TEST)
                .reference(PRODUCT_REFERENCE_TEST)
                .priceExclTax(PRODUCT_PRICE_TEST)
                .unity(PRODUCT_UNITY_TEST)
                .qualification(PRODUCT_QUALIFICATION_TEST)
                .tax(PRODUCT_TAX_TEST)
                .customer(customer)
                .build();
    }

    private static ExpenseReportModel buildExpenseReportModel(CustomerModel customer) {
        return ExpenseReportModel.builder()
                .label(EXPENSE_LABEL_TEST)
                .status(ExpenseReportStatus.SAVED)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST)
                .tax(EXPENSE_TAX_TEST)
                .customer(customer)
                .build();
    }

    private static QuotationModel buildQuotationModel(CustomerModel customer, ClientModel client) {
        return QuotationModel.builder()
                .status(QuotationStatus.SAVED)
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .customer(customer)
                .client(client)
                .build();
    }

    private static QuotationProductModel buildQuotationProductModel(QuotationModel quotation, ProductModel product) {
        return QuotationProductModel.builder()
                .quotation(quotation)
                .product(product)
                .label(QUOTATION_PRODUCT_LABEL_TEST)
                .reference(QUOTATION_PRODUCT_REFERENCE_TEST)
                .priceExclTax(QUOTATION_PRODUCT_PRICE_TEST)
                .unity(QUOTATION_PRODUCT_UNITY_TEST)
                .qualification(QUOTATION_PRODUCT_QUALIFICATION_TEST)
                .tax(QUOTATION_PRODUCT_TAX_TEST)
                .quantity(QUOTATION_PRODUCT_QUANTITY_TEST)
                .build();
    }
    
    private static InvoiceModel buildInvoiceModel(CustomerModel customer, ClientModel client) {
        return InvoiceModel.builder()
                .status(InvoiceStatus.SAVED)
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .customer(customer)
                .client(client)
                .build();
    }
    
    private static InvoiceModel buildInvoiceModel(CustomerModel customer, ClientModel client, QuotationModel quotation) {
        return InvoiceModel.builder()
                .status(InvoiceStatus.SAVED)
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .customer(customer)
                .client(client)
                .quotation(quotation)
                .build();
    }

    private static InvoiceProductModel buildInvoiceProductModel(InvoiceModel invoice, ProductModel product) {
        return InvoiceProductModel.builder()
                .invoice(invoice)
                .product(product)
                .label(INVOICE_PRODUCT_LABEL_TEST)
                .reference(INVOICE_PRODUCT_REFERENCE_TEST)
                .priceExclTax(INVOICE_PRODUCT_PRICE_TEST)
                .unity(INVOICE_PRODUCT_UNITY_TEST)
                .qualification(INVOICE_PRODUCT_QUALIFICATION_TEST)
                .tax(INVOICE_PRODUCT_TAX_TEST)
                .quantity(INVOICE_PRODUCT_QUANTITY_TEST)
                .build();
    }
}
