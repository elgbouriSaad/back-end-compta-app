package dot.compta.backend.constants;

public interface APIConstants {

    String ALL_MAPPINGS = "/**";
    String API_PREFIX = "/api/v1/";

    String ACCOUNTANTS_URL = API_PREFIX + "accountants";
    String CITIES_URL = API_PREFIX + "cities";
    String COUNTRIES_URL = API_PREFIX + "countries";
    String CUSTOMERS_URL = API_PREFIX + "customers";
    String CLIENTS_URL = API_PREFIX + "clients";
    String CUSTOMER_CLIENTS_URL = "/customer/";
    String PRODUCTS_URL = API_PREFIX + "products";
    String CUSTOMER_PRODUCTS_URL = "/customer/";
    String ACCOUNTANT_CUSTOMERS_URL = "/accountant/";
    String QUOTATIONS_URL = API_PREFIX + "quotations";
    String EXPENSE_REPORTS_URL = API_PREFIX + "expense_reports";
    String CUSTOMER_EXPENSE_REPORTS_URL = "/customer/";
    String CUSTOMER_QUOTATIONS_URL = "/customer/";
    String QUOTATION_UPDATE_URL = "/update/";
    String QUOTATION_VALIDATE_URL = "/validate/";
    String QUOTATION_TRANSFORM_URL = "/transform/";
    String CLIENT_QUOTATIONS_URL = "/client/";
    String INVOICES_URL = API_PREFIX + "invoices";
    String EXPENSE_REPORT_UPDATE_URL = "/update/";
    String EXPENSE_REPORT_VALIDATE_URL = "/validate/";
    String CUSTOMER_INVOICES_URL = "/customer/";
    String CLIENT_INVOICES_URL = "/client/";
    String INVOICE_VALIDATE_URL = "/validate/";
    String INVOICE_UPDATE_URL = "/update/";

}