package dot.compta.backend.mappers.quotation;

import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.quotation.QuotationModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuotationMapperImpl implements QuotationMapper {

    private final ModelMapper modelMapper;

    @Override
    public QuotationModel mapToQuotationModel(RequestQuotationDto requestQuotationDto, CustomerModel customer, ClientModel client) {
        QuotationModel quotation = modelMapper.map(requestQuotationDto, QuotationModel.class);
        quotation.setClient(client);
        quotation.setCustomer(customer);
        return quotation;
    }
    
    @Override
    public QuotationModel mapToQuotationModel(UpdateQuotationDto updateQuotationDto, CustomerModel customer, ClientModel client) {
        QuotationModel quotation = modelMapper.map(updateQuotationDto, QuotationModel.class);
        quotation.setClient(client);
        quotation.setCustomer(customer);
        return quotation;
    }

    @Override
    public ResponseQuotationDto mapToResponseQuotationDto(QuotationModel quotation) {
        ResponseQuotationDto responseQuotationDto = modelMapper.map(quotation, ResponseQuotationDto.class);
        responseQuotationDto.setClientId(quotation.getClient().getId());
        responseQuotationDto.setCustomerId(quotation.getCustomer().getId());
        return responseQuotationDto;
    }

}
