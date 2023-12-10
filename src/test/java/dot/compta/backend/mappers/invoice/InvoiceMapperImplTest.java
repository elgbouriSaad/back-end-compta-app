package dot.compta.backend.mappers.invoice;

import static dot.compta.backend.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dot.compta.backend.constants.InvoiceStatus;
import dot.compta.backend.constants.QuotationStatus;
import dot.compta.backend.dtos.invoice.RequestInvoiceDto;
import dot.compta.backend.dtos.invoice.ResponseInvoiceDto;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import dot.compta.backend.models.invoice.InvoiceModel;
import dot.compta.backend.models.quotation.QuotationModel;

public class InvoiceMapperImplTest {
	
	private InvoiceMapperImpl invoiceMapper;
	
	@BeforeEach
    void setUp() {
		invoiceMapper = new InvoiceMapperImpl(getModelMapper());
    }
	
	@Test
    void mapToInvoiceTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ClientModel client = mock(ClientModel.class);
        QuotationModel quotation = mock(QuotationModel.class);
        RequestInvoiceDto invoice = RequestInvoiceDto.builder()
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .customerId(CUSTOMER_ID_TEST__1004)
                .clientId(CLIENT_ID_TEST__1005)
                .build();
        when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);
        when(client.getId()).thenReturn(CLIENT_ID_TEST__1005);

        // WHEN
        InvoiceModel result = invoiceMapper.mapToInvoiceModel(invoice, customer, client, quotation);

        // THEN
        assertNotNull(result);
        assertEquals(INVOICE_PAYMENT_DELAY_TEST, result.getPaymentDelay());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomer().getId());
        assertEquals(CLIENT_ID_TEST__1005, result.getClient().getId());
        assertEquals(QUOTATION_ID_TEST__1007, result.getQuotation().getId());
    }
	
	@Test
    void mapToInvoiceDtoTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ClientModel client = mock(ClientModel.class);
        QuotationModel quotation = mock(QuotationModel.class);
        InvoiceModel invoice = InvoiceModel.builder()
                .status(InvoiceStatus.SAVED)
                .paymentDelay(INVOICE_PAYMENT_DELAY_TEST)
                .customer(customer)
                .client(client)
                .quotation(quotation)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);
        when(client.getId()).thenReturn(CLIENT_ID_TEST__1005);
        when(quotation.getId()).thenReturn(QUOTATION_ID_TEST__1007);

        // WHEN
        ResponseInvoiceDto result = invoiceMapper.mapToResponseInvoiceDto(invoice);

        // THEN
        assertNotNull(result);
        assertEquals(InvoiceStatus.SAVED, result.getStatus());
        assertEquals(INVOICE_PAYMENT_DELAY_TEST, result.getPaymentDelay());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomerId());
        assertEquals(CLIENT_ID_TEST__1005, result.getClientId());
        assertEquals(QUOTATION_ID_TEST__1007, result.getQuotationId());
    }
	
	@Test
    void mapToInvoiceModelTest() {
        // GIVEN
        CustomerModel customer = mock(CustomerModel.class);
        ClientModel client = mock(ClientModel.class);
        QuotationModel quotation = QuotationModel.builder()
        		.id(QUOTATION_ID_TEST__1007)
                .status(QuotationStatus.VALIDATED)
                .validationDelay(QUOTATION_VALIDATION_DELAY_TEST)
                .customer(customer)
                .client(client)
                .build();
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);
        when(client.getId()).thenReturn(CLIENT_ID_TEST__1005);

        // WHEN
        InvoiceModel result = invoiceMapper.mapToInvoiceModel(quotation, INVOICE_PAYMENT_DELAY_TEST);

        // THEN
        assertNotNull(result);
        assertEquals(QUOTATION_ID_TEST__1007, result.getQuotation().getId());
        assertEquals(INVOICE_PAYMENT_DELAY_TEST, result.getPaymentDelay());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomer().getId());
        assertEquals(CLIENT_ID_TEST__1005, result.getClient().getId());
        assertEquals(QUOTATION_ID_TEST__1007, result.getQuotation().getId());
    }

}
