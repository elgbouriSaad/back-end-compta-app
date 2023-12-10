package dot.compta.backend.mappers.quotation;

import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.quotation.QuotationModel;

public interface QuotationMapper {

    QuotationModel mapToQuotationModel(RequestQuotationDto requestQuotationDto, CustomerModel customer, ClientModel client);
    
    QuotationModel mapToQuotationModel(UpdateQuotationDto updateQuotationDto, CustomerModel customer, ClientModel client);

    ResponseQuotationDto mapToResponseQuotationDto(QuotationModel quotation);

}
