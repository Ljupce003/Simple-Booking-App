package finki.emt.lab_emt.service.application;



import java.util.List;
import java.util.Optional;

import finki.emt.lab_emt.model.dto.reservationDTO.CreateReservationDTO;
import finki.emt.lab_emt.model.exceptions.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import finki.emt.lab_emt.model.dto.LoginResponseDTO;
import finki.emt.lab_emt.model.dto.UserDTO.CreateUserDTO;
import finki.emt.lab_emt.model.dto.UserDTO.DisplayUserDTO;
import finki.emt.lab_emt.model.dto.UserDTO.DisplayUserWithRelationsDTO;
import finki.emt.lab_emt.model.dto.UserDTO.LoginUserDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTO;
import finki.emt.lab_emt.model.projections.UserProjection;
import finki.emt.lab_emt.model.projections.User_Without_Relations_Projection;

public interface UserApplicationService {

    Optional<DisplayUserDTO> register(CreateUserDTO createUserDto) throws InvalidArgumentsException, PasswordsDoNotMatchException, UsernameAlreadyExistsException;

    Optional<LoginResponseDTO> login(LoginUserDTO loginUserDto) throws InvalidArgumentsException,InvalidUsernameOrPasswordException;

    Optional<DisplayUserDTO> findByUsername(String username);

    Optional<DisplayReservationDTO> reserve(CreateReservationDTO countryDto, String username);

    Optional<List<DisplayReservationDTO>> confirmReserveAll(String username)throws UsernameNotFoundException, SmestuvanjeOccupiedException;

    Optional<Boolean> confirmReserve(Long id, String username) throws ReservationNonExistentToBeConfirmedException;

    List<UserProjection> getNameAndSurnameUserProjection();

    List<DisplayUserWithRelationsDTO> fetchAll();

    List<User_Without_Relations_Projection> fetchAll_without_Relations();
}
