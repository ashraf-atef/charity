package org.revo.charity.Domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
public class PackageInfo {
    @Id
    @GeneratedValue
    @JsonView(View.BasicPackageInfo.class)
    private Long id;
    @CreatedDate
    @JsonView(View.BasicPackageInfo.class)
    private LocalDate createdDate = LocalDate.now();
    @LastModifiedDate
    @JsonView(View.BasicPackageInfo.class)
    private LocalDate lastModifiedDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    @JsonView(View.BasicPackageInfo.class)
    private PackageType packageType = PackageType.UNKNOWN;
    @Enumerated(EnumType.STRING)
    @JsonView(View.BasicPackageInfo.class)
    private PackageStatus packageStatus = PackageStatus.NONE;
}

