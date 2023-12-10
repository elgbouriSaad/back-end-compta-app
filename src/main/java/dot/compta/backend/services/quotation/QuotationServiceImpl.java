package dot.compta.backend.services.quotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.mappers.invoice.InvoiceMapper;
import dot.compta.backend.mappers.quotation.QuotationMapper;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.invoice.InvoiceRepository;
import dot.compta.backend.repositories.quotation.QuotationRepository;
import dot.compta.backend.services.quotationProduct.QuotationProductService;
import dot.compta.backend.validators.client.ClientValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
import dot.compta.backend.validators.product.ProductValidator;
import dot.compta.backend.validators.quotation.QuotationValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuotationServiceImpl implements QuotationService {

	private final QuotationMapper quotationMapper;

	private final QuotationRepository quotationRepository;

	private final CustomerValidator customerValidator;

	private final CustomerRepository customerRepository;

	private final ClientValidator clientValidator;

	private final ClientRepository clientRepository;

	private final ProductValidator productValidator;

	private final QuotationProductService quotationProductService;

	private final QuotationValidator quotationValidator;
	
	private final InvoiceMapper invoiceMapper;
	
	private final InvoiceRepository invoiceRepository;

	@Override
	public void createQuotation(RequestQuotationDto requestQuotationDto) {
		customerValidator.validateExistsAndNotDeleted(requestQuotationDto.getCustomerId());
		clientValidator.validateExistsAndNotDeleted(requestQuotationDto.getClientId());
		List<RequestProductQuantityDto> productQuantities = requestQuotationDto.getProductQuantities();
		for (RequestProductQuantityDto productQuantity : productQuantities) {
			productValidator.validateExistsAndNotDeleted(productQuantity.getProductId());
		}
		Optional<CustomerModel> customer = customerRepository.findById(requestQuotationDto.getCustomerId());
		Optional<ClientModel> client = clientRepository.findById(requestQuotationDto.getClientId());
		QuotationModel quotation = quotationMapper.mapToQuotationModel(requestQuotationDto, customer.get(), client.get());
		quotation.setStatus(QuotationStatus.SAVED);
		QuotationModel savedQuotation = quotationRepository.save(quotation);
		quotationProductService.createQuotationProducts(savedQuotation, productQuantities);
	}

	@Override
	public List<ResponseQuotationDto> getQuotations() {
		List<QuotationModel> nonDeletedQuotations = quotationRepository.findAllByDeletedFalse();
		return buildResponseQuotationDtos(nonDeletedQuotations);
	}

	@Override
	public ResponseQuotationDto getQuotationById(int id) {
		quotationValidator.validateExistsAndNotDeleted(id);
		Optional<QuotationModel> quotation = quotationRepository.findById(id);
		ResponseQuotationDto responseQuotationDto = quotationMapper.mapToResponseQuotationDto(quotation.get());
		List<ResponseQuotationProductDto> responseQuotationProducts = quotationProductService.getQuotationProducts(quotation.get().getId());
		responseQuotationDto.setQuotationProducts(responseQuotationProducts);
		return responseQuotationDto;
	}
	
	@Override
	public List<ResponseQuotationDto> getQuotationsByCustomerId(int customerId){
		customerValidator.validateExistsAndNotDeleted(customerId);
		List<QuotationModel> nonDeletedQuotations = quotationRepository.findAllByCustomerIdAndDeletedFalse(customerId);
		return buildResponseQuotationDtos(nonDeletedQuotations);
	}
	
	@Override
	public List<ResponseQuotationDto> getQuotationsByClientId(int clientId){
		clientValidator.validateExistsAndNotDeleted(clientId);
		List<QuotationModel> nonDeletedQuotations = quotationRepository.findAllByClientIdAndDeletedFalse(clientId);
		return buildResponseQuotationDtos(nonDeletedQuotations);
	}
	
	private List<ResponseQuotationDto> buildResponseQuotationDtos(List<QuotationModel> quotations){
		List<ResponseQuotationDto> responseQuotationDtos = new ArrayList<>();
		for(QuotationModel quotation : quotations) {
			ResponseQuotationDto responseQuotationDto = quotationMapper.mapToResponseQuotationDto(quotation);
			List<ResponseQuotationProductDto> responseQuotationProducts = quotationProductService.getQuotationProducts(quotation.getId());
			responseQuotationDto.setQuotationProducts(responseQuotationProducts);
			responseQuotationDtos.add(responseQuotationDto);
		}
		return responseQuotationDtos;
	}
	
	@Override
	public void validateQuotation(int id) {
		quotationValidator.validateExistsAndNotDeleted(id);
		quotationValidator.validateStatus(id, QuotationStatus.SAVED);
		quotationRepository.updateStatusById(QuotationStatus.VALIDATED, id);
	}
	
	@Override
	public void updateQuotation(UpdateQuotationDto updateQuotationDto, int id) {
		quotationValidator.validateExistsAndNotDeleted(id);
		quotationValidator.validateStatus(id, QuotationStatus.SAVED);
		clientValidator.validateExistsAndNotDeleted(updateQuotationDto.getClientId());
		List<RequestProductQuantityDto> newProductQuantities = updateQuotationDto.getProductQuantities();
		for (RequestProductQuantityDto productQuantity : newProductQuantities) {
			productValidator.validateExistsAndNotDeleted(productQuantity.getProductId());
		}
		Optional<ClientModel> client = clientRepository.findById(updateQuotationDto.getClientId());
		Optional<QuotationModel> oldQuotation = quotationRepository.findById(id);
		QuotationModel quotation = quotationMapper.mapToQuotationModel(updateQuotationDto, oldQuotation.get().getCustomer(), client.get());
		quotation.setId(id);
		quotation.setStatus(QuotationStatus.SAVED);
		QuotationModel savedQuotation = quotationRepository.save(quotation);
		quotationProductService.updateQuotationProducts(savedQuotation, newProductQuantities);
	}
	
	@Override
	public void transformToInvoice(int id, int paymentDelay) {
		quotationValidator.validateExistsAndNotDeleted(id);
		quotationValidator.validateStatus(id, QuotationStatus.VALIDATED);
		quotationValidator.validateNotLinkedToInvoice(id);
		Optional<QuotationModel> quotation = quotationRepository.findById(id);
		quotationRepository.updateStatusById(QuotationStatus.TRANSFORMED, id);
		InvoiceModel invoice = invoiceMapper.mapToInvoiceModel(quotation.get(), paymentDelay);
		invoice.setStatus(InvoiceStatus.VALIDATED);
		InvoiceModel savedInvoice = invoiceRepository.save(invoice);
		quotationProductService.transformQuotationProducts(quotation.get(), savedInvoice);
	}
	
	@Override
	public void deleteQuotation(int id){
		quotationValidator.validateExistsAndNotDeleted(id);
		quotationValidator.validateStatus(id, QuotationStatus.SAVED);
		quotationRepository.logicalDeleteById(id);
		quotationProductService.deleteQuotationProducts(id);
	}
	
	@Override
	public void deleteClientQuotations(int clientId) {
		List<QuotationModel> nonDeletedQuotations = quotationRepository.findAllByClientIdAndStatusAndDeletedFalse(clientId, QuotationStatus.SAVED);
		for (QuotationModel quotation : nonDeletedQuotations) {
			quotationRepository.logicalDeleteById(quotation.getId());
			quotationProductService.deleteQuotationProducts(quotation.getId());
		}
	}
	
	@Override
	public void deleteCustomerQuotations(int customerId) {
		List<QuotationModel> nonDeletedQuotations = quotationRepository.findAllByCustomerIdAndDeletedFalse(customerId);
		for (QuotationModel quotation : nonDeletedQuotations) {
			quotationRepository.logicalDeleteById(quotation.getId());
			quotationProductService.deleteQuotationProducts(quotation.getId());
		}
	}
	
}
