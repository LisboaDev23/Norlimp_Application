package com.gbLisboa.NorlimpApplication.domain.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private List<Date> dates;

    @Valid
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_schedule_user"), nullable = false)
    private User user;

    @Valid
    @ManyToOne
    @JoinColumn(name = "payment_id", foreignKey = @ForeignKey(name = "fk_schedule_payment"), nullable = false)
    private Payment payment;

    @OneToMany
    private List<Service> serviceRequest;

}
