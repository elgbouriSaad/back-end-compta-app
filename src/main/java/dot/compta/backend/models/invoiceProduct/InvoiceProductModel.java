package dot.compta.backend.models.invoiceProduct;

import dot.compta.backend.constants.ModelConstants;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.product.ProductModel;
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
@Table(name = ModelConstants.INVOICE_PRODUCT_MODEL_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceProductModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_ID)
    private int id;

    @ManyToOne
    @JoinColumn(name = ModelConstants.INVOICE_PRODUCT_MODEL_INVOICE_ID, referencedColumnName = ModelConstants.INVOICE_MODEL_ID)
    private InvoiceModel invoice;
    
    @ManyToOne
    @JoinColumn(name = ModelConstants.INVOICE_PRODUCT_MODEL_PRODUCT_ID, referencedColumnName = ModelConstants.PRODUCT_MODEL_ID)
    private ProductModel product;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_LABEL)
    @NotBlank
    private String label;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_REFERENCE)
    @NotBlank
    private String reference;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_PRICE)
    @NotNull
    private double priceExclTax;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_UNITY)
    @NotBlank
    private String unity;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_QUALIFICATION)
    @NotBlank
    private String qualification;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_TAX)
    @NotNull
    private double tax;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_QUANTITY)
    @NotNull
    private int quantity;

    @Column(name = ModelConstants.INVOICE_PRODUCT_MODEL_DELETED)
    private boolean deleted;

}
