package finki.emt.lab_emt.model.dto.UserDTO;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.enums.UserRole;

public record DisplayUserDTO(
        String username,
        UserRole role,
        String name,
        String surname
) {

    public static DisplayUserDTO fromUser(AgencyUser agencyUser){
        return new DisplayUserDTO(
                agencyUser.getUsername(),
                agencyUser.getRole(),
                agencyUser.getName(),
                agencyUser.getSurname()
        );
    }

    public AgencyUser toUser(){
        return new AgencyUser(username,role,name,surname);
    }
}
