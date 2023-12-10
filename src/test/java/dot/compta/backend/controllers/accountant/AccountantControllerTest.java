package dot.compta.backend.controllers.accountant;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.services.accountant.AccountantService;
import dot.compta.backend.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static dot.compta.backend.utils.TestUtils.ACCOUNTANT_ID_TEST__1003;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class AccountantControllerTest extends BaseAccountantControllerTest {

    @Mock
    private AccountantService accountantService;
    @InjectMocks
    private AccountantController accountantController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(accountantController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(accountantService);
    }

    @Test
    void given_an_empty_payload_should_return_bad_request_createAccountantTest() throws Exception {
        RequestAccountantDto requestAccountantDto = RequestAccountantDto.builder().build();
        String content = objectMapper.writeValueAsString(requestAccountantDto);
        mvc.perform(post(APIConstants.ACCOUNTANTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        // TODO check exact error msgs
    }

    @Test
    void given_invalid_email_should_return_bad_request_createAccountantTest() throws Exception {
        RequestAccountantDto requestAccountantDto = buildRequestAccountantDto();
        requestAccountantDto.setEmail("test");
        String content = objectMapper.writeValueAsString(requestAccountantDto);
        mvc.perform(post(APIConstants.ACCOUNTANTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
        // TODO check exact error msgs
    }

    @Test
    void given_valid_payload_should_return_created_createAccountantTest() throws Exception {
        RequestAccountantDto requestAccountantDto = buildRequestAccountantDto();
        String content = objectMapper.writeValueAsString(requestAccountantDto);
        mvc.perform(post(APIConstants.ACCOUNTANTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
        verify(accountantService).createAccountant(requestAccountantDto);
    }

    @Test
    public void testGetAccountants() throws Exception {
        ResponseAccountantDto responseAccountantDto = buildResponseAccountantDto();
        List<ResponseAccountantDto> accountants = Collections.singletonList(responseAccountantDto);
        given(accountantService.getAccountants()).willReturn(accountants);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.ACCOUNTANTS_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("AccountantController_getAccountants.json"), true));
        verify(accountantService).getAccountants();
    }

    @Test
    public void testGetAccountantById() throws Exception {
        ResponseAccountantDto responseAccountantDto = buildResponseAccountantDto();
        given(accountantService.getAccountantById(ACCOUNTANT_ID_TEST__1003)).willReturn(responseAccountantDto);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.ACCOUNTANTS_URL + "/" + ACCOUNTANT_ID_TEST__1003)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("AccountantController_getAccountantById.json"), true));
        verify(accountantService).getAccountantById(ACCOUNTANT_ID_TEST__1003);
    }

    @Test
    public void testUpdateAccountant() throws Exception {
        RequestAccountantDto requestAccountantDto = buildRequestAccountantDto();
        String content = objectMapper.writeValueAsString(requestAccountantDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.ACCOUNTANTS_URL + "/" + ACCOUNTANT_ID_TEST__1003)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(accountantService).updateAccountant(requestAccountantDto, ACCOUNTANT_ID_TEST__1003);
    }

    @Test
    public void testDeleteAccountant() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.ACCOUNTANTS_URL + "/" + ACCOUNTANT_ID_TEST__1003))
                .andExpect(status().isOk());
        verify(accountantService).deleteAccountant(ACCOUNTANT_ID_TEST__1003);
    }
}