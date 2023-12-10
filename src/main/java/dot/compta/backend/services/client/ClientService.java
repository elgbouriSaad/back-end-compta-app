package dot.compta.backend.services.client;

import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;

import java.util.List;

public interface ClientService {

    void createClient(RequestClientDto requestClientDto);

    void updateClient(UpdateClientDto updateClientDto, int id);

    void deleteClient(int id);
    
    void deleteCustomerClients(int customerId);

    List<ResponseClientDto> getClients();

    ResponseClientDto getClientById(int id);

    List<ResponseClientDto> getClientsByCustomerId(int id);

}
