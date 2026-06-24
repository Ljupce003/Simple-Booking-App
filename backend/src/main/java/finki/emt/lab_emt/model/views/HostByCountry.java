package finki.emt.lab_emt.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("select * from public.host_by_country")
public class HostByCountry {

    @Id
    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "number_of_hosts")
    private Integer numHosts;
}
