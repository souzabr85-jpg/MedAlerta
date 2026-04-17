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

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuarioMedicamento")
    @JoinColumn(name = "idUsuarioMedicamento")
    private Integer idUsuarioMedicamento;

    @Column(name = "horario")
    private LocalTime horario;

    @Column(name = "frequenciaValor")
    private Integer frequenciaValor;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequenciaUnidade")
    private FrequenciaUnidade frequenciaUnidade;
}
