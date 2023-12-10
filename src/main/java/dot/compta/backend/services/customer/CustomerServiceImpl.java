package dot.compta.backend.services.customer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;
import dot.compta.backend.mappers.customer.CustomerMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.services.client.ClientService;
import dot.compta.backend.services.expenseReport.ExpenseReportService;
import dot.compta.backend.services.invoice.InvoiceService;
import dot.compta.backend.services.product.ProductService;
import dot.compta.backend.services.quotation.QuotationService;
import dot.compta.backend.validators.accountant.AccountantValidator;
import dot.compta.backend.validators.address.AddressValidator;
import dot.compta.backend.validators.customer.CustomerValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CityRepository cityRepository;

	private final AccountantRepository accountantRepository;

	private final CustomerMapper customerMapper;

	private final AddressValidator addressValidator;

	private final CustomerValidator customerValidator;

	private final AccountantValidator accountantValidator;
	
	private final InvoiceService invoiceService;
	
	private final QuotationService quotationService;
	
	private final ProductService productService;
	
	private final ExpenseReportService expenseReportService;
	
	private final ClientService clientService;

	@Override
	public void createCustomer(RequestCustomerDto requestCustomerDto) {
		addressValidator.validate(requestCustomerDto.getAddress());
		accountantValidator.validateExistsAndNotDeleted(requestCustomerDto.getAccountantId());
		Optional<AccountantModel> optAccountant = accountantRepository.findById(requestCustomerDto.getAccountantId());
		Optional<City> optCity = cityRepository.findOneByNameIgnoreCase(requestCustomerDto.getAddress().getCity());
		CustomerModel customer = customerMapper.mapToCustomerModel(requestCustomerDto, optCity.get(), optAccountant.get());
		customerRepository.save(customer);
	}

	@Override
	public List<ResponseCustomerDto> getCustomers() {
		List<CustomerModel> nonDeletedCustomers = customerRepository.findAllByDeletedFalse();
		return nonDeletedCustomers.stream()
				.map(customerMapper::mapToResponseCustomerDto)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseCustomerDto getCustomerById(int id) {
		customerValidator.validateExistsAndNotDeleted(id);
		Optional<CustomerModel> customer = customerRepository.findById(id);
		return customerMapper.mapToResponseCustomerDto(customer.get());
	}

	@Override
	public void updateCustomer(UpdateCustomerDto newCustomerDetails, int id) {
		customerValidator.validateExistsAndNotDeleted(id);
		addressValidator.validate(newCustomerDetails.getAddress());
		Optional<CustomerModel> optCustomer = customerRepository.findById(id);
		Optional<City> optCity = cityRepository.findOneByNameIgnoreCase(newCustomerDetails.getAddress().getCity());
		CustomerModel customer = customerMapper.mapToCustomerModel(newCustomerDetails, optCity.get(), optCustomer.get().getAddress().getId(),optCustomer.get().getAccountant());
		customer.setId(id);
		customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(int id) {
		customerValidator.validateExistsAndNotDeleted(id);
		customerRepository.logicalDeleteById(id);
		invoiceService.deleteCustomerInvoices(id);
		quotationService.deleteCustomerQuotations(id);
		productService.deleteCustomerProducts(id);
		expenseReportService.deleteCustomerExpenseReports(id);
		clientService.deleteCustomerClients(id);
	}

	@Override
	public List<ResponseCustomerDto> getCustomersByAccountantId(int id) {
		accountantValidator.validateExistsAndNotDeleted(id);
		List<CustomerModel> customers = customerRepository.findAllByAccountantIdAndDeletedFalse(id);
		return customers.stream()
				.map(customerMapper::mapToResponseCustomerDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public void deleteAccountantCustomers(int accountantId) {
		List<CustomerModel> nonDeletedCustomers = customerRepository.findAllByAccountantIdAndDeletedFalse(accountantId);
		for (CustomerModel customer : nonDeletedCustomers) {
			deleteCustomer(customer.getId());
		}
	}

}
