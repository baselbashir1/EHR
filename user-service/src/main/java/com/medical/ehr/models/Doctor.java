package com.medical.ehr.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "clinic_id")
    private Long clinicId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
