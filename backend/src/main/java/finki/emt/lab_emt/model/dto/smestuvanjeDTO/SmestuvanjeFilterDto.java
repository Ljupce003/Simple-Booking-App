package finki.emt.lab_emt.model.dto.smestuvanjeDTO;

import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmestuvanjeFilterDto {

    private String name;

    private CategorySmestuvanje category;

    private Long hostId;

    private Integer numRooms;

    private Boolean occupied;

    private String username;

}
