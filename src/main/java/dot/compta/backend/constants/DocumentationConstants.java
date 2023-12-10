package dot.compta.backend.constants;

public interface DocumentationConstants {

    String DOC_ADDRESS_PRIMARY = "Primary address";
    String DOC_ADDRESS_SECONDARY = "Secondary address";
    String DOC_ADDRESS_POSTAL_CODE = "Postal code";
    String DOC_ADDRESS_CITY = "City Name";
    String DOC_ADDRESS_COUNTRY = "Country Name";

    String DOC_CITY_ID = "City ID";
    String DOC_CITY_CONTROLLER_GET_SUM_DESC = "Get all cities";
    String DOC_CITY_CONTROLLER_TAGS = "Cities";
    String DOC_CITY_CONTROLLER_POST_SUM_DESC = "Create new city";

    String DOC_COUNTRY_ID = "Country ID";
    String DOC_COUNTRY_CONTROLLER_GET_SUM_DESC = "Get all countries";
    String DOC_COUNTRY_CONTROLLER_POST_SUM_DESC = "Create new country";
    String DOC_COUNTRY_CONTROLLER_TAGS = "Countries";

    String DOC_ACCOUNTANT_ID = "Accountant id";
    String DOC_ACCOUNTANT_COMPANY_NAME = "Company name";
    String DOC_ACCOUNTANT_RC = "RC Number";
    String DOC_ACCOUNTANT_EMAIL = "Email";
    String DOC_ACCOUNTANT_ADDRESS = "Company address";
    String DOC_ACCOUNTANT_MOBILE_PHONE = "Mobile phone number";
    String DOC_ACCOUNTANT_PHONE = "Phone number";
    String DOC_ACCOUNTANT_FAX = "Fax number";
    String DOC_ACCOUNTANT_CONTROLLER_POST_SUM_DESC = "Create new accountant";
    String DOC_ACCOUNTANT_CONTROLLER_GET_SUM_DESC = "Get all accountants";
    String DOC_ACCOUNTANT_CONTROLLER_IDGET_SUM_DESC = "Get accountant by Id";
    String DOC_ACCOUNTANT_CONTROLLER_TAGS = "Accountants";
    String DOC_ACCOUNTANT_CONTROLLER_PUT_SUM_DESC = "Update accountant";
    String DOC_ACCOUNTANT_CONTROLLER_DELETE_SUM_DESC = "Delete accountant";

    String DOC_CUSTOMER_CONTROLLER_POST_SUM_DESC = "Create customer";
    String DOC_CUSTOMER_CONTROLLER_TAGS = "Customers";
    String DOC_CUSTOMER_CONTROLLER_GET_SUM_DESC = "Get all customers";
    String DOC_CUSTOMER_CONTROLLER_IDGET_SUM_DESC = "Get customer by Id";
    String DOC_CUSTOMER_CONTROLLER_PUT_SUM_DESC = "Update customer";
    String DOC_CUSTOMER_CONTROLLER_DELETE_SUM_DESC = "Delete customer";
    String DOC_CUSTOMER_CONTROLLER_ACCOUNTANTGET_SUM_DESC = "Get customers by accountant Id";
    String DOC_CUSTOMER_ID = "Customer id";
    String DOC_CUSTOMER_COMPANY_NAME = "Company name";
    String DOC_CUSTOMER_RC = "RC Number";
    String DOC_CUSTOMER_EMAIL = "Email";
    String DOC_CUSTOMER_ADDRESS = "Company address";
    String DOC_CUSTOMER_MOBILE_PHONE = "Mobile phone number";
    String DOC_CUSTOMER_PHONE = "Phone number";
    String DOC_CUSTOMER_FAX = "Fax number";
    String DOC_CUSTOMER_ACCOUNTANT_ID = "Accountant id";

