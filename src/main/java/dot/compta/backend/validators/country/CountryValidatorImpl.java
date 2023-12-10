package dot.compta.backend.validators.country;

import dot.compta.backend.constants.ValidationConstants;
import dot.compta.backend.exceptions.CustomBadInputException;
import dot.compta.backend.exceptions.CustomNotFoundException;
import dot.compta.backend.repositories.country.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountryValidatorImpl implements CountryValidator {

    private final CountryRepository countryRepository;

    @Override
    public void validateNotExist(String name) {
        if (countryRepository.existsByNameIgnoreCase(name)) {
            throw new CustomBadInputException(String.format(ValidationConstants.COUNTRY_ALREADY_EXISTS, name));
        }
    }

    @Override
    public void validateExist(String name) {
        if (!countryRepository.existsByNameIgnoreCase(name)) {
            throw new CustomNotFoundException(String.format(ValidationConstants.COUNTRY_NOT_EXISTS, name));
        }
    }

}
