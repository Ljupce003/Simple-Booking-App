package finki.emt.lab_emt.service.domain.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.repository.UserRepository;
import finki.emt.lab_emt.service.domain.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import finki.emt.lab_emt.model.domain.Reservation;
import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.model.dto.smestuvanjeDTO.SmestuvanjeStatsDTO;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import finki.emt.lab_emt.model.views.SmestuvanjeByHostView;
import finki.emt.lab_emt.repository.HostRepository;
import finki.emt.lab_emt.repository.SmestuvanjeRepository;
import finki.emt.lab_emt.repository.viewRepository.SmestuvanjeByHostViewRepository;
import finki.emt.lab_emt.service.domain.ReservationService;
import finki.emt.lab_emt.service.domain.SmestuvanjeService;
import static finki.emt.lab_emt.service.domain.specifications.FieldFilterSpecification.filterContainsText;
import static finki.emt.lab_emt.service.domain.specifications.FieldFilterSpecification.filterEqualsV;

@Service
public class SmestuvanjeServiceImpl implements SmestuvanjeService {

    private final SmestuvanjeRepository smestuvanjeRepository;
    private final SmestuvanjeByHostViewRepository smestuvanjeByHostViewRepository;

    private final HostRepository hostRepository;
    private final ReservationService reservationService;
    private final UserRepository userRepository;

    public SmestuvanjeServiceImpl(SmestuvanjeRepository smestuvanjeRepository, SmestuvanjeByHostViewRepository smestuvanjeByHostViewRepository, HostRepository hostRepository, ReservationService reservationService, UserRepository userRepository) {
        this.smestuvanjeRepository = smestuvanjeRepository;
        this.smestuvanjeByHostViewRepository = smestuvanjeByHostViewRepository;
        this.hostRepository = hostRepository;
        this.reservationService = reservationService;
        this.userRepository = userRepository;
    }


    @Override
    public List<Smestuvanje> findAll() {
        return this.smestuvanjeRepository.findAll();
    }

    @Override
    public Page<Smestuvanje> findAll(Pageable pageable) {
        return this.smestuvanjeRepository.findAll(pageable);
    }

    @Override
    public List<Smestuvanje> findRented(String username) {
        return this.smestuvanjeRepository.findAllByKorisnik(username);
    }

    @Override
    public Optional<Smestuvanje> findById(Long id) {
        return this.smestuvanjeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Smestuvanje> smestuvanje = findById(id);
        if(smestuvanje.isPresent()){
            for (Reservation reservation : this.reservationService.findAllBySmestuvanjeId(id)) {
                this.reservationService.deleteById(reservation.getId());
            }
            this.smestuvanjeRepository.deleteById(id);
        }else throw new RuntimeException("Smestuvanje with id: "+id+" doesnt exist");


    }

    @Override
    public Optional<Smestuvanje> save(Smestuvanje smestuvanje) {

        if(smestuvanje == null || smestuvanje.getHost()==null || this.hostRepository.findById(smestuvanje.getHost().getId()).isEmpty() ||
        smestuvanje.getName()==null || smestuvanje.getCategory()==null ){
            return Optional.empty();
        }

//        return Optional.of(this.smestuvanjeRepository.save(new Smestuvanje(smestuvanje.getName(),
//                smestuvanje.getCategory(),
//                this.hostRepository.findById(smestuvanje.getHost().getId()).get(),
//                smestuvanje.getNumRooms())));
        return Optional.of(this.smestuvanjeRepository.save(smestuvanje));

    }

    @Override
    public List<Smestuvanje> filter(Smestuvanje smestuvanje) {
//        List<Smestuvanje> all = this.smestuvanjeRepository.findAll();
//
//        if(smestuvanje.getName()!=null){
//            all = all.stream().filter(rental -> rental.getName().toLowerCase().contains(smestuvanje.getName().toLowerCase())).toList();
//        }
//        if(smestuvanje.getHostId()!=null){
//            all = all.stream().filter(rental -> rental.getHost().getId().equals(smestuvanje.getHostId())).toList();
//        }
//        if(smestuvanje.getNumRooms()!=null){
//            all = all.stream().filter(rental -> rental.getNumRooms().equals(smestuvanje.getNumRooms())).toList();
//        }
//        if(smestuvanje.getCategory()!=null){
//            all = all.stream().filter(rental -> rental.getCategory().equals(smestuvanje.getCategory())).toList();
//        }
//        if(smestuvanje.getOccupied()!=null){
//            all = all.stream().filter(rental -> rental.getOccupied().equals(smestuvanje.getOccupied())).toList();
//        }
//        if(smestuvanje.getUsername()!=null){
//            all = all.stream().filter(rental -> rental.getKorisnik().contains(smestuvanje.getUsername()) ).toList();
//        }

        Long hostId = smestuvanje.getHost() !=null ? smestuvanje.getHost().getId() : null;


        return this.smestuvanjeRepository.findAll(Specification
                        .where(filterContainsText(Smestuvanje.class,"name",smestuvanje.getName()))
                        .and(filterContainsText(Smestuvanje.class,"korisnik",smestuvanje.getKorisnik()))
                        .and(filterEqualsV(Smestuvanje.class,"host.id",hostId))
                        .and(filterEqualsV(Smestuvanje.class,"category",smestuvanje.getCategory()))
                        .and(filterEqualsV(Smestuvanje.class,"numRooms",smestuvanje.getNumRooms()))
                        .and(filterEqualsV(Smestuvanje.class,"occupied",smestuvanje.getOccupied()))
        );
    }


