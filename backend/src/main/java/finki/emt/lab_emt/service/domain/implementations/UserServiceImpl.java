package finki.emt.lab_emt.service.domain.implementations;

import java.util.List;
import java.util.Optional;
import java.util.Iterator;

import finki.emt.lab_emt.model.exceptions.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.enums.UserRole;
import finki.emt.lab_emt.model.projections.UserProjection;
import finki.emt.lab_emt.model.projections.User_Without_Relations_Projection;
import finki.emt.lab_emt.repository.ReservationRepository;
import finki.emt.lab_emt.repository.UserRepository;
import finki.emt.lab_emt.service.domain.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationRepository reservationRepository;


    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public AgencyUser register(String username,
                               String password,
                               String repeatPassword,
                               String name,
                               String surname,
                               UserRole role) throws InvalidArgumentsException,PasswordsDoNotMatchException,UsernameAlreadyExistsException {


        if(username == null || username.isEmpty() || password == null
                || password.isEmpty()){
            throw new InvalidArgumentsException();
        }

        if(!password.equals(repeatPassword)) throw new PasswordsDoNotMatchException();

        if(this.userRepository.findByUsername(username).isPresent()) throw new UsernameAlreadyExistsException(username);

        AgencyUser user = new AgencyUser(username,passwordEncoder.encode(password),role,name,surname);

        return this.userRepository.save(user);
    }

    @Override
    public AgencyUser login(String username, String password) throws InvalidArgumentsException,InvalidUsernameOrPasswordException,UsernameNotFoundException {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new InvalidArgumentsException();
        }
        AgencyUser user;
        try {
            user = findByUsernameWithRelations(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(username);
        }

        if(passwordEncoder.matches(password,user.getPassword()))
            return user;

        else throw new InvalidUsernameOrPasswordException();
    }

    @Override
    public AgencyUser findByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public AgencyUser findByUsernameWithRelations(String username) throws UsernameNotFoundException {
        return this.userRepository.fetchByUsernameWithReservations(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<Reservation> confirmReserveAll(String username) throws UsernameNotFoundException,SmestuvanjeOccupiedException {
        //T-ODO add reserve logic

        AgencyUser user = findByUsernameWithRelations(username);

        for (Reservation temporaryReservation : user.getTemporaryReservations()) {
            temporaryReservation.getSmestuvanje().setKorisnik(user.getUsername());
            temporaryReservation.getSmestuvanje().setOccupied(true);
        }
        user.getReservedReservations().addAll(user.getTemporaryReservations());
        user.getTemporaryReservations().clear();

        this.userRepository.save(user);

        return user.getReservedReservations().stream().toList();
    }

    @Override
    public Optional<Reservation> reserve(Reservation reservation, String username) throws SmestuvanjeOccupiedException {

        // T-ODO add reservation check
        AgencyUser user = findByUsernameWithRelations(username);
        Reservation r = this.reservationRepository.save(reservation);

        checkReservationTimeSlot(r);
        user.getTemporaryReservations().add(r);
        this.userRepository.save(user);

        return Optional.of(r);

    }

    @Override
    public Optional<Boolean> confirmReserve(Long reservationId, String username) throws ReservationNonExistentToBeConfirmedException {

        AgencyUser user = findByUsernameWithRelations(username);

        Optional<Reservation> reservationInTempList = user.getTemporaryReservations().stream().filter(r -> r.getId().equals(reservationId)).findFirst();
        if(reservationInTempList.isEmpty()) throw new ReservationNonExistentToBeConfirmedException(reservationId);

        Reservation reservation = reservationInTempList.get();

        reservation.getSmestuvanje().setKorisnik(user.getUsername());
        reservation.getSmestuvanje().setOccupied(true);
        reservationRepository.save(reservation);

        user.getReservedReservations().add(reservation);
        user.getTemporaryReservations().remove(reservation);
        userRepository.save(user);

        return Optional.of(true);
    }

    @Override
    public List<UserProjection> getNameAndSurnameUserProjection() {
        return this.userRepository.getNameAndSurnameByProjection();
    }

    @Override
    public List<AgencyUser> fetchAll() {
        return this.userRepository.fetchAll_With_Reservations();
    }

    @Override
    public List<User_Without_Relations_Projection> fetchAll_Without_Relations() {
        return this.userRepository.fetchAll_Without_Reservations();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }


    private void checkReservationTimeSlot(Reservation reservation){

        for (Reservation reservation1 : reservationRepository.findAllBySmestuvanje_Id(reservation.getSmestuvanjeId())) {
            if(reservation.getStartDate().isBefore(reservation1.getStartDate()) && reservation.getEndDate().isAfter(reservation1.getEndDate())){
                throw new SmestuvanjeOccupiedException();
            }
        }

    }
}
