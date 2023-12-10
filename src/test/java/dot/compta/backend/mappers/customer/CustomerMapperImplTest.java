package dot.compta.backend.mappers.customer;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.dtos.customer.RequestCustomerDto;
import dot.compta.backend.dtos.customer.ResponseCustomerDto;
import dot.compta.backend.mappers.address.AddressMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.customer.CustomerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static dot.compta.backend.utils.TestUtils.*;

public class CustomerMapperImplTest {

    private AddressMapper addressMapper;

    private CustomerMapperImpl customerMapper;

    @BeforeEach
    void setUp() {
        addressMapper = mock(AddressMapper.class);
        customerMapper = new CustomerMapperImpl(new ModelMapper(), addressMapper);
    }

    @Test
    void mapToCustomerTest() {
        // GIVEN
        AddressDto addressDto = mock(AddressDto.class);
        RequestCustomerDto requestCustomerDto = RequestCustomerDto.builder()
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST)
                .email(CUSTOMER_EMAIL_TEST)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST)
                .phone(CUSTOMER_PHONE_TEST)
                .fax(CUSTOMER_FAX_TEST)
                .address(addressDto)
                .build();
        City city = mock(City.class);
        AddressModel addressModel = mock(AddressModel.class);
        when(addressMapper.mapToAddressModel(addressDto, city)).thenReturn(addressModel);
        AccountantModel accountantModel = mock(AccountantModel.class);

        // WHEN
        CustomerModel result = customerMapper.mapToCustomerModel(requestCustomerDto, city, accountantModel);

        // THEN
        assertNotNull(result);
        assertEquals(CUSTOMER_RC_TEST__101, result.getRc());
        assertEquals(CUSTOMER_SOCIAL_PURPOSE_TEST, result.getCompanyName());
        assertEquals(CUSTOMER_EMAIL_TEST, result.getEmail());
        assertEquals(CUSTOMER_MOBILE_PHONE_TEST, result.getMobilePhone());
        assertEquals(CUSTOMER_PHONE_TEST, result.getPhone());
        assertEquals(CUSTOMER_FAX_TEST, result.getFax());
        assertEquals(addressModel, result.getAddress());
        assertFalse(result.isDeleted());
        assertEquals(accountantModel, result.getAccountant());
    }

    @Test
    public void mapToCustomerDtoTest() {
        // GIVEN
        AddressModel addressModel = mock(AddressModel.class);
        AccountantModel accountantModel = mock(AccountantModel.class);
        int accountantId = 9;
        when(accountantModel.getId()).thenReturn(accountantId);
        CustomerModel customerModel = CustomerModel.builder()
                .id(CUSTOMER_ID_TEST__1004)
                .rc(CUSTOMER_RC_TEST__101)
                .companyName(CUSTOMER_SOCIAL_PURPOSE_TEST)
                .email(CUSTOMER_EMAIL_TEST)
                .mobilePhone(CUSTOMER_MOBILE_PHONE_TEST)
                .phone(CUSTOMER_PHONE_TEST)
                .fax(CUSTOMER_FAX_TEST)
                .address(addressModel)
                .accountant(accountantModel)
                .build();
        AddressDto addressDto = mock(AddressDto.class);
        when(addressMapper.mapToAddressDto(addressModel)).thenReturn(addressDto);

        // WHEN
        ResponseCustomerDto result = customerMapper.mapToResponseCustomerDto(customerModel);

        // THEN
        assertNotNull(result);
        assertEquals(CUSTOMER_ID_TEST__1004, result.getId());
        assertEquals(CUSTOMER_RC_TEST__101, result.getRc());
        assertEquals(CUSTOMER_SOCIAL_PURPOSE_TEST, result.getCompanyName());
        assertEquals(CUSTOMER_EMAIL_TEST, result.getEmail());
        assertEquals(CUSTOMER_MOBILE_PHONE_TEST, result.getMobilePhone());
        assertEquals(CUSTOMER_PHONE_TEST, result.getPhone());
        assertEquals(CUSTOMER_FAX_TEST, result.getFax());
        assertEquals(addressDto, result.getAddress());
        assertEquals(accountantId, result.getAccountantId());

    }

}
