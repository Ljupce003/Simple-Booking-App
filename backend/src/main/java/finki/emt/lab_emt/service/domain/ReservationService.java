package finki.emt.lab_emt.service.domain;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.exceptions.SmestuvanjeOccupiedException;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    List<Reservation> findAll();

    List<Reservation> findAllBySmestuvanjeId(Long SmestuvanjeId);

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);


    Optional<Reservation> update(Long id, Reservation reservation);

    List<Reservation> getUserConfirmedReservations(String username);
    List<Reservation> getUserTemporaryReservations(String username);

    Optional<Reservation> save(Reservation reservation);


}
