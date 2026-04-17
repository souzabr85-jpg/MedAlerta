package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescricaoRepository extends JpaRepository<Prescricao, Integer> {

    List<Prescricao> findByUsuario_IdUsuario(Integer idUsuario);

    List<Prescricao> findByMedicamento_IdMedicamento(Integer idMedicamento);
}
