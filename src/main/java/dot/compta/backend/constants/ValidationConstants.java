package dot.compta.backend.constants;

public interface ValidationConstants {

    String MANDATORY_FIELD = "Mandatory field";

    String NOT_VALID_EMAIL = "Not valid email";

    String CITY_NOT_CORRECT_OR_NOT_SUPPORTED_MSG = "City <%s> not correct or not supported, please contact support";

    String COUNTRY_AND_CITY_DONT_MATCH_MSG = "Country <%s> and city <%s> don't match, please contact support";

    String ACCOUNTANT_NOT_FOUND = "No accountant with id <%d> was found";

    String COUNTRY_ALREADY_EXISTS = "Country <%s> already exists";

    String COUNTRY_NOT_EXISTS = "Country <%s> doesn't exist";

    String CITY_ALREADY_EXISTS = "City <%s> already exists";

    String CUSTOMER_NOT_FOUND = "No customer with id <%d> was found";

    String ACCOUNTANT_DELETED = "Accountant with id <%d> is deleted";

    String CUSTOMER_DELETED = "Customer with id <%d> is deleted";

    String CLIENT_NOT_FOUND = "No client with id <%d> was found";

    String CLIENT_DELETED = "Client with id <%d> is deleted";

    String PRODUCT_ALREADY_EXISTS = "Product <%s> already exists for customer <%d>";

    String PRODUCT_NOT_FOUND = "No product with id <%d> was found";

    String PRODUCT_DELETED = "Product with id <%d> is deleted";

    String EXPENSE_REPORT_NOT_FOUND = "No expense report with id <%d> was found";

    String EXPENSE_REPORT_DELETED = "Expense report with id <%d> is deleted";

    String QUOTATION_NOT_FOUND = "No quotation with id <%d> was found";

    String QUOTATION_DELETED = "Quotation with id <%d> is deleted";
    
    String QUOTATION_STATUS_INVALID = "Quotation with id <%d> is not <%s>";
    
    String EXPENSE_REPORT_STATUS_INVALID = "Expense report with id <%d> is not saved";
    
    String INVOICE_NOT_FOUND = "No invoice with id <%d> was found";

    String INVOICE_DELETED = "Invoice with id <%d> is deleted";
    
    String INVOICE_STATUS_INVALID = "Invoice with id <%d> is not saved";
    
    String QUOTATION_LINKED_TO_INVOICE = "Quotation with id <%d> is linked to an invoice and cannot be transformed";

}