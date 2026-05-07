import java.util.*;

public class Locadora {

    private Map<String, List<Filme>> filmes; // private Map<String , Filme> filmes;
    private List<Cliente> clientes;
    private List<Reserva> reservas;
    private List<Funcionario> funcionarios;
    private List<Locacao> locacoes;


    public Locadora() {
        this.filmes = new HashMap<>();
        this.clientes = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.locacoes = new ArrayList<>();
    }

    public void adicionarFilmes(Filme filme) {
        String key = filme.getNome();

        if (filmes.containsKey(key)) {  // adicionar copia caso já exista um filme com exatamente o mesmo nome

            int idAnterior = filmes.get(key).getLast().getId();
            filme.setId(idAnterior + 1);

            for (Filme f : filmes.get(key)) {
                f.setCopias(filmes.get(key).size() + 1);
            }
            filme.setCopias(filmes.get(key).getFirst().getCopias());

            filmes.get(key).add(filme);

        } else {
            filme.setId(1);
            filme.setCopias(1);

            List<Filme> listaFilme = new ArrayList<>();
            listaFilme.add(filme);
            filmes.put(key, listaFilme);
        }
    }

    public void mostrarFilmes() {
        for (String k : filmes.keySet()) {
            for (Filme f : filmes.get(k)) {
                System.out.println(f);
            }
        }
    }

    public void mostrarFilme(String nomeFilme) {
        if (!filmes.containsKey(nomeFilme)) {
            System.out.println("Nenhum filme com o nome \"" + nomeFilme + "\" foi encontrado");
        } else {
            for (Filme f : filmes.get(nomeFilme)) {
                System.out.println(f);
            }
        }
    }

    public void removerFilme(String nomeFilme) {
        Scanner scanner = new Scanner(System.in);

        if (!filmes.containsKey(nomeFilme)) {
            System.out.println("Nenhum filme com o nome \"" + nomeFilme + "\" foi encontrado");
        } else if (filmes.get(nomeFilme).size() == 1) {
            filmes.remove(nomeFilme);
            System.out.println("Filme \"" + nomeFilme + "\" removido");
        } else {
            System.out.println("Filmes com o nome \"" + nomeFilme + "\" :");
            mostrarFilme(nomeFilme);

            System.out.print("Informe o id para remover: "); //Validar id sendo o menor o primeiro e maior o ultimo da lista
            int idRemove = scanner.nextInt();
            List<Filme> lista = filmes.get(nomeFilme);

            for (Filme f : lista) {
                if (f.getId() == idRemove) {
                    lista.remove(f);
                    break;
                }
            }
            // Atualizando o numero de copias e o numero dos ids dos filmes que restaram
            for (Filme f : lista) {
                f.setCopias(lista.size()); //O numero de copias
                f.setId(lista.indexOf(f) + 1);
            }
        }

    }

    //public void buscarPorTitulo(String nome) {} Já faz atualmente na mostrarFilme

    public void listarFilmesDisponiveis() {
        for (String k : this.filmes.keySet()) {
            for (Filme f : filmes.get(k)) {
                if (f.getEstado() == Estado.DISPONIVEL) {
                    System.out.println(f);
                }
            }
        }
    }

    public void listarFilmesAlugados() {
        for (String k : this.filmes.keySet()) {
            for (Filme f : filmes.get(k)) {
                if (f.getEstado() == Estado.ALUGADO) {
                    System.out.println(f);
                }
            }
        }
    }

}
