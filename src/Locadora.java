import java.util.List;
import java.util.Map;

public class Locadora {

    private Map<String , Filme> filmes; // Map para ser melhor a busca
    private List<Cliente> clientes;
    private List<Reserva> reservas;
    private List<Funcionario> funcionarios;
    private List<Locacao> locacoes;




    public void adicionarFilmes(List<Filme> filmes , Filme filme){
        filmes.add(filme); // aidicionar copia caso ja exista
    }

    public void removerFilme(List<Filme> filmes , Filme filme){
        filmes.remove(filme); // Caso exista o filme com mais de uma copia entao tira uma copia
        // Caso exista o filme e so tenha uma copia usa o remove
    }
}
