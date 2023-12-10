package dot.compta.backend.services.invoice;

import java.util.List;

import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;

public interface InvoiceService {
	
	void createInvoice(RequestInvoiceDto requestInvoiceDto);
	
	void updateInvoice(UpdateInvoiceDto updateInvoiceDto,int id);
	
	void validateInvoice(int id);
	
	void deleteInvoice(int id);
	
	void deleteClientInvoices(int clientId);
	
	void deleteCustomerInvoices(int customerId);
	
	List<ResponseInvoiceDto> getInvoices();
	
	List<ResponseInvoiceDto> getInvoicesByCustomerId(int customerId);
	
	List<ResponseInvoiceDto> getInvoicesByClientId(int clientId);
	
	ResponseInvoiceDto getInvoiceById(int id);
	
}
