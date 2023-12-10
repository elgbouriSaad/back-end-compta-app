package dot.compta.backend.services.accountant;

import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.mappers.accountant.AccountantMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import dot.compta.backend.repositories.accountant.AccountantRepository;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.services.customer.CustomerService;
import dot.compta.backend.validators.accountant.AccountantValidator;
import dot.compta.backend.validators.address.AddressValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static dot.compta.backend.utils.TestUtils.*;

class AccountantServiceImplTest {

    @Mock
    private AccountantRepository accountantRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AccountantMapper accountantMapper;

    @Mock
    private AddressValidator addressValidator;

    @Mock
    private AccountantValidator accountantValidator;
    
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AccountantServiceImpl accountantService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(accountantRepository,
                cityRepository,
                accountantMapper,
                addressValidator,
                accountantValidator,
                customerService
        );
    }

    @Test
    void createAccountantTest() {
        // GIVEN
        RequestAccountantDto requestAccountantDto = mock(RequestAccountantDto.class);
        AddressDto addressDto = mock(AddressDto.class);
        when(requestAccountantDto.getAddress()).thenReturn(addressDto);
        when(addressDto.getCity()).thenReturn(CITY_NAME_TEST__CASABLANCA);
        City city = mock(City.class);
        when(cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA)).thenReturn(Optional.of(city));
        AccountantModel accountantModel = mock(AccountantModel.class);
        when(accountantMapper.mapToAccountantModel(requestAccountantDto, city)).thenReturn(accountantModel);


        // WHEN
        accountantService.createAccountant(requestAccountantDto);

        // THEN
        verify(addressValidator).validate(addressDto);
        verify(cityRepository).findOneByNameIgnoreCase(CITY_NAME_TEST__CASABLANCA);
        verify(accountantMapper).mapToAccountantModel(requestAccountantDto, city);
        verify(accountantRepository).save(accountantModel);

    }

    @Test
    public void testGetAccountants() {
        // GIVEN
        AccountantModel accountant1 = mock(AccountantModel.class);
        AccountantModel accountant2 = mock(AccountantModel.class);
        List<AccountantModel> mockAccountants = Arrays.asList(accountant1, accountant2);
        when(accountantRepository.findAllByDeletedFalse()).thenReturn(mockAccountants);
        ResponseAccountantDto accountantDto1 = mock(ResponseAccountantDto.class);
        ResponseAccountantDto accountantDto2 = mock(ResponseAccountantDto.class);
        when(accountantMapper.mapToResponseAccountantDto(accountant1)).thenReturn(accountantDto1);
        when(accountantMapper.mapToResponseAccountantDto(accountant2)).thenReturn(accountantDto2);

        // WHEN
        List<ResponseAccountantDto> result = accountantService.getAccountants();

        // THEN
        assertEquals(2, result.size());
        assertEquals(accountantDto1, result.get(0));
        assertEquals(accountantDto2, result.get(1));

        verify(accountantRepository).findAllByDeletedFalse();
        verify(accountantMapper).mapToResponseAccountantDto(accountant1);
        verify(accountantMapper).mapToResponseAccountantDto(accountant2);
    }

    @Test
    public void getAccountantByIdTest() {
        // GIVEN
        AccountantModel accountant1 = mock(AccountantModel.class);
        when(accountantRepository.findById(ACCOUNTANT_ID_TEST__1003)).thenReturn(Optional.of(accountant1));
        ResponseAccountantDto accountantDto1 = mock(ResponseAccountantDto.class);
        when(accountantMapper.mapToResponseAccountantDto(accountant1)).thenReturn(accountantDto1);

        // WHEN
        ResponseAccountantDto result = accountantService.getAccountantById(ACCOUNTANT_ID_TEST__1003);

        // THEN
        assertNotNull(result);
        assertEquals(accountantDto1, result);
        verify(accountantRepository).findById(ACCOUNTANT_ID_TEST__1003);
        verify(accountantMapper).mapToResponseAccountantDto(accountant1);
        verify(accountantValidator).validateExistsAndNotDeleted(ACCOUNTANT_ID_TEST__1003);
    }

    @Test
    public void updateAccountantTest() {
        // GIVEN
        AddressModel addressModel = mock(AddressModel.class);
        when(addressModel.getId()).thenReturn(ADDRESS_ID_TEST__1002);
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
        RequestAccountantDto requestAccountantDto = mock(RequestAccountantDto.class);
        AddressDto addressDto = mock(AddressDto.class);
        when(requestAccountantDto.getAddress()).thenReturn(addressDto);
        when(addressDto.getCity()).thenReturn(CITY_NAME_TEST__RABAT);
        City city = mock(City.class);
        when(cityRepository.findOneByNameIgnoreCase(CITY_NAME_TEST__RABAT)).thenReturn(Optional.of(city));
        when(accountantRepository.findById(ACCOUNTANT_ID_TEST__1003)).thenReturn(Optional.of(accountantModel));
        when(accountantMapper.mapToAccountantModel(requestAccountantDto, city, ADDRESS_ID_TEST__1002)).thenReturn(accountantModel);

        // WHEN
        accountantService.updateAccountant(requestAccountantDto, ACCOUNTANT_ID_TEST__1003);

        // THEN
        verify(accountantValidator).validateExistsAndNotDeleted(ACCOUNTANT_ID_TEST__1003);
        verify(accountantMapper).mapToAccountantModel(requestAccountantDto, city, ADDRESS_ID_TEST__1002);
        verify(addressValidator).validate(addressDto);
        verify(cityRepository).findOneByNameIgnoreCase(CITY_NAME_TEST__RABAT);
        verify(accountantRepository).findById(ACCOUNTANT_ID_TEST__1003);
        verify(accountantRepository).save(accountantModel);
    }

    @Test
    public void deleteAccountantTest() {
        // WHEN
        accountantService.deleteAccountant(ACCOUNTANT_ID_TEST__1003);

        // THEN
        verify(accountantValidator).validateExistsAndNotDeleted(ACCOUNTANT_ID_TEST__1003);
        verify(accountantRepository).logicalDeleteById(ACCOUNTANT_ID_TEST__1003);
        verify(customerService).deleteAccountantCustomers(ACCOUNTANT_ID_TEST__1003);
    }
}