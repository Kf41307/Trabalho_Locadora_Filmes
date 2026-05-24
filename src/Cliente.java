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

    @Override
    public String toString() {
        String parte1 = "\nLocações: ";
        if(locacoes.isEmpty()){
            parte1 += "Nenhuma";
        }else{
            for(Locacao l : locacoes){
                parte1 += "\n" + l;
            }
        }

        String parte2 = "\nReservas: ";
        if(reservas.isEmpty()){
            parte2 += "Nenhuma";
        }else{
            for(Reserva r : reservas){
                parte2 += "\n" + r;
            }
        }

        String parte3 = "\nBloqueado: " + (bloqueado ? "Sim" : "Não");

        String parte4 = "\nTotal de multas : " + totalMultas;

        String parte5 = "\nValor pendente: " + valorPendente;

        return super.toString() + parte1 + parte2 + parte3 + parte4 + parte5;
    }

    public double getTotalMultas() {
        return totalMultas;
    }

    public void setTotalMultas(double totalMultas) {
        this.totalMultas = totalMultas;
    }

    public double getValorPendente() {
        return valorPendente;
    }

    public void setValorPendente(double valorPendente) {
        this.valorPendente = valorPendente;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public void addLocacao(Locacao locacao) {
        if (locacao != null) {
            locacoes.add(locacao);
        }
    }

    public void removerLocacao(Locacao locacao) {
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

    public void removerReserva(Reserva reserva) {
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
