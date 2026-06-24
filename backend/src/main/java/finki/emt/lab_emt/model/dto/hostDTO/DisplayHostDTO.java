package finki.emt.lab_emt.model.dto.hostDTO;

import finki.emt.lab_emt.model.domain.Country;
import finki.emt.lab_emt.model.domain.Host;

import java.util.List;


public record DisplayHostDTO(String name, String surname, Long countryID,Long id) {

    public static DisplayHostDTO toDTO(Host host){
        return new DisplayHostDTO(host.getName(), host.getSurname(), host.getCountry().getId(), host.getId());
    }

    public static List<DisplayHostDTO> toDTO(List<Host> hosts){
        return hosts.stream().map(DisplayHostDTO::toDTO).toList();
    }

    public Host toHost(Country country){
        return new Host(name,surname,country);
    }

}
