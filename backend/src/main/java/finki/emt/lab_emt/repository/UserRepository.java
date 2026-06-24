package finki.emt.lab_emt.repository;

import finki.emt.lab_emt.model.domain.AgencyUser;
import finki.emt.lab_emt.model.projections.UserProjection;
import finki.emt.lab_emt.model.projections.User_Without_Relations_Projection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<AgencyUser,String> {

    Optional<AgencyUser> findByUsername(String username);

    @Query("select u.name as name, u.surname as surname from AgencyUser u")
    List<UserProjection> getNameAndSurnameByProjection();

    @EntityGraph(attributePaths = {"temporaryReservations", "reservedReservations"})
    @Query("select u from AgencyUser u where u.username = ?1")
    Optional<AgencyUser> fetchByUsernameWithReservations(String username);

//    @EntityGraph(
//            attributePaths = {
//                    "username",
//                    "name",
//                    "surname",
//                    "role",
//                    "isAccountNonExpired",
//                    "isAccountNonLocked",
//                    "isCredentialsNonExpired",
//                    "isEnabled"
//            })
//    @Query("select u from AgencyUser u")
//    List<AgencyUser> fetchAll();


    @EntityGraph(attributePaths = {
            "temporaryReservations",
            "temporaryReservations.smestuvanje",
            "reservedReservations",
            "reservedReservations.smestuvanje"})
    @Query("select u from AgencyUser u")
    List<AgencyUser> fetchAll_With_Reservations();


    @Query("select u.username as username,u.role as role,u.name as name,u.surname as surname from AgencyUser u")
    List<User_Without_Relations_Projection> fetchAll_Without_Reservations();


}
