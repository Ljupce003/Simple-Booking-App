package finki.emt.lab_emt.model.dto.smestuvanjeDTO;

import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.model.dto.hostDTO.DisplayHostWithRelationDTO;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;

public record DisplaySmestuvanjeWithRelationDTO(
        String name,
        CategorySmestuvanje category,
        DisplayHostWithRelationDTO hostDTO,
        Integer numRooms
) {

    public static DisplaySmestuvanjeWithRelationDTO toDTO(Smestuvanje smestuvanje){
        return new DisplaySmestuvanjeWithRelationDTO(
                smestuvanje.getName(),
                smestuvanje.getCategory(),
                DisplayHostWithRelationDTO.toDTO(smestuvanje.getHost()),
                smestuvanje.getNumRooms()
        );
    }
}
