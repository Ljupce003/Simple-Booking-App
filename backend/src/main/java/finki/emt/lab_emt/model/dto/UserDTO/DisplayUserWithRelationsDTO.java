package finki.emt.lab_emt.model.dto.UserDTO;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.enums.UserRole;

import java.util.ArrayList;
import java.util.List;

public record DisplayUserWithRelationsDTO(
        String username,
        UserRole role,
        String name,
        String surname,
        List<Reservation> temporaryReservation,
        List<Reservation> reservedReservation
) {

    public static DisplayUserWithRelationsDTO toDTO(AgencyUser agencyUser){
        return new DisplayUserWithRelationsDTO(
                agencyUser.getUsername(),
                agencyUser.getRole(),
                agencyUser.getName(),
                agencyUser.getSurname(),
                agencyUser.getTemporaryReservations() !=null ? new ArrayList<>(agencyUser.getTemporaryReservations()) : null,
                agencyUser.getReservedReservations() !=null ? new ArrayList<>(agencyUser.getReservedReservations()) : null);
    }

    public static List<DisplayUserWithRelationsDTO> toDTOs(List<AgencyUser> agencyUsers){
        return agencyUsers.stream().map(DisplayUserWithRelationsDTO::toDTO).toList();
    }
}
