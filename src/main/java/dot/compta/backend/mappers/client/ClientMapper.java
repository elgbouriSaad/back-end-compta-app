package dot.compta.backend.mappers.client;

import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;

public interface ClientMapper {

    ClientModel mapToClientModel(UpdateClientDto updateClientDto, City city, CustomerModel customer, int addressId);

    ClientModel mapToClientModel(RequestClientDto requestClientDto, City city, CustomerModel customer);

    ResponseClientDto mapToResponseClientDto(ClientModel clientModel);

}
