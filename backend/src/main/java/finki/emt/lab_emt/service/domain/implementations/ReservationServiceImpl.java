package finki.emt.lab_emt.service.domain.implementations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import finki.emt.lab_emt.model.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.exceptions.InvalidArgumentsException;
import finki.emt.lab_emt.model.exceptions.SmestuvanjeOccupiedException;
import finki.emt.lab_emt.repository.ReservationRepository;
import finki.emt.lab_emt.repository.UserRepository;
import finki.emt.lab_emt.service.domain.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;


    public ReservationServiceImpl(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return this.reservationRepository.findAll();
    }

    @Override
    public List<Reservation> findAllBySmestuvanjeId(Long smestuvanjeId) {
        return this.reservationRepository.findAllBySmestuvanje_Id(smestuvanjeId);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return this.reservationRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Reservation r = findById(id).orElseThrow(InvalidArgumentsException::new);

        for (AgencyUser user : userRepository.findAll()) {
            user.getReservedReservations().remove(r);
            user.getTemporaryReservations().remove(r);
            userRepository.save(user);
        }

        this.reservationRepository.deleteById(id);
    }

//    @Override
//    public Optional<Reservation> save(Reservation reservation) {
//
//        if(reservation != null && reservation.getStartDate()!=null
//                && reservation.getEndDate()!=null
//                && reservation.getSmestuvanje() !=null
//                && reservation.getGuestNum() !=null) {
//
//            return Optional.of(this.reservationRepository.save(reservation));
//        }
//
//        return Optional.empty();
//    }




    @Override
    public Optional<Reservation> update(Long id, Reservation reservation) {

        if(reservation == null) return Optional.empty();

        return this.reservationRepository.findById(id).map(
                existingReservation -> {

                    if(reservation.getStartDate() !=null){
                        existingReservation.setStartDate(reservation.getStartDate());
                    }
                    if(reservation.getEndDate() !=null){
                        existingReservation.setEndDate(reservation.getEndDate());
                    }
                    if(reservation.getGuestNum() !=null){
                        existingReservation.setGuestNum(reservation.getGuestNum());
                    }
                    if(reservation.getSmestuvanje()!=null){
                        existingReservation.setSmestuvanje(reservation.getSmestuvanje());
                    }


                    return this.reservationRepository.save(existingReservation);
                }
        );
    }

    @Override
    public List<Reservation> getUserConfirmedReservations(String username) {
        Optional<AgencyUser> userOptional = userRepository.fetchByUsernameWithReservations(username);

        if(userOptional.isEmpty()) throw new UserNotFoundException(username);

        return userOptional.get().getReservedReservations().stream().toList();
    }

    @Override
    public List<Reservation> getUserTemporaryReservations(String username) {
        Optional<AgencyUser> userOptional = userRepository.fetchByUsernameWithReservations(username);

        if(userOptional.isEmpty()) throw new UserNotFoundException(username);

        return userOptional.get().getTemporaryReservations().stream().toList();
    }

    @Override
    public Optional<Reservation> save(Reservation reservation) {
        return Optional.of(reservationRepository.save(reservation));
    }


}
