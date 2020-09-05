package org.revo.charity.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Phone {
    @Id
    @GeneratedValue
    private Long id;
    private String num;
    @ManyToOne
    private User user;
}
