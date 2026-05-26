package Locadora.Repositorios;

import Locadora.ClassesPrincipais.Funcionario;
import Locadora.Interfaces.Salvavel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepositorio implements Salvavel {

    private List<Funcionario> funcionarios;

    public FuncionarioRepositorio() {
        funcionarios = new ArrayList<>();
    }

    public void adicionar(Funcionario f) {
        funcionarios.add((f));
    }

    public void remover(Funcionario f) {
        funcionarios.remove(f);
    }

    public void alterarTelefone(Funcionario f, String novoTelefone) { //Ja tem que estar validado o novoTelefone
        f.setTelefone(novoTelefone);
    }

    public void alterarSalario(Funcionario f, double novoSalario) {
        f.setSalario(novoSalario);
    }

    public Funcionario buscarPorCpf(String cpf) {
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) return f;
        }
        return null;
    }

    public Funcionario buscarPorTelefone(String telefone) {
        for (Funcionario f : funcionarios) {
            if (f.getTelefone().equals(telefone)) return f;
        }
        return null;
    }

    public List<Funcionario> listarFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

    public boolean existeCpf(String cpf) {
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) return true;
        }
        return false;
    }

    public boolean existeTelefone(String telefone) {
        for (Funcionario f : funcionarios) {
            if (f.getTelefone().equals(telefone)) return true;
        }
        return false;
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Dados/funcionarios.txt"))) {
            for (Funcionario f : listarFuncionarios()) {
                bw.write(f.getNome() + ";" + f.getCpf() + ";" + f.getTelefone() + ";" + f.getSalario() + ";" +
                        f.getCargo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar funcionários");
        }
    }

    public void carregarDados() {

        try (BufferedReader br = new BufferedReader(new FileReader("Dados/funcionarios.txt"))) {
            String linha;

            while((linha = br.readLine()) != null){
                String[] partes = linha.split(";");
                Funcionario f = new Funcionario(partes[0], partes[1], partes[2], Double.parseDouble(partes[3]), partes[4]);

                adicionar(f);
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionários");
        }

    }
}