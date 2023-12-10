package dot.compta.backend.dtos.expenseReport;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseExpenseReportDto {

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_EXPENSE_REPORT_LABEL)
    protected String label;

    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_EXPENSE_REPORT_PRICE)
    protected Double priceExclTax;

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_EXPENSE_REPORT_QUALIFICATION)
    protected String qualification;

    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_EXPENSE_REPORT_TAX)
    protected Double tax;

}
