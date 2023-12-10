package dot.compta.backend.mappers.invoiceProduct;

import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.invoiceProduct.InvoiceProductModel;
import dot.compta.backend.models.product.ProductModel;
import dot.compta.backend.models.quotationProduct.QuotationProductModel;

public interface InvoiceProductMapper {
	
	InvoiceProductModel mapToInvoiceProductModel(ProductModel product);
	
	InvoiceProductModel mapToInvoiceProductModel(QuotationProductModel quotationProduct, InvoiceModel invoice);
	
	ResponseInvoiceProductDto mapToResponseInvoiceProductDto(InvoiceProductModel invoiceProduct);

}
