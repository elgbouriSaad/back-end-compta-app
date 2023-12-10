package dot.compta.backend.dtos.country;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseCountryDto {

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_ADDRESS_COUNTRY)
    private String name;

}
