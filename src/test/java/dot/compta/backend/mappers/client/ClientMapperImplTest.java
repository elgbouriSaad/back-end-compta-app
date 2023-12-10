package dot.compta.backend.mappers.client;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.client.RequestClientDto;
import dot.compta.backend.dtos.client.ResponseClientDto;
import dot.compta.backend.mappers.address.AddressMapper;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.client.ClientModel;
import dot.compta.backend.models.customer.CustomerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static dot.compta.backend.utils.TestUtils.*;

public class ClientMapperImplTest {

    private AddressMapper addressMapper;

    private ClientMapperImpl clientMapper;

    @BeforeEach
    void setUp() {
        addressMapper = mock(AddressMapper.class);
        clientMapper = new ClientMapperImpl(new ModelMapper(), addressMapper);
    }

    @Test
    void mapToClientTest() {
        // GIVEN
        AddressDto addressDto = mock(AddressDto.class);
        RequestClientDto requestClientDto = RequestClientDto.builder()
                .rc(CLIENT_RC_TEST__102)
                .companyName(CLIENT_SOCIAL_PURPOSE_TEST)
                .email(CLIENT_EMAIL_TEST)
                .mobilePhone(CLIENT_MOBILE_PHONE_TEST)
                .phone(CLIENT_PHONE_TEST)
                .fax(CLIENT_FAX_TEST)
                .address(addressDto)
                .customerId(CUSTOMER_ID_TEST__1004)
                .build();
        City city = mock(City.class);
        AddressModel addressModel = mock(AddressModel.class);
        when(addressMapper.mapToAddressModel(addressDto, city)).thenReturn(addressModel);
        CustomerModel customer = mock(CustomerModel.class);
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);

        // WHEN
        ClientModel result = clientMapper.mapToClientModel(requestClientDto, city, customer);

        // THEN
        assertNotNull(result);
        assertEquals(CLIENT_RC_TEST__102, result.getRc());
        assertEquals(CLIENT_SOCIAL_PURPOSE_TEST, result.getCompanyName());
        assertEquals(CLIENT_EMAIL_TEST, result.getEmail());
        assertEquals(CLIENT_MOBILE_PHONE_TEST, result.getMobilePhone());
        assertEquals(CLIENT_PHONE_TEST, result.getPhone());
        assertEquals(CLIENT_FAX_TEST, result.getFax());
        assertEquals(addressModel, result.getAddress());
        assertFalse(result.isDeleted());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomer().getId());
    }

    @Test
    public void mapToClientDtoTest() {
        // GIVEN
        AddressModel addressModel = mock(AddressModel.class);
        CustomerModel customer = mock(CustomerModel.class);
        ClientModel clientModel = ClientModel.builder()
                .id(CLIENT_ID_TEST__1005)
                .rc(CLIENT_RC_TEST__102)
                .companyName(CLIENT_SOCIAL_PURPOSE_TEST)
                .email(CLIENT_EMAIL_TEST)
                .mobilePhone(CLIENT_MOBILE_PHONE_TEST)
                .phone(CLIENT_PHONE_TEST)
                .fax(CLIENT_FAX_TEST)
                .address(addressModel)
                .customer(customer)
                .build();
        AddressDto addressDto = mock(AddressDto.class);
        when(addressMapper.mapToAddressDto(addressModel)).thenReturn(addressDto);
        when(customer.getId()).thenReturn(CUSTOMER_ID_TEST__1004);

        // WHEN
        ResponseClientDto result = clientMapper.mapToResponseClientDto(clientModel);

        // THEN
        assertNotNull(result);
        assertEquals(CLIENT_ID_TEST__1005, result.getId());
        assertEquals(CLIENT_RC_TEST__102, result.getRc());
        assertEquals(CLIENT_SOCIAL_PURPOSE_TEST, result.getCompanyName());
        assertEquals(CLIENT_EMAIL_TEST, result.getEmail());
        assertEquals(CLIENT_MOBILE_PHONE_TEST, result.getMobilePhone());
        assertEquals(CLIENT_PHONE_TEST, result.getPhone());
        assertEquals(CLIENT_FAX_TEST, result.getFax());
        assertEquals(addressDto, result.getAddress());
        assertEquals(CUSTOMER_ID_TEST__1004, result.getCustomerId());

    }

}
