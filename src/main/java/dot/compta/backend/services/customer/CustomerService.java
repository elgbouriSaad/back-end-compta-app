package dot.compta.backend.services.customer;

import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;

import java.util.List;

public interface CustomerService {

    void createCustomer(RequestCustomerDto requestCustomerDto);

    void updateCustomer(UpdateCustomerDto updateCustomerDto, int id);

    void deleteCustomer(int id);
    
    void deleteAccountantCustomers(int accountantId);

    List<ResponseCustomerDto> getCustomers();

    ResponseCustomerDto getCustomerById(int id);

    List<ResponseCustomerDto> getCustomersByAccountantId(int id);

}
