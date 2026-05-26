package Locadora;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import Locadora.ClassesPrincipais.*;
import Locadora.Repositorios.*;
import Locadora.Enums.*;

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

    // Parte Filmes (OK)

    public void adicionarFilme(Filme f) {
        filmes.adicionar(f);
    }

    public List<Filme> listarTodosFilmes() {
        return this.filmes.listarTodos();
    }

    public List<Filme> buscarFilmePorNome(String nome) {
        return filmes.buscarPorNome(nome);
    }

    public void removerFilme(String nomeFilme, int id) {
        filmes.remover(nomeFilme, id);
    }

    public List<Filme> listarFilmesDisponiveis() {
        return filmes.listarDisponiveis();
    }

    public List<Filme> listarFilmesAlugados() {
        return filmes.listarAlugados();
    }


    // Parte funcionarios (OK)

    public void adicionarFuncionario(Funcionario f) {
        funcionarios.adicionar(f);
    }

    public void demitirFuncionario(Funcionario funcionario) {
        funcionarios.remover(funcionario);
    }

    public void alterarSalario(Funcionario f, double novoSalario) {
        funcionarios.alterarSalario(f, novoSalario);
    }

    public List<Funcionario> listarFuncionarios() {
        return this.funcionarios.listarFuncionarios();
    }

    public Funcionario buscarFuncionarioPorCpf(String cpf) {
        return funcionarios.buscarPorCpf(cpf);
    }

    public Funcionario buscarFuncionarioPorTelefone(String telefone) {
        return funcionarios.buscarPorTelefone(telefone);
    }

    public void atualizarTelefoneFuncionario(Funcionario funcionario, String novoTelefone) { //Mostrar telefone antigo na tela antes de trocar
        funcionarios.alterarTelefone(funcionario, novoTelefone);
    }


    // Parte locações ()

    public void adicionarLocacao(Cliente cliente, String nomeFilme, LocalDate dataLocacao,LocalDate dataDevolucao) {
        Filme f = filmes.buscarDisponivel(nomeFilme);
        if(f == null) return;

        Locacao locacao = new Locacao(f, dataLocacao, dataDevolucao, cliente);

        long dias = ChronoUnit.DAYS.between(dataLocacao, dataDevolucao);

        f.setEstado(Estado.ALUGADO);
        locacoes.adicionar(locacao);
        cliente.addLocacao(locacao);
        cliente.setValorPendente(cliente.getValorPendente() + f.getPrecoLocacao() * dias);
    }

    public void cancelarLocacao(Locacao locacao){
        Filme filme = locacao.getFilme();
        filme.setEstado(Estado.DISPONIVEL);

        locacoes.remover(locacao);

        locacao.getCliente().removerLocacao(locacao);

        double valor = filme.getPrecoLocacao();

        locacao.getCliente().setValorPendente(-valor);

    }

    public void renovarLocacao(Locacao locacao, int dias){

        LocalDate novaData = locacao.getDataDevolucao().plusDays(dias);
        locacao.setDataDevolucao(novaData);
        double valorExtra = locacao.getFilme().getPrecoLocacao() * dias;
        locacao.getCliente().setValorPendente(locacao.getCliente().getValorPendente() + valorExtra);
    }

    public Filme buscarFilmeDisponivel(String nomeFilme){
        return filmes.buscarDisponivel(nomeFilme);
    }

    public List<Locacao> listarLocacoesAtivas() {
        return this.locacoes.listarTodas();
    }

    public List<Locacao> listarLocacoesAtrasadas() {
        return locacoes.listarAtrasadas();
    }

    public boolean adicionarDevolucao(Cliente cliente, String nomeFilme) {
        Locacao locacao = null;
        for (Locacao l : cliente.getLocacoes()) {
            if (l.getCliente().equals(cliente) && l.getFilme().getNome().equals(nomeFilme)) {
                locacao = l;
                break;
            }
        }

        if (locacao == null) return false;

        locacao.aplicarMulta();


        locacao.getFilme().setEstado(Estado.DISPONIVEL);
        locacoes.remover(locacao);
        cliente.removerLocacao(locacao);

        return true;
    }

    public void registrarPagamento(Cliente c, double valorPago){
        c.setValorPendente(c.getValorPendente() - valorPago);

        if(c.getValorPendente() == 0){
            c.setBloqueado(false);
        }
    }

    // Parte reservas (INCOMPLETA)

    public void adicionarReserva(Cliente cliente, Filme filme, int horasReservadas) {
        LocalDateTime horaLimite = LocalDateTime.now().plusHours(horasReservadas);
        Reserva r = new Reserva(horaLimite, cliente, filme);
        reservas.adicionar(r);
        cliente.addReserva(r);
        filme.setEstado(Estado.RESERVADO);
    }

    public List<Reserva> listarReservas() {
        return this.reservas.listarReservas();
    }

    public List<Reserva> buscarReservasCliente(Cliente cliente) {
        return clientes.buscarReservasPorCliente(cliente);
    }

    public Reserva buscarReservaFilme(Filme filme){
        return reservas.buscarReservaPorFilme(filme);
    }

    public void cancelarReserva(Reserva reserva) {
        reserva.getFilme().setEstado(Estado.DISPONIVEL);

        reservas.remover(reserva);
        reserva.getCliente().removerReserva(reserva);
    }

    public void converterReservaEmLocacao(Reserva r, int dias) {
        Cliente c = r.getCliente();
        Filme f = r.getFilme();
        LocalDate dataDevolucao = LocalDate.now().plusDays(dias);

        reservas.remover(r);
        c.removerReserva(r);

        f.setEstado(Estado.ALUGADO);
        Locacao l = new Locacao(f, LocalDate.now(),dataDevolucao, c);
        locacoes.adicionar(l);
        c.addLocacao(l);

        cancelarReserva(r);

        c.setValorPendente(f.getPrecoLocacao() * dias);
    }

    public void removerReservasExpiradas(){
        List<Reserva> expiradas = reservas.listarExpiradas();

        for(Reserva r : expiradas){
            r.getFilme().setEstado(Estado.DISPONIVEL);
            r.getCliente().removerReserva(r);
            reservas.remover(r);
        }

    }

    // Parte clientes (OK)

    public void adicionarCliente(Cliente cliente) {
        clientes.adicionar(cliente);
    }

    public void removerCliente(String cpf) {
        clientes.remover(cpf);
    }

    public void atualizarTelefoneCliente(Cliente cliente, String novoTelefone) {
        clientes.alterarTelefone(cliente, novoTelefone);
    }

    public List<Cliente> listarClientes() {
        return this.clientes.listarTodos();
    }

    public List<Cliente> listarClientesComDivida() {
        return this.clientes.listarEndividados();
    }

    public Cliente buscarCliente(String cpf) { // Poderia ser sobrecarregado com parametros telefone e cpf tambem
        return clientes.buscarPorCpf(cpf);
    }

    // Salvamento e carregamento de dados

    public void carregarSistema(){
        clientes.carregarDados();
        funcionarios.carregarDados();
        filmes.carregarDados();
        locacoes.carregarDados(clientes, filmes);
        reservas.carregarDados(clientes, filmes);

        removerReservasExpiradas();
    }

    public void salvarSistema(){
        clientes.salvarDados();
        funcionarios.salvarDados();
        filmes.salvarDados();
        reservas.salvarDados();
        locacoes.salvarDados();
    }


    //Getters

    public FilmeRepositorio getFilmes() {
        return filmes;
    }

    public ClienteRepositorio getClientes() {
        return clientes;
    }

    public ReservaRepositorio getReservas() {
        return reservas;
    }

    public FuncionarioRepositorio getFuncionarios() {
        return funcionarios;
    }

    public LocacaoRepositorio getLocacoes() {
        return locacoes;
    }
}
