package dot.compta.backend.controllers.customer;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.dtos.customer.UpdateCustomerDto;
import dot.compta.backend.services.customer.CustomerService;
import dot.compta.backend.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static dot.compta.backend.utils.TestUtils.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class CustomerControllerTest extends BaseCustomerControllerTest {

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(customerController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void given_an_empty_payload_should_return_bad_request_createCustomerTest() throws Exception {
        RequestCustomerDto requestCustomerDto = RequestCustomerDto.builder().build();
        String content = objectMapper.writeValueAsString(requestCustomerDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.CUSTOMERS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void given_invalid_email_should_return_bad_request_createCustomerTest() throws Exception {
        RequestCustomerDto requestCustomerDto = buildRequestCustomerDto();
        requestCustomerDto.setEmail("test");
        String content = objectMapper.writeValueAsString(requestCustomerDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.CUSTOMERS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void given_valid_payload_should_return_created_createCustomerTest() throws Exception {
        RequestCustomerDto requestCustomerDto = buildRequestCustomerDto();
        String content = objectMapper.writeValueAsString(requestCustomerDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.CUSTOMERS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(customerService).createCustomer(requestCustomerDto);
    }

    @Test
    public void testGetCustomers() throws Exception {
        ResponseCustomerDto responseCustomerDto = buildResponseCustomerDto();
        List<ResponseCustomerDto> customers = Collections.singletonList(responseCustomerDto);
        given(customerService.getCustomers()).willReturn(customers);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.CUSTOMERS_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CustomerController_getCustomers.json"), true));
        verify(customerService).getCustomers();
    }

    @Test
    public void testGetCustomerById() throws Exception {
        ResponseCustomerDto responseCustomerDto = buildResponseCustomerDto();
        given(customerService.getCustomerById(CUSTOMER_ID_TEST__1004)).willReturn(responseCustomerDto);
        mvc.perform(MockMvcRequestBuilders.get(APIConstants.CUSTOMERS_URL + "/" + CUSTOMER_ID_TEST__1004)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(TestUtils.getJsonContent("CustomerController_getCustomerById.json"), true));
        verify(customerService).getCustomerById(CUSTOMER_ID_TEST__1004);
    }

    @Test
    public void testGetCustomersByAccountantId() throws Exception {
        ResponseCustomerDto responseCustomerDto = buildResponseCustomerDto();
        List<ResponseCustomerDto> customers = Collections.singletonList(responseCustomerDto);
        given(customerService.getCustomersByAccountantId(ACCOUNTANT_ID_TEST__1003)).willReturn(customers);
        mvc.perform(get(APIConstants.CUSTOMERS_URL + APIConstants.ACCOUNTANT_CUSTOMERS_URL + ACCOUNTANT_ID_TEST__1003)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("CustomerController_getCustomersByAccountant.json"), true));
        verify(customerService).getCustomersByAccountantId(ACCOUNTANT_ID_TEST__1003);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        UpdateCustomerDto updateCustomerDto = buildUpdateCustomerDto();
        String content = objectMapper.writeValueAsString(updateCustomerDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.CUSTOMERS_URL + "/" + CUSTOMER_ID_TEST__1004)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(customerService).updateCustomer(updateCustomerDto, CUSTOMER_ID_TEST__1004);
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        int idTest = 1;
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.CUSTOMERS_URL + "/" + idTest))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(customerService).deleteCustomer(idTest);
    }

}
