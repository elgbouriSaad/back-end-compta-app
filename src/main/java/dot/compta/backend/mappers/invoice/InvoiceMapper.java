package dot.compta.backend.mappers.invoice;

import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.quotation.QuotationModel;

public interface InvoiceMapper {
	
	InvoiceModel mapToInvoiceModel(RequestInvoiceDto requestInvoiceDto,
			CustomerModel customer,
			ClientModel client,
			QuotationModel quotation);
	
	InvoiceModel mapToInvoiceModel(UpdateInvoiceDto updateInvoiceDto,
			CustomerModel customer,
			ClientModel client,
			QuotationModel quotation);
	
	InvoiceModel mapToInvoiceModel(QuotationModel quotation, int paymentDelay);
	
	ResponseInvoiceDto mapToResponseInvoiceDto(InvoiceModel invoice);
	

}
