package br.uninter.medalerta.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "HorarioMedicamento")
public class HorarioMedicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHorarioMedicamento", nullable = false)
    private Integer idHorarioMedicamento;

    @ManyToMany(fetch = FetchType.LAZY)
    @MapsId("idPrescricao")
    @JoinColumn(name = "idPrescricao")
    private Integer idPrescricao;

    @Column(name = "horario")
    private LocalTime horario;
}
