package dot.compta.backend.dtos.expenseReport;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseExpenseReportDto extends BaseExpenseReportDto {

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_EXPENSE_REPORT_ID)
    private int id;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_EXPENSE_REPORT_LABEL)
    private ExpenseReportStatus status;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_EXPENSE_REPORT_CUSTOMER_ID)
    private Integer customerId;

}
