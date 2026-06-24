package finki.emt.lab_emt.model.dto.hostDTO;

import finki.emt.lab_emt.model.domain.Country;
import finki.emt.lab_emt.model.domain.Host;

import java.util.List;

public record CreateHostDTO(String name, String surname, Long countryID) {

    public static CreateHostDTO toDTO(Host host){
        return new CreateHostDTO(host.getName(), host.getSurname(), host.getCountry().getId());
    }

    public static List<CreateHostDTO> toDTO(List<Host> hosts){
        return hosts.stream().map(host -> toDTO(host)).toList();
    }

    public Host toHost(Country country){
        return new Host(name,surname,country);
    }

}
