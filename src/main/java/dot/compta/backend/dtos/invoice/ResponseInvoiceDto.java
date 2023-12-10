package dot.compta.backend.dtos.invoice;

import java.util.List;

import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
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
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ResponseInvoiceDto extends BaseInvoiceDto{
	
	@NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_INVOICE_ID)
    private int id;

    @NotBlank(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_INVOICE_INVOICE_PRODUCTS)
    private List<ResponseInvoiceProductDto> invoiceProducts;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_INVOICE_STATUS)
    private InvoiceStatus status;
    
    @Schema(description = DocumentationConstants.DOC_INVOICE_QUOTATION_ID)
    protected Integer quotationId;
    
    @NotNull(message = ValidationConstants.MANDATORY_FIELD)
    @Schema(description = DocumentationConstants.DOC_INVOICE_CUSTOMER_ID)
    protected Integer customerId;

}
