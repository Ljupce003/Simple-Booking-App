package finki.emt.lab_emt.service.application.implementations;

import finki.emt.lab_emt.config.jwt.JwtHelper;
import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.model.dto.LoginResponseDTO;
import finki.emt.lab_emt.model.dto.UserDTO.CreateUserDTO;
import finki.emt.lab_emt.model.dto.UserDTO.DisplayUserDTO;
import finki.emt.lab_emt.model.dto.UserDTO.DisplayUserWithRelationsDTO;
import finki.emt.lab_emt.model.dto.UserDTO.LoginUserDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.CreateReservationDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTO;
import finki.emt.lab_emt.model.exceptions.*;
import finki.emt.lab_emt.model.projections.UserProjection;
import finki.emt.lab_emt.model.projections.User_Without_Relations_Projection;
import finki.emt.lab_emt.service.application.UserApplicationService;
import finki.emt.lab_emt.service.domain.ReservationService;
import finki.emt.lab_emt.service.domain.SmestuvanjeService;
import finki.emt.lab_emt.service.domain.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final SmestuvanjeService smestuvanjeService;
    private final ReservationService reservationService;


    public UserApplicationServiceImpl(UserService userService, JwtHelper jwtHelper, SmestuvanjeService smestuvanjeService, ReservationService reservationService) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.smestuvanjeService = smestuvanjeService;
        this.reservationService = reservationService;
    }

    @Override
    public Optional<DisplayUserDTO> register(CreateUserDTO createUserDto) throws InvalidUsernameOrPasswordException, PasswordsDoNotMatchException, UsernameAlreadyExistsException {
        AgencyUser agencyUser = this.userService.register(
                createUserDto.username(),
                createUserDto.password(),
                createUserDto.repeatPassword(),
                createUserDto.name(),
                createUserDto.surname(),
                createUserDto.role()
        );
        return Optional.of(DisplayUserDTO.fromUser(agencyUser));

    }

    @Override
    public Optional<LoginResponseDTO> login(LoginUserDTO loginUserDto) throws UsernameNotFoundException, InvalidArgumentsException,InvalidUsernameOrPasswordException {

        AgencyUser user;

        try {
            user = userService.login(loginUserDto.username(),loginUserDto.password());
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(loginUserDto.username());
        } catch (InvalidArgumentsException e) {
            throw new InvalidArgumentsException();
        } catch (InvalidUsernameOrPasswordException e) {
            throw new InvalidUsernameOrPasswordException();
        }

        String token = jwtHelper.generateToken(user);

        return Optional.of(new LoginResponseDTO(token));

    }

    @Override
    public Optional<DisplayUserDTO> findByUsername(String username) {
        return Optional.of(
                DisplayUserDTO
                        .fromUser(this.userService.findByUsername(username))
        );

    }

    @Override
    public Optional<List<DisplayReservationDTO>> confirmReserveAll(String username) throws UsernameNotFoundException, SmestuvanjeOccupiedException {
        return Optional.of(this.userService.confirmReserveAll(username).stream().map(DisplayReservationDTO::toDTO).toList());
    }

    @Override
    public List<UserProjection> getNameAndSurnameUserProjection() {
        return this.userService.getNameAndSurnameUserProjection();
    }

    @Override
    public List<DisplayUserWithRelationsDTO> fetchAll() {
        return DisplayUserWithRelationsDTO.toDTOs(this.userService.fetchAll());
    }

    @Override
    public List<User_Without_Relations_Projection> fetchAll_without_Relations() {
        return this.userService.fetchAll_Without_Relations();
    }

    @Override
    public Optional<DisplayReservationDTO> reserve(CreateReservationDTO reservationDTO, String username) {

        Optional<Smestuvanje> smestuvanjeOptional = smestuvanjeService.fetch_w_relations_by_id(reservationDTO.smestuvanjeID());
        if(smestuvanjeOptional.isEmpty())throw new SmestuvanjeNotFoundException(reservationDTO.smestuvanjeID());

        return this.userService
                .reserve(reservationDTO.toReservation(smestuvanjeOptional.get()), username)
                .map(DisplayReservationDTO::toDTO);
    }

    @Override
    public Optional<Boolean> confirmReserve(Long id, String username) throws ReservationNonExistentToBeConfirmedException {

        Optional<Reservation> reservationOptional = reservationService.findById(id);
        if(reservationOptional.isEmpty()) throw new ReservationNonExistentToBeConfirmedException(id);

        return this.userService.confirmReserve(id,username);
    }
}
