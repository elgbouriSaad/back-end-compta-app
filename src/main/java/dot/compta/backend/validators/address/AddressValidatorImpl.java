package dot.compta.backend.validators.address;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.dtos.address.AddressDto;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;
import dot.compta.backend.repositories.city.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressValidatorImpl implements AddressValidator {

    private final CityRepository cityRepository;

    @Override
    public void validate(AddressDto addressDto) {
        String cityName = addressDto.getCity();
        Optional<City> optCity = cityRepository.findOneByNameIgnoreCase(cityName);
        if (optCity.isEmpty()) {
            throw new CustomBadInputException(String.format(ValidationConstants.CITY_NOT_CORRECT_OR_NOT_SUPPORTED_MSG, cityName));
        }
        Country country = optCity.get().getCountry();
        String countryName = addressDto.getCountry();
        if (!country.getName().equalsIgnoreCase(countryName)) {
            throw new CustomBadInputException(String.format(ValidationConstants.COUNTRY_AND_CITY_DONT_MATCH_MSG, countryName, cityName));
        }
    }
}
