package finki.emt.lab_emt.web.controlers;


import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.dto.UserDTO.UserNameRequestDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.CreateReservationDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTO;
import finki.emt.lab_emt.model.dto.reservationDTO.DisplayReservationDTOwC;
import finki.emt.lab_emt.model.exceptions.SmestuvanjeOccupiedException;
import finki.emt.lab_emt.service.application.ReservationApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@Tag(name = "Reservation API", description = "Endpoints for managing reservations")
public class ReservationController {

    private final ReservationApplicationService reservationService;

    public ReservationController(ReservationApplicationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get all reservations", description = "Returns a list of all reservations in the system")
    @ApiResponse(responseCode = "200", description = "List of reservations retrieved successfully")
    @GetMapping
    public List<DisplayReservationDTO> findAll() {
        return this.reservationService.findAll();
    }

    @Operation(summary = "Find reservation by ID", description = "Returns reservation details for the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation found"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DisplayReservationDTO> findById(@PathVariable Long id) {
        return this.reservationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

//    @Operation(summary = "Create new reservation", description = "Adds a new reservation")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Reservation created successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid reservation data"),
//            @ApiResponse(responseCode = "404", description = "Some smestuvanje cannot be reserved")
//    })
//    @PostMapping("/add")
//    public ResponseEntity<DisplayReservationDTO> save(@RequestBody CreateReservationDTO countryDto) {
//        try {
//            return this.reservationService.save(countryDto)
//                    .map(ResponseEntity::ok)
//                    .orElseGet(ResponseEntity.badRequest()::build);
//        } catch (SmestuvanjeOccupiedException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }




//    @Operation(
//            summary = "Initiate a reservation (temporary)",
//            description = "Attempts to reserve a smestuvanje for given start/end dates and smestuvanje ID. " +
//                    "This creates a pending reservation that must be confirmed by the user."
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Temporary reservation created successfully"),
//            @ApiResponse(responseCode = "400", description = "Bad request (e.g., missing user or invalid data)"),
//            @ApiResponse(responseCode = "404", description = "Smestuvanje is already occupied")
//    })
//    @PostMapping("/reserve")
//    public ResponseEntity<DisplayReservationDTO> reserve(
//            @RequestBody CreateReservationDTO countryDto,
//            HttpServletRequest request) {
//
//        String username = request.getRemoteUser();
//        if (username == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        try {
//            return this.reservationService.reserve(countryDto, username)
//                    .map(ResponseEntity::ok)
//                    .orElseGet(ResponseEntity.badRequest()::build);
//        } catch (SmestuvanjeOccupiedException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }


    @PostMapping("/userReservations")
    public ResponseEntity<List<DisplayReservationDTOwC>> getUserReservations(@AuthenticationPrincipal AgencyUser user){
        return ResponseEntity.ok(reservationService.getUserReservations(user.getUsername()));

    }

    @Operation(summary = "Update reservation", description = "Updates an existing reservation by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Update failed due to invalid data"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayReservationDTO> update(
            @PathVariable Long id,
            @RequestBody CreateReservationDTO countryDto) {

        return this.reservationService.update(id, countryDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.badRequest()::build);
    }




    @Operation(summary = "Delete reservation", description = "Deletes the reservation with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        if (this.reservationService.findById(id).isPresent()) {
            this.reservationService.deleteById(id);
            return ResponseEntity.ok(Boolean.TRUE);
        }
        return ResponseEntity.notFound().build();
    }


//    @PutMapping("/confirmReserve/{id}")
//    public ResponseEntity<Boolean> confirmReserve(@PathVariable Long id,@AuthenticationPrincipal AgencyUser user) throws UserPrincipalNotFoundException {
//        return reservationService.confirmReserve(id,user)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.badRequest().build());
//
//    }




}
