package dot.compta.backend.mappers.customer;

import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.customer.CustomerModel;

public interface CustomerMapper {

    CustomerModel mapToCustomerModel(UpdateCustomerDto updateCustomerDto, City city, int addressId, AccountantModel accountantModel);

    CustomerModel mapToCustomerModel(RequestCustomerDto requestCustomerDto, City city, AccountantModel accountantModel);

    ResponseCustomerDto mapToResponseCustomerDto(CustomerModel customerModel);

}
