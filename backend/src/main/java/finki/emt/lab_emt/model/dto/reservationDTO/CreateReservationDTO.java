package finki.emt.lab_emt.model.dto.reservationDTO;

import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.domain.Smestuvanje;

import java.time.LocalDateTime;
import java.util.List;

public record CreateReservationDTO(LocalDateTime startDate,LocalDateTime endDate,Integer guestNum,Long smestuvanjeID) {

    public static CreateReservationDTO toDTO(Reservation reservation){
        return new CreateReservationDTO(reservation.getStartDate(),reservation.getEndDate(),reservation.getGuestNum(),reservation.getSmestuvanje().getId());
    }

    public static List<CreateReservationDTO> toDTO(List<Reservation> reservations){
        return reservations.stream().map(CreateReservationDTO::toDTO).toList();
    }

    public Reservation toReservation(Smestuvanje smestuvanje){
        return new Reservation(startDate,endDate,guestNum,smestuvanje);
    }

}
