package Locadora.ClassesPrincipais;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reserva {

    private LocalDateTime tempoLimite;
    private Cliente cliente;
    private Filme filme;

    public Reserva(LocalDateTime tempoReservado, Cliente cliente, Filme filme) {
        this.tempoLimite = tempoReservado;
        this.cliente = cliente;
        this.filme = filme;
    }

    public boolean isExpirada(){
        return LocalDateTime.now().isAfter(tempoLimite);
    }

    public LocalDateTime getTempoLimite() {
        return tempoLimite;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Filme getFilme() {
        return filme;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String dataFormatada = tempoLimite.format(formatador);

        return "Filme: \"" + filme.getNome() + "\" reservado para \"" + cliente.getNome()
                + "\" até " + dataFormatada;
    }
}
