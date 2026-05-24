import java.util.ArrayList;
import java.util.List;

public class LocacaoRepositorio {

    private List<Locacao> locacoes;

    public LocacaoRepositorio(){
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

}
