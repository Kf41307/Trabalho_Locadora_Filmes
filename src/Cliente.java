import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {

    private double totalMultas;
    private double valorPendente;
    private List<Locacao> locacoes;
    private List<Reserva> reservas;
    private boolean bloqueado;

    public Cliente(String nome, String cpf, String telefone, double totalMultas, double valorPendente, List<Locacao> locacoes, List<Reserva> reservas, boolean bloqueado) {
        super(nome, cpf, telefone);
        this.totalMultas = totalMultas;
        this.valorPendente = valorPendente;
        this.locacoes = new ArrayList<>(locacoes);
        this.reservas = new ArrayList<>(reservas);
        this.bloqueado = bloqueado;
    }



}
