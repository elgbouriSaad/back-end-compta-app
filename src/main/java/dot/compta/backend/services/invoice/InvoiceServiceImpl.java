package dot.compta.backend.services.invoice;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.mappers.invoice.InvoiceMapper;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import dot.compta.backend.services.invoiceProduct.InvoiceProductService;
import dot.compta.backend.validators.client.ClientValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
import dot.compta.backend.validators.invoice.InvoiceValidator;
import dot.compta.backend.validators.product.ProductValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService{

	private final CustomerValidator customerValidator;

	private final CustomerRepository customerRepository;

	private final ClientValidator clientValidator;

	private final ClientRepository clientRepository;

	private final ProductValidator productValidator;
	
	private final InvoiceMapper invoiceMapper;
	
	private final InvoiceRepository invoiceRepository;
	
	private final InvoiceProductService invoiceProductService;
	
	private final InvoiceValidator invoiceValidator;
	
	@Override
	public void createInvoice(RequestInvoiceDto requestInvoiceDto) {
		customerValidator.validateExistsAndNotDeleted(requestInvoiceDto.getCustomerId());
		clientValidator.validateExistsAndNotDeleted(requestInvoiceDto.getClientId());
		List<RequestProductQuantityDto> productQuantities = requestInvoiceDto.getProductQuantities();
		for (RequestProductQuantityDto productQuantity : productQuantities) {
			productValidator.validateExistsAndNotDeleted(productQuantity.getProductId());
		}
		Optional<CustomerModel> customer = customerRepository.findById(requestInvoiceDto.getCustomerId());
		Optional<ClientModel> client = clientRepository.findById(requestInvoiceDto.getClientId());
		InvoiceModel invoice = invoiceMapper.mapToInvoiceModel(requestInvoiceDto, customer.get(), client.get(), null);
		invoice.setStatus(InvoiceStatus.SAVED);
		InvoiceModel savedInvoice = invoiceRepository.save(invoice);
		invoiceProductService.createInvoiceProducts(savedInvoice, productQuantities);
	}
	
	@Override
	public List<ResponseInvoiceDto> getInvoices(){
		List<InvoiceModel> nonDeletedInvoices = invoiceRepository.findAllByDeletedFalse();
		return buildResponseInvoiceDtos(nonDeletedInvoices);
	}
	
	@Override
	public ResponseInvoiceDto getInvoiceById(int id) {
		invoiceValidator.validateExistsAndNotDeleted(id);
		Optional<InvoiceModel> invoice = invoiceRepository.findById(id);
		ResponseInvoiceDto responseInvoiceDto = invoiceMapper.mapToResponseInvoiceDto(invoice.get());
		List<ResponseInvoiceProductDto> responseInvoiceProducts = invoiceProductService.getInvoiceProducts(invoice.get().getId());
		responseInvoiceDto.setInvoiceProducts(responseInvoiceProducts);
		return responseInvoiceDto;
	}
	
	@Override
	public List<ResponseInvoiceDto> getInvoicesByCustomerId(int customerId){
		customerValidator.validateExistsAndNotDeleted(customerId);
		List<InvoiceModel> nonDeletedInvoices = invoiceRepository.findAllByCustomerIdAndDeletedFalse(customerId);
		return buildResponseInvoiceDtos(nonDeletedInvoices);
	}
	
	@Override
	public List<ResponseInvoiceDto> getInvoicesByClientId(int clientId){
		clientValidator.validateExistsAndNotDeleted(clientId);
		List<InvoiceModel> nonDeletedInvoices = invoiceRepository.findAllByClientIdAndDeletedFalse(clientId);
		return buildResponseInvoiceDtos(nonDeletedInvoices);
	}
	
	private List<ResponseInvoiceDto> buildResponseInvoiceDtos(List<InvoiceModel> invoices){
		List<ResponseInvoiceDto> responseInvoiceDtos = new ArrayList<>();
		for(InvoiceModel invoice : invoices) {
			ResponseInvoiceDto responseInvoiceDto = invoiceMapper.mapToResponseInvoiceDto(invoice);
			List<ResponseInvoiceProductDto> responseInvoiceProducts = invoiceProductService.getInvoiceProducts(invoice.getId());
			responseInvoiceDto.setInvoiceProducts(responseInvoiceProducts);
			responseInvoiceDtos.add(responseInvoiceDto);
		}
		return responseInvoiceDtos;
	}
	
	@Override
	public void validateInvoice(int id) {
		invoiceValidator.validateExistsAndNotDeleted(id);
		invoiceValidator.validateSavedStatus(id);
		invoiceRepository.updateStatusById(InvoiceStatus.VALIDATED, id);
	}
	
	@Override
	public void updateInvoice(UpdateInvoiceDto updateInvoiceDto, int id) {
		invoiceValidator.validateExistsAndNotDeleted(id);
		invoiceValidator.validateSavedStatus(id);
		clientValidator.validateExistsAndNotDeleted(updateInvoiceDto.getClientId());
		List<RequestProductQuantityDto> newProductQuantities = updateInvoiceDto.getProductQuantities();
		for (RequestProductQuantityDto productQuantity : newProductQuantities) {
			productValidator.validateExistsAndNotDeleted(productQuantity.getProductId());
		}
		Optional<ClientModel> client = clientRepository.findById(updateInvoiceDto.getClientId());
		Optional<InvoiceModel> oldInvoice = invoiceRepository.findById(id);
		InvoiceModel invoice = invoiceMapper.mapToInvoiceModel(updateInvoiceDto, oldInvoice.get().getCustomer(), client.get(), null);
		invoice.setId(id);
		invoice.setStatus(InvoiceStatus.SAVED);
		InvoiceModel savedInvoice = invoiceRepository.save(invoice);
		invoiceProductService.updateInvoiceProducts(savedInvoice, newProductQuantities);
	}
	
	@Override
	public void deleteInvoice(int id) {
		invoiceValidator.validateExistsAndNotDeleted(id);
		invoiceValidator.validateSavedStatus(id);
		invoiceRepository.logicalDeleteById(id);
		invoiceProductService.deleteInvoiceProducts(id);
	}
	
	@Override
	public void deleteClientInvoices(int clientId) {
		List<InvoiceModel> nonDeletedInvoices = invoiceRepository.findAllByClientIdAndStatusAndDeletedFalse(clientId, InvoiceStatus.SAVED);
		for (InvoiceModel invoice : nonDeletedInvoices) {
			invoiceRepository.logicalDeleteById(invoice.getId());
			invoiceProductService.deleteInvoiceProducts(invoice.getId());
		}
	}
	
	@Override
	public void deleteCustomerInvoices(int customerId) {
		List<InvoiceModel> nonDeletedInvoices = invoiceRepository.findAllByCustomerIdAndDeletedFalse(customerId);
		for (InvoiceModel invoice : nonDeletedInvoices) {
			invoiceRepository.logicalDeleteById(invoice.getId());
			invoiceProductService.deleteInvoiceProducts(invoice.getId());
		}
	}

}
