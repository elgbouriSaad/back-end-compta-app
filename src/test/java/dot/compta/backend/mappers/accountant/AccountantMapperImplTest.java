package dot.compta.backend.mappers.accountant;

import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.mappers.address.AddressMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static dot.compta.backend.utils.TestUtils.*;

class AccountantMapperImplTest {

    private AddressMapper addressMapper;

    private AccountantMapperImpl accountantMapper;

    @BeforeEach
    void setUp() {
        addressMapper = mock(AddressMapper.class);
        accountantMapper = new AccountantMapperImpl(new ModelMapper(), addressMapper);
    }

    @Test
    void mapToAccountantTest() {
        // GIVEN
        AddressDto addressDto = mock(AddressDto.class);
        RequestAccountantDto requestAccountantDto = RequestAccountantDto.builder()
                .rc(ACCOUNTANT_RC_TEST__100)
                .companyName(ACCOUNTANT_SOCIAL_PURPOSE_TEST)
                .email(ACCOUNTANT_EMAIL_TEST)
                .mobilePhone(ACCOUNTANT_MOBILE_PHONE_TEST)
                .phone(ACCOUNTANT_PHONE_TEST)
                .fax(ACCOUNTANT_FAX_TEST)
                .address(addressDto)
                .build();
        City city = mock(City.class);
        AddressModel addressModel = mock(AddressModel.class);
        when(addressMapper.mapToAddressModel(addressDto, city)).thenReturn(addressModel);

        // WHEN
        AccountantModel result = accountantMapper.mapToAccountantModel(requestAccountantDto, city);

        // THEN
        assertNotNull(result);
        assertEquals(ACCOUNTANT_RC_TEST__100, result.getRc());
        assertEquals(ACCOUNTANT_SOCIAL_PURPOSE_TEST, result.getCompanyName());
        assertEquals(ACCOUNTANT_EMAIL_TEST, result.getEmail());
        assertEquals(ACCOUNTANT_MOBILE_PHONE_TEST, result.getMobilePhone());
        assertEquals(ACCOUNTANT_PHONE_TEST, result.getPhone());
        assertEquals(ACCOUNTANT_FAX_TEST, result.getFax());
        assertEquals(addressModel, result.getAddress());
        assertFalse(result.isDeleted());
    }

    @Test
    public void mapToAccountantDtoTest() {
        // GIVEN
        AddressModel addressModel = mock(AddressModel.class);
        AccountantModel accountantModel = AccountantModel.builder()
                .id(ACCOUNTANT_ID_TEST__1003)
                .rc(ACCOUNTANT_RC_TEST__100)
                .companyName(ACCOUNTANT_SOCIAL_PURPOSE_TEST)
                .email(ACCOUNTANT_EMAIL_TEST)
                .mobilePhone(ACCOUNTANT_MOBILE_PHONE_TEST)
                .phone(ACCOUNTANT_PHONE_TEST)
                .fax(ACCOUNTANT_FAX_TEST)
                .address(addressModel)
                .build();
        AddressDto addressDto = mock(AddressDto.class);
        when(addressMapper.mapToAddressDto(addressModel)).thenReturn(addressDto);

        // WHEN
        ResponseAccountantDto result = accountantMapper.mapToResponseAccountantDto(accountantModel);

        // THEN
        assertNotNull(result);
        assertEquals(ACCOUNTANT_ID_TEST__1003, result.getId());
        assertEquals(ACCOUNTANT_RC_TEST__100, result.getRc());
        assertEquals(ACCOUNTANT_SOCIAL_PURPOSE_TEST, result.getCompanyName());
        assertEquals(ACCOUNTANT_EMAIL_TEST, result.getEmail());
        assertEquals(ACCOUNTANT_MOBILE_PHONE_TEST, result.getMobilePhone());
        assertEquals(ACCOUNTANT_PHONE_TEST, result.getPhone());
        assertEquals(ACCOUNTANT_FAX_TEST, result.getFax());
        assertEquals(addressDto, result.getAddress());

    }


}