    String DOC_CLIENT_CONTROLLER_POST_SUM_DESC = "Create new client";
    String DOC_CLIENT_CONTROLLER_GET_SUM_DESC = "Get all clients";
    String DOC_CLIENT_CONTROLLER_IDGET_SUM_DESC = "Get client by Id";
    String DOC_CLIENT_CONTROLLER_PUT_SUM_DESC = "Update client";
    String DOC_CLIENT_CONTROLLER_DELETE_SUM_DESC = "Delete client";
    String DOC_CLIENT_CONTROLLER_CUSTOMERGET_SUM_DESC = "Get clients by customer Id";
    String DOC_CLIENT_CONTROLLER_TAGS = "Clients";
    String DOC_CLIENT_ID = "Client id";
    String DOC_CLIENT_COMPANY_NAME = "Company name";
    String DOC_CLIENT_RC = "RC Number";
    String DOC_CLIENT_EMAIL = "Email";
    String DOC_CLIENT_ADDRESS = "Company address";
    String DOC_CLIENT_MOBILE_PHONE = "Mobile phone number";
    String DOC_CLIENT_PHONE = "Phone number";
    String DOC_CLIENT_FAX = "Fax number";
    String DOC_CLIENT_CUSTOMER_ID = "Customer id";

    String DOC_PRODUCT_ID = "Product id";
    String DOC_PRODUCT_LABEL = "Product label";
    String DOC_PRODUCT_REFERENCE = "Product reference";
    String DOC_PRODUCT_PRICE = "Product price excl tax";
    String DOC_PRODUCT_UNITY = "Product unity";
    String DOC_PRODUCT_QUALIFICATION = "Product qualification";
    String DOC_PRODUCT_TAX = "Product tax";
    String DOC_PRODUCT_CONTROLLER_TAGS = "Products";
    String DOC_PRODUCT_CONTROLLER_POST_SUM_DESC = "Create new product";
    String DOC_PRODUCT_CONTROLLER_GET_SUM_DESC = "Get all products";
    String DOC_PRODUCT_CONTROLLER_IDGET_SUM_DESC = "Get product by Id";
    String DOC_PRODUCT_CONTROLLER_PUT_SUM_DESC = "Update product";
    String DOC_PRODUCT_CONTROLLER_DELETE_SUM_DESC = "Delete product";
    String DOC_PRODUCT_CONTROLLER_CUSTOMERGET_SUM_DESC = "Get products by customer Id";
    String DOC_PRODUCT_CUSTOMER_ID = "Customer id";
    String DOC_PRODUCT_QUANTITY_PRODUCT_ID = "Product id";
    String DOC_PRODUCT_QUANTITY_QUANTITY = "Quantity";

    String DOC_QUOTATION_ID = "Quotation id";
    String DOC_QUOTATION_STATUS = "Quotation status";
    String DOC_QUOTATION_DELAY = "Quotation validation delay";
    String DOC_QUOTATION_CUSTOMER_ID = "Customer id";
    String DOC_QUOTATION_CLIENT_ID = "Client id";
    String DOC_QUOTATION_PRODUCT_QUANTITIES = "Product quantities";
    String DOC_QUOTATION_QUOTATION_PRODUCTS = "Quotation products";
    String DOC_QUOTATION_CONTROLLER_TAGS = "Quotations";
    String DOC_QUOTATION_CONTROLLER_POST_SUM_DESC = "Create new quotation";
    String DOC_QUOTATION_CONTROLLER_VALIDATE_PUT_SUM_DESC = "Validate a quotation";
    String DOC_QUOTATION_CONTROLLER_UPDATE_PUT_SUM_DESC = "Update a quotation";
    String DOC_QUOTATION_CONTROLLER_TRANSFORM_PUT_SUM_DESC = "Transform a quotation into an invoice";
    String DOC_QUOTATION_CONTROLLER_GET_SUM_DESC = "Get all quotations";
    String DOC_QUOTATION_CONTROLLER_IDGET_SUM_DESC = "Get quotation by Id";
    String DOC_QUOTATION_CONTROLLER_CUSTOMERGET_SUM_DESC = "Get quotations by customer Id";
    String DOC_QUOTATION_CONTROLLER_CLIENTGET_SUM_DESC = "Get quotations by client Id";
    String DOC_QUOTATION_CONTROLLER_DELETE_SUM_DESC = "Delete a quotation";

    String DOC_QUOTATION_PRODUCT_ID = "Quotation product id";
    String DOC_QUOTATION_PRODUCT_PRODUCTID = "Quotation product productId";
    String DOC_QUOTATION_PRODUCT_LABEL = "Quotation product label";
    String DOC_QUOTATION_PRODUCT_REFERENCE = "Quotation product reference";
    String DOC_QUOTATION_PRODUCT_PRICE = "Quotation product price excluding tax";
    String DOC_QUOTATION_PRODUCT_UNITY = "Quotation product unity";
    String DOC_QUOTATION_PRODUCT_QUALIFICATION = "Quotation product qualification";
    String DOC_QUOTATION_PRODUCT_TAX = "Quotation product tax";
    String DOC_QUOTATION_PRODUCT_QUANTITY = "Quotation product quantity";

