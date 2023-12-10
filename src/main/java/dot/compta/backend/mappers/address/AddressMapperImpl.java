package dot.compta.backend.mappers.address;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressMapperImpl implements AddressMapper {

    private final ModelMapper modelMapper;

    @Override
    public AddressModel mapToAddressModel(AddressDto addressDto, City city) {
        AddressModel address = modelMapper.map(addressDto, AddressModel.class);
        address.setCity(city);
        return address;
    }

    @Override
    public AddressDto mapToAddressDto(AddressModel address) {
        String city = address.getCity().getName();
        String country = address.getCity().getCountry().getName();
        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        addressDto.setCity(city);
        addressDto.setCountry(country);
        return addressDto;
    }

}
