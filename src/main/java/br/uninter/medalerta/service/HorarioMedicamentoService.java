package br.uninter.medalerta.service;

import br.uninter.medalerta.model.HorarioMedicamento;
import br.uninter.medalerta.repository.HorarioMedicamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class HorarioMedicamentoService {
    private final HorarioMedicamentoRepository repository;

    public HorarioMedicamentoService(HorarioMedicamentoRepository repository) {
        this.repository = repository;
    }
}
