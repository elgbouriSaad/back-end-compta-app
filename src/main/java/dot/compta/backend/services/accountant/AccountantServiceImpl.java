package dot.compta.backend.services.accountant;

import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.mappers.accountant.AccountantMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.services.customer.CustomerService;
import dot.compta.backend.validators.accountant.AccountantValidator;
import dot.compta.backend.validators.address.AddressValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountantServiceImpl implements AccountantService {

	private final AccountantRepository accountantRepository;

	private final CityRepository cityRepository;

	private final AccountantMapper accountantMapper;

	private final AddressValidator addressValidator;

	private final AccountantValidator accountantValidator;
	
	private final CustomerService customerService;

	@Override
	public void createAccountant(RequestAccountantDto requestAccountantDto) {
		addressValidator.validate(requestAccountantDto.getAddress());
		Optional<City> optCity = cityRepository.findOneByNameIgnoreCase(requestAccountantDto.getAddress().getCity());
		AccountantModel accountant = accountantMapper.mapToAccountantModel(requestAccountantDto, optCity.get());
		accountantRepository.save(accountant);
	}

	@Override
	public List<ResponseAccountantDto> getAccountants() {
		List<AccountantModel> nonDeletedAccountants = accountantRepository.findAllByDeletedFalse();
		return nonDeletedAccountants.stream()
				.map(accountantMapper::mapToResponseAccountantDto)
				.collect(Collectors.toList());
	}

	@Override
	public ResponseAccountantDto getAccountantById(int id) {
		accountantValidator.validateExistsAndNotDeleted(id);
		Optional<AccountantModel> accountant = accountantRepository.findById(id);
		return accountantMapper.mapToResponseAccountantDto(accountant.get());
	}

	@Override
	public void updateAccountant(RequestAccountantDto newAccountantDetails, int id) {
		accountantValidator.validateExistsAndNotDeleted(id);
		addressValidator.validate(newAccountantDetails.getAddress());
		Optional<City> optCity = cityRepository.findOneByNameIgnoreCase(newAccountantDetails.getAddress().getCity());
		Optional<AccountantModel> optAccountant = accountantRepository.findById(id);
		AccountantModel accountant = accountantMapper.mapToAccountantModel(newAccountantDetails, optCity.get(), optAccountant.get().getAddress().getId());
		accountant.setId(id);
		accountantRepository.save(accountant);
	}

	@Override
	public void deleteAccountant(int id) {
		accountantValidator.validateExistsAndNotDeleted(id);
		accountantRepository.logicalDeleteById(id);
		customerService.deleteAccountantCustomers(id);
	}

}
