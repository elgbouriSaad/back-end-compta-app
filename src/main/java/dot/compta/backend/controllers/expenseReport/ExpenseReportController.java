package dot.compta.backend.controllers.expenseReport;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;
import dot.compta.backend.services.expenseReport.ExpenseReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.EXPENSE_REPORTS_URL)
@AllArgsConstructor
public class ExpenseReportController {

    private final ExpenseReportService expenseReportService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_TAGS)
    @Transactional
    public void createExpenseReport(@RequestBody @Validated RequestExpenseReportDto expenseReport) {
        expenseReportService.createExpenseReport(expenseReport);
    }
    
    @PutMapping(APIConstants.EXPENSE_REPORT_VALIDATE_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_VALIDATE_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_VALIDATE_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_TAGS)
    @Transactional
    public void validateExpenseReport(@PathVariable int id) {
    	expenseReportService.validateExpenseReport(id);
    }
    
    @PutMapping(APIConstants.EXPENSE_REPORT_UPDATE_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_UPDATE_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_UPDATE_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_TAGS)
    @Transactional
    public void updateExpenseReport(@RequestBody @Validated UpdateExpenseReportDto expenseReport ,@PathVariable int id) {
    	expenseReportService.updateExpenseReport(expenseReport,id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_TAGS)
    public List<ResponseExpenseReportDto> getExpenseReports() {
        return expenseReportService.getExpenseReports();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_IDGET_SUM_DESC,
            description = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_IDGET_SUM_DESC,
            tags = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_TAGS)
    public ResponseExpenseReportDto getExpenseReportById(@PathVariable int id) {
        return expenseReportService.getExpenseReportById(id);
    }

    @GetMapping(APIConstants.CUSTOMER_EXPENSE_REPORTS_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_CUSTOMERGET_SUM_DESC,
            description = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_CUSTOMERGET_SUM_DESC,
            tags = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_TAGS)
    public List<ResponseExpenseReportDto> getExpenseReportsByCustomerId(@PathVariable int id) {
        return expenseReportService.getExpenseReportsByCustomerId(id);
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_DELETE_SUM_DESC,
            description = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_DELETE_SUM_DESC,
            tags = DocumentationConstants.DOC_EXPENSE_REPORT_CONTROLLER_TAGS)
    @Transactional
    public void deleteExpenseReport(@PathVariable int id) {
    	expenseReportService.deleteExpenseReport(id);
    }

}
