import java.time.LocalDateTime;

public class Reserva {

    private LocalDateTime tempoReservado; // setar tempo maximo
    private Cliente cliente; // ver se cliente nao esta bloqueado
    private Filme filme;

    public Reserva(LocalDateTime tempoReservado, Cliente cliente, Filme filme) {
        this.tempoReservado = tempoReservado;
        this.cliente = cliente;
        this.filme = filme;
    }

    public boolean isExpirada(){
        return LocalDateTime.now().isAfter(tempoReservado);
    }

    public LocalDateTime getTempoReservado() {
        return tempoReservado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Filme getFilme() {
        return filme;
    }

    @Override
    public String toString() {
        return "Filme: \"" + filme.getNome() + "\" reservado para \"" + cliente.getNome()
                + "\" até " + tempoReservado;
    }
}
