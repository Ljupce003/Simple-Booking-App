package finki.emt.lab_emt.model.domain;


import jakarta.persistence.*;

import lombok.Data;


@Entity
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String Continent;

    public Country() {
    }

    public Country(String name, String continent) {
        this.name = name;
        Continent = continent;
    }


}
