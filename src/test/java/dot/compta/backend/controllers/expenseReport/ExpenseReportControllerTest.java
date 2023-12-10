package dot.compta.backend.controllers.expenseReport;

import static dot.compta.backend.utils.TestUtils.getJsonContent;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static dot.compta.backend.utils.TestUtils.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;
import dot.compta.backend.services.expenseReport.ExpenseReportService;

public class ExpenseReportControllerTest extends BaseExpenseReportControllerTest {

    @Mock
    private ExpenseReportService expenseReportService;
    @InjectMocks
    private ExpenseReportController expenseReportController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(expenseReportController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(expenseReportService);
    }

    @Test
    public void testCreateExpenseReport() throws Exception {
        RequestExpenseReportDto requestExpenseReportDto = buildRequestExpenseReportDto();
        String content = objectMapper.writeValueAsString(requestExpenseReportDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.EXPENSE_REPORTS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(expenseReportService).createExpenseReport(requestExpenseReportDto);
    }

    @Test
    public void testGetExpenseReports() throws Exception {
        ResponseExpenseReportDto responseExpenseReportDto = buildResponseExpenseReportDto();
        List<ResponseExpenseReportDto> expenseReports = Collections.singletonList(responseExpenseReportDto);
        given(expenseReportService.getExpenseReports()).willReturn(expenseReports);
        mvc.perform(get(APIConstants.EXPENSE_REPORTS_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ExpenseReportController_getExpenseReports.json"), true));
        verify(expenseReportService).getExpenseReports();
    }

    @Test
    public void testGetExpenseReportById() throws Exception {
        ResponseExpenseReportDto responseExpenseReportDto = buildResponseExpenseReportDto();
        given(expenseReportService.getExpenseReportById(EXPENSE_ID_TEST__1009)).willReturn(responseExpenseReportDto);
        mvc.perform(get(APIConstants.EXPENSE_REPORTS_URL + "/" + EXPENSE_ID_TEST__1009)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ExpenseReportController_getExpenseReportById.json"), true));
        verify(expenseReportService).getExpenseReportById(EXPENSE_ID_TEST__1009);
    }

    @Test
    public void testGetExpenseReportsByCustomerId() throws Exception {
        ResponseExpenseReportDto responseExpenseReportDto = buildResponseExpenseReportDto();
        List<ResponseExpenseReportDto> expenseReports = Collections.singletonList(responseExpenseReportDto);
        given(expenseReportService.getExpenseReportsByCustomerId(EXPENSE_ID_TEST__1009)).willReturn(expenseReports);
        mvc.perform(get(APIConstants.EXPENSE_REPORTS_URL + APIConstants.CUSTOMER_EXPENSE_REPORTS_URL + EXPENSE_ID_TEST__1009)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("ExpenseReportController_getExpenseReportsByCustomer.json"), true));
        verify(expenseReportService).getExpenseReportsByCustomerId(EXPENSE_ID_TEST__1009);
    }
    
    @Test
    public void testValidateExpenseReport() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.put(APIConstants.EXPENSE_REPORTS_URL + APIConstants.EXPENSE_REPORT_VALIDATE_URL + EXPENSE_ID_TEST__1009))
        		.andExpect(status().isOk());
    	verify(expenseReportService).validateExpenseReport(EXPENSE_ID_TEST__1009);
    }
    
    @Test
    public void testUpdateExpenseReport() throws Exception {
        UpdateExpenseReportDto updateExpenseReportDto = buildUpdateExpenseReportDto();
        String content = objectMapper.writeValueAsString(updateExpenseReportDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.EXPENSE_REPORTS_URL + APIConstants.EXPENSE_REPORT_UPDATE_URL + "/" + EXPENSE_ID_TEST__1009)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        verify(expenseReportService).updateExpenseReport(updateExpenseReportDto, EXPENSE_ID_TEST__1009);
    }
    
    @Test
    public void testDeleteExpenseReport() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.EXPENSE_REPORTS_URL + "/" + EXPENSE_ID_TEST__1009))
                .andExpect(status().isOk());
        verify(expenseReportService).deleteExpenseReport(EXPENSE_ID_TEST__1009);
    }

}
