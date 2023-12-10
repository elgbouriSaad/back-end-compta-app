package dot.compta.backend.services.expenseReport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import dot.compta.backend.constants.ExpenseReportStatus;
import dot.compta.backend.dtos.expenseReport.RequestExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.ResponseExpenseReportDto;
import dot.compta.backend.dtos.expenseReport.UpdateExpenseReportDto;
import dot.compta.backend.mappers.expenseReport.ExpenseReportMapper;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.expenseReport.ExpenseReportModel;
import dot.compta.backend.repositories.customer.CustomerRepository;
import dot.compta.backend.repositories.expenseReport.ExpenseReportRepository;
import dot.compta.backend.validators.customer.CustomerValidator;
import dot.compta.backend.validators.expenseReport.ExpenseReportValidator;

public class ExpenseReportServiceImplTest {

	@Mock
	private ExpenseReportRepository expenseReportRepository;

	@Mock
	private ExpenseReportMapper expenseReportMapper;

	@Mock
	private CustomerValidator customerValidator;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private ExpenseReportValidator expenseReportValidator;

	@InjectMocks
	private ExpenseReportServiceImpl expenseReportService;

	@BeforeEach
	void setUp() {
		openMocks(this);
	}

	@AfterEach
	void tearDown() {
		verifyNoMoreInteractions(
				expenseReportRepository,
				expenseReportMapper,
				customerValidator,
				customerRepository
		);
	}

	@Test
	public void testCreateExpenseReport() {
		// GIVEN
		RequestExpenseReportDto expenseReportDto = mock(RequestExpenseReportDto.class);
		CustomerModel customer = mock(CustomerModel.class);
        ExpenseReportModel expenseReport = ExpenseReportModel.builder()
                .id(EXPENSE_ID_TEST__1009)
                .label(EXPENSE_LABEL_TEST)
                .status(ExpenseReportStatus.SAVED)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST)
                .tax(EXPENSE_TAX_TEST)
                .customer(customer)
                .build();
        when(expenseReportDto.getCustomerId()).thenReturn(CUSTOMER_ID_TEST__1004);
        when(customerRepository.findById(CUSTOMER_ID_TEST__1004)).thenReturn(Optional.of(customer));
        when(expenseReportMapper.mapToExpenseReportModel(expenseReportDto, customer)).thenReturn(expenseReport);

        // WHEN
        expenseReportService.createExpenseReport(expenseReportDto);

