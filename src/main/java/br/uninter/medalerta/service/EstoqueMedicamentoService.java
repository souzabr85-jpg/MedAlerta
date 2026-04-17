package br.uninter.medalerta.service;

import br.uninter.medalerta.model.EstoqueMedicamento;
import br.uninter.medalerta.model.Prescricao;
import br.uninter.medalerta.repository.EstoqueMedicamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstoqueMedicamentoService {

    private final EstoqueMedicamentoRepository repository;

    public EstoqueMedicamentoService(EstoqueMedicamentoRepository repository) {
        this.repository = repository;
    }

    public EstoqueMedicamento salvar(EstoqueMedicamento estoqueMedicamento) {
        return repository.save(estoqueMedicamento);
    }

    public EstoqueMedicamento criarEstoqueInicial(Prescricao prescricao, Integer quantidadeTotal) {
        Optional<EstoqueMedicamento> estoqueExistente = repository.findByPrescricao(prescricao);
        
        if (estoqueExistente.isPresent()) {
            throw new RuntimeException("Já existe um estoque cadastrado para esta prescrição");
        }

        EstoqueMedicamento novoEstoque = new EstoqueMedicamento(prescricao, quantidadeTotal);
        return repository.save(novoEstoque);
    }

    public EstoqueMedicamento buscarPorPrescricao(Prescricao prescricao) {
        return repository.findByPrescricao(prescricao)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para a prescrição: " + prescricao.getIdPrescricao()));
    }

    public EstoqueMedicamento buscarPorPrescricaoId(Integer idPrescricao) {
        return repository.findByPrescricaoIdPrescricao(idPrescricao)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para a prescrição: " + idPrescricao));
    }

    public List<EstoqueMedicamento> listarTodos() {
        return repository.findAll();
    }

    public EstoqueMedicamento atualizar(Integer idEstoque, EstoqueMedicamento novoEstoque) {
        EstoqueMedicamento existente = buscarPorId(idEstoque);

        existente.setQuantidadeTotal(novoEstoque.getQuantidadeTotal());
        existente.setQuantidadeAtual(novoEstoque.getQuantidadeAtual());

        return repository.save(existente);
    }

    public void deletar(Integer idEstoque) {
        EstoqueMedicamento estoque = buscarPorId(idEstoque);
        repository.delete(estoque);
    }

    public EstoqueMedicamento diminuirQuantidade(Integer idPrescricao, Integer quantidade) {
        EstoqueMedicamento estoque = buscarPorPrescricaoId(idPrescricao);
        
        if (!estoque.temQuantidadeSuficiente(quantidade)) {
            throw new RuntimeException("Quantidade insuficiente em estoque. Disponível: " + 
                estoque.getQuantidadeAtual() + ", Solicitada: " + quantidade);
        }
        
        estoque.diminuirQuantidade(quantidade);
        return repository.save(estoque);
    }

    public EstoqueMedicamento adicionarQuantidade(Integer idPrescricao, Integer quantidade) {
        EstoqueMedicamento estoque = buscarPorPrescricaoId(idPrescricao);
        estoque.adicionarQuantidade(quantidade);
        return repository.save(estoque);
    }

    public boolean verificarDisponibilidade(Integer idPrescricao, Integer quantidade) {
        Optional<EstoqueMedicamento> estoque = repository.findByPrescricaoIdPrescricao(idPrescricao);
        return estoque.map(e -> e.temQuantidadeSuficiente(quantidade)).orElse(false);
    }

    private EstoqueMedicamento buscarPorId(Integer idEstoque) {
        return repository.findById(idEstoque)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado: " + idEstoque));
    }
}
