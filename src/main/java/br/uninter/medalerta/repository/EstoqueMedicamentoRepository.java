package br.uninter.medalerta.repository;

import br.uninter.medalerta.model.EstoqueMedicamento;
import br.uninter.medalerta.model.Prescricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueMedicamentoRepository extends JpaRepository<EstoqueMedicamento, Integer> {
    
    Optional<EstoqueMedicamento> findByPrescricao(Prescricao prescricao);
    
    Optional<EstoqueMedicamento> findByPrescricaoIdPrescricao(Integer idPrescricao);
}