    @Override
    public Optional<Smestuvanje> update(Long id, Smestuvanje smestuvanje) {
        return this.smestuvanjeRepository.findById(id).map(
                existingSmestuvanje ->{

                    if(smestuvanje.getName() !=null){
                        existingSmestuvanje.setName(smestuvanje.getName());
                    }
                    if(smestuvanje.getCategory() !=null){
                        existingSmestuvanje.setCategory(smestuvanje.getCategory());
                    }
                    if(smestuvanje.getHost()!= null && smestuvanje.getHost().getId() !=null && this.hostRepository.findById(smestuvanje.getHost().getId()).isPresent()){
                        existingSmestuvanje.setHost(this.hostRepository.findById(smestuvanje.getHost().getId()).get());
                    }
                    if(smestuvanje.getNumRooms() !=null){
                        existingSmestuvanje.setNumRooms(smestuvanje.getNumRooms());
                    }
                    if(smestuvanje.getOccupied()!=null){
                        existingSmestuvanje.setOccupied(smestuvanje.getOccupied());
                    }

                    if(smestuvanje.getKorisnik()!=null){
                        existingSmestuvanje.setKorisnik(smestuvanje.getKorisnik());
                    }

                    return this.smestuvanjeRepository.save(existingSmestuvanje);
                }
        );
    }


    @Override
    public Optional<Smestuvanje> rent(Long id, String user) {
        return this.smestuvanjeRepository.findById(id)
                .map(smestuvanje -> {

                    if(smestuvanje.getKorisnik() != null){
                        return null;
                    }

                    if(user != null){
                        smestuvanje.setKorisnik(user);
                        smestuvanje.setOccupied(true);
                    }

                    return this.smestuvanjeRepository.save(smestuvanje);
                });
    }

    @Override
    public Optional<Smestuvanje> rentOut(Long id, String userName) {

        Optional<Smestuvanje> smestuvanjeOpt = this.smestuvanjeRepository
                .findAllByKorisnik(userName)
                .stream()
                .filter(smestuvanje -> smestuvanje.getId().equals(id)).findFirst();

        if(smestuvanjeOpt.isPresent()){
            Smestuvanje smestuvanje = smestuvanjeOpt.get();
            smestuvanje.setOccupied(false);
            smestuvanje.setKorisnik(null);
            return Optional.of(this.smestuvanjeRepository.save(smestuvanje));
        }

        return Optional.empty();
    }


    @Override
    public HashMap<CategorySmestuvanje, Double> statistics() {

        HashMap<CategorySmestuvanje, Integer> mapa = new HashMap<>();

        int total = 0;
        for (Smestuvanje smestuvanje : findAll()) {
            if(smestuvanje.getOccupied()){
                if(mapa.containsKey(smestuvanje.getCategory())){
                    Integer curr = mapa.get(smestuvanje.getCategory());
                    curr++;
                    mapa.put(smestuvanje.getCategory(),curr);

                }
                else {
                    mapa.put(smestuvanje.getCategory(),1);
                }
                total++;
            }
        }

        HashMap<CategorySmestuvanje,Double> resultMap = new HashMap<>();

        for (Map.Entry<CategorySmestuvanje, Integer> entry : mapa.entrySet()) {
            resultMap.put(entry.getKey(),(entry.getValue()/(total*1.0))*100);
        }

        for (CategorySmestuvanje value : CategorySmestuvanje.values()) {
            if(!resultMap.containsKey(value)){
                resultMap.put(value,0.0);
            }
        }

        return resultMap;
    }

    @Override
    public List<SmestuvanjeStatsDTO> stats() {
        List<Object[]> stats = smestuvanjeRepository.stats();
        List<SmestuvanjeStatsDTO> statsTransformed = new ArrayList<>(stats.stream().map(objects -> new SmestuvanjeStatsDTO(CategorySmestuvanje.valueOf(objects[0].toString()), Double.valueOf(objects[1].toString()))).toList());

        for (CategorySmestuvanje value : CategorySmestuvanje.values()) {
            if(statsTransformed.stream().map(SmestuvanjeStatsDTO::category).noneMatch(cat -> cat.equals(value))){
                statsTransformed.add(new SmestuvanjeStatsDTO(value,0.0));
            }
        }
        return statsTransformed;
    }

    @Override
    public List<SmestuvanjeByHostView> getViewAll() {
        return this.smestuvanjeByHostViewRepository.findAll();
    }

    @Override
    public Optional<SmestuvanjeByHostView> getViewByHost(Long host) {
        return this.smestuvanjeByHostViewRepository.findByHostId(host);
    }



    @Override
    public void updateView() {
        this.smestuvanjeByHostViewRepository.updateMaterializedView();
    }

    @Override
    public List<Smestuvanje> fetch_w_relations() {
        return this.smestuvanjeRepository.fetchAll_w_relations();
    }

    @Override
    public Optional<Smestuvanje> fetch_w_relations_by_id(Long id) {
        return this.smestuvanjeRepository.fetchAll_w_relations_by_id(id);
    }

    @Override
    public Optional<Boolean> addReservation(Smestuvanje smestuvanje, AgencyUser user) {

        Reservation reservation = new Reservation(LocalDateTime.now(),LocalDateTime.now().plusDays(10),5,smestuvanje);

        Optional<Reservation> returnReservation = this.reservationService.save(reservation);

        if(returnReservation.isPresent()) {

            user.getTemporaryReservations().add(returnReservation.get());
            userRepository.save(user);

            return Optional.of(true);
        }

        return Optional.of(false);
    }

}
