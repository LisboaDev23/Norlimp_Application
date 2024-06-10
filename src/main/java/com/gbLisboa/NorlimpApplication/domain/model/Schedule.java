package com.gbLisboa.NorlimpApplication.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gbLisboa.NorlimpApplication.domain.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_schedule")
public class Schedule {
    @Id
    @NotNull(groups = ValidationGroups.ScheduleId.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private List<Date> dates = new ArrayList<>();

    @Valid
    @ConvertGroup(to = ValidationGroups.UserId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Valid
    @ConvertGroup(to = ValidationGroups.PaymentId.class)
    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    @NotNull
    @JsonBackReference
    private Payment payment;

    @OneToMany(mappedBy = "schedule")
    @JsonManagedReference
    private List<Service> serviceRequest = new ArrayList<>();

}
