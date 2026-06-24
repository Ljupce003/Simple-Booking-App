package finki.emt.lab_emt.model.dto.countryDTO;

import finki.emt.lab_emt.model.domain.Country;

import java.util.List;

public record CreateCountryDTO(String name, String continent) {

    public static CreateCountryDTO toDTO(Country country){
        return new CreateCountryDTO(country.getName(), country.getContinent());
    }

    public static List<CreateCountryDTO> toDTO(List<Country> countries){
        return countries.stream().map(c -> toDTO(c)).toList();
    }

    public Country toCountry(){
        return new Country(name,continent);
    }
}
