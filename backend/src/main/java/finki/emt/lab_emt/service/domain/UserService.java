package finki.emt.lab_emt.service.domain;

import java.util.List;
import java.util.Optional;

import finki.emt.lab_emt.model.exceptions.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.enums.UserRole;
import finki.emt.lab_emt.model.projections.UserProjection;
import finki.emt.lab_emt.model.projections.User_Without_Relations_Projection;


public interface UserService extends UserDetailsService {
    AgencyUser register(String username, String password, String repeatPassword, String name, String surname, UserRole role) throws InvalidArgumentsException, PasswordsDoNotMatchException, UsernameAlreadyExistsException;

    AgencyUser login(String username, String password) throws UsernameNotFoundException, InvalidArgumentsException,InvalidUsernameOrPasswordException;

    AgencyUser findByUsername(String username) throws UsernameNotFoundException;

    AgencyUser findByUsernameWithRelations(String username) throws UsernameNotFoundException;

    List<Reservation> confirmReserveAll(String username) throws UsernameNotFoundException, SmestuvanjeOccupiedException;

    Optional<Reservation> reserve(Reservation reservation, String username) throws SmestuvanjeOccupiedException;

    Optional<Boolean> confirmReserve(Long reservation, String username) throws ReservationNonExistentToBeConfirmedException;

    List<UserProjection> getNameAndSurnameUserProjection();

    List<AgencyUser> fetchAll();

    List<User_Without_Relations_Projection> fetchAll_Without_Relations();
}
