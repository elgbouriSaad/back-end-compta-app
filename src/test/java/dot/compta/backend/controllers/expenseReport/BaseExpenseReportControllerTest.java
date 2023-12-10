package dot.compta.backend.controllers.expenseReport;

import com.fasterxml.jackson.databind.ObjectMapper;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;

import org.springframework.test.web.servlet.MockMvc;

import static dot.compta.backend.utils.TestUtils.*;

class BaseExpenseReportControllerTest {

    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mvc;

    protected RequestExpenseReportDto buildRequestExpenseReportDto(String suffix) {
        return RequestExpenseReportDto.builder()
                .label(EXPENSE_LABEL_TEST + suffix)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST + suffix)
                .tax(EXPENSE_TAX_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
    }

    protected RequestExpenseReportDto buildRequestExpenseReportDto() {
        return buildRequestExpenseReportDto("");
    }
    
    protected UpdateExpenseReportDto buildUpdateExpenseReportDto(String suffix) {
        return UpdateExpenseReportDto.builder()
                .label(EXPENSE_LABEL_TEST + suffix)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST + suffix)
                .tax(EXPENSE_TAX_TEST)
                .build();
    }

    protected UpdateExpenseReportDto buildUpdateExpenseReportDto() {
        return buildUpdateExpenseReportDto("");
    }

    protected ResponseExpenseReportDto buildResponseExpenseReportDto() {
        return ResponseExpenseReportDto.builder()
                .id(EXPENSE_ID_TEST__1009)
                .label(EXPENSE_LABEL_TEST)
                .status(ExpenseReportStatus.SAVED)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST)
                .tax(EXPENSE_TAX_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
    }

}
