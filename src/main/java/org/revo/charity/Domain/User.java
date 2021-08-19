package org.revo.charity.Domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.core.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.revo.charity.Controller.View;

import javax.persistence.*;

@Entity
@Table(name = "r_user")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends SecurityUser {
    @Id
    @GeneratedValue
    @JsonView(View.BasicUser.class)
    private Long id;
    @JsonView(View.BasicUser.class)
    private String firstName;
    @JsonView(View.BasicUser.class)
    private String lastName;
    @Column(unique = true)
    @JsonView(View.BasicUser.class)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonView(View.BasicUser.class)
    private String phone;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    @JsonView(View.BasicUser.class)
    @Nullable
    private UserLocation location;

    @JsonView(View.BasicUser.class)
    @Override
    public String getUsername() {
        return this.email;
    }
}
