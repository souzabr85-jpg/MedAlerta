package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.HorarioMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioMedicamentoRepository extends JpaRepository<HorarioMedicamento, Integer> {
}
