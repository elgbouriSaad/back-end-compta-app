package dot.compta.backend.models.quotation;

import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.QUOTATION_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.QUOTATION_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.QUOTATION_MODEL_STATUS)
    @NotNull
    private QuotationStatus status;

    @Column(name = ModelConstants.QUOTATION_MODEL_DELAY)
    @NotNull
    private int validationDelay;

    @ManyToOne
    @JoinColumn(name = ModelConstants.QUOTATION_MODEL_CUSTOMER_ID, referencedColumnName = ModelConstants.CUSTOMER_MODEL_ID)
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = ModelConstants.QUOTATION_MODEL_CLIENT_ID, referencedColumnName = ModelConstants.CLIENT_MODEL_ID)
    private ClientModel client;

    @Column(name = ModelConstants.QUOTATION_MODEL_DELETED)
    private boolean deleted;

}
