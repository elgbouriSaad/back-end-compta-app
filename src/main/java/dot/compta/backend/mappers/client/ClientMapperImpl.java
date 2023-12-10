package dot.compta.backend.mappers.client;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.mappers.address.AddressMapper;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientMapperImpl implements ClientMapper {

    private final ModelMapper modelMapper;

    private final AddressMapper addressMapper;

    @Override
    public ClientModel mapToClientModel(UpdateClientDto updateClientDto, City city, CustomerModel customer, int addressId) {
        ClientModel clientModel = mapToClientModel(updateClientDto, city, customer);
        clientModel.getAddress().setId(addressId);
        return clientModel;
    }
    
    private ClientModel mapToClientModel(UpdateClientDto updateClientDto, City city, CustomerModel customer) {
        AddressModel address = addressMapper.mapToAddressModel(updateClientDto.getAddress(), city);
        ClientModel clientModel = modelMapper.map(updateClientDto, ClientModel.class);
        clientModel.setAddress(address);
        clientModel.setCustomer(customer);
        return clientModel;
    }

    @Override
    public ClientModel mapToClientModel(RequestClientDto requestClientDto, City city, CustomerModel customer) {
        AddressModel address = addressMapper.mapToAddressModel(requestClientDto.getAddress(), city);
        ClientModel clientModel = modelMapper.map(requestClientDto, ClientModel.class);
        clientModel.setAddress(address);
        clientModel.setCustomer(customer);
        return clientModel;
    }

    @Override
    public ResponseClientDto mapToResponseClientDto(ClientModel client) {
        AddressDto addressDto = addressMapper.mapToAddressDto(client.getAddress());
        ResponseClientDto responseClientDto = modelMapper.map(client, ResponseClientDto.class);
        responseClientDto.setAddress(addressDto);
        responseClientDto.setCustomerId(client.getCustomer().getId());
        return responseClientDto;
    }

}
