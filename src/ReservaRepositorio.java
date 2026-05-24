import java.util.ArrayList;
import java.util.List;

public class ReservaRepositorio {
    private List<Reserva> reservas;

    public ReservaRepositorio(){
        reservas = new ArrayList<>();
    }

    public void adicionar(Reserva reserva) {
        reservas.add(reserva);
    }

    public void remover(Reserva reserva) {
        reservas.remove(reserva);
    }

    public List<Reserva> listarReservas(){
        return new ArrayList<>(reservas);
    }

    public void removerExpiradas(){
        for(Reserva r : reservas){
            if(r.isExpirada()) remover(r);
        }
    }
}
