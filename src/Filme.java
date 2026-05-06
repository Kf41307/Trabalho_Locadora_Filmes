import java.util.ArrayList;
import java.util.List;

public class Filme {
    private String nome;
    private int ano;
    private int duracaoMinutos;
    private int id;
    private List<Genero> generos;
    private Estado estado;
    private int precoLocacao;
    private int copias;

    public Filme(String nome, int ano, int duracaoMinutos, List<Genero> generos, Estado estado, int precoLocacao, int copias) {
        this.nome = nome;
        this.ano = ano;
        this.duracaoMinutos = duracaoMinutos;
        this.generos = new ArrayList<>(generos);
        this.estado = estado;
        this.precoLocacao = precoLocacao;
        this.copias = copias;
    }
}
