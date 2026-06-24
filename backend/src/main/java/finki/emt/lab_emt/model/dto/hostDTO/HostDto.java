package finki.emt.lab_emt.model.dto.hostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class HostDto {

    private String name;

    private String Surname;

    private Long CountryId;

}
