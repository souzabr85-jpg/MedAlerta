package br.uninter.medalerta.app;

import br.uninter.medalerta.model.*;
import br.uninter.medalerta.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

@Component
public class App implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final MedicamentoService medicamentoService;
    private final PrescricaoService prescricaoService;
    private final HorarioMedicamentoService horarioMedicamentoService;
    private final AlertaService alertaService;
    private final EstoqueMedicamentoService estoqueMedicamentoService;
    private final CuidadorService cuidadorService;

    public App(
            UsuarioService usuarioService,
            MedicamentoService medicamentoService,
            PrescricaoService prescricaoService,
            HorarioMedicamentoService horarioMedicamentoService,
            AlertaService alertaService,
            EstoqueMedicamentoService estoqueMedicamentoService,
            CuidadorService cuidadorService
    ) {
        this.usuarioService = usuarioService;
        this.medicamentoService = medicamentoService;
        this.prescricaoService = prescricaoService;
        this.horarioMedicamentoService = horarioMedicamentoService;
        this.alertaService = alertaService;
        this.estoqueMedicamentoService = estoqueMedicamentoService;
        this.cuidadorService = cuidadorService;
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            exibirMenuPrincipal();
            opcao = lerInteiro(sc, "Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> menuUsuario(sc);
                    case 2 -> menuMedicamento(sc);
                    case 3 -> menuPrescricao(sc);
                    case 4 -> menuHorarioMedicamento(sc);
                    case 5 -> menuAlerta(sc);
                    case 6 -> menuEstoqueMedicamento(sc);
                    case 7 -> menuCuidador(sc);
                    case 0 -> System.out.println("Encerrando aplicação...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        sc.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n===== MEDALERTA - MENU PRINCIPAL =====");
        System.out.println("1 - CRUD Usuário");
        System.out.println("2 - CRUD Medicamento");
        System.out.println("3 - CRUD Prescrição");
        System.out.println("4 - CRUD Horário Medicamento");
        System.out.println("5 - CRUD Alerta");
        System.out.println("6 - CRUD Estoque Medicamento");
        System.out.println("7 - CRUD Cuidador");
        System.out.println("0 - Sair");
    }

    private void menuUsuario(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- CRUD USUÁRIO ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Voltar");

            opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarUsuario(sc);
                case 2 -> listarUsuarios();
                case 3 -> buscarUsuarioPorId(sc);
                case 4 -> atualizarUsuario(sc);
                case 5 -> removerUsuario(sc);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarUsuario(Scanner sc) {
        Usuario u = new Usuario();

        System.out.println("\nCadastro de Usuário");
        u.setNome(lerTexto(sc, "Nome: "));
        u.setTelefone(lerTexto(sc, "Telefone: "));
        u.setEmail(lerTexto(sc, "Email: "));
        u.setEnderecoRua(lerTexto(sc, "Rua: "));
        u.setEnderecoNumero(lerInteiroOpcional(sc, "Número (vazio para null): "));
        u.setEnderecoComplemento(lerTextoOpcional(sc, "Complemento (vazio para null): "));
        u.setEnderecoBairro(lerTextoOpcional(sc, "Bairro (vazio para null): "));
        u.setEnderecoCEP(lerTextoOpcional(sc, "CEP (vazio para null): "));
        u.setEnderecoCidade(lerTextoOpcional(sc, "Cidade (vazio para null): "));
        u.setEnderecoEstado(lerTextoOpcional(sc, "Estado (vazio para null): "));

        Usuario salvo = usuarioService.salvar(u);
        System.out.println("Usuário cadastrado com sucesso: " + salvo);
    }

    private void listarUsuarios() {
        List<Usuario> lista = usuarioService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void buscarUsuarioPorId(Scanner sc) {
        Integer id = lerInteiro(sc, "ID do usuário: ");
        Usuario usuario = usuarioService.buscarPorId(id);
        System.out.println(usuario);
    }

    private void atualizarUsuario(Scanner sc) {
        Integer id = lerInteiro(sc, "ID do usuário a atualizar: ");

        Usuario u = new Usuario();
        u.setNome(lerTexto(sc, "Novo nome: "));
        u.setTelefone(lerTexto(sc, "Novo telefone: "));
        u.setEmail(lerTexto(sc, "Novo email: "));
        u.setEnderecoRua(lerTexto(sc, "Nova rua: "));
        u.setEnderecoNumero(lerInteiroOpcional(sc, "Novo número (vazio para null): "));
        u.setEnderecoComplemento(lerTextoOpcional(sc, "Novo complemento (vazio para null): "));
        u.setEnderecoBairro(lerTextoOpcional(sc, "Novo bairro (vazio para null): "));
        u.setEnderecoCEP(lerTextoOpcional(sc, "Novo CEP (vazio para null): "));
        u.setEnderecoCidade(lerTextoOpcional(sc, "Nova cidade (vazio para null): "));
        u.setEnderecoEstado(lerTextoOpcional(sc, "Novo estado (vazio para null): "));

        Usuario atualizado = usuarioService.atualizar(id, u);
        System.out.println("Usuário atualizado: " + atualizado);
    }

    private void removerUsuario(Scanner sc) {
        Integer id = lerInteiro(sc, "ID do usuário a remover: ");
        usuarioService.deletar(id);
        System.out.println("Usuário removido com sucesso.");
    }

    private void menuMedicamento(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- CRUD MEDICAMENTO ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Voltar");

            opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarMedicamento(sc);
                case 2 -> listarMedicamentos();
                case 3 -> buscarMedicamentoPorId(sc);
                case 4 -> atualizarMedicamento(sc);
                case 5 -> removerMedicamento(sc);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarMedicamento(Scanner sc) {
        Medicamento m = new Medicamento();

        System.out.println("\nCadastro de Medicamento");
        m.setNomeComercial(lerTexto(sc, "Nome comercial: "));
        m.setNomeGenerico(lerTextoOpcional(sc, "Nome genérico (vazio para null): "));
        m.setQuantidade(lerQuantidadeTipo(sc));
        m.setFormaUso(lerTextoOpcional(sc, "Forma de uso (vazio para null): "));
        m.setObservacao(lerTextoOpcional(sc, "Observação (vazio para null): "));

        Medicamento salvo = medicamentoService.salvar(m);
        System.out.println("Medicamento cadastrado com sucesso: " + salvo);
    }

    private void listarMedicamentos() {
        List<Medicamento> lista = medicamentoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum Medicamento encontrado.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void buscarMedicamentoPorId(Scanner sc) {
        Integer id = lerInteiro(sc, "ID do Medicamento: ");
        Medicamento Medicamento = medicamentoService.buscarPorId(id);
        System.out.println(Medicamento);
    }

    private void atualizarMedicamento(Scanner sc) {
        Integer id = lerInteiro(sc, "ID do Medicamento a atualizar: ");

        Medicamento m = new Medicamento();
        m.setNomeComercial(lerTexto(sc, "Novo nome comercial: "));
        m.setNomeGenerico(lerTextoOpcional(sc, "Novo nome genérico (vazio para null): "));
        m.setQuantidade(lerQuantidadeTipo(sc));
        m.setFormaUso(lerTextoOpcional(sc, "Nova forma de uso (vazio para null): "));
        m.setObservacao(lerTextoOpcional(sc, "Nova observação (vazio para null): "));

        Medicamento atualizado = medicamentoService.atualizar(id, m);
        System.out.println("Medicamento atualizado: " + atualizado);
    }

    private void removerMedicamento(Scanner sc) {
        Integer id = lerInteiro(sc, "ID do Medicamento a remover: ");
        medicamentoService.deletar(id);
        System.out.println("Medicamento removido com sucesso.");
    }


    private Integer lerInteiro(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = sc.nextLine();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }

    private Integer lerInteiroOpcional(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        String valor = sc.nextLine().trim();
        if (valor.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Número inválido.");
        }
    }

    private String lerTexto(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        String valor = sc.nextLine();
        if (valor == null || valor.trim().isEmpty()) {
            throw new RuntimeException("Campo obrigatório.");
        }
        return valor;
    }

    private String lerTextoOpcional(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        String valor = sc.nextLine().trim();
        return valor.isEmpty() ? null : valor;
    }

    private QuantidadeTipo lerQuantidadeTipo(Scanner sc) {
        while (true) {
            System.out.print("Quantidade [UNIDADE/ML]: ");
            String valor = sc.nextLine().trim().toUpperCase();
            try {
                return QuantidadeTipo.valueOf(valor);
            } catch (Exception e) {
                System.out.println("Valor inválido. Use: UNIDADE ou ML.");
            }
        }
    }

    private StatusAlerta lerStatusAlerta(Scanner sc) {
        while (true) {
            System.out.print("Status alerta [EMITIDO/PENDENTE/CONFIRMADO]: ");
            String valor = sc.nextLine().trim().toUpperCase();
            try {
                return StatusAlerta.valueOf(valor);
            } catch (Exception e) {
                System.out.println("Valor inválido. Use: EMITIDO, PENDENTE ou CONFIRMADO.");
            }
        }
    }

    private ConfirmacaoConsumo lerConfirmacaoConsumo(Scanner sc) {
        while (true) {
            System.out.print("Confirmação consumo [SIM/NAO]: ");
            String valor = sc.nextLine().trim().toUpperCase();
            try {
                return ConfirmacaoConsumo.valueOf(valor);
            } catch (Exception e) {
                System.out.println("Valor inválido. Use: SIM ou NAO.");
            }
        }
    }

    private FrequenciaTipo lerFrequenciaTipo(Scanner sc) {
        while (true) {
            System.out.print("Frequência [HORAS/DIAS/SEMANAS/DOSE_UNICA]: ");
            String valor = sc.nextLine().trim().toUpperCase();
            try {
                return FrequenciaTipo.valueOf(valor);
            } catch (Exception e) {
                System.out.println("Valor inválido. Use: HORAS, DIAS, SEMANAS ou DOSE_UNICA.");
            }
        }
    }

    // Métodos do menu Prescrição
    private void menuPrescricao(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- CRUD PRESCRIÇÃO ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Remover");
            System.out.println("0 - Voltar");

            opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarPrescricao(sc);
                case 2 -> listarPrescricoes();
                case 3 -> buscarPrescricaoPorId(sc);
                case 4 -> atualizarPrescricao(sc);
                case 5 -> removerPrescricao(sc);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarPrescricao(Scanner sc) {
        Prescricao p = new Prescricao();

        System.out.println("\nCadastro de Prescrição");
        Integer idUsuario = lerInteiro(sc, "ID do Usuário: ");
        Integer idMedicamento = lerInteiro(sc, "ID do Medicamento: ");
        
        p.setUsuario(usuarioService.buscarPorId(idUsuario));
        p.setMedicamento(medicamentoService.buscarPorId(idMedicamento));
        p.setDosagemValor(lerInteiro(sc, "Valor da dosagem: "));
        p.setDosagemUnidade(lerTexto(sc, "Unidade da dosagem: "));
        p.setFrequenciaUso(lerInteiroOpcional(sc, "Frequência de uso (vazio para null): "));
        p.setFrequenciaTipo(lerFrequenciaTipo(sc));

        Prescricao salvo = prescricaoService.salvar(p);
        System.out.println("Prescrição cadastrada com sucesso: " + salvo);
    }

    private void listarPrescricoes() {
        List<Prescricao> lista = prescricaoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma prescrição encontrada.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void buscarPrescricaoPorId(Scanner sc) {
        Integer id = lerInteiro(sc, "ID da prescrição: ");
        Prescricao prescricao = prescricaoService.buscarPorId(id);
        System.out.println(prescricao);
    }

    private void atualizarPrescricao(Scanner sc) {
        Integer id = lerInteiro(sc, "ID da prescrição a atualizar: ");
        Prescricao existente = prescricaoService.buscarPorId(id);
        
        existente.setDosagemValor(lerInteiro(sc, "Novo valor da dosagem: "));
        existente.setDosagemUnidade(lerTexto(sc, "Nova unidade da dosagem: "));
        existente.setFrequenciaUso(lerInteiroOpcional(sc, "Nova frequência de uso (vazio para null): "));
        existente.setFrequenciaTipo(lerFrequenciaTipo(sc));

        Prescricao atualizado = prescricaoService.atualizar(id, existente);
        System.out.println("Prescrição atualizada: " + atualizado);
    }

    private void removerPrescricao(Scanner sc) {
        Integer id = lerInteiro(sc, "ID da prescrição a remover: ");
        prescricaoService.deletar(id);
        System.out.println("Prescrição removida com sucesso.");
    }

    // Métodos do menu Estoque Medicamento
    private void menuEstoqueMedicamento(Scanner sc) {
        int opcao;
        do {
            System.out.println("\n--- CRUD ESTOQUE MEDICAMENTO ---");
            System.out.println("1 - Criar estoque inicial");
            System.out.println("2 - Listar todos");
            System.out.println("3 - Buscar por prescrição");
            System.out.println("4 - Diminuir quantidade");
            System.out.println("5 - Adicionar quantidade");
            System.out.println("6 - Verificar disponibilidade");
            System.out.println("0 - Voltar");

            opcao = lerInteiro(sc, "Escolha uma opção: ");

            switch (opcao) {
                case 1 -> criarEstoqueInicial(sc);
                case 2 -> listarEstoques();
                case 3 -> buscarEstoquePorPrescricao(sc);
                case 4 -> diminuirEstoque(sc);
                case 5 -> adicionarEstoque(sc);
                case 6 -> verificarDisponibilidade(sc);
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void criarEstoqueInicial(Scanner sc) {
        System.out.println("\nCriar Estoque Inicial");
        Integer idPrescricao = lerInteiro(sc, "ID da prescrição: ");
        Integer quantidadeTotal = lerInteiro(sc, "Quantidade total: ");
        
        Prescricao prescricao = prescricaoService.buscarPorId(idPrescricao);
        EstoqueMedicamento estoque = estoqueMedicamentoService.criarEstoqueInicial(prescricao, quantidadeTotal);
        System.out.println("Estoque criado com sucesso: " + estoque);
    }

    private void listarEstoques() {
        List<EstoqueMedicamento> lista = estoqueMedicamentoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum estoque encontrado.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void buscarEstoquePorPrescricao(Scanner sc) {
        Integer idPrescricao = lerInteiro(sc, "ID da prescrição: ");
        EstoqueMedicamento estoque = estoqueMedicamentoService.buscarPorPrescricaoId(idPrescricao);
        System.out.println(estoque);
    }

    private void diminuirEstoque(Scanner sc) {
        Integer idPrescricao = lerInteiro(sc, "ID da prescrição: ");
        Integer quantidade = lerInteiro(sc, "Quantidade a diminuir: ");
        
        EstoqueMedicamento estoque = estoqueMedicamentoService.diminuirQuantidade(idPrescricao, quantidade);
        System.out.println("Estoque atualizado: " + estoque);
    }

    private void adicionarEstoque(Scanner sc) {
        Integer idPrescricao = lerInteiro(sc, "ID da prescrição: ");
        Integer quantidade = lerInteiro(sc, "Quantidade a adicionar: ");
        
        EstoqueMedicamento estoque = estoqueMedicamentoService.adicionarQuantidade(idPrescricao, quantidade);
        System.out.println("Estoque atualizado: " + estoque);
    }

    private void verificarDisponibilidade(Scanner sc) {
        Integer idPrescricao = lerInteiro(sc, "ID da prescrição: ");
        Integer quantidade = lerInteiro(sc, "Quantidade desejada: ");
        
        boolean disponivel = estoqueMedicamentoService.verificarDisponibilidade(idPrescricao, quantidade);
        System.out.println(disponivel ? "Quantidade disponível" : "Quantidade insuficiente");
    }

    // Métodos simplificados para outros menus
    private void menuHorarioMedicamento(Scanner sc) {
        System.out.println("\n--- CRUD HORÁRIO MEDICAMENTO ---");
        System.out.println("Funcionalidade em desenvolvimento.");
    }

    private void menuAlerta(Scanner sc) {
        System.out.println("\n--- CRUD ALERTA ---");
        System.out.println("Funcionalidade em desenvolvimento.");
    }

    private void menuCuidador(Scanner sc) {
        System.out.println("\n--- CRUD CUIDADOR ---");
        System.out.println("Funcionalidade em desenvolvimento.");
    }
}
