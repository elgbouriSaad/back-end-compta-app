package dot.compta.backend.mappers.address;

import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.models.address.AddressModel;
import dot.compta.backend.models.city.City;

public interface AddressMapper {

    AddressModel mapToAddressModel(AddressDto addressDto, City city);

    AddressDto mapToAddressDto(AddressModel address);

}
