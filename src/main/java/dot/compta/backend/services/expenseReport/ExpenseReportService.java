package dot.compta.backend.services.expenseReport;

import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;

import java.util.List;

public interface ExpenseReportService {

    void createExpenseReport(RequestExpenseReportDto requestExpenseReportDto);
    
    void validateExpenseReport(int id);
    
    void updateExpenseReport(UpdateExpenseReportDto updateExpenseReportDto, int id);
    
    void deleteExpenseReport(int id);
    
    void deleteCustomerExpenseReports(int customerId);

    List<ResponseExpenseReportDto> getExpenseReports();

    ResponseExpenseReportDto getExpenseReportById(int id);

    List<ResponseExpenseReportDto> getExpenseReportsByCustomerId(int id);

}
