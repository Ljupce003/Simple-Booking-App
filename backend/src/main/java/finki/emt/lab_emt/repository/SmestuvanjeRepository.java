package finki.emt.lab_emt.repository;

import finki.emt.lab_emt.model.domain.Smestuvanje;
import finki.emt.lab_emt.repository.specificationRepository.JpaSpecificationRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmestuvanjeRepository extends JpaSpecificationRepository<Smestuvanje,Long> {

    List<Smestuvanje> findAllByKorisnik(String korisnik);

//    @Query("""
//            SELECT new finki.emt.lab_emt.model.dto.smestuvanjeDTO
//                        .SmestuvanjeStatsDTO(s.category, (count(s) * 1.0 / s1.total * 1.0) * 100.00)
//            FROM Smestuvanje s,(select count() as total from Smestuvanje where occupied=true) s1
//            WHERE s.occupied = true
//            GROUP BY s.category""")
    @Query(value = """
        SELECT s.category,COUNT(*) * 1.0 / s1.total * 100.0 as percentage
        FROM Smestuvanje s,(SELECT COUNT(*) as total FROM Smestuvanje s2 WHERE s2.occupied = true) as s1
        where s.occupied=true
        GROUP BY s.category,s1.total
        """,nativeQuery = true)
    List<Object[]> stats();

    @EntityGraph(attributePaths = {"smestuvanje.host","smestuvanje.host.country"})
    @Query("select s from Smestuvanje s")
    List<Smestuvanje> fetchAll_w_relations();

    @EntityGraph(attributePaths = {"host","host.country"})
    @Query("select s from Smestuvanje s where s.id = :s_id")
    Optional<Smestuvanje> fetchAll_w_relations_by_id(@Param("s_id") Long id);

}
