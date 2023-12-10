package dot.compta.backend.mappers.accountant;

import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.city.City;

public interface AccountantMapper {

    AccountantModel mapToAccountantModel(RequestAccountantDto requestAccountantDto, City city);

    AccountantModel mapToAccountantModel(RequestAccountantDto requestAccountantDto, City city, int addressId);

    ResponseAccountantDto mapToResponseAccountantDto(AccountantModel accountantModel);
}
