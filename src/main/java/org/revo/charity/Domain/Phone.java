package org.revo.charity.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Phone {
    @Id
    @GeneratedValue
    private Long id;
    private String num;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private User user;
}
