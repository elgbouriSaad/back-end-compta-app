package dot.compta.backend.services.client;

import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.mappers.client.ClientMapper;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.client.ClientRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.services.invoice.InvoiceService;
import dot.compta.backend.services.quotation.QuotationService;
import dot.compta.backend.validators.address.AddressValidator;
import dot.compta.backend.validators.client.ClientValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

	private final CustomerRepository customerRepository;

	private final CustomerValidator customerValidator;

	private final CityRepository cityRepository;

	private final ClientMapper clientMapper;

	private final AddressValidator addressValidator;

	private final ClientValidator clientValidator;
	
	private final QuotationService quotationService;
	
	private final InvoiceService invoiceService;

	@Override
	public void createClient(RequestClientDto requestClientDto) {
		addressValidator.validate(requestClientDto.getAddress());
		customerValidator.validateExistsAndNotDeleted(requestClientDto.getCustomerId());
		Optional<City> optCity = cityRepository.findOneByNameIgnoreCase(requestClientDto.getAddress().getCity());
		Optional<CustomerModel> customer = customerRepository.findById(requestClientDto.getCustomerId());
		ClientModel client = clientMapper.mapToClientModel(requestClientDto, optCity.get(), customer.get());
		clientRepository.save(client);
	}

	@Override
	public List<ResponseClientDto> getClients() {
		List<ClientModel> nonDeletedClients = clientRepository.findAllByDeletedFalse();
		return nonDeletedClients.stream()
				.map(clientMapper::mapToResponseClientDto)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseClientDto getClientById(int id) {
		clientValidator.validateExistsAndNotDeleted(id);
		Optional<ClientModel> client = clientRepository.findById(id);
		return clientMapper.mapToResponseClientDto(client.get());
	}

	@Override
	public void updateClient(UpdateClientDto newClientDetails, int id) {
		clientValidator.validateExistsAndNotDeleted(id);
		addressValidator.validate(newClientDetails.getAddress());
		Optional<City> optCity = cityRepository.findOneByNameIgnoreCase(newClientDetails.getAddress().getCity());
		Optional<ClientModel> existingClient = clientRepository.findById(id);
		ClientModel client = clientMapper.mapToClientModel(newClientDetails, optCity.get(), existingClient.get().getCustomer(), existingClient.get().getAddress().getId());
		client.setId(id);
		clientRepository.save(client);
	}

	@Override
	public void deleteClient(int id) {
		clientValidator.validateExistsAndNotDeleted(id);
		clientRepository.logicalDeleteById(id);
		quotationService.deleteClientQuotations(id);
		invoiceService.deleteClientInvoices(id);
	}

	@Override
	public List<ResponseClientDto> getClientsByCustomerId(int id) {
		customerValidator.validateExistsAndNotDeleted(id);
		List<ClientModel> clients = clientRepository.findAllByCustomerIdAndDeletedFalse(id);
		return clients.stream()
				.map(clientMapper::mapToResponseClientDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public void deleteCustomerClients(int customerId) {
		List<ClientModel> nonDeletedClients = clientRepository.findAllByCustomerIdAndDeletedFalse(customerId);
		for (ClientModel client : nonDeletedClients) {
			clientRepository.logicalDeleteById(client.getId());
		}
	}

}
