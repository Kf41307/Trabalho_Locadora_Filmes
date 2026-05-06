import java.time.LocalDateTime;

public class Reserva {

    private LocalDateTime tempoReservado; // setar tempo maximo
    private Cliente cliente; // ver se cliente nao esta bloqueado
    private Filme filme; // Ver se existe esse filme e se tem uma copia dele com estado DISPONIVEL

    public Reserva(LocalDateTime tempoReservado, Cliente cliente, Filme filme) {
        this.tempoReservado = tempoReservado;
        this.cliente = cliente;
        this.filme = filme;
    }
}
