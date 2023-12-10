package dot.compta.backend.mappers.expenseReport;

import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExpenseReportMapperImpl implements ExpenseReportMapper {

    private final ModelMapper modelMapper;

    @Override
    public ExpenseReportModel mapToExpenseReportModel(RequestExpenseReportDto requestExpenseReportDto, CustomerModel customer) {
        ExpenseReportModel expenseReport = modelMapper.map(requestExpenseReportDto, ExpenseReportModel.class);
        expenseReport.setCustomer(customer);
        return expenseReport;
    }
    
    @Override
    public ExpenseReportModel mapToExpenseReportModel(UpdateExpenseReportDto updateExpenseReportDto, CustomerModel customer) {
        ExpenseReportModel expenseReport = modelMapper.map(updateExpenseReportDto, ExpenseReportModel.class);
        expenseReport.setCustomer(customer);
        return expenseReport;
    }

    @Override
    public ResponseExpenseReportDto mapToResponseExpenseReportDto(ExpenseReportModel expenseReportModel) {
        ResponseExpenseReportDto responseExpenseReportDto = modelMapper.map(expenseReportModel, ResponseExpenseReportDto.class);
        responseExpenseReportDto.setCustomerId(expenseReportModel.getCustomer().getId());
        return responseExpenseReportDto;
    }

}
