package dot.compta.backend.mappers.expenseReport;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static dot.compta.backend.utils.TestUtils.*;

public class ExpenseReportMapperImplTest {

    private ExpenseReportMapperImpl expenseReportMapper;

    @BeforeEach
    void setUp() {
        expenseReportMapper = new ExpenseReportMapperImpl(new ModelMapper());
    }

    @Test
    void mapToExpenseReport() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        RequestExpenseReportDto expenseReport = RequestExpenseReportDto.builder()
                .label(EXPENSE_LABEL_TEST)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST)
                .tax(EXPENSE_TAX_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);

        // WHEN
        ExpenseReportModel result = expenseReportMapper.mapToExpenseReportModel(expenseReport, customer);

        // THEN
        assertNotNull(result);
        assertEquals(EXPENSE_LABEL_TEST, result.getLabel());
        assertEquals(EXPENSE_PRICE_TEST, result.getPriceExclTax());
        assertEquals(EXPENSE_QUALIFICATION_TEST, result.getQualification());
        assertEquals(EXPENSE_TAX_TEST, result.getTax());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomer().getId());
    }

    @Test
    public void mapToExpenseReportDtoTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ExpenseReportModel expenseReportModel = ExpenseReportModel.builder()
                .id(EXPENSE_ID_TEST__1009)
                .status(ExpenseReportStatus.SAVED)
                .label(EXPENSE_LABEL_TEST)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST)
                .tax(EXPENSE_TAX_TEST)
                .customer(customer)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);

        // WHEN
        ResponseExpenseReportDto result = expenseReportMapper.mapToResponseExpenseReportDto(expenseReportModel);

        // THEN
        assertNotNull(result);
        assertEquals(EXPENSE_ID_TEST__1009, result.getId());
        assertEquals(EXPENSE_LABEL_TEST, result.getLabel());
        assertEquals(ExpenseReportStatus.SAVED, result.getStatus());
        assertEquals(EXPENSE_PRICE_TEST, result.getPriceExclTax());
        assertEquals(EXPENSE_QUALIFICATION_TEST, result.getQualification());
        assertEquals(EXPENSE_TAX_TEST, result.getTax());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomerId());

    }

}
