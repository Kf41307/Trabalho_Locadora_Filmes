import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Locacao {
    private Filme filme; //Uma locação por vez
    private LocalDate dataDevolucao; //Duração fixa ? Se escolher setar preco por dia
    private Cliente cliente;

    public Locacao(Filme filme, LocalDate dataDevolucao, Cliente cliente) {
        this.filme = filme;
        this.dataDevolucao = dataDevolucao;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Filme: \"" + filme.getNome() + "\" alugado para \"" + cliente.getNome() + "\" até "+ dataDevolucao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".";
    }

    public Filme getFilme() {
        return filme;
    }
    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public LocalDate getDuracao() {
        return dataDevolucao;
    }
    public void setDuracao(LocalDate duracao) {
        this.dataDevolucao = duracao;
    }

    public Cliente getCliente() {
        return cliente;
    }
}