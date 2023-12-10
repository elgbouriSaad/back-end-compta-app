package dot.compta.backend.dtos.quotationProduct;

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
public class ResponseQuotationProductDto {
	
	@NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_ID)
    private int id;
	
	@NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_PRODUCTID)
    private int productId;

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_LABEL)
    protected String label;
    
    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_REFERENCE)
    protected String reference;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_PRICE)
    protected double priceExclTax;
    
    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_UNITY)
    protected String unity;
    
    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_QUALIFICATION)
    protected String qualification;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_TAX)
    protected double tax;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_QUANTITY)
    protected int quantity;

}
