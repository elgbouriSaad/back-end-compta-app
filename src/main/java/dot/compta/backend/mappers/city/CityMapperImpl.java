package dot.compta.backend.mappers.city;

import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CityMapperImpl implements CityMapper {

    private final ModelMapper modelMapper;

    @Override
    public ResponseCityDto mapToResponseCityDto(City city) {
        String cityName = city.getName();
        String countryName = city.getCountry().getName();
        ResponseCityDto cityDto = modelMapper.map(city, ResponseCityDto.class);
        cityDto.setName(cityName);
        cityDto.setCountry(countryName);
        return cityDto;
    }

    @Override
    public City mapToCity(RequestCityDto cityDto, Country country) {
        City city = modelMapper.map(cityDto, City.class);
        city.setCountry(country);
        return city;
    }

}
