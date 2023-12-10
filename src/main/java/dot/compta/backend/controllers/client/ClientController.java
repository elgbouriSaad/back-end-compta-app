package dot.compta.backend.controllers.client;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.services.client.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.CLIENTS_URL)
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_CLIENT_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_CLIENT_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_CLIENT_CONTROLLER_TAGS)
    @Transactional
    public void createClient(@RequestBody @Validated RequestClientDto client) {
        clientService.createClient(client);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CLIENT_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_CLIENT_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_CLIENT_CONTROLLER_TAGS)
    public List<ResponseClientDto> getClients() {
        return clientService.getClients();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CLIENT_CONTROLLER_IDGET_SUM_DESC,
            description = DocumentationConstants.DOC_CLIENT_CONTROLLER_IDGET_SUM_DESC,
            tags = DocumentationConstants.DOC_CLIENT_CONTROLLER_TAGS)
    public ResponseClientDto getClientById(@PathVariable int id) {
        return clientService.getClientById(id);
    }

    @GetMapping(APIConstants.CUSTOMER_CLIENTS_URL + "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CLIENT_CONTROLLER_CUSTOMERGET_SUM_DESC,
            description = DocumentationConstants.DOC_CLIENT_CONTROLLER_CUSTOMERGET_SUM_DESC,
            tags = DocumentationConstants.DOC_CLIENT_CONTROLLER_TAGS)
    public List<ResponseClientDto> getClientsByCustomerId(@PathVariable int id) {
        return clientService.getClientsByCustomerId(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CLIENT_CONTROLLER_PUT_SUM_DESC,
            description = DocumentationConstants.DOC_CLIENT_CONTROLLER_PUT_SUM_DESC,
            tags = DocumentationConstants.DOC_CLIENT_CONTROLLER_TAGS)
    @Transactional
    public void updateClient(@RequestBody @Validated UpdateClientDto client, @PathVariable int id) {
        clientService.updateClient(client, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CLIENT_CONTROLLER_DELETE_SUM_DESC,
            description = DocumentationConstants.DOC_CLIENT_CONTROLLER_DELETE_SUM_DESC,
            tags = DocumentationConstants.DOC_CLIENT_CONTROLLER_TAGS)
    @Transactional
    public void deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
    }

}
