package dot.compta.backend.dtos.country;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class ResponseCountryDto extends BaseCountryDto {

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_COUNTRY_ID)
    private int id;

}
