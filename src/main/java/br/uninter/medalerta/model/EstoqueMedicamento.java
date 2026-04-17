package br.uninter.medalerta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "EstoqueMedicamento")
public class EstoqueMedicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEstoque")
    private Integer idEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPrescricao", nullable = false)
    private Prescricao prescricao;

    @Column(name = "quantidadeTotal", nullable = false)
    private Integer quantidadeTotal;

    @Column(name = "quantidadeAtual", nullable = false)
    private Integer quantidadeAtual;

    public EstoqueMedicamento() {
    }

    public EstoqueMedicamento(Prescricao prescricao, Integer quantidadeTotal) {
        this.prescricao = prescricao;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeAtual = quantidadeTotal;
    }

    public Integer getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Integer idEstoque) {
        this.idEstoque = idEstoque;
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Integer getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(Integer quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    public void diminuirQuantidade(Integer quantidade) {
        if (quantidadeAtual >= quantidade) {
            this.quantidadeAtual -= quantidade;
        } else {
            throw new RuntimeException("Quantidade insuficiente em estoque. Atual: " + quantidadeAtual + ", Solicitada: " + quantidade);
        }
    }

    public void adicionarQuantidade(Integer quantidade) {
        this.quantidadeAtual += quantidade;
        this.quantidadeTotal += quantidade;
    }

    public boolean temQuantidadeSuficiente(Integer quantidade) {
        return quantidadeAtual >= quantidade;
    }

    @Override
    public String toString() {
        return "EstoqueMedicamento{" +
                "idEstoque=" + idEstoque +
                ", quantidadeTotal=" + quantidadeTotal +
                ", quantidadeAtual=" + quantidadeAtual +
                '}';
    }
}
