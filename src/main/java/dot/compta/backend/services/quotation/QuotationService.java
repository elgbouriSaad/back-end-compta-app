package dot.compta.backend.services.quotation;

import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;

import java.util.List;

public interface QuotationService {
	
	  void createQuotation(RequestQuotationDto requestQuotationDto);
	  
	  void updateQuotation(UpdateQuotationDto updateQuotationDto,int id);
	  
	  void validateQuotation(int id);
	  
	  void transformToInvoice(int id, int paymentDelay);
	  
	  void deleteQuotation(int id);
	  
	  void deleteClientQuotations(int clientId);
	  
	  void deleteCustomerQuotations(int customerId);
	
	  List<ResponseQuotationDto> getQuotations();
	
	  List<ResponseQuotationDto> getQuotationsByCustomerId(int customerId);
	  
	  List<ResponseQuotationDto> getQuotationsByClientId(int clientId);
	
	  ResponseQuotationDto getQuotationById(int id);
  
}
