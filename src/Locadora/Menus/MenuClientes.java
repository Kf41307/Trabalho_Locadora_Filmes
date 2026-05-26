package Locadora.Menus;

import Locadora.ClassesPrincipais.Cliente;
import Locadora.Locadora;
import Locadora.Utilitario.ValidadorInput;

import java.util.List;
import java.util.Scanner;

public class MenuClientes {

    private final Locadora locadora;
    private final Scanner scanner;

    public MenuClientes(Locadora locadora, Scanner scanner) {
        this.locadora = locadora;
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Remover cliente");
            System.out.println("3 - Atualizar telefone");
            System.out.println("4 - Buscar cliente por CPF");
            System.out.println("5 - Listar todos os clientes");
            System.out.println("6 - Listar clientes com dívida");
            System.out.println("0 - Voltar");
            opcao = ValidadorInput.lerInteiro(0, 6);

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> removerCliente();
                case 3 -> atualizarTelefone();
                case 4 -> buscarCliente();
                case 5 -> mostrarClientes();
                case 6 -> mostrarClientesComDivida();
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        String nome = ValidadorInput.inputNomeValido();
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), false);
        String telefone = ValidadorInput.inputTelefoneValido(locadora.getClientes(), locadora.getFuncionarios(), false);

        Cliente c = new Cliente(nome, cpf, telefone);

        locadora.adicionarCliente(c);
        System.out.println("Cliente \"" + c.getNome() + "\" cadastrado");
    }

    private void removerCliente() {
        if (locadora.listarClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }

        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Cliente c = locadora.buscarCliente(cpf);
        if (c == null) {
            System.out.println("Nenhum cliente com o CPF " + cpf);
            return;
        }

        locadora.removerCliente(cpf);
        System.out.println("Cliente removido.");
    }

    private void atualizarTelefone() {
        if (locadora.listarClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }

        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Cliente cliente = locadora.getClientes().buscarPorCpf(cpf);

        if (cliente == null) {
            System.out.println("Nenhum cliente com o CPF " + cpf);
            return;
        }

        System.out.println("Telefone atual: " + cliente.getTelefone());
        String novoTelefone = ValidadorInput.inputTelefoneValido(locadora.getClientes(), locadora.getFuncionarios(), false);
        locadora.atualizarTelefoneCliente(cliente, novoTelefone);
        System.out.println("Telefone atualizado.");
    }

    private void buscarCliente() {
        if (locadora.listarClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }

        System.out.print("CPF do cliente: ");
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Cliente c = locadora.buscarCliente(cpf);

        if (c == null) {
            System.out.println("Nenhum cliente com o CPF " + cpf);
        } else {
            System.out.println(c);
        }
    }

    private void mostrarClientes() {
        List<Cliente> clientes = locadora.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println("Nome: " + c.getNome() + " , CPF : " + c.getCpf() + " , Telefone: " + c.getTelefone());
        }
    }

    private void mostrarClientesComDivida() {
        List<Cliente> endividados = locadora.listarClientesComDivida();
        if (endividados.isEmpty()) {
            System.out.println("Nenhum cliente com dívida");
            return;
        }

        System.out.println("Endividados: ");
        for (Cliente c : endividados) {
            System.out.println("Nome: " + c.getNome() + " , CPF: " + c.getCpf() + " , Telefone: " + c.getTelefone() + " , Dívida: R$" + c.getValorPendente());
        }
    }
}
