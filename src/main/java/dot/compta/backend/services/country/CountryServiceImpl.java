package dot.compta.backend.services.country;

import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import dot.compta.backend.mappers.country.CountryMapper;
import dot.compta.backend.models.country.Country;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.validators.country.CountryValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryMapper countryMapper;

    private final CountryRepository countryRepository;

    private final CountryValidator countryValidator;

    @Override
    public List<ResponseCountryDto> getCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream()
                .map(countryMapper::mapToResponseCountryDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createCountry(RequestCountryDto requestCountryDto) {
        countryValidator.validateNotExist(requestCountryDto.getName());
        countryRepository.save(countryMapper.mapToCountry(requestCountryDto));
    }

}
