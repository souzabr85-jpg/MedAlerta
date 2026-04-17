package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Alerta;
import br.uninter.medalerta.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository repository;

    // LISTAR TODOS
    public List<Alerta> listarTodos() {
        return repository.findAll();
    }

    // BUSCAR POR ID (já retorna direto)
    public Alerta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta não encontrado"));
    }

    // SALVAR
    public Alerta salvar(Alerta alerta) {
        return repository.save(alerta);
    }

    public Alerta atualizar(Long id, Alerta novoAlerta) {
        Alerta existente = buscarPorId(id);

        existente.setIdHorarioMedicamento(novoAlerta.getIdHorarioMedicamento());
        existente.setTempoMinutos(novoAlerta.getTempoMinutos());
        existente.setStatusAlerta(novoAlerta.getStatusAlerta());
        existente.setAtivo(novoAlerta.getAtivo());

        return repository.save(existente);
    }

       public void deletar(Long id) {
        Alerta alerta = buscarPorId(id);
        repository.delete(alerta);
    }
}