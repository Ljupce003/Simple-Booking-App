package finki.emt.lab_emt.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import finki.emt.lab_emt.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class AgencyUser implements UserDetails {

    @Id
    private String username;

    @JsonIgnore
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    private String name;
    private String surname;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;



//    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Reservation> temporaryReservations=new HashSet<>();

//    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Reservation> reservedReservations=new HashSet<>();


    public AgencyUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AgencyUser(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public AgencyUser(String username, String password, UserRole role, String name, String surname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
    }

    public AgencyUser(String username, UserRole role, String name, String surname) {
        this.username=username;
        this.role=role;
        this.name=name;
        this.surname=surname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
