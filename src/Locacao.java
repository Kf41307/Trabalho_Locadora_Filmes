import java.time.LocalDate;

public class Locacao {
    private Filme filme; //Uma locação por vez
    private LocalDate dataDevolucao; //Duração fixa ? Se escolher setar preco por dia
    private Cliente cliente;

    public Locacao(Filme filme, LocalDate dataDevolucao, Cliente cliente) {
        this.filme = filme;
        this.dataDevolucao = dataDevolucao;
        this.cliente = cliente;
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}