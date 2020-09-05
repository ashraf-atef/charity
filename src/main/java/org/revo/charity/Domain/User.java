package org.revo.charity.Domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "r_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
