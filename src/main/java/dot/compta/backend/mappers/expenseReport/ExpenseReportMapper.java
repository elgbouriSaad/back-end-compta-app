package dot.compta.backend.mappers.expenseReport;

import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;

public interface ExpenseReportMapper {

    ExpenseReportModel mapToExpenseReportModel(RequestExpenseReportDto requestExpenseReportDto, CustomerModel customer);
    
    ExpenseReportModel mapToExpenseReportModel(UpdateExpenseReportDto updateExpenseReportDto, CustomerModel customer);

    ResponseExpenseReportDto mapToResponseExpenseReportDto(ExpenseReportModel expenseReportModel);

}
