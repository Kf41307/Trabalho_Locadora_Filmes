package Locadora.Menus;

import Locadora.ClassesPrincipais.Funcionario;
import Locadora.Locadora;
import Locadora.Utilitario.*;

import java.util.List;
import java.util.Scanner;

public class MenuFuncionarios {
    private final Locadora locadora;
    private final Scanner scanner;

    public MenuFuncionarios(Locadora locadora, Scanner scanner) {
        this.locadora = locadora;
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n--- FUNCIONÁRIOS ---");
            System.out.println("1 - Contratar funcionário");
            System.out.println("2 - Demitir funcionário");
            System.out.println("3 - Atualizar telefone");
            System.out.println("4 - Alterar salário");
            System.out.println("5 - Listar funcionários");
            System.out.println("0 - Voltar");
            opcao = ValidadorInput.lerInteiro(0, 5);

            switch (opcao) {
                case 1 -> contratarFuncionario();
                case 2 -> demitirFuncionario();
                case 3 -> atualizarTelefone();
                case 4 -> alterarSalario();
                case 5 -> mostrarFuncionarios();
            }
        } while (opcao != 0);
    }

    private void contratarFuncionario() {
        String nome = ValidadorInput.inputNomeValido();
        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), false);
        String telefone = ValidadorInput.inputTelefoneValido(locadora.getClientes(), locadora.getFuncionarios(), false);

        double salario = ValidadorInput.inputSalarioValido();

        System.out.print("Cargo: ");
        String cargo = ValidadorInput.lerString();

        locadora.adicionarFuncionario(new Funcionario(nome, cpf, telefone, salario, cargo));
        System.out.println("Funcionário \"" + nome + "\" contratado.");
    }

    private void demitirFuncionario() {
        if (locadora.listarFuncionarios().isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado");
            return;
        }

        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Funcionario f = locadora.buscarFuncionarioPorCpf(cpf);
        if (f == null) {
            System.out.println("Nenhum funcionário cadastrado com o CPF " + cpf);
            return;
        }

        locadora.demitirFuncionario(f);
        System.out.println("Funcionáro demitido.");
    }

    private void atualizarTelefone() {
        if (locadora.listarFuncionarios().isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado");
            return;
        }

        System.out.println("Telefone para alterar: ");
        String telefone = ValidadorInput.inputTelefoneValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Funcionario f = locadora.buscarFuncionarioPorTelefone(telefone);

        if (f == null) {
            System.out.println("Nenhum funcionário cadastrado com o telefone \"" + telefone + "\".");
            return;
        }

        System.out.println("Novo telefone: ");
        String novoTelefone = ValidadorInput.inputTelefoneValido(locadora.getClientes(), locadora.getFuncionarios(), false);
        locadora.atualizarTelefoneFuncionario(f, novoTelefone);
        System.out.println("Telefone alterado");
    }

    private void alterarSalario() {
        if (locadora.listarFuncionarios().isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado");
            return;
        }

        String cpf = ValidadorInput.inputCpfValido(locadora.getClientes(), locadora.getFuncionarios(), true);
        Funcionario f = locadora.buscarFuncionarioPorCpf(cpf);
        if (f == null) {
            System.out.println("Nenhum funcionário cadastrado com o CPF " + cpf);
            return;
        }

        System.out.println("Salário atual do funcionário \"" + f.getNome() + "\": R$" + f.getSalario());

        System.out.println("Novo salário: ");
        double novoSalario = ValidadorInput.inputSalarioValido();
        locadora.alterarSalario(f, novoSalario);
    }

    private void mostrarFuncionarios() {
        List<Funcionario> funcionarios = locadora.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado");
            return;
        }

        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
    }
}