package finki.emt.lab_emt.model.dto.countryDTO;

import finki.emt.lab_emt.model.domain.Country;

import java.util.List;

public record DisplayCountryDTO(Long id, String name, String continent) {

    public static DisplayCountryDTO toDTO(Country country){
        if(country==null) return null;
        return new DisplayCountryDTO(country.getId(), country.getName(), country.getContinent());
    }

    public static List<DisplayCountryDTO> toDTO(List<Country> countries){
        return countries.stream().map(c -> toDTO(c)).toList();
    }



    public Country toCountry(){
        Country country =new Country(name,continent);
        country.setId(id);

        return country;
    }
}
