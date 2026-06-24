package finki.emt.lab_emt.model.dto.hostDTO;

import finki.emt.lab_emt.model.domain.Host;
import finki.emt.lab_emt.model.dto.countryDTO.DisplayCountryDTO;

public record DisplayHostWithRelationDTO(
        String name, String surname, DisplayCountryDTO countryDTO
) {

    public static DisplayHostWithRelationDTO toDTO(Host host){
        if(host==null) return null;
        return new DisplayHostWithRelationDTO(
                host.getName(),
                host.getSurname(),
                DisplayCountryDTO.toDTO(host.getCountry())
        );
    }
}
