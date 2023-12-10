package dot.compta.backend.mappers.accountant;

import dot.compta.backend.dtos.accountant.RequestAccountantDto;
import dot.compta.backend.dtos.accountant.ResponseAccountantDto;
import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.mappers.address.AddressMapper;
import dot.compta.backend.models.accountant.AccountantModel;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountantMapperImpl implements AccountantMapper {

    private final ModelMapper modelMapper;

    private final AddressMapper addressMapper;

    @Override
    public AccountantModel mapToAccountantModel(RequestAccountantDto requestAccountantDto, City city, int addressId) {
        AccountantModel accountantModel = mapToAccountantModel(requestAccountantDto, city);
        accountantModel.getAddress().setId(addressId);
        return accountantModel;
    }

    @Override
    public AccountantModel mapToAccountantModel(RequestAccountantDto requestAccountantDto, City city) {
        AddressModel address = addressMapper.mapToAddressModel(requestAccountantDto.getAddress(), city);
        AccountantModel accountantModel = modelMapper.map(requestAccountantDto, AccountantModel.class);
        accountantModel.setAddress(address);
        return accountantModel;
    }

    @Override
    public ResponseAccountantDto mapToResponseAccountantDto(AccountantModel accountant) {
        AddressDto addressDto = addressMapper.mapToAddressDto(accountant.getAddress());
        ResponseAccountantDto responseAccountantDto = modelMapper.map(accountant, ResponseAccountantDto.class);
        responseAccountantDto.setAddress(addressDto);
        return responseAccountantDto;
    }


}
