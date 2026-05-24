import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmeRepositorio {

    private Map<String, List<Filme>> filmes;

    public FilmeRepositorio(){
        filmes = new HashMap<>();
    }

    public void adicionar(Filme filme) {
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

    public Filme buscarDisponivel(String nomeFilme) {
        if (!filmes.containsKey(nomeFilme)) return null;

        for (Filme f : filmes.get(nomeFilme)) {
            if (f.getEstado() == Estado.DISPONIVEL) return f;
        }
        return null;
    }

    public boolean existe(String nomeFilme) {
        return filmes.containsKey(nomeFilme);
    }

    public void remover(String nomeFilme, int id) {
        List<Filme> lista = filmes.get(nomeFilme);

        lista.removeIf(f -> f.getId() == id);

        if(lista.isEmpty()){
            filmes.remove(nomeFilme);
        }else{
            for (Filme f : lista) {
                f.setCopias(lista.size()); //O numero de copias
                f.setId(lista.indexOf(f) + 1);
            }
        }
    }

    public List<Filme> buscarPorNome(String nomeFilme){
        List<Filme> filmes = new ArrayList<>();

        if(this.filmes.containsKey(nomeFilme)){
            for(Filme f : this.filmes.get(nomeFilme)) {
                filmes.add(f);
            }
        }
        return filmes;
    }

    public List<Filme> listarTodos(){
        List<Filme> todos = new ArrayList<>();
        for(List<Filme> f : filmes.values()){
            todos.addAll(f);
        }
        return todos;
    }

    public List<Filme> listarDisponiveis() {
        List<Filme> disponiveis = new ArrayList<>();

        for (List<Filme> lista : filmes.values()) {
            for (Filme f : lista) {
                if (f.getEstado() == Estado.DISPONIVEL) disponiveis.add(f);
            }
        }
        return disponiveis;
    }

    public List<Filme> listarAlugados() {
        List<Filme> alugados = new ArrayList<>();

        for (List<Filme> lista : filmes.values()) {
            for (Filme f : lista) {
                if (f.getEstado() == Estado.ALUGADO) alugados.add(f);
            }
        }
        return alugados;
    }
}
