package dot.compta.backend.dtos.product;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class RequestProductDto extends BaseProductDto {
	
	@NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_PRODUCT_CUSTOMER_ID)
    private Integer customerId;

}
