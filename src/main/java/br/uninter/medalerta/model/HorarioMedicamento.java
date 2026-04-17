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
    @JoinColumn(name = "idPrescricao", nullable = false)
    private Prescricao prescricao;

    @Column(name = "horario", nullable = false)
    private LocalTime horario;

    public HorarioMedicamento() {
    }

    public HorarioMedicamento(Prescricao prescricao, LocalTime horario) {
        this.prescricao = prescricao;
        this.horario = horario;
    }

    public Integer getIdHorarioMedicamento() {
        return idHorarioMedicamento;
    }

    public void setIdHorarioMedicamento(Integer idHorarioMedicamento) {
        this.idHorarioMedicamento = idHorarioMedicamento;
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "HorarioMedicamento{" +
                "idHorarioMedicamento=" + idHorarioMedicamento +
                ", prescricao=" + (prescricao != null ? prescricao.getIdPrescricao() : null) +
                ", horario=" + horario +
                '}';
    }
}
