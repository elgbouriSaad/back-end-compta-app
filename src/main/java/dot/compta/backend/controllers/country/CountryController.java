package dot.compta.backend.controllers.country;

import dot.compta.backend.constants.APIConstants;
import dot.compta.backend.constants.DocumentationConstants;
import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import dot.compta.backend.services.country.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstants.COUNTRIES_URL)
@AllArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = DocumentationConstants.DOC_COUNTRY_CONTROLLER_GET_SUM_DESC,
            description = DocumentationConstants.DOC_COUNTRY_CONTROLLER_GET_SUM_DESC,
            tags = DocumentationConstants.DOC_COUNTRY_CONTROLLER_TAGS)
    public List<ResponseCountryDto> getCountries() {
        return countryService.getCountries();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = DocumentationConstants.DOC_COUNTRY_CONTROLLER_POST_SUM_DESC,
            description = DocumentationConstants.DOC_COUNTRY_CONTROLLER_POST_SUM_DESC,
            tags = DocumentationConstants.DOC_COUNTRY_CONTROLLER_TAGS)
    @Transactional
    public void createCountry(@RequestBody @Validated RequestCountryDto country) {
        countryService.createCountry(country);
    }

}
