import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public void adicionarLocacao(Cliente cliente, String nomeFilme) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (cliente.isBloqueado()) {
            System.out.println("Cliente \"" + cliente.getNome() + " \" não pode fazer locação porque está bloqueado.");
            return;
        }else if (!filmes.containsKey(nomeFilme)) {
            System.out.println("Não existe um filme com o nome \"" + nomeFilme + "\".");
            return;
        }


        for (Filme f : filmes.get(nomeFilme)) {
            if (f.getEstado() == Estado.DISPONIVEL) {

                LocalDate hoje = LocalDate.now();

                System.out.println("Valor da locação filme \"" + f.getNome() + "\" por dia: R$ " + f.getPrecoLocacao());

                boolean input = true;

                do {
                    System.out.print("Locação por quantos dias?: ");
                    int diasLocacao = scanner.nextInt();

                    if (diasLocacao < 1) {
                        System.out.println("Digite uma quantidade de dias maior do que 0.");
                        continue;
                    }

                    LocalDate dataDevolucao = hoje.plusDays(diasLocacao);

                    if (dataDevolucao.getDayOfWeek() == DayOfWeek.SATURDAY || dataDevolucao.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        System.out.println("A devolução ficará para um dia não útil, a data para devolução deve ser um dia útil.");
                    } else {

                        Locacao locacao = new Locacao(f, dataDevolucao, cliente);

                        f.setEstado(Estado.ALUGADO);

                        locacoes.add(locacao);
                        System.out.println("Locação do filme \"" + f.getNome() + "\" para o cliente \"" + cliente.getNome() + "\" adicionada.");
                        System.out.println("Data da devolução: " + dataDevolucao.format(formatter));
                        System.out.println("Valor da locação: " + f.getPrecoLocacao()*diasLocacao);

                        cliente.setValorPendente(f.getPrecoLocacao()*diasLocacao);
                        cliente.addLocacao(locacao);
                        input = false;
                        return;
                    }
                } while (input);
            }
        }

        System.out.println("Nenhum filme com o nome \"" + nomeFilme + "\" está disponível para locação");
    }

    public void mostrarLocacoesAtivas(){
        for(Locacao l : locacoes){
            System.out.println(l);
        }
    }

    //public void buscarPorTitulo(String nome) {} Já faz atualmente na mostrarFilme

    public void listarFilmesDisponiveis() {
        for (String k : filmes.keySet()) {
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
