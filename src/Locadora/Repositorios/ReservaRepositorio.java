package Locadora.Repositorios;

import Locadora.ClassesPrincipais.Cliente;
import Locadora.ClassesPrincipais.Filme;
import Locadora.ClassesPrincipais.Reserva;
import Locadora.Enums.Estado;
import Locadora.Interfaces.Salvavel;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaRepositorio implements Salvavel {
    private List<Reserva> reservas;

    public ReservaRepositorio() {
        reservas = new ArrayList<>();
    }

    public void adicionar(Reserva reserva) {
        reservas.add(reserva);
    }

    public void remover(Reserva reserva) {
        reservas.remove(reserva);
    }

    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    public Reserva buscarReservaPorFilme(Filme filme) {
        for (Reserva r : reservas) {
            if (r.getFilme().equals(filme)) {
                return r;
            }
        }
        return null;
    }

    // Função para chamada na hora da inicialização do programa
    public List<Reserva> listarExpiradas() {
        List<Reserva> expiradas = new ArrayList<>();
        for (Reserva r : reservas) {
            if (r.isExpirada()) {
                expiradas.add(r);
            }
        }
        return expiradas;
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Dados/reservas.txt"));) {
            for (Reserva r : listarReservas()) {
                bw.write(r.getCliente().getCpf() + ";" + r.getFilme().getNome() + ";" + r.getFilme().getId() + ";" +
                        r.getTempoLimite());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvar reservas");
        }
    }

    public void carregarDados(ClienteRepositorio clientes, FilmeRepositorio filmes) {

        try (BufferedReader br = new BufferedReader(new FileReader("Dados/reservas.txt"))) {
            String linha;

            while ((linha = br.readLine()) != null) {

                String[] partes = linha.split(";");

                String cpf = partes[0];
                String nomeFilme = partes[1];
                int id = Integer.parseInt(partes[2]);

                LocalDateTime tempo = LocalDateTime.parse(partes[3]);

                // REMOVE RESERVA EXPIRADA
                if (tempo.isBefore(LocalDateTime.now())) {
                    continue;
                }

                Cliente cliente = clientes.buscarPorCpf(cpf);

                Filme filme = filmes.buscarPorNomeEId(nomeFilme, id);

                Reserva r = new Reserva(tempo, cliente, filme);

                adicionar(r);
                cliente.addReserva(r);
                filme.setEstado(Estado.RESERVADO);
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar reservas");
        }
    }
}