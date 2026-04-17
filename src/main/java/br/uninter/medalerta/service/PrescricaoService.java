package br.uninter.medalerta.service;

import br.uninter.medalerta.model.Medicamento;
import br.uninter.medalerta.model.Prescricao;
import br.uninter.medalerta.model.Usuario;
import br.uninter.medalerta.repository.MedicamentoRepository;
import br.uninter.medalerta.repository.PrescricaoRepository;
import br.uninter.medalerta.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescricaoService {

    private final PrescricaoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final MedicamentoRepository medicamentoRepository;

    public PrescricaoService(PrescricaoRepository repository,
                             UsuarioRepository usuarioRepository,
                             MedicamentoRepository medicamentoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    public Prescricao salvar(Prescricao prescricao) {
        return repository.save(prescricao);
    }

    public List<Prescricao> listarTodos() {
        return repository.findAll();
    }

    public Prescricao buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescrição não encontrada: " + id));
    }

    public List<Prescricao> listarPorUsuario(Integer idUsuario) {
        usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + idUsuario));
        return repository.findByUsuario_IdUsuario(idUsuario);
    }

    public List<Prescricao> listarPorMedicamento(Integer idMedicamento) {
        medicamentoRepository.findById(idMedicamento)
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado: " + idMedicamento));
        return repository.findByMedicamento_IdMedicamento(idMedicamento);
    }

    public Prescricao atualizar(Integer id, Prescricao novaPrescricao) {
        Prescricao existente = buscarPorId(id);

        Usuario usuario = usuarioRepository.findById(novaPrescricao.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + novaPrescricao.getUsuario().getIdUsuario()));
        Medicamento medicamento = medicamentoRepository.findById(novaPrescricao.getMedicamento().getIdMedicamento())
                .orElseThrow(() -> new RuntimeException("Medicamento não encontrado: " + novaPrescricao.getMedicamento().getIdMedicamento()));

        existente.setUsuario(usuario);
        existente.setMedicamento(medicamento);
        existente.setDosagemValor(novaPrescricao.getDosagemValor());
        existente.setDosagemUnidade(novaPrescricao.getDosagemUnidade());
        existente.setFrequenciaUso(novaPrescricao.getFrequenciaUso());
        existente.setFrequenciaTipo(novaPrescricao.getFrequenciaTipo());

        return repository.save(existente);
    }

    public void deletar(Integer id) {
        Prescricao prescricao = buscarPorId(id);
        repository.delete(prescricao);
    }
}
