import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Locadora {

    private FilmeRepositorio filmes;
    private ClienteRepositorio clientes;
    private ReservaRepositorio reservas;
    private FuncionarioRepositorio funcionarios;
    private LocacaoRepositorio locacoes;


    public Locadora() {
        this.filmes = new FilmeRepositorio();
        this.clientes = new ClienteRepositorio();
        this.reservas = new ReservaRepositorio();
        this.funcionarios = new FuncionarioRepositorio();
        this.locacoes = new LocacaoRepositorio();
    }

    public void adicionarFilme(Filme f) {
        filmes.adicionar(f);
    }

    public void mostrarFilmes() {
        List<Filme> filmes = this.filmes.listarTodos();
        for (Filme f : filmes) {
            System.out.println(f);
        }
    }

    public void mostrarFilme(String nomeFilme) {
        if (filmes.existe(nomeFilme)) {
            List<Filme> filme = filmes.buscarPorNome(nomeFilme);

            for (Filme f : filme) {
                System.out.println(f);
            }
        } else {
            System.out.println("Nenhum filme com o nome \"" + nomeFilme + "\" foi encontrado");
        }
    }

    public void removerFilme(String nomeFilme, int id) { // O id tem que ser perguntado pelo menu caso haja mais de uma copia
        if (!filmes.existe(nomeFilme)) {
            System.out.println("Nenhum filme com o nome \"" + nomeFilme + "\" foi encontrado");
            return;
        }
        filmes.remover(nomeFilme, id);
        System.out.println("Filme \"" + nomeFilme + "\" id: " + id + " , foi removido");
    }

    public void mostrarFilmesDisponiveis() {
        List<Filme> disponiveis = filmes.listarDisponiveis();
        if (disponiveis == null) {
            System.out.println("Nenhum filme disponível");
            return;
        }

        for (Filme f : disponiveis) {
            System.out.println(f);
        }

    }

    public void mostrarFilmesAlugados() {
        List<Filme> alugados = filmes.listarAlugados();
        if (alugados == null) {
            System.out.println("Nenhum filme alugado");
            return;
        }
        for (Filme f : alugados) {
            System.out.println(f);
        }
    }

    // Parte funcionarios

    public void adicionarFuncionario(Funcionario f){
        funcionarios.adicionar(f);
    }

    public void demitirFuncionario(Funcionario f){
        funcionarios.remover(f);
    }

    public void atualizarDados(Funcionario f, String novoTelefone){
        funcionarios.alterarTelefone(f, novoTelefone);
    }

    public void alterarSalario(Funcionario f, double novoSalario){
        funcionarios.alterarSalario(f, novoSalario);
    }

    public void mostrarFuncionarios(){
        List<Funcionario> funcionarios = this.funcionarios.listarFuncionarios();
        for(Funcionario f : funcionarios){
            System.out.println(f);
        }
    }


    // Parte locações:

    public void adicionarLocacao(Cliente cliente, String nomeFilme, int diasLocacao) {
        if (cliente.isBloqueado()) {
            System.out.println("Cliente \"" + cliente.getNome() + "\" está bloqueado e não pode fazer locações.");
            return;
        }

        Filme filme = filmes.buscarDisponivel(nomeFilme);
        if (filme == null) {
            System.out.println("Nenhum filme com o nome \"" + nomeFilme + "\" disponível para locação");
            return;
        }

        LocalDate dataDevolucao = LocalDate.now().plusDays(diasLocacao);
        if (dataDevolucao.getDayOfWeek() == DayOfWeek.SATURDAY
                || dataDevolucao.getDayOfWeek() == DayOfWeek.SUNDAY) {
            System.out.println("A data de devolução cai em um fim de semana. Escolha outro número de dias.");
            return;
        }

        Locacao locacao = new Locacao(filme, dataDevolucao, cliente);
        filme.setEstado(Estado.ALUGADO);
        locacoes.adicionar(locacao);
        cliente.addLocacao(locacao);
        cliente.setValorPendente(cliente.getValorPendente() + filme.getPrecoLocacao() * diasLocacao);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Locação registrada: \"" + filme.getNome() + "\" para \"" + cliente.getNome() + "\".");
        System.out.println("Devolução em: " + dataDevolucao.format(formatador));
        System.out.println("Valor total: R$ " + filme.getPrecoLocacao() * diasLocacao);
        //Adicionar uma funcção de pagamento aqui ?
    }

    public void mostrarLocacoesAtivas() {
        List<Locacao> locacoes = this.locacoes.listarTodas();
        for (Locacao l : locacoes) {
            System.out.println(l);
        }
    }

    // Parte clientes:

    public void adicionarCliente(Cliente cliente) {
        clientes.adicionar(cliente);
        System.out.println("Cliente \"" + cliente.getNome() + "\" cadastrado");
    }

    public void removerCliente(String cpf) {
        clientes.remover(cpf);
    }

    public void atualizarTelefoneCliente(Cliente cliente, String novoTelefone) { //Mostrar telefone antigo na tela antes de trocar
        clientes.atualizarTelefone(cliente, novoTelefone);
    }

    public void listarClientes() {
        List<Cliente> clientes = this.clientes.listarTodos();
        for(Cliente c : clientes){
            System.out.println(c);
        }
    }

    public void listarClientesComDivida() {
        List<Cliente> clientes = this.clientes.listarEndividados();

        for(Cliente c : clientes){
            System.out.println(c);
        }
    }

    public void imprimirCliente(String cpf) { // Poderia ser sobrecarregado com parametros telefone e cpf tambem
        Cliente c = clientes.buscarPorCpf(cpf);
        if (c == null) {
            System.out.println("Nenhum cliente com o CPF \"" + cpf + "\" foi encontrado.");
        } else {
            System.out.println(c);
        }

    }

    // Parte reservas:


}
