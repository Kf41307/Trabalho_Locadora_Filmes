package Locadora.Repositorios;

import Locadora.ClassesPrincipais.Filme;
import Locadora.Enums.Estado;
import Locadora.Enums.Genero;
import Locadora.Interfaces.Salvavel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmeRepositorio implements Salvavel {

    private Map<String, List<Filme>> filmes;

    public FilmeRepositorio() {
        filmes = new HashMap<>();
    }

    public void adicionar(Filme filme) {
        String key = filme.getNome();

        if (filmes.containsKey(key)) {

            int idAnterior = filmes.get(key).getLast().getId();
            filme.setId(idAnterior + 1);

            for (Filme f : filmes.get(key)) {
                f.setCopias(filmes.get(key).size() + 1);
            }
            filme.setCopias(filmes.get(key).getFirst().getCopias());

            filmes.get(key).add(filme);

        } else {
            filme.setId(1);
            filme.setCopias(1);

            List<Filme> listaFilme = new ArrayList<>();
            listaFilme.add(filme);
            filmes.put(key, listaFilme);
        }
    }

    public Filme buscarDisponivel(String nomeFilme) {
        if (!filmes.containsKey(nomeFilme)) return null;

        for (Filme f : filmes.get(nomeFilme)) {
            if (f.getEstado() == Estado.DISPONIVEL) return f;
        }
        return null;
    }

    public boolean existe(String nomeFilme) {
        return filmes.containsKey(nomeFilme);
    }

    public void remover(String nomeFilme, int id) {
        List<Filme> lista = filmes.get(nomeFilme);

        lista.removeIf(f -> f.getId() == id);

        if (lista.isEmpty()) {
            filmes.remove(nomeFilme);
        } else {
            for (Filme f : lista) {
                f.setCopias(lista.size()); //O numero de copias
                f.setId(lista.indexOf(f) + 1);
            }
        }
    }

    public List<Filme> buscarPorNome(String nomeFilme) {
        List<Filme> filmes = new ArrayList<>();

        if (this.filmes.containsKey(nomeFilme)) {
            for (Filme f : this.filmes.get(nomeFilme)) {
                filmes.add(f);
            }
        }
        return filmes;
    }

    public Filme buscarPorNomeEId(String nome, int id) {
        List<Filme> lista = filmes.get(nome);

        if (lista == null) {
            return null;
        }

        for (Filme f : lista) {
            if (f.getId() == id) {
                return f;
            }
        }

        return null;
    }

    public List<Filme> listarTodos() {
        List<Filme> todos = new ArrayList<>();
        for (List<Filme> f : filmes.values()) {
            todos.addAll(f);
        }
        return todos;
    }

    public List<Filme> listarDisponiveis() {
        List<Filme> disponiveis = new ArrayList<>();

        for (List<Filme> lista : filmes.values()) {
            for (Filme f : lista) {
                if (f.getEstado() == Estado.DISPONIVEL) disponiveis.add(f);
            }
        }
        return disponiveis;
    }

    public List<Filme> listarAlugados() {
        List<Filme> alugados = new ArrayList<>();

        for (List<Filme> lista : filmes.values()) {
            for (Filme f : lista) {
                if (f.getEstado() == Estado.ALUGADO) alugados.add(f);
            }
        }
        return alugados;
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Dados/filmes.txt"))) {
            for (Filme f : listarTodos()) {
                StringBuilder generos = new StringBuilder();

                for (Genero g : f.getGeneros()) {
                    generos.append(g.name()).append(",");
                }

                if (!generos.isEmpty()) {
                    generos.deleteCharAt(generos.length() - 1);
                }

                bw.write(f.getNome() + ";" + f.getAnoLancamento() + ";" + f.getDuracaoMinutos() + ";" + f.getPrecoLocacao() + ";" +
                        f.getEstado().name() + ";" + f.getId() + ";" + generos);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvar filmes");
        }
    }

    public void carregarDados() {
        try (BufferedReader br = new BufferedReader(new FileReader("Dados/filmes.txt"))) {

            String linha;

            while ((linha = br.readLine()) != null) {

                String[] partes = linha.split(";");

                String nome = partes[0];
                int ano = Integer.parseInt(partes[1]);
                int duracao = Integer.parseInt(partes[2]);
                double preco = Double.parseDouble(partes[3]);
                Estado estado = Estado.valueOf(partes[4]);
                int id = Integer.parseInt(partes[5]);

                List<Genero> generos = new ArrayList<>();
                String[] listaGeneros = partes[6].split(",");
                for (String g : listaGeneros) {
                    generos.add(Genero.valueOf(g));
                }

                Filme filme = new Filme(nome, ano, duracao, generos, estado, preco, 1);
                adicionarComId(filme, id);
            }

            for (List<Filme> lista : filmes.values()) {
                for (Filme f : lista) {
                    f.setCopias(lista.size());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar filmes.");
        }
    }

    private void adicionarComId(Filme filme, int id) {
        filme.setId(id);

        String key = filme.getNome();

        if (!filmes.containsKey(key)) {
            filmes.put(key, new ArrayList<>());
        }

        filmes.get(key).add(filme);
    }

}
