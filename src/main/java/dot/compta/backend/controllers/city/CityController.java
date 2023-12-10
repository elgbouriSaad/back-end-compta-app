package dot.compta.backend.controllers.city;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.city.RequestCityDto;
import dot.compta.backend.dtos.city.ResponseCityDto;
import dot.compta.backend.services.city.CityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.CITIES_URL)
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_CITY_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_CITY_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_CITY_CONTROLLER_TAGS)
    public List<ResponseCityDto> getCities() {
        return cityService.getCities();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_CITY_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_CITY_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_CITY_CONTROLLER_TAGS)
    @Transactional
    public void createCity(@RequestBody @Validated RequestCityDto city) {
        cityService.createCity(city);
    }

}
