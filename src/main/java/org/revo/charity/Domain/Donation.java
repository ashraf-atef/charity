package org.revo.charity.Domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.revo.charity.Controller.View;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Donation {
    @Id
    @GeneratedValue
    @JsonView(View.BasicDonation.class)
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    @JsonView(View.DetailedDonation.class)
    private User donor;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    @JsonView(View.DetailedDonation.class)
    private User beneficiary;
    @OneToOne
    @JoinColumn
    private PackageInfo packageInfo;
    @CreatedDate
    @JsonView(View.BasicDonation.class)
    private LocalDate createdDate = LocalDate.now();
    @LastModifiedDate
    @JsonView(View.BasicDonation.class)
    private LocalDate lastModifiedDate = LocalDate.now();
}
