package dot.compta.backend.models.expenseReport;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.customer.CustomerModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.EXPENSE_REPORT_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseReportModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.EXPENSE_REPORT_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.EXPENSE_REPORT_MODEL_STATUS)
    @NotNull
    private ExpenseReportStatus status;

    @Column(name = ModelConstants.EXPENSE_REPORT_MODEL_LABEL)
    @NotBlank
    private String label;

    @Column(name = ModelConstants.EXPENSE_REPORT_MODEL_PRICE)
    @NotNull
    private double priceExclTax;

    @Column(name = ModelConstants.EXPENSE_REPORT_MODEL_QUALIFICATION)
    @NotBlank
    private String qualification;

    @Column(name = ModelConstants.EXPENSE_REPORT_MODEL_TAX)
    @NotNull
    private double tax;

    @ManyToOne
    @JoinColumn(name = ModelConstants.EXPENSE_REPORT_MODEL_CUSTOMER_ID, referencedColumnName = ModelConstants.CUSTOMER_MODEL_ID)
    private CustomerModel customer;

    @Column(name = ModelConstants.EXPENSE_REPORT_MODEL_DELETED)
    private boolean deleted;

}
