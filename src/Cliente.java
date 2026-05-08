import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {

    private double totalMultas;
    private double valorPendente;
    private List<Locacao> locacoes;
    private List<Reserva> reservas;
    private boolean bloqueado;

    public Cliente(String nome, String cpf, String telefone) {
        super(nome, cpf, telefone);
        this.totalMultas = 0.0;
        this.valorPendente = 0.0;
        this.locacoes = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.bloqueado = false;
    }

    public double getTotalMultas() {
        return totalMultas;
    }

    public void setTotalMultas(double totalMultas) {
        this.totalMultas += totalMultas;
    }

    public double getValorPendente() {
        return valorPendente;
    }

    public void setValorPendente(double valorPendente) {
        this.valorPendente += valorPendente;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public void addLocacao(Locacao locacao) {
        if (locacao != null) {
            locacoes.add(locacao);
        }
    }

    public void removeLocacao(Locacao locacao) {
        for (Locacao l : locacoes) {
            if (l.equals(locacao)) {
                locacoes.remove(l);
                break;
            }
        }
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void addReserva(Reserva reserva) {
        if (reserva != null) {
            reservas.add(reserva);
        }
    }

    public void removeReserva(Reserva reserva){
        for (Reserva r : reservas) {
            if (r.equals(reserva)) {
                reservas.remove(r);
                break;
            }
        }
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
