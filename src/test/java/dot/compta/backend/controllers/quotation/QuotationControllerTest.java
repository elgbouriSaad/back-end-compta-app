package dot.compta.backend.controllers.quotation;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.dtos.productQuantity.RequestProductQuantityDto;
import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.RequestTransformQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.dtos.quotation.UpdateQuotationDto;
import dot.compta.backend.dtos.quotationProduct.ResponseQuotationProductDto;
import dot.compta.backend.services.quotation.QuotationService;
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

public class QuotationControllerTest extends BaseQuotationControllerTest {

    @Mock
    private QuotationService quotationService;
    @InjectMocks
    private QuotationController quotationController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mvc = standaloneSetup(quotationController).build();
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(quotationService);
    }

    @Test
    public void testCreateQuotation() throws Exception {
        List<RequestProductQuantityDto> productQuantities = buildProductQuantities();
        RequestQuotationDto requestQuotationDto = buildRequestQuotationDto(productQuantities);
        String content = objectMapper.writeValueAsString(requestQuotationDto);
        mvc.perform(MockMvcRequestBuilders.post(APIConstants.QUOTATIONS_URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
        verify(quotationService).createQuotation(requestQuotationDto);
    }

    @Test
    public void testGetQuotations() throws Exception {
        ResponseQuotationProductDto responseQuotationProductDto = buildResponseQuotationProductDto();
        List<ResponseQuotationProductDto> responseQuotationProductDtos = Collections.singletonList(responseQuotationProductDto);
        ResponseQuotationDto responseQuotationDto = buildResponseQuotationDto(responseQuotationProductDtos);
        List<ResponseQuotationDto> quotations = Collections.singletonList(responseQuotationDto);
        given(quotationService.getQuotations()).willReturn(quotations);
        mvc.perform(get(APIConstants.QUOTATIONS_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("QuotationController_getQuotations.json"), true));
        verify(quotationService).getQuotations();
    }

    @Test
    public void testGetQuotationById() throws Exception {
        ResponseQuotationProductDto responseQuotationProductDto = buildResponseQuotationProductDto();
        List<ResponseQuotationProductDto> responseQuotationProductDtos = Collections.singletonList(responseQuotationProductDto);
        ResponseQuotationDto responseQuotationDto = buildResponseQuotationDto(responseQuotationProductDtos);
        given(quotationService.getQuotationById(QUOTATION_ID_TEST__1007)).willReturn(responseQuotationDto);
        mvc.perform(get(APIConstants.QUOTATIONS_URL + "/" + QUOTATION_ID_TEST__1007)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("QuotationController_getQuotationById.json"), true));
        verify(quotationService).getQuotationById(QUOTATION_ID_TEST__1007);
    }
    
    @Test
    public void testGetQuotationsByCustomerId() throws Exception {
        ResponseQuotationProductDto responseQuotationProductDto = buildResponseQuotationProductDto();
        List<ResponseQuotationProductDto> responseQuotationProductDtos = Collections.singletonList(responseQuotationProductDto);
        ResponseQuotationDto responseQuotationDto = buildResponseQuotationDto(responseQuotationProductDtos);
        List<ResponseQuotationDto> quotations = Collections.singletonList(responseQuotationDto);
        given(quotationService.getQuotationsByCustomerId(CUSTOMER_ID_TEST__1004)).willReturn(quotations);
        mvc.perform(get(APIConstants.QUOTATIONS_URL + APIConstants.CUSTOMER_QUOTATIONS_URL + CUSTOMER_ID_TEST__1004)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("QuotationController_getQuotationsByCustomerId.json"), true));
        verify(quotationService).getQuotationsByCustomerId(CUSTOMER_ID_TEST__1004);
    }
    
    @Test
    public void testGetQuotationsByClientId() throws Exception {
        ResponseQuotationProductDto responseQuotationProductDto = buildResponseQuotationProductDto();
        List<ResponseQuotationProductDto> responseQuotationProductDtos = Collections.singletonList(responseQuotationProductDto);
        ResponseQuotationDto responseQuotationDto = buildResponseQuotationDto(responseQuotationProductDtos);
        List<ResponseQuotationDto> quotations = Collections.singletonList(responseQuotationDto);
        given(quotationService.getQuotationsByClientId(CLIENT_ID_TEST__1005)).willReturn(quotations);
        mvc.perform(get(APIConstants.QUOTATIONS_URL + APIConstants.CLIENT_QUOTATIONS_URL + CLIENT_ID_TEST__1005)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonContent("QuotationController_getQuotationsByClientId.json"), true));
        verify(quotationService).getQuotationsByClientId(CLIENT_ID_TEST__1005);
    }
    
    @Test
    public void testValidateQuotation() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_VALIDATE_URL + QUOTATION_ID_TEST__1007))
        		.andExpect(status().isOk());
    	verify(quotationService).validateQuotation(QUOTATION_ID_TEST__1007);
    }
    
    @Test
    public void testUpdateQuotation() throws Exception {
    	List<RequestProductQuantityDto> productQuantities = buildProductQuantities();
        UpdateQuotationDto updateQuotationDto = buildUpdateQuotationDto(productQuantities);
        String content = objectMapper.writeValueAsString(updateQuotationDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_UPDATE_URL + QUOTATION_ID_TEST__1007)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(quotationService).updateQuotation(updateQuotationDto,QUOTATION_ID_TEST__1007);
    }
    
    @Test
    public void testTransformQuotation() throws Exception {
        RequestTransformQuotationDto requestTransformQuotationDto = buildRequestTransformQuotationDto();
        String content = objectMapper.writeValueAsString(requestTransformQuotationDto);
        mvc.perform(MockMvcRequestBuilders.put(APIConstants.QUOTATIONS_URL + APIConstants.QUOTATION_TRANSFORM_URL + QUOTATION_ID_TEST__1007)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(quotationService).transformToInvoice(QUOTATION_ID_TEST__1007, INVOICE_PAYMENT_DELAY_TEST);
    }

    @Test
    public void testDeleteQuotation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(APIConstants.QUOTATIONS_URL + "/" + QUOTATION_ID_TEST__1007))
                .andExpect(status().isOk());
        verify(quotationService).deleteQuotation(QUOTATION_ID_TEST__1007);
    }
}
