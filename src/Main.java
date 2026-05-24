import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {

        Locadora l = new Locadora();

        l.adicionarFilme(new Filme("Fear", 1995, 120, new ArrayList<>(List.of(Genero.ACAO, Genero.DRAMA)), Estado.DISPONIVEL, 7, 1));
        l.adicionarFilme(new Filme("Batman", 1995, 120, new ArrayList<>(List.of(Genero.SUSPENSE, Genero.DRAMA)), Estado.ALUGADO, 7, 1));

        System.out.println("Todos os filmes: \n");
        l.mostrarFilmes();

        System.out.println("\n\nFilme especifico: \n");
        l.mostrarFilme("Fear");

        System.out.println("\n\nFilmes disponiveis: \n");
        l.mostrarFilmesDisponiveis();

        System.out.println("\n\nFilmes alugados: \n");
        l.mostrarFilmesAlugados();

        l.removerFilme("Batman", 1);
        l.removerFilme("fear", 1);

        System.out.println("\n\nDepois de remover Batman: "); // Nao remove como deveria
        l.mostrarFilmes();


    }
    private static final Scanner scanner = new Scanner(System.in);
}
