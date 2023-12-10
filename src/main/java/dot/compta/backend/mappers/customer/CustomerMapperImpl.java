package dot.compta.backend.mappers.customer;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;
import dot.compta.backend.mappers.address.AddressMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.customer.CustomerModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomerMapperImpl implements CustomerMapper {

    private final ModelMapper modelMapper;

    private final AddressMapper addressMapper;

    @Override
    public CustomerModel mapToCustomerModel(RequestCustomerDto requestCustomerDto, City city, AccountantModel accountantModel) {
        AddressModel address = addressMapper.mapToAddressModel(requestCustomerDto.getAddress(), city);
        CustomerModel customerModel = modelMapper.map(requestCustomerDto, CustomerModel.class);
        customerModel.setAddress(address);
        customerModel.setAccountant(accountantModel);
        return customerModel;
    }
    
    private CustomerModel mapToCustomerModel(UpdateCustomerDto updateCustomerDto, City city, AccountantModel accountantModel) {
        AddressModel address = addressMapper.mapToAddressModel(updateCustomerDto.getAddress(), city);
        CustomerModel customerModel = modelMapper.map(updateCustomerDto, CustomerModel.class);
        customerModel.setAddress(address);
        customerModel.setAccountant(accountantModel);
        return customerModel;
    }

    @Override
    public CustomerModel mapToCustomerModel(UpdateCustomerDto updateCustomerDto, City city, int addressId, AccountantModel accountantModel) {
        CustomerModel customerModel = mapToCustomerModel(updateCustomerDto, city, accountantModel);
        customerModel.getAddress().setId(addressId);
        return customerModel;
    }

    @Override
    public ResponseCustomerDto mapToResponseCustomerDto(CustomerModel customer) {
        AddressDto addressDto = addressMapper.mapToAddressDto(customer.getAddress());
        ResponseCustomerDto responseCustomerDto = modelMapper.map(customer, ResponseCustomerDto.class);
        responseCustomerDto.setAddress(addressDto);
        responseCustomerDto.setAccountantId(customer.getAccountant().getId());
        return responseCustomerDto;
    }

}
