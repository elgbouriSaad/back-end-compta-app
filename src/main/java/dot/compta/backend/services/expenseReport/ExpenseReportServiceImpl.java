package dot.compta.backend.services.expenseReport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;
import dot.compta.backend.mappers.expenseReport.ExpenseReportMapper;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.expenseReport.ExpenseReportRepository;
import dot.compta.backend.validators.customer.CustomerValidator;
import dot.compta.backend.validators.expenseReport.ExpenseReportValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpenseReportServiceImpl implements ExpenseReportService {

	private final ExpenseReportMapper expenseReportMapper;

	private final ExpenseReportRepository expenseReportRepository;

	private final CustomerValidator customerValidator;

	private final CustomerRepository customerRepository;

	private final ExpenseReportValidator expenseReportValidator;

	@Override
	public void createExpenseReport(RequestExpenseReportDto requestExpenseReportDto) {
		customerValidator.validateExistsAndNotDeleted(requestExpenseReportDto.getCustomerId());
		Optional<CustomerModel> customer = customerRepository.findById(requestExpenseReportDto.getCustomerId());
		ExpenseReportModel expenseReport = expenseReportMapper.mapToExpenseReportModel(requestExpenseReportDto, customer.get()) ;
		expenseReport.setStatus(ExpenseReportStatus.SAVED);
		expenseReportRepository.save(expenseReport);
	}

	@Override
	public List<ResponseExpenseReportDto> getExpenseReports() {
		List<ExpenseReportModel> nonDeletedExpenseReports = expenseReportRepository.findAllByDeletedFalse();
		return nonDeletedExpenseReports.stream()
				.map(expenseReportMapper::mapToResponseExpenseReportDto)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseExpenseReportDto getExpenseReportById(int id) {
		expenseReportValidator.validateExistsAndNotDeleted(id);
		Optional<ExpenseReportModel> expenseReport = expenseReportRepository.findById(id);
		return expenseReportMapper.mapToResponseExpenseReportDto(expenseReport.get());
	}

	@Override
	public List<ResponseExpenseReportDto> getExpenseReportsByCustomerId(int id) {
		customerValidator.validateExistsAndNotDeleted(id);
		List<ExpenseReportModel> expenseReports = expenseReportRepository.findAllByCustomerIdAndDeletedFalse(id);
		return expenseReports.stream()
				.map(expenseReportMapper::mapToResponseExpenseReportDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public void validateExpenseReport(int id) {
		expenseReportValidator.validateExistsAndNotDeleted(id);
		expenseReportValidator.validateSavedStatus(id);
		expenseReportRepository.updateStatusById(ExpenseReportStatus.VALIDATED, id);
	}
	
	@Override
	public void updateExpenseReport(UpdateExpenseReportDto updateExpenseReportDto, int id) {
		expenseReportValidator.validateExistsAndNotDeleted(id);
		expenseReportValidator.validateSavedStatus(id);
		Optional<ExpenseReportModel> oldExpenseReport = expenseReportRepository.findById(id);
		ExpenseReportModel expenseReport = expenseReportMapper.mapToExpenseReportModel(updateExpenseReportDto, oldExpenseReport.get().getCustomer());
		expenseReport.setId(id);
		expenseReport.setStatus(ExpenseReportStatus.SAVED);
		expenseReportRepository.save(expenseReport);
	}
	
	@Override
	public void deleteExpenseReport(int id) {
		expenseReportValidator.validateExistsAndNotDeleted(id);
		expenseReportValidator.validateSavedStatus(id);
		expenseReportRepository.logicalDeleteById(id);
	}
	
	@Override
	public void deleteCustomerExpenseReports(int customerId) {
		List<ExpenseReportModel> nonDeletedExpenseReports = expenseReportRepository.findAllByCustomerIdAndDeletedFalse(customerId);
		for (ExpenseReportModel expenseReport : nonDeletedExpenseReports) {
			expenseReportRepository.logicalDeleteById(expenseReport.getId());
		}
	}

}
