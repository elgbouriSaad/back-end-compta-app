package dot.compta.backend.mappers.city;

import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;

public interface CityMapper {

    ResponseCityDto mapToResponseCityDto(City city);

    City mapToCity(RequestCityDto cityDto, Country country);
}
