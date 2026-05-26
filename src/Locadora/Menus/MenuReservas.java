package Locadora.Menus;

import Locadora.ClassesPrincipais.Cliente;
import Locadora.ClassesPrincipais.Filme;
import Locadora.ClassesPrincipais.Reserva;
import Locadora.Enums.Estado;
import Locadora.Locadora;
import Locadora.Utilitario.ValidadorInput;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuReservas {
    private final Locadora locadora;
    private final Scanner scanner;

    public MenuReservas(Locadora locadora, Scanner scanner) {
        this.locadora = locadora;
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n--- RESERVAS ---");
            System.out.println("1 - Adicionar reserva");
            System.out.println("2 - Cancelar reserva");
            System.out.println("3 - Converter reserva em locação");
            System.out.println("4 - Ver reservas ativas");
            System.out.println("0 - Voltar");
            opcao = ValidadorInput.lerInteiro(0, 4);

            switch (opcao) {
                case 1 -> adicionarReserva();
                case 2 -> cancelarReserva();
                case 3 -> converterReservaLocacao();
                case 4 -> mostrarReservas();
            }
        } while (opcao != 0);
    }

    public void adicionarReserva() {
        if (locadora.listarClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }

        System.out.println("Informe o cpf do cliente para cadastrar a reserva: ");
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Cliente c = locadora.buscarCliente(cpf);
        if (c == null) {
            System.out.println("Nnehum cliente cadastrado com o CPF " + cpf);
        } else if (c.isBloqueado()) {
            System.out.println("Cliente \"" + c.getNome() + "\" está bloqueado");
        } else {
            System.out.print("Informe o nome do filme para reservar: ");
            String filme = scanner.nextLine();

            List<Filme> filmes = locadora.buscarFilmePorNome(filme);
            Filme filmeReserva = null;
            if (filmes.isEmpty()) {
                System.out.println("Nenhum filme com o nome \"" + filme + "\" foi encontrado");
            } else {
                filmeReserva = locadora.buscarFilmeDisponivel(filme);

                if (filmeReserva == null) {
                    System.out.println("Nenhum filme com o nome \"" + filme + "\" está disponível");
                    return;
                }

                System.out.print("Horas para reservar: ");
                int horas = ValidadorInput.lerInteiro(1, 24);

                locadora.adicionarReserva(c, filmeReserva, horas);
            }
        }
    }

    public void cancelarReserva() {
        if (locadora.listarReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        System.out.print("Cpf do cliente para cancelar a reserva: ");
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);

        Cliente c = locadora.buscarCliente(cpf);
        if (c != null) {
            List<Reserva> reservasCliente = locadora.buscarReservasCliente(c);

            if (reservasCliente.isEmpty()) {
                System.out.println("Cliente não possui reservas");
                return;
            }

            System.out.println("Reservas do cliente: ");
            for (int i = 0; i < reservasCliente.size(); i++) {
                System.out.println((i + 1) + " - " + reservasCliente.get(i));
            }

            System.out.print("Escolha a reserva para cancelar (1 - " + reservasCliente.size() + "): ");

            int escolha = ValidadorInput.lerInteiro(1, reservasCliente.size());

            Reserva reserva = reservasCliente.get(escolha - 1);
            locadora.cancelarReserva(reserva);

            System.out.println("Reserva cancelada.");
        } else {
            System.out.println("Nenhum cliente cadastrado com o cpf \"" + cpf + "\".");
        }
    }

    public void converterReservaLocacao() {
        if (locadora.listarReservas().isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
            return;
        }

        System.out.println("Cpf do cliente que fez a reserva: ");
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);

        Cliente c = locadora.buscarCliente(cpf);

        if(c == null){
            System.out.println("Nenhum cliente cadastrado com o CPF " + cpf);
            return;
        }

        List<Reserva> reservasCliente = locadora.buscarReservasCliente(c);

        if (reservasCliente.isEmpty()) {
            System.out.println("Cliente não possui reservas.");
            return;
        }

        System.out.println("\nReservas:");

        for (int i = 0; i < reservasCliente.size(); i++) {
            System.out.println((i + 1) + " - " + reservasCliente.get(i));
        }

        System.out.print("Escolha a reserva (1 - " + reservasCliente.size() + "): ");

        int escolha = ValidadorInput.lerInteiro(1, reservasCliente.size());

        Reserva reserva = reservasCliente.get(escolha - 1);

        System.out.print("Locação por quantos dias?: ");
        int dias;
        LocalDate dataDevolucao;
        do {
            dias = ValidadorInput.lerInteiro(1, 14);
            dataDevolucao = LocalDate.now().plusDays(dias);

            if (dataDevolucao.getDayOfWeek() == DayOfWeek.SATURDAY || dataDevolucao.getDayOfWeek() == DayOfWeek.SUNDAY) {
                System.out.println("A data de devolução cai em um fim de semana. Escolha outro número de dias.");
            }
        } while (dataDevolucao.getDayOfWeek() == DayOfWeek.SATURDAY || dataDevolucao.getDayOfWeek() == DayOfWeek.SUNDAY);

        System.out.println("Reserva convertida");
        locadora.converterReservaEmLocacao(reserva, dias);
    }

    public void mostrarReservas() {
        List<Reserva> reservas = locadora.listarReservas();
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada");
            return;
        }

        for (Reserva r : reservas) {
            System.out.println(r);
        }
    }

}