        // THEN
        verify(expenseReportRepository).save(expenseReport);
        verify(expenseReportMapper).mapToExpenseReportModel(expenseReportDto, customer);
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
        verify(customerRepository).findById(CUSTOMER_ID_TEST__1004);
    }

    @Test
    public void testGetExpenseReports() {
        // GIVEN
        ExpenseReportModel expenseReport1 = mock(ExpenseReportModel.class);
        ExpenseReportModel expenseReport2 = mock(ExpenseReportModel.class);
        List<ExpenseReportModel> mockExpenseReports = Arrays.asList(expenseReport1, expenseReport2);
        when(expenseReportRepository.findAllByDeletedFalse()).thenReturn(mockExpenseReports);
        ResponseExpenseReportDto expenseReportDto1 = mock(ResponseExpenseReportDto.class);
        ResponseExpenseReportDto expenseReportDto2 = mock(ResponseExpenseReportDto.class);
        when(expenseReportMapper.mapToResponseExpenseReportDto(expenseReport1)).thenReturn(expenseReportDto1);
        when(expenseReportMapper.mapToResponseExpenseReportDto(expenseReport2)).thenReturn(expenseReportDto2);

        // WHEN
        List<ResponseExpenseReportDto> result = expenseReportService.getExpenseReports();

        // THEN
        assertEquals(2, result.size());
        assertEquals(expenseReportDto1, result.get(0));
        assertEquals(expenseReportDto2, result.get(1));

        verify(expenseReportRepository).findAllByDeletedFalse();
        verify(expenseReportMapper).mapToResponseExpenseReportDto(expenseReport1);
        verify(expenseReportMapper).mapToResponseExpenseReportDto(expenseReport2);
    }

	@Test
    public void getExpenseReportByIdTest() {
        // GIVEN
        ExpenseReportModel expenseReport = mock(ExpenseReportModel.class);
        when(expenseReportRepository.findById(EXPENSE_ID_TEST__1009)).thenReturn(Optional.of(expenseReport));
        ResponseExpenseReportDto expenseReportDto = mock(ResponseExpenseReportDto.class);
        when(expenseReportMapper.mapToResponseExpenseReportDto(expenseReport)).thenReturn(expenseReportDto);

        // WHEN
        ResponseExpenseReportDto result = expenseReportService.getExpenseReportById(EXPENSE_ID_TEST__1009);

        // THEN
        assertNotNull(result);
        assertEquals(expenseReportDto, result);
        verify(expenseReportRepository).findById(EXPENSE_ID_TEST__1009);
        verify(expenseReportMapper).mapToResponseExpenseReportDto(expenseReport);
        verify(expenseReportValidator).validateExistsAndNotDeleted(EXPENSE_ID_TEST__1009);
    }

	@Test
    public void testGetExpenseReportsByCustomerId() {
        // GIVEN
        ExpenseReportModel expenseReport1 = mock(ExpenseReportModel.class);
        ExpenseReportModel expenseReport2 = mock(ExpenseReportModel.class);
        List<ExpenseReportModel> mockExpenseReports = Arrays.asList(expenseReport1, expenseReport2);
        when(expenseReportRepository.findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004)).thenReturn(mockExpenseReports);
        ResponseExpenseReportDto expenseReportDto1 = mock(ResponseExpenseReportDto.class);
        ResponseExpenseReportDto expenseReportDto2 = mock(ResponseExpenseReportDto.class);
        when(expenseReportMapper.mapToResponseExpenseReportDto(expenseReport1)).thenReturn(expenseReportDto1);
        when(expenseReportMapper.mapToResponseExpenseReportDto(expenseReport2)).thenReturn(expenseReportDto2);

        // WHEN
        List<ResponseExpenseReportDto> result = expenseReportService.getExpenseReportsByCustomerId(CUSTOMER_ID_TEST__1004);

        // THEN
        assertEquals(2, result.size());
        assertEquals(expenseReportDto1, result.get(0));
        assertEquals(expenseReportDto2, result.get(1));

        verify(expenseReportRepository).findAllByCustomerIdAndDeletedFalse(CUSTOMER_ID_TEST__1004);
        verify(expenseReportMapper).mapToResponseExpenseReportDto(expenseReport1);
        verify(expenseReportMapper).mapToResponseExpenseReportDto(expenseReport2);
        verify(customerValidator).validateExistsAndNotDeleted(CUSTOMER_ID_TEST__1004);
    }
	
	@Test
	  public void testValidateExpenseReport() {
        // WHEN
        expenseReportService.validateExpenseReport(EXPENSE_ID_TEST__1009);

        // THEN
        verify(expenseReportValidator).validateExistsAndNotDeleted(EXPENSE_ID_TEST__1009);
        verify(expenseReportValidator).validateSavedStatus(EXPENSE_ID_TEST__1009);
        verify(expenseReportRepository).updateStatusById(ExpenseReportStatus.VALIDATED, EXPENSE_ID_TEST__1009);
    }
	
	@Test
    public void testUpdateProduct() {
        // GIVEN
        CustomerModel customerModel = mock(CustomerModel.class);
        ExpenseReportModel expenseReport = ExpenseReportModel.builder()
                .id(EXPENSE_ID_TEST__1009)
                .status(ExpenseReportStatus.SAVED)
                .label(EXPENSE_LABEL_TEST)
                .priceExclTax(EXPENSE_PRICE_TEST)
                .qualification(EXPENSE_QUALIFICATION_TEST)
                .tax(EXPENSE_TAX_TEST)
                .customer(customerModel)
                .build();
        UpdateExpenseReportDto updateExpenseReportDto = mock(UpdateExpenseReportDto.class);
        when(expenseReportMapper.mapToExpenseReportModel(updateExpenseReportDto, customerModel)).thenReturn(expenseReport);
        when(expenseReportRepository.findById(EXPENSE_ID_TEST__1009)).thenReturn(Optional.of(expenseReport));

        // WHEN
        expenseReportService.updateExpenseReport(updateExpenseReportDto, EXPENSE_ID_TEST__1009);

        // THEN
        verify(expenseReportValidator).validateExistsAndNotDeleted(EXPENSE_ID_TEST__1009);
        verify(expenseReportValidator).validateSavedStatus(EXPENSE_ID_TEST__1009);
        verify(expenseReportMapper).mapToExpenseReportModel(updateExpenseReportDto, customerModel);
        verify(expenseReportRepository).save(expenseReport);
        verify(expenseReportRepository).findById(EXPENSE_ID_TEST__1009);
    }
	
	@Test
    public void testDeleteExpenseReport() {
        // WHEN
        expenseReportService.deleteExpenseReport(EXPENSE_ID_TEST__1009);

        // THEN
        verify(expenseReportValidator).validateExistsAndNotDeleted(EXPENSE_ID_TEST__1009);
        verify(expenseReportValidator).validateSavedStatus(EXPENSE_ID_TEST__1009);
        verify(expenseReportRepository).logicalDeleteById(EXPENSE_ID_TEST__1009);
    }
}
