package Locadora.Repositorios;

import Locadora.ClassesPrincipais.Cliente;
import Locadora.ClassesPrincipais.Filme;
import Locadora.ClassesPrincipais.Locacao;
import Locadora.Enums.Estado;
import Locadora.Interfaces.Salvavel;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoRepositorio implements Salvavel {

    private List<Locacao> locacoes;

    public LocacaoRepositorio() {
        locacoes = new ArrayList<>();
    }

    public void adicionar(Locacao locacao) {
        locacoes.add(locacao);
    }

    public void remover(Locacao locacao) {
        locacoes.remove(locacao);
    }

    public List<Locacao> listarTodas() {
        return new ArrayList<>(locacoes);
    }

    public List<Locacao> listarAtrasadas() {
        List<Locacao> atrasadas = new ArrayList<>();
        for (Locacao l : locacoes) {
            if (l.isAtrasada()) atrasadas.add(l);
        }
        return atrasadas;
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Dados/locacoes.txt"));) {
            for (Locacao l : listarTodas()) {
                bw.write(l.getCliente().getCpf() + ";" + l.getFilme().getNome() + ";" + l.getFilme().getId() + ";" +
                        l.getDataLocacao() + ";" + l.getDataDevolucao());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar locações");
        }
    }

    public void carregarDados(ClienteRepositorio clientes, FilmeRepositorio filmes) {
        try (BufferedReader br = new BufferedReader(new FileReader("Dados/locacoes.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");

                Cliente cliente = clientes.buscarPorCpf(partes[0]);

                Filme filme = filmes.buscarPorNomeEId(partes[1], Integer.parseInt(partes[2]));

                LocalDate dataLocacao = LocalDate.parse(partes[3]);

                LocalDate dataDevolucao = LocalDate.parse(partes[4]);

                Locacao l = new Locacao(filme, dataLocacao, dataDevolucao, cliente);

                adicionar(l);
                cliente.addLocacao(l);
                filme.setEstado(Estado.ALUGADO);
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar locações");
        }
    }
}