    String DOC_EXPENSE_REPORT_ID = "Expense report id";
    String DOC_EXPENSE_REPORT_LABEL = "Expense report label";
    String DOC_EXPENSE_REPORT_PRICE = "Expense report price excl tax";
    String DOC_EXPENSE_REPORT_QUALIFICATION = "Expense report qualification";
    String DOC_EXPENSE_REPORT_TAX = "Expense report tax";
    String DOC_EXPENSE_REPORT_CUSTOMER_ID = "Customer id";
    String DOC_EXPENSE_REPORT_CONTROLLER_TAGS = "Expense reports";
    String DOC_EXPENSE_REPORT_CONTROLLER_POST_SUM_DESC = "Create new expense report";
    String DOC_EXPENSE_REPORT_CONTROLLER_GET_SUM_DESC = "Get all expense reports";
    String DOC_EXPENSE_REPORT_CONTROLLER_IDGET_SUM_DESC = "Get expense report by Id";
    String DOC_EXPENSE_REPORT_CONTROLLER_CUSTOMERGET_SUM_DESC = "Get expense report by customer Id";
    String DOC_EXPENSE_REPORT_CONTROLLER_VALIDATE_PUT_SUM_DESC = "Validate an expense report";
    String DOC_EXPENSE_REPORT_CONTROLLER_UPDATE_PUT_SUM_DESC = "Update an expense report";
    String DOC_EXPENSE_REPORT_CONTROLLER_DELETE_SUM_DESC = "Delete expense report";

    String DOC_INVOICE_ID = "Invoice id";
    String DOC_INVOICE_DELAY = "Invoice payment delay";
    String DOC_INVOICE_STATUS = "Invoice status";
    String DOC_INVOICE_CUSTOMER_ID = "Customer id";
    String DOC_INVOICE_CLIENT_ID = "Client id";
    String DOC_INVOICE_QUOTATION_ID = "Quotation id";
    String DOC_INVOICE_PRODUCT_QUANTITIES = "Product quantities";
    String DOC_INVOICE_CONTROLLER_TAGS = "Invoices";
    String DOC_INVOICE_CONTROLLER_POST_SUM_DESC = "Create new invoice";
    String DOC_INVOICE_CONTROLLER_GET_SUM_DESC = "Get all invoices";
    String DOC_INVOICE_CONTROLLER_IDGET_SUM_DESC = "Get invoice by Id";
    String DOC_INVOICE_CONTROLLER_CUSTOMERGET_SUM_DESC = "Get invoices by customer Id";
    String DOC_INVOICE_CONTROLLER_CLIENTGET_SUM_DESC = "Get invoices by client Id";
    String DOC_INVOICE_CONTROLLER_VALIDATE_PUT_SUM_DESC = "Validate an invoice";
    String DOC_INVOICE_CONTROLLER_UPDATE_PUT_SUM_DESC = "Update an invoice";
    String DOC_INVOICE_CONTROLLER_DELETE_SUM_DESC = "Delete an invoice";
    String DOC_INVOICE_INVOICE_PRODUCTS = "Invoice products";
    
    String DOC_INVOICE_PRODUCT_ID = "Invoice product id";
    String DOC_INVOICE_PRODUCT_PRODUCTID = "Invoice product productId";
    String DOC_INVOICE_PRODUCT_LABEL = "Invoice product label";
    String DOC_INVOICE_PRODUCT_REFERENCE = "Invoice product reference";
    String DOC_INVOICE_PRODUCT_PRICE = "Invoice product price excluding tax";
    String DOC_INVOICE_PRODUCT_UNITY = "Invoice product unity";
    String DOC_INVOICE_PRODUCT_QUALIFICATION = "Invoice product qualification";
    String DOC_INVOICE_PRODUCT_TAX = "Invoice product tax";
    String DOC_INVOICE_PRODUCT_QUANTITY = "Invoice product quantity";

}
