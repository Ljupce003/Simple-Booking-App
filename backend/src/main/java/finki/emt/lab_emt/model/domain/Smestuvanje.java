package finki.emt.lab_emt.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.emt.lab_emt.model.enums.CategorySmestuvanje;
import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Smestuvanje {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private CategorySmestuvanje category;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne()
    private Host host;

    private Integer numRooms;

    private String korisnik;
    private Boolean occupied = false;



    public Smestuvanje() {
    }


    public Smestuvanje(String name, CategorySmestuvanje category, Host host, Integer numRooms) {
        this.name = name;
        this.category = category;
        this.host = host;
        this.numRooms = numRooms;
    }


}
