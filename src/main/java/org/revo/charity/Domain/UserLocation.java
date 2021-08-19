package org.revo.charity.Domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.revo.charity.Controller.View;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLocation {
    @Id
    @GeneratedValue
    @JsonView(View.BasicUserLocation.class)
    private Long id;
    @JsonView(View.BasicUserLocation.class)
    private String flatNo;
    @JsonView(View.BasicUserLocation.class)
    private String floorNo;
    @JsonView(View.BasicUserLocation.class)
    private String BuildingNo;
    @JsonView(View.BasicUserLocation.class)
    private String streetName;
    @JsonView(View.BasicUserLocation.class)
    private String landmark;

}
