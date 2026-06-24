package finki.emt.lab_emt.web.controlers;

import finki.emt.lab_emt.model.domain.AgencyUser;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Endpoints for user authentication and registration")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or passwords do not match")
    })
    @PostMapping("/register")
    public ResponseEntity<DisplayUserDTO> register(@RequestBody CreateUserDTO createUserDto) {
        try {
            return this.userApplicationService
                    .register(createUserDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (InvalidUsernameOrPasswordException | PasswordsDoNotMatchException | UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "User login", description = "Authenticates a user and generates a JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginUserDTO loginUserDto) {
        try {
            return userApplicationService.login(loginUserDto)
                    .map(ResponseEntity::ok)
                    .orElseThrow(InvalidUserCredentialsException::new);
        } catch (InvalidUserCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Confirm reservations for current user",
            description = "Attempts to convert all the user's temporary reservations into actual bookings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservations confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "User not found or reservation process failed"),
            @ApiResponse(responseCode = "404", description = "One or more items were already reserved by someone else")
    })
    @PostMapping("/confirmReserve")
    public ResponseEntity<List<DisplayReservationDTO>> confirmReserveAll(@AuthenticationPrincipal AgencyUser user) {
        try {
            return this.userApplicationService
                    .confirmReserveAll(user.getUsername())
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.badRequest().build());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (SmestuvanjeOccupiedException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/confirmReserve/{id}")
    public ResponseEntity<Boolean> confirmReserve(@PathVariable Long id,@AuthenticationPrincipal AgencyUser user) throws UserPrincipalNotFoundException, ReservationNonExistentToBeConfirmedException {
        return userApplicationService.confirmReserve(id,user.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());

    }


    @Operation(
            summary = "Initiate a reservation (temporary)",
            description = "Attempts to reserve a smestuvanje for given start/end dates and smestuvanje ID. " +
                    "This creates a pending reservation that must be confirmed by the user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Temporary reservation created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request (e.g., missing user or invalid data)"),
            @ApiResponse(responseCode = "404", description = "Smestuvanje is already occupied")})
    @PostMapping("/reserve")
    public ResponseEntity<DisplayReservationDTO> reserve(
            @RequestBody CreateReservationDTO countryDto,
            HttpServletRequest request) {

        String username = request.getRemoteUser();
        if (username == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return this.userApplicationService.reserve(countryDto, username)
                    .map(ResponseEntity::ok)
                    .orElseGet(ResponseEntity.badRequest()::build);
        } catch (SmestuvanjeOccupiedException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "List user names and surnames", description = "Returns a projection containing only each user's name and surname")
    @ApiResponse(responseCode = "200", description = "User name/surname list retrieved successfully")
    @GetMapping("/names")
    public ResponseEntity<List<UserProjection>> getNameAndSurnameProjection() {
        return ResponseEntity.ok(this.userApplicationService.getNameAndSurnameUserProjection());
    }

    @Operation(summary = "Fetch all agency users", description = "Returns full user entities for all users in the system")
    @ApiResponse(responseCode = "200", description = "List of all users retrieved successfully")
    @GetMapping("/fetch")
    public ResponseEntity<List<DisplayUserWithRelationsDTO>> fetch() {
        return ResponseEntity.ok(this.userApplicationService.fetchAll());
    }


    @Operation(summary = "Fetch all agency users without their relations initially", description = "Returns full user entities for all users in the system")
    @ApiResponse(responseCode = "200", description = "List of all users retrieved successfully")
    @GetMapping("/fetch-without-relations")
    public ResponseEntity<List<User_Without_Relations_Projection>> fetchWithoutRelations() {
        return ResponseEntity.ok(this.userApplicationService.fetchAll_without_Relations());
    }

}
