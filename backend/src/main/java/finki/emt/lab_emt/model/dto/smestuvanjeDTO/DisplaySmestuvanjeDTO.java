package finki.emt.lab_emt.model.dto.smestuvanjeDTO;

import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.domain.Host;
import finki.emt.lab_emt.model.domain.Smestuvanje;

import java.util.List;


public record DisplaySmestuvanjeDTO(String name,
                                   CategorySmestuvanje category,
                                   Long hostID,
                                   Integer numRooms,
                                   Long id) {

    public static DisplaySmestuvanjeDTO toDTO(Smestuvanje smestuvanje){
        if(smestuvanje.getHost()!=null)
            return new DisplaySmestuvanjeDTO(smestuvanje.getName(), smestuvanje.getCategory(),
                    smestuvanje.getHost().getId(),
                    smestuvanje.getNumRooms(),smestuvanje.getId());
        else return new DisplaySmestuvanjeDTO(smestuvanje.getName(), smestuvanje.getCategory(),
                null,
                smestuvanje.getNumRooms(),smestuvanje.getId());
    }

    public static List<DisplaySmestuvanjeDTO> toDTO(List<Smestuvanje> smestuvanjaa){
        return smestuvanjaa.stream().map(DisplaySmestuvanjeDTO::toDTO).toList();
    }

    public Smestuvanje toSmestuvanje(Host host){
        return new Smestuvanje(name,category,host,numRooms);
    }

}
