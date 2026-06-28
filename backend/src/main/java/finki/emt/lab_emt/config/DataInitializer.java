package finki.emt.lab_emt.config;

import finki.emt.lab_emt.model.domain.*;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.enums.UserRole;
import finki.emt.lab_emt.repository.CountryRepository;
import finki.emt.lab_emt.repository.SmestuvanjeRepository;
import finki.emt.lab_emt.repository.UserRepository;
import finki.emt.lab_emt.service.domain.HostService;
import finki.emt.lab_emt.service.domain.ReservationService;
import finki.emt.lab_emt.service.domain.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer {
    private final HostService hostService;
    private final CountryRepository countryRepository;
    private final SmestuvanjeRepository smestuvanjeRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationService reservationService;
//    private final ApplicationEventPublisher eventPublisher;

    public DataInitializer(HostService hostService,
                           CountryRepository countryRepository,
                           SmestuvanjeRepository smestuvanjeRepository,
                           UserService userService,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ReservationService reservationService) {
        this.hostService = hostService;
        this.countryRepository = countryRepository;
        this.smestuvanjeRepository = smestuvanjeRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.reservationService = reservationService;
//        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    public void init(){

        if(!countryRepository.findAll().isEmpty() ||
                !this.smestuvanjeRepository.findAll().isEmpty() ||
                !this.userRepository.findAll().isEmpty() ||
                !this.reservationService.findAll().isEmpty()){
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        Country countryNM = new Country("Macedonia","Europe");
        this.countryRepository.save(countryNM);
        this.countryRepository.save(new Country("Greece","Europe"));
        this.countryRepository.save(new Country("Canada","North America"));
        Country countryBR = new Country("Brazil","South America");
        this.countryRepository.save(countryBR);

        Host host = new Host("Peter","Griffin",countryNM);
        Optional<Host> optionalHost = this.hostService.save(host);
        if(optionalHost.isPresent()) host = optionalHost.get();
        this.hostService.save(new Host("Jovana","Jovanova",countryBR));
        Host host1 = new Host("Aleksandar","McDonald",countryBR);
        Optional<Host> optionalHost1 = this.hostService.save(host1);
        if(optionalHost1.isPresent()) host1 = optionalHost1.get();


        Smestuvanje smestuvanje1 = new Smestuvanje("Stan Centar", CategorySmestuvanje.FLAT,host,4);
        this.smestuvanjeRepository.save(smestuvanje1);
        Smestuvanje smestuvanje2=new Smestuvanje("Deluxe Room Hotel Tino", CategorySmestuvanje.ROOM,host1,1);
        this.smestuvanjeRepository.save(smestuvanje2);
        this.smestuvanjeRepository.save(new Smestuvanje("Garage in Kisela Voda", CategorySmestuvanje.ROOM,host1,2));
        this.smestuvanjeRepository.save(new Smestuvanje("Sandy Seaside Suites Nea Flogita", CategorySmestuvanje.APARTMENT,host,5));


        AgencyUser host_user = new AgencyUser("host",passwordEncoder.encode("host"), UserRole.ROLE_HOST,"EMT-Host","Host_Surname");
        AgencyUser user_user = new AgencyUser("user",passwordEncoder.encode("user"),UserRole.ROLE_USER,"EMT-User","User_Surname");

        host_user = this.userRepository.save(host_user);
        user_user = this.userRepository.save(user_user);

        Reservation hostReservation = this.reservationService
                .save(new Reservation(now.minusDays(2), now, 5, smestuvanje1))
                .orElseThrow(() -> new IllegalStateException("Failed to seed host reservation"));
        host_user.getTemporaryReservations().add(hostReservation);
        this.userRepository.save(host_user);

        Reservation userReservation = this.reservationService
                .save(new Reservation(now.minusMonths(13), now.minusMonths(3), 15, smestuvanje2))
                .orElseThrow(() -> new IllegalStateException("Failed to seed user reservation"));
        user_user.getTemporaryReservations().add(userReservation);
        this.userRepository.save(user_user);

        this.userService.confirmReserveAll(user_user.getUsername());



    }
}
