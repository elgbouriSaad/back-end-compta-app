package dot.compta.backend.dtos.invoice;

import java.util.List;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateInvoiceDto extends BaseInvoiceDto{
	
	@NotEmpty(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_INVOICE_PRODUCT_QUANTITIES)
    @Valid
    private List<RequestProductQuantityDto> productQuantities;

}
