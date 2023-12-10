package dot.compta.backend.controllers.invoice;

import static dot.compta.backend.utils.TestUtils.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

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
import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.dtos.invoice.UpdateInvoiceDto;
import dot.compta.backend.dtos.invoiceProduct.ResponseInvoiceProductDto;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.services.invoice.InvoiceService;

public class InvoiceControllerTest extends BaseInvoiceControllerTest{
	
	@Mock
    private InvoiceService invoiceService;
    @InjectMocks
    private InvoiceController invoiceController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(invoiceController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(invoiceService);
    }

    @Test
    public void testCreateInvoice() throws Exception {
        List<RequestProductQuantityDto> productQuantities = buildProductQuantities();
        RequestInvoiceDto requestInvoiceDto = buildRequestInvoiceDto(productQuantities);
        String content = objectMapper.writeValueAsString(requestInvoiceDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.INVOICES_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(invoiceService).createInvoice(requestInvoiceDto);
    }
    
    @Test
    public void testGetInvoices() throws Exception {
        ResponseInvoiceProductDto responseInvoiceProductDto = buildResponseInvoiceProductDto();
        List<ResponseInvoiceProductDto> responseInvoiceProductDtos = Collections.singletonList(responseInvoiceProductDto);
        ResponseInvoiceDto responseInvoiceDto = buildResponseInvoiceDto(responseInvoiceProductDtos);
        List<ResponseInvoiceDto> invoices = Collections.singletonList(responseInvoiceDto);
        given(invoiceService.getInvoices()).willReturn(invoices);
        mvc.perform(get(APIConstants.INVOICES_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("InvoiceController_getInvoices.json"), true));
        verify(invoiceService).getInvoices();
    }
    
    @Test
    public void testGetInvoiceById() throws Exception {
        ResponseInvoiceProductDto responseInvoiceProductDto = buildResponseInvoiceProductDto();
        List<ResponseInvoiceProductDto> responseInvoiceProductDtos = Collections.singletonList(responseInvoiceProductDto);
        ResponseInvoiceDto responseInvoiceDto = buildResponseInvoiceDto(responseInvoiceProductDtos);
        given(invoiceService.getInvoiceById(INVOICE_ID_TEST__1010)).willReturn(responseInvoiceDto);
        mvc.perform(get(APIConstants.INVOICES_URL + "/" + INVOICE_ID_TEST__1010)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("InvoiceController_getInvoiceById.json"), true));
        verify(invoiceService).getInvoiceById(INVOICE_ID_TEST__1010);
    }
    
    @Test
    public void testGetInvoicesByCustomerId() throws Exception {
        ResponseInvoiceProductDto responseInvoiceProductDto = buildResponseInvoiceProductDto();
        List<ResponseInvoiceProductDto> responseInvoiceProductDtos = Collections.singletonList(responseInvoiceProductDto);
        ResponseInvoiceDto responseInvoiceDto = buildResponseInvoiceDto(responseInvoiceProductDtos);
        List<ResponseInvoiceDto> invoices = Collections.singletonList(responseInvoiceDto);
        given(invoiceService.getInvoicesByCustomerId(CUSTOMER_ID_TEST__1004)).willReturn(invoices);
        mvc.perform(get(APIConstants.INVOICES_URL + APIConstants.CUSTOMER_INVOICES_URL + CUSTOMER_ID_TEST__1004)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("InvoiceController_getInvoicesByCustomerId.json"), true));
        verify(invoiceService).getInvoicesByCustomerId(CUSTOMER_ID_TEST__1004);
    }
    
    @Test
    public void testGetInvoicesByClientId() throws Exception {
        ResponseInvoiceProductDto responseInvoiceProductDto = buildResponseInvoiceProductDto();
        List<ResponseInvoiceProductDto> responseInvoiceProductDtos = Collections.singletonList(responseInvoiceProductDto);
        ResponseInvoiceDto responseInvoiceDto = buildResponseInvoiceDto(responseInvoiceProductDtos);
        List<ResponseInvoiceDto> invoices = Collections.singletonList(responseInvoiceDto);
        given(invoiceService.getInvoicesByClientId(CLIENT_ID_TEST__1005)).willReturn(invoices);
        mvc.perform(get(APIConstants.INVOICES_URL + APIConstants.CLIENT_INVOICES_URL + CLIENT_ID_TEST__1005)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("InvoiceController_getInvoicesByClientId.json"), true));
        verify(invoiceService).getInvoicesByClientId(CLIENT_ID_TEST__1005);
    }
    
    @Test
    public void testValidateInvoice() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.put(APIConstants.INVOICES_URL + APIConstants.INVOICE_VALIDATE_URL + INVOICE_ID_TEST__1010))
        		.andExpect(status().isOk());
    	verify(invoiceService).validateInvoice(INVOICE_ID_TEST__1010);
    }
    
    @Test
    public void testUpdateInvoice() throws Exception {
    	List<RequestProductQuantityDto> productQuantities = buildProductQuantities();
        UpdateInvoiceDto updateInvoiceDto = buildUpdateInvoiceDto(productQuantities);
        String content = objectMapper.writeValueAsString(updateInvoiceDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.INVOICES_URL + APIConstants.INVOICE_UPDATE_URL + INVOICE_ID_TEST__1010)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(invoiceService).updateInvoice(updateInvoiceDto,INVOICE_ID_TEST__1010);
    }
    
    @Test
    public void testDeleteInvoice() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.INVOICES_URL + "/" + INVOICE_ID_TEST__1010))
                .andExpect(status().isOk());
        verify(invoiceService).deleteInvoice(INVOICE_ID_TEST__1010);
    }

}
