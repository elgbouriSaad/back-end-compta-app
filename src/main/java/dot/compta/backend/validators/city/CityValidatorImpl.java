package dot.compta.backend.validators.city;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.repositories.city.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityValidatorImpl implements CityValidator {

    private CityRepository cityRepository;

    @Override
    public void validateNotExist(String name) {
        if (cityRepository.existsByNameIgnoreCase(name)) {
            throw new CustomBadInputException(String.format(ValidationConstants.CITY_ALREADY_EXISTS, name));
        }
    }
}
