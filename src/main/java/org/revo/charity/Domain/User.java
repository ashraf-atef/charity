package org.revo.charity.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "r_user")
@Getter
@Setter
public class User extends SecurityUser{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    private Set<Phone> phones = new HashSet<>();

    @Override
    public String getUsername() {
        return this.email;
    }
}
