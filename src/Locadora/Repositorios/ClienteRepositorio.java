package Locadora.Repositorios;

import Locadora.ClassesPrincipais.*;
import Locadora.Interfaces.Salvavel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ClienteRepositorio implements Salvavel {

    private Map<String, Cliente> clientes;
    private final String ARQUIVO = "Dados/clientes.txt";

    public ClienteRepositorio() {
        clientes = new HashMap<>();
    }


    public void adicionar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
    }

    public void remover(String cpf) {
        clientes.remove(cpf);
    }

    public void alterarTelefone(Cliente c, String novoTelefone) {
        c.setTelefone(novoTelefone);
    }

    public Cliente buscarPorCpf(String cpf) {
        return clientes.get(cpf);
    }

    public boolean existeCpf(String cpf) {
        return clientes.containsKey(cpf);
    }

    public boolean existeTelefone(String telefone) {
        for (Cliente c : clientes.values()) {
            if (c.getTelefone().equals(telefone)) return true;
        }
        return false;
    }

    public List<Reserva> buscarReservasPorCliente(Cliente c) {
        return c.getReservas();
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    public List<Cliente> listarEndividados() {
        List<Cliente> endividados = new ArrayList<>();

        for (Cliente c : clientes.values()) {
            if (c.getValorPendente() > 0) {
                endividados.add(c);
            }
        }
        return endividados;
    }

    @Override
    public void salvarDados() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Dados/clientes.txt"))) {
            for(Cliente c : listarTodos()){

                bw.write(c.getNome() + ";" + c.getCpf() + ";" + c.getTelefone() + ";" + c.getValorPendente() + ";" +
                        c.isBloqueado());
                bw.newLine();
            }

        } catch(IOException e){
            System.out.println("Erro ao salvar clientes");
        }
    }

    public void carregarDados() {
        try(BufferedReader br = new BufferedReader(new FileReader("Dados/clientes.txt"))) {
            String linha;

            while((linha = br.readLine()) != null){

                String[] partes = linha.split(";");

                String nome = partes[0];
                String cpf = partes[1];
                String telefone = partes[2];
                double valorPendente = Double.parseDouble(partes[3]);
                boolean bloqueado = Boolean.parseBoolean(partes[4]);

                Cliente c = new Cliente(nome, cpf, telefone);

                c.setValorPendente(valorPendente);
                c.setBloqueado(bloqueado);

                clientes.put(c.getCpf(), c);
            }

        } catch(IOException e){
            System.out.println("Erro ao carregar clientes");
        }
    }
}
