package finki.emt.lab_emt.model.dto.UserDTO;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.enums.UserRole;
import finki.emt.lab_emt.model.exceptions.PasswordsDoNotMatchException;

public record CreateUserDTO(
        String username,
        String password,
        String repeatPassword,
        String name,
        String surname,
        UserRole role
) {

    public boolean passwordMatch(){
        return this.password.equals(this.repeatPassword);
    }

    public AgencyUser toShopUser(){
        if(passwordMatch()){
            throw new PasswordsDoNotMatchException();
        }
        else {
            return new AgencyUser(username,password,role,name,surname);
        }
    }
}
