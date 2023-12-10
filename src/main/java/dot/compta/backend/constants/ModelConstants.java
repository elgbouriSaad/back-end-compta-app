package dot.compta.backend.constants;

public interface ModelConstants {

    String COUNTRY_MODEL_NAME = "name";
    String COUNTRY_MODEL_TABLE = "country";

    String CITY_MODEL_ID = "id";
    String CITY_MODEL_NAME = "name";
    String CITY_MODEL_COUNTRY_ID = "country_id";
    String CITY_MODEL_TABLE = "city";

    String ADDRESS_MODEL_ID = "id";
    String ADDRESS_MODEL_PRIMARY_ADDRESS = "primary_address";
    String ADDRESS_MODEL_SECONDARY_ADDRESS = "secondary_address";
    String ADDRESS_MODEL_POSTAL_CODE = "postal_code";
    String ADDRESS_MODEL_CITY_ID = "city_id";
    String ADDRESS_MODEL_TABLE = "address";

    String ACCOUNTANT_MODEL_ID = "id";
    String ACCOUNTANT_MODEL_COMPANY_NAME = "company_name";
    String ACCOUNTANT_MODEL_RC = "rc";
    String ACCOUNTANT_MODEL_EMAIL = "email";
    String ACCOUNTANT_MODEL_ADDRESS_ID = "address_id";
    String ACCOUNTANT_MODEL_MOBILE_PHONE = "mobile_phone";
    String ACCOUNTANT_MODEL_PHONE = "phone";
    String ACCOUNTANT_MODEL_FAX = "fax";
    String ACCOUNTANT_MODEL_TABLE = "accountant";
    String ACCOUNTANT_MODEL_DELETED = "deleted";

    String CUSTOMER_MODEL_TABLE = "customer";
    String CUSTOMER_MODEL_ID = "id";
    String CUSTOMER_MODEL_COMPANY_NAME = "company_name";
    String CUSTOMER_MODEL_RC = "rc";
    String CUSTOMER_MODEL_EMAIL = "email";
    String CUSTOMER_MODEL_ADDRESS_ID = "address_id";
    String CUSTOMER_MODEL_ACCOUNTANT_ID = "accountant_id";
    String CUSTOMER_MODEL_MOBILE_PHONE = "mobile_phone";
    String CUSTOMER_MODEL_PHONE = "phone";
    String CUSTOMER_MODEL_FAX = "fax";
    String CUSTOMER_MODEL_DELETED = "deleted";

    String CLIENT_MODEL_TABLE = "client";
    String CLIENT_MODEL_ID = "id";
    String CLIENT_MODEL_COMPANY_NAME = "company_name";
    String CLIENT_MODEL_RC = "rc";
    String CLIENT_MODEL_EMAIL = "email";
    String CLIENT_MODEL_ADDRESS_ID = "address_id";
    String CLIENT_MODEL_CUSTOMER_ID = "customer_id";
    String CLIENT_MODEL_MOBILE_PHONE = "mobile_phone";
    String CLIENT_MODEL_PHONE = "phone";
    String CLIENT_MODEL_FAX = "fax";
    String CLIENT_MODEL_DELETED = "deleted";

    String PRODUCT_MODEL_TABLE = "product";
    String PRODUCT_MODEL_ID = "id";
    String PRODUCT_MODEL_LABEL = "label";
    String PRODUCT_MODEL_REFERENCE = "reference";
    String PRODUCT_MODEL_PRICE = "price_excl_tax";
    String PRODUCT_MODEL_UNITY = "unity";
    String PRODUCT_MODEL_QUALIFICATION = "qualification";
    String PRODUCT_MODEL_TAX = "tax";
    String PRODUCT_MODEL_CUSTOMER_ID = "customer_id";
    String PRODUCT_MODEL_DELETED = "deleted";

    String EXPENSE_REPORT_MODEL_TABLE = "expense_report";
    String EXPENSE_REPORT_MODEL_ID = "id";
    String EXPENSE_REPORT_MODEL_STATUS = "status";
    String EXPENSE_REPORT_MODEL_LABEL = "label";
    String EXPENSE_REPORT_MODEL_PRICE = "price_excl_tax";
    String EXPENSE_REPORT_MODEL_QUALIFICATION = "qualification";
    String EXPENSE_REPORT_MODEL_TAX = "tax";
    String EXPENSE_REPORT_MODEL_CUSTOMER_ID = "customer_id";
    String EXPENSE_REPORT_MODEL_DELETED = "deleted";

    String QUOTATION_MODEL_TABLE = "quotation";
    String QUOTATION_MODEL_ID = "id";
    String QUOTATION_MODEL_STATUS = "status";
    String QUOTATION_MODEL_DELAY = "validation_delay";
    String QUOTATION_MODEL_CUSTOMER_ID = "customer_id";
    String QUOTATION_MODEL_CLIENT_ID = "client_id";
    String QUOTATION_MODEL_DELETED = "deleted";

    String QUOTATION_PRODUCT_MODEL_ID = "id";
    String QUOTATION_PRODUCT_MODEL_TABLE = "quotation_product";
    String QUOTATION_PRODUCT_MODEL_QUOTATION_ID = "quotation_id";
    String QUOTATION_PRODUCT_MODEL_PRODUCT_ID = "product_id";
    String QUOTATION_PRODUCT_MODEL_LABEL = "label";
    String QUOTATION_PRODUCT_MODEL_REFERENCE = "reference";
    String QUOTATION_PRODUCT_MODEL_PRICE = "price_excl_tax";
    String QUOTATION_PRODUCT_MODEL_UNITY = "unity";
    String QUOTATION_PRODUCT_MODEL_QUALIFICATION = "qualification";
    String QUOTATION_PRODUCT_MODEL_TAX = "tax";
    String QUOTATION_PRODUCT_MODEL_QUANTITY = "quantity";
    String QUOTATION_PRODUCT_MODEL_DELETED = "deleted";
    
    String INVOICE_MODEL_TABLE = "invoice";
    String INVOICE_MODEL_ID = "id";
    String INVOICE_MODEL_STATUS = "status";
    String INVOICE_MODEL_DELAY = "payment_delay";
    String INVOICE_MODEL_CUSTOMER_ID = "customer_id";
    String INVOICE_MODEL_CLIENT_ID = "client_id";
    String INVOICE_MODEL_QUOTATION_ID = "quotation_id";
    String INVOICE_MODEL_DELETED = "deleted";
    
    String INVOICE_PRODUCT_MODEL_TABLE = "invoice_product";
    String INVOICE_PRODUCT_MODEL_ID = "id";
    String INVOICE_PRODUCT_MODEL_INVOICE_ID = "invoice_id";
    String INVOICE_PRODUCT_MODEL_PRODUCT_ID = "product_id";
    String INVOICE_PRODUCT_MODEL_LABEL = "label";
    String INVOICE_PRODUCT_MODEL_REFERENCE = "reference";
    String INVOICE_PRODUCT_MODEL_PRICE = "price_excl_tax";
    String INVOICE_PRODUCT_MODEL_UNITY = "unity";
    String INVOICE_PRODUCT_MODEL_QUALIFICATION = "qualification";
    String INVOICE_PRODUCT_MODEL_TAX = "tax";
    String INVOICE_PRODUCT_MODEL_QUANTITY = "quantity";
    String INVOICE_PRODUCT_MODEL_DELETED = "deleted";

}