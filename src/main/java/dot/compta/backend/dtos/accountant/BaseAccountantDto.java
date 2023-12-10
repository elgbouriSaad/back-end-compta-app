package dot.compta.backend.dtos.accountant;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.dtos.address.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class BaseAccountantDto {
    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_ACCOUNTANT_COMPANY_NAME)
    protected String companyName;

    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_ACCOUNTANT_RC)
    protected Integer rc;

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Email(message = ValidationConstants.NOT_VALID_EMAIL)
    @Schema(description = DocumentationConstants.DOC_ACCOUNTANT_EMAIL)
    protected String email;

    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_ACCOUNTANT_ADDRESS)
    @Valid
    protected AddressDto address;

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_ACCOUNTANT_MOBILE_PHONE)
    protected String mobilePhone;

    @Schema(description = DocumentationConstants.DOC_ACCOUNTANT_PHONE)
    protected String phone;

    @Schema(description = DocumentationConstants.DOC_ACCOUNTANT_FAX)
    protected String fax;
}
