package finki.emt.lab_emt.model.dto.smestuvanjeDTO;

import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.domain.Host;
import finki.emt.lab_emt.model.domain.Smestuvanje;

import java.util.List;

public record CreateSmestuvanjeDTO(String name,
                                   CategorySmestuvanje category,
                                   Long hostID,
                                   Integer numRooms) {

    public static CreateSmestuvanjeDTO toDTO(Smestuvanje smestuvanje){
        return new CreateSmestuvanjeDTO(smestuvanje.getName(), smestuvanje.getCategory(),
                smestuvanje.getHost().getId(),
                smestuvanje.getNumRooms());
    }

    public static List<CreateSmestuvanjeDTO> toDTO(List<Smestuvanje> smestuvanjaa){
        return smestuvanjaa.stream().map(smestuvanje -> toDTO(smestuvanje)).toList();
    }

    public Smestuvanje toSmestuvanje(Host host){
        return new Smestuvanje(name,category,host,numRooms);
    }

}
