package dot.compta.backend.dtos.quotation;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class RequestQuotationDto extends BaseQuotationDto {

    @NotEmpty(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_PRODUCT_QUANTITIES)
    @Valid
    private List<RequestProductQuantityDto> productQuantities;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_QUOTATION_CUSTOMER_ID)
    protected Integer customerId;

}
