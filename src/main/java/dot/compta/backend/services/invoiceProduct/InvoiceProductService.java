package dot.compta.backend.services.invoiceProduct;

import java.util.List;

import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.models.invoice.InvoiceModel;

public interface InvoiceProductService {
	
	void createInvoiceProducts(InvoiceModel invoice, List<RequestProductQuantityDto> productQuantities);
	
	void updateInvoiceProducts(InvoiceModel invoice, List<RequestProductQuantityDto> productQuantities);
	
	void deleteInvoiceProducts(int invoiceId);
	
	List<ResponseInvoiceProductDto> getInvoiceProducts(int invoiceId);

}
