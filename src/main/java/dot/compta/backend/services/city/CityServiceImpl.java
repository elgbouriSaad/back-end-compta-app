package dot.compta.backend.services.city;

import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import dot.compta.backend.mappers.city.CityMapper;
import dot.compta.backend.models.city.City;
import dot.compta.backend.models.country.Country;
import dot.compta.backend.repositories.city.CityRepository;
import dot.compta.backend.repositories.country.CountryRepository;
import dot.compta.backend.validators.city.CityValidator;
import dot.compta.backend.validators.country.CountryValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    private final CountryValidator countryValidator;

    private final CountryRepository countryRepository;

    private final CityValidator cityValidator;

    @Override
    public List<ResponseCityDto> getCities() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(cityMapper::mapToResponseCityDto)
                .collect(Collectors.toList());
    }

    @Override
    public void createCity(RequestCityDto requestCityDto) {
        cityValidator.validateNotExist(requestCityDto.getName());
        String countryName = requestCityDto.getCountry();
        countryValidator.validateExist(countryName);
        Optional<Country> country = countryRepository.findOneByNameIgnoreCase(countryName);
        City city = cityMapper.mapToCity(requestCityDto, country.get());
        cityRepository.save(city);
    }

}
