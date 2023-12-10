package dot.compta.backend.mappers.invoice;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.quotation.QuotationModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InvoiceMapperImpl implements InvoiceMapper{
	
	private final ModelMapper modelMapper;
	
	@Override
	public InvoiceModel mapToInvoiceModel(RequestInvoiceDto requestInvoiceDto,
			CustomerModel customer,
			ClientModel client,
			QuotationModel quotation) {
		InvoiceModel invoice = modelMapper.map(requestInvoiceDto, InvoiceModel.class);
		invoice.setCustomer(customer);
		invoice.setClient(client);
		invoice.setQuotation(quotation);
		return invoice;
	}
	
	@Override
	public InvoiceModel mapToInvoiceModel(UpdateInvoiceDto updateInvoiceDto,
			CustomerModel customer,
			ClientModel client,
			QuotationModel quotation) {
		InvoiceModel invoice = modelMapper.map(updateInvoiceDto, InvoiceModel.class);
		invoice.setCustomer(customer);
		invoice.setClient(client);
		invoice.setQuotation(quotation);
		return invoice;
	}
	
	@Override
	public ResponseInvoiceDto mapToResponseInvoiceDto(InvoiceModel invoice) {
		ResponseInvoiceDto responseInvoiceDto = modelMapper.map(invoice, ResponseInvoiceDto.class);
		responseInvoiceDto.setCustomerId(invoice.getCustomer().getId());
		responseInvoiceDto.setClientId(invoice.getClient().getId());
		if (Objects.nonNull(invoice.getQuotation())) {
			responseInvoiceDto.setQuotationId(invoice.getQuotation().getId());
		}
		return responseInvoiceDto;
	}
	
	@Override
	public InvoiceModel mapToInvoiceModel(QuotationModel quotation, int paymentDelay) {
		InvoiceModel invoice = modelMapper.map(quotation, InvoiceModel.class);
		invoice.setCustomer(quotation.getCustomer());
		invoice.setClient(quotation.getClient());
		invoice.setQuotation(quotation);
		invoice.setPaymentDelay(paymentDelay);
		invoice.setId(0);
		return invoice;
	}

}
