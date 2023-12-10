package dot.compta.backend.dtos.city;

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
public class ResponseCityDto extends BaseCityDto {

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_CITY_ID)
    private int id;

}
