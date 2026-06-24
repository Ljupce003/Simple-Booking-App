package finki.emt.lab_emt.model.domain;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Data
//@Table(indexes = @Index(name = "name_surname_index",columnList = "name,surname"))
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne
    private Country country;

    public Host() {
    }

    public Host(String name, String surname, Country country) {
        this.name = name;
        this.surname = surname;
        this.country = country;
    }

}
