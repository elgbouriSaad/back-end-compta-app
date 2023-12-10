package dot.compta.backend.mappers.country;

import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import dot.compta.backend.models.country.Country;

public interface CountryMapper {

    Country mapToCountry(RequestCountryDto requestCountryDto);

    ResponseCountryDto mapToResponseCountryDto(Country country);

}
