package dot.compta.backend.controllers.client;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.dtos.client.UpdateClientDto;
import dot.compta.backend.services.client.ClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static dot.compta.backend.utils.TestUtils.CLIENT_ID_TEST__1005;
import static dot.compta.backend.utils.TestUtils.getJsonContent;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ClientControllerTest extends BaseClientControllerTest {

    @Mock
    private ClientService clientService;
    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(clientController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void given_an_empty_payload_should_return_bad_request_createClientTest() throws Exception {
        RequestClientDto requestClientDto = RequestClientDto.builder().build();
        String content = objectMapper.writeValueAsString(requestClientDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.CLIENTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void given_invalid_email_should_return_bad_request_createClientTest() throws Exception {
        RequestClientDto requestClientDto = buildRequestClientDto();
        requestClientDto.setEmail("test");
        String content = objectMapper.writeValueAsString(requestClientDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.CLIENTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void given_valid_payload_should_return_created_createClientTest() throws Exception {
        RequestClientDto requestClientDto = buildRequestClientDto();
        String content = objectMapper.writeValueAsString(requestClientDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.CLIENTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
        verify(clientService).createClient(requestClientDto);
    }

    @Test
    public void testGetClients() throws Exception {
        ResponseClientDto responseClientDto = buildResponseClientDto();
        List<ResponseClientDto> clients = Collections.singletonList(responseClientDto);
        given(clientService.getClients()).willReturn(clients);
        mvc.perform(get(APIConstants.CLIENTS_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ClientController_getClients.json"), true));
        verify(clientService).getClients();
    }

    @Test
    public void testGetClientById() throws Exception {
        ResponseClientDto responseClientDto = buildResponseClientDto();
        given(clientService.getClientById(CLIENT_ID_TEST__1005)).willReturn(responseClientDto);
        mvc.perform(get(APIConstants.CLIENTS_URL + "/" + CLIENT_ID_TEST__1005)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ClientController_getClientById.json"), true));
        verify(clientService).getClientById(CLIENT_ID_TEST__1005);
    }

    @Test
    public void testGetClientsByCustomerId() throws Exception {
        int id = 1;
        ResponseClientDto responseClientDto = buildResponseClientDto();
        List<ResponseClientDto> clients = Collections.singletonList(responseClientDto);
        given(clientService.getClientsByCustomerId(id)).willReturn(clients);
        mvc.perform(get(APIConstants.CLIENTS_URL + APIConstants.CUSTOMER_CLIENTS_URL + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ClientController_getClientsByCustomer.json"), true));
        verify(clientService).getClientsByCustomerId(id);
    }

    @Test
    public void testUpdateClient() throws Exception {
        UpdateClientDto updateClientDto = buildUpdateClientDto();
        String content = objectMapper.writeValueAsString(updateClientDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.CLIENTS_URL + "/" + CLIENT_ID_TEST__1005)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(clientService).updateClient(updateClientDto, CLIENT_ID_TEST__1005);
    }

    @Test
    public void testDeleteClient() throws Exception {
        int idTest = 1;
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.CLIENTS_URL + "/" + idTest))
                .andExpect(status().isOk());
        verify(clientService).deleteClient(idTest);
    }

}
