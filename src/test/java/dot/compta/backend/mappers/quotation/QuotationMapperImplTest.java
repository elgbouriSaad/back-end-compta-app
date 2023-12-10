package dot.compta.backend.mappers.quotation;

import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.dtos.quotation.RequestQuotationDto;
import dot.compta.backend.dtos.quotation.ResponseQuotationDto;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.quotation.QuotationModel;
import dot.compta.backend.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static dot.compta.backend.utils.TestUtils.*;

public class QuotationMapperImplTest {

    private QuotationMapperImpl quotationMapper;

    @BeforeEach
    void setUp() {
        quotationMapper = new QuotationMapperImpl(TestUtils.getModelMapper());
    }

    @Test
    void mapToQuotationTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ClientModel client = mock(ClientModel.class);
        RequestQuotationDto quotation = RequestQuotationDto.builder()
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);
        when(client.getId()).thenReturn(CLIENT_ID_TEST__1005);

        // WHEN
        QuotationModel result = quotationMapper.mapToQuotationModel(quotation, customer, client);

        // THEN
        assertNotNull(result);
        assertEquals(QUOTATION_VALIDATION_DELAY_TEST, result.getValidationDelay());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomer().getId());
        assertEquals(CLIENT_ID_TEST__1005, result.getClient().getId());
    }

    @Test
    void mapToQuotationDtoTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ClientModel client = mock(ClientModel.class);
        QuotationModel quotation = QuotationModel.builder()
                .status(QuotationStatus.SAVED)
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .customer(customer)
                .client(client)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);
        when(client.getId()).thenReturn(CLIENT_ID_TEST__1005);

        // WHEN
        ResponseQuotationDto result = quotationMapper.mapToResponseQuotationDto(quotation);

        // THEN
        assertNotNull(result);
        assertEquals(QuotationStatus.SAVED, result.getStatus());
        assertEquals(QUOTATION_VALIDATION_DELAY_TEST, result.getValidationDelay());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomerId());
        assertEquals(CLIENT_ID_TEST__1005, result.getClientId());
    }

}
