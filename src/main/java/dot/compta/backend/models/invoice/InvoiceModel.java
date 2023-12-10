package dot.compta.backend.models.invoice;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.quotation.QuotationModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.INVOICE_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.INVOICE_MODEL_ID)
    private int id;
	
	@Column(name = ModelConstants.INVOICE_MODEL_STATUS)
    @NotNull
    private InvoiceStatus status;
	
	@Column(name = ModelConstants.INVOICE_MODEL_DELAY)
    @NotNull
    private int paymentDelay;
	
	@ManyToOne
    @JoinColumn(name = ModelConstants.INVOICE_MODEL_CUSTOMER_ID, referencedColumnName = ModelConstants.CUSTOMER_MODEL_ID)
    private CustomerModel customer;
	
	@ManyToOne
    @JoinColumn(name = ModelConstants.INVOICE_MODEL_CLIENT_ID, referencedColumnName = ModelConstants.CLIENT_MODEL_ID)
    private ClientModel client;
	
	@ManyToOne
    @JoinColumn(name = ModelConstants.INVOICE_MODEL_QUOTATION_ID, referencedColumnName = ModelConstants.QUOTATION_MODEL_ID)
    private QuotationModel quotation;
	
	@Column(name = ModelConstants.INVOICE_MODEL_DELETED)
    private boolean deleted;

}
