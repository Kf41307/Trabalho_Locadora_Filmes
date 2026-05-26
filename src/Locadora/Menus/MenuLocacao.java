package Locadora.Menus;

import Locadora.ClassesPrincipais.Cliente;
import Locadora.ClassesPrincipais.Filme;
import Locadora.ClassesPrincipais.Locacao;
import Locadora.ClassesPrincipais.Reserva;
import Locadora.Locadora;
import Locadora.Utilitario.ValidadorInput;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuLocacao {
    private final Locadora locadora;
    private final Scanner scanner;

    public MenuLocacao(Locadora locadora, Scanner scanner) {
        this.locadora = locadora;
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n--- LOCAÇÕES ---");
            System.out.println("1 - Registrar locação");
            System.out.println("2 - Registrar devolução");
            System.out.println("3 - Renovar locação");
            System.out.println("4 - Cancelar locação");
            System.out.println("5 - Ver locações ativas");
            System.out.println("6 - Ver locações atrasadas");
            System.out.println("7 - Ver dívidas pendentes");
            System.out.println("0 - Voltar");
            opcao = ValidadorInput.lerInteiro(0, 7);

            switch (opcao) {
                case 1 -> registrarLocacao();
                case 2 -> registrarDevolucao();
                case 3 -> renovarLocacao();
                case 4 -> cancelarLocacao();
                case 5 -> mostrarLocacoes();
                case 6 -> mostrarLocacoesAtrasadas();
                case 7 -> mostrarDividasPendentes();
            }
        } while (opcao != 0);
    }

    private void registrarLocacao() {
        if (locadora.listarClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }

        System.out.print("Nome do filme: ");
        String nomeFilme = scanner.nextLine();

        Filme filme = locadora.buscarFilmeDisponivel(nomeFilme);
        if (filme == null) {
            System.out.println("Nenhum filme com o nome \"" + nomeFilme + "\" disponível para locação");
            return;
        }

        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Cliente cliente = locadora.getClientes().buscarPorCpf(cpf);

        if (cliente.isBloqueado()) {
            System.out.println("Cliente \"" + cliente.getNome() + "\" está bloqueado e não pode fazer locações.");
            return;
        }

        System.out.print("Quantidade de dias: ");
        int dias;
        LocalDate dataDevolucao;
        do {
            dias = ValidadorInput.lerInteiro(1, 14);
            dataDevolucao = LocalDate.now().plusDays(dias);

            if (dataDevolucao.getDayOfWeek() == DayOfWeek.SATURDAY || dataDevolucao.getDayOfWeek() == DayOfWeek.SUNDAY) {
                System.out.println("A data de devolução cai em um fim de semana. Escolha outro número de dias.");
            }
        } while (dataDevolucao.getDayOfWeek() == DayOfWeek.SATURDAY || dataDevolucao.getDayOfWeek() == DayOfWeek.SUNDAY);

        locadora.adicionarLocacao(cliente, nomeFilme, LocalDate.now(), dataDevolucao);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Locação registrada: \"" + filme.getNome() + "\" para \"" + cliente.getNome() + "\".");
        System.out.println("Devolução em: " + dataDevolucao.format(formatador));
        System.out.println("Valor total: R$ " + filme.getPrecoLocacao() * dias);
    }

    private void registrarDevolucao() {
        if (locadora.listarLocacoesAtivas().isEmpty()) {
            System.out.println("Nenhuma locação para devolver");
            return;
        }

        System.out.print("Cpf do cliente para devolução: ");
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Cliente cliente = locadora.getClientes().buscarPorCpf(cpf);

        if (cliente == null) {
            System.out.println("Nenhum cliente cadastrado com o CPF " + cpf);
            return;
        }

        List<Locacao> locacoesCliente = cliente.getLocacoes();
        if (locacoesCliente.isEmpty()) {
            System.out.println("Cliente não possui locações ativas.");
            return;
        }

        System.out.println("Locações ativas:");
        for (int i = 0; i < locacoesCliente.size(); i++) {
            System.out.println((i + 1) + " - " + locacoesCliente.get(i));
        }

        int escolha = ValidadorInput.lerInteiro(1, locacoesCliente.size());
        String nomeFilme = locacoesCliente.get(escolha - 1).getFilme().getNome();

        if (locadora.adicionarDevolucao(cliente, nomeFilme)) {
            System.out.println("Devolução registrada.");

            System.out.println("Valor pendente do cliente: R$ " + cliente.getValorPendente());
            System.out.print("Quanto foi pago?: ");
            double valorPago;
            do {
                valorPago = scanner.nextDouble();
                scanner.nextLine();
                if (valorPago > cliente.getValorPendente() || valorPago < 0) {
                    System.out.println("Valor inválido");
                }
            } while (valorPago > cliente.getValorPendente() || valorPago < 0);
            locadora.registrarPagamento(cliente, valorPago);
        } else {
            System.out.println("Ocorreu um erro na devolução.");
        }
    }

    private void renovarLocacao() {
        if (locadora.listarLocacoesAtivas().isEmpty()) {
            System.out.println("Nenhuma locação para devolver");
            return;
        }

        System.out.print("Cpf do cliente: ");

        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Cliente cliente = locadora.buscarCliente(cpf);

        if (cliente == null) {
            System.out.println("Nenhum cliente cadastrado com o CPF " + cpf);
            return;
        }

        List<Locacao> locacoes = cliente.getLocacoes();

        if (locacoes.isEmpty()) {
            System.out.println("Cliente não possui locações.");
            return;
        }

        System.out.println("\nLocações:");

        for (int i = 0; i < locacoes.size(); i++) {
            System.out.println((i + 1) + " - " + locacoes.get(i));
        }

        System.out.print("Escolha a locação: ");

        int escolha = ValidadorInput.lerInteiro(1, locacoes.size());

        Locacao locacao = locacoes.get(escolha - 1);

        System.out.print("Renovar por quantos dias?: ");

        LocalDate novaData;
        int dias;
        do {
            dias = ValidadorInput.lerInteiro(1, 7);
            novaData = locacao.getDataDevolucao().plusDays(dias);

            if (novaData.getDayOfWeek() == DayOfWeek.SATURDAY || novaData.getDayOfWeek() == DayOfWeek.SUNDAY) {
                System.out.println("A data de devolução cai em um fim de semana. Escolha outro número de dias.");
            }
        } while (novaData.getDayOfWeek() == DayOfWeek.SATURDAY || novaData.getDayOfWeek() == DayOfWeek.SUNDAY);

        Reserva reserva = locadora.buscarReservaFilme(locacao.getFilme());

        if (reserva != null) {

            LocalDate dataReserva = reserva.getTempoLimite().toLocalDate();

            if (!novaData.isBefore(dataReserva)) {
                System.out.println("Existe uma reserva para esse filme.");
                System.out.println("Máximo permitido: " + dataReserva.minusDays(1));
                return;
            }
        }

        locadora.renovarLocacao(locacao, dias);
        System.out.println("Locação renovada");
    }

    private void cancelarLocacao() {
        if (locadora.listarLocacoesAtivas().isEmpty()) {
            System.out.println("Nenhuma locação para devolver");
            return;
        }

        System.out.print("Cpf do cliente: ");
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);

        Cliente cliente = locadora.buscarCliente(cpf);

        if (cliente == null) {
            System.out.println("Nenhum cliente cadatrado com o CPF " + cpf);
            return;
        }

        List<Locacao> locacoes = cliente.getLocacoes();

        if (locacoes.isEmpty()) {
            System.out.println("Cliente não possui locações.");
            return;
        }

        System.out.println("\nLocações:");

        for (int i = 0; i < locacoes.size(); i++) {
            System.out.println((i + 1) + " - " + locacoes.get(i));
        }

        System.out.print("Escolha a locação: ");
        int escolha = ValidadorInput.lerInteiro(1, locacoes.size());

        Locacao locacao = locacoes.get(escolha - 1);
        if (!locacao.podeCancelar()) {
            System.out.println("O prazo de cancelamento dessa locação expirou");
            return;
        }

        locadora.cancelarLocacao(locacao);
        System.out.println("Locação cancelada");
    }

    private void mostrarLocacoes() {
        List<Locacao> locacoes = locadora.listarLocacoesAtivas();
        if (locacoes.isEmpty()) {
            System.out.println("Nenhuma locação cadastrada");
            return;
        }

        for (Locacao l : locacoes) {
            System.out.println(l);
        }
    }

    private void mostrarLocacoesAtrasadas() {
        List<Locacao> atrasadas = locadora.listarLocacoesAtrasadas();
        if (atrasadas.isEmpty()) {
            System.out.println("Nenhuma locação atrasada");
            return;
        }

        for (Locacao l : atrasadas) {
            System.out.println(l);
        }
    }

    private void mostrarDividasPendentes() {
        List<Cliente> endividados = locadora.listarClientesComDivida();
        if (endividados.isEmpty()) {
            System.out.println("Nenhum cliente possui dívidas pendentes");
            return;
        }

        for (Cliente c : endividados) {
            System.out.println("Cliente: " + c.getNome() + " - R$ " + c.getValorPendente());
        }
    }
}
