package com.gbLisboa.NorlimpApplication.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime date;

    @NotEmpty
    private Double value;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "schedule_user_fk"), nullable = false)
    private User user;


    private Service service;

    @ManyToOne
    @JoinColumn(name = "payment_id", foreignKey = @ForeignKey(name = "schedule_payment_id"), nullable = false)
    private Payment payment;

}
