import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() {

        Locadora l = new Locadora();

        l.adicionarFilmes(new Filme("Fear", 1995, 120, new ArrayList<>(List.of(Genero.ACAO, Genero.DRAMA)), Estado.DISPONIVEL, 7, 1));
        l.adicionarFilmes(new Filme("Batman", 1995, 120, new ArrayList<>(List.of(Genero.SUSPENSE, Genero.DRAMA)), Estado.ALUGADO, 7, 1));
        //l.mostrarFilmes();
        Cliente c = new Cliente("Augusto", "000.000.000-00", "(99) 99999-9999");

        l.adicionarLocacao(c, "Fear");
        l.mostrarLocacoesAtivas();

        //l.mostrarFilmes();

    }
}
