package dot.compta.backend.models.quotationProduct;

import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotation.QuotationModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ModelConstants.QUOTATION_PRODUCT_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_ID)
    private int id;

    @ManyToOne
    @JoinColumn(name = ModelConstants.QUOTATION_PRODUCT_MODEL_QUOTATION_ID, referencedColumnName = ModelConstants.QUOTATION_MODEL_ID)
    private QuotationModel quotation;

    @ManyToOne
    @JoinColumn(name = ModelConstants.QUOTATION_PRODUCT_MODEL_PRODUCT_ID, referencedColumnName = ModelConstants.PRODUCT_MODEL_ID)
    private ProductModel product;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_LABEL)
    @NotBlank
    private String label;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_REFERENCE)
    @NotBlank
    private String reference;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_PRICE)
    @NotNull
    private double priceExclTax;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_UNITY)
    @NotBlank
    private String unity;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_QUALIFICATION)
    @NotBlank
    private String qualification;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_TAX)
    @NotNull
    private double tax;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_QUANTITY)
    @NotNull
    private int quantity;

    @Column(name = ModelConstants.QUOTATION_PRODUCT_MODEL_DELETED)
    private boolean deleted;

}
