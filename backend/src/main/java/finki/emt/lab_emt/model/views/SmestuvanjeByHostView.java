package finki.emt.lab_emt.model.views;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Immutable
@Subselect("select * from public.smestuvanje_stats_by_host")
public class SmestuvanjeByHostView {

    @Id
    @Column(name = "host_id")
    private Long hostId;

    @Column(name = "number_of_smestuvanje")
    private Integer numSmestuvanje;

}
