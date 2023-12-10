package dot.compta.backend.services.city;

import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;

import java.util.List;

public interface CityService {

    void createCity(RequestCityDto requestCityDto);

    List<ResponseCityDto> getCities();

}
