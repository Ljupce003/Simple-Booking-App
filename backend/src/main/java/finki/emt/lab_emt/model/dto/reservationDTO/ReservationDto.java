package finki.emt.lab_emt.model.dto.reservationDTO;

import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.domain.Smestuvanje;

import java.time.LocalDateTime;


public record ReservationDto(LocalDateTime startDate,
                             LocalDateTime endDate,
                             Integer guestNum) {

    public Reservation toReservation(Smestuvanje smestuvanje){
        return new Reservation(startDate,endDate,guestNum,smestuvanje);
    }

}
