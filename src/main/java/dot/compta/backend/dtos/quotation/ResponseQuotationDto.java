package dot.compta.backend.dtos.quotation;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseQuotationDto extends BaseQuotationDto {

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_ID)
    private int id;

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_QUOTATION_PRODUCTS)
    private List<ResponseQuotationProductDto> quotationProducts;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_STATUS)
    private QuotationStatus status;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_CUSTOMER_ID)
    protected Integer customerId;

}
