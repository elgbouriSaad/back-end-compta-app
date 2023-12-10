package dot.compta.backend.dtos.customer;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResponseCustomerDto extends BaseCustomerDto {

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_CUSTOMER_ID)
    private Integer id;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_CUSTOMER_ACCOUNTANT_ID)
    private Integer accountantId;

}
