package dot.compta.backend.mappers.country;

import dot.compta.backend.dtos.country.RequestCountryDto;
import dot.compta.backend.dtos.country.ResponseCountryDto;
import dot.compta.backend.models.country.Country;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CountryMapperImpl implements CountryMapper {

    private final ModelMapper modelMapper;

    @Override
    public ResponseCountryDto mapToResponseCountryDto(Country country) {
        return modelMapper.map(country, ResponseCountryDto.class);
    }

    @Override
    public Country mapToCountry(RequestCountryDto requestCountryDto) {
        return modelMapper.map(requestCountryDto, Country.class);
    }

}
