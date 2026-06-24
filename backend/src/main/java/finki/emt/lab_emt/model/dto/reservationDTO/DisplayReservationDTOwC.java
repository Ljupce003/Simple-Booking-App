package finki.emt.lab_emt.model.dto.reservationDTO;

import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.domain.Smestuvanje;

import java.time.LocalDateTime;
import java.util.List;

public record DisplayReservationDTOwC(Long id,LocalDateTime startDate, LocalDateTime endDate, Integer guestNum, String smestuvanjeName,boolean confirmed) {

    public static DisplayReservationDTOwC toDTO(Reservation reservation,Boolean reserved){
        return new DisplayReservationDTOwC(reservation.getId(),reservation.getStartDate(),reservation.getEndDate(),reservation.getGuestNum(),reservation.getSmestuvanje().getName(),reserved);
    }

    public static List<DisplayReservationDTOwC> toDTO(List<Reservation> reservations,Boolean reserved){
        return reservations.stream().map(reservation -> toDTO(reservation,reserved)).toList();
    }

    public Reservation toReservation(Smestuvanje smestuvanje){
        return new Reservation(startDate,endDate,guestNum,smestuvanje);
    }

}
