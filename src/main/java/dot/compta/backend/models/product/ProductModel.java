package dot.compta.backend.models.product;

import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.customer.CustomerModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.PRODUCT_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.PRODUCT_MODEL_ID)
    private int id;

    @Column(name = ModelConstants.PRODUCT_MODEL_LABEL)
    @NotBlank
    private String label;

    @Column(name = ModelConstants.PRODUCT_MODEL_REFERENCE)
    @NotBlank
    private String reference;

    @Column(name = ModelConstants.PRODUCT_MODEL_PRICE)
    @NotNull
    private double priceExclTax;

    @Column(name = ModelConstants.PRODUCT_MODEL_UNITY)
    @NotBlank
    private String unity;

    @Column(name = ModelConstants.PRODUCT_MODEL_QUALIFICATION)
    @NotBlank
    private String qualification;

    @Column(name = ModelConstants.PRODUCT_MODEL_TAX)
    @NotNull
    private double tax;

    @ManyToOne
    @JoinColumn(name = ModelConstants.PRODUCT_MODEL_CUSTOMER_ID, referencedColumnName = ModelConstants.CUSTOMER_MODEL_ID)
    private CustomerModel customer;

    @Column(name = ModelConstants.PRODUCT_MODEL_DELETED)
    private boolean deleted;

}
