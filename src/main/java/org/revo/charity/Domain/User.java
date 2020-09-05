package org.revo.charity.Domain;

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
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    private Set<Phone> phones = new HashSet<>();
}
