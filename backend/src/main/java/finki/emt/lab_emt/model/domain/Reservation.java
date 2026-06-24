package finki.emt.lab_emt.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer guestNum;


    @JsonIgnore
    @ManyToOne
    private Smestuvanje smestuvanje;

    @JsonProperty("smestuvanjeId")
    public Long getSmestuvanjeId(){
        return smestuvanje !=null ? smestuvanje.getId() : null;
    }

    public Reservation() {

    }

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, Integer guestNum) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestNum = guestNum;
    }

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, Integer guestNum, Smestuvanje smestuvanje) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestNum = guestNum;
        this.smestuvanje = smestuvanje;
    }


}
