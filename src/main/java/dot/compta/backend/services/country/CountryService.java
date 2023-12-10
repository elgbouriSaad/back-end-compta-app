package dot.compta.backend.services.country;

import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;

import java.util.List;

public interface CountryService {

    void createCountry(RequestCountryDto requestCountryDto);

    List<ResponseCountryDto> getCountries();

}
