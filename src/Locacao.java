import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Locacao {
    private Filme filme;
    private LocalDate dataDevolucao; //Duração fixa ? Se escolher setar preco por dia
    private Cliente cliente;

    public Locacao(Filme filme, LocalDate dataDevolucao, Cliente cliente) {
        this.filme = filme;
        this.dataDevolucao = dataDevolucao;
        this.cliente = cliente;
    }

    public boolean isAtrasada(){
        return LocalDate.now().isAfter(dataDevolucao);
    }

    public void aplicarMulta(){
        if(this.isAtrasada()){
                LocalDate agora = LocalDate.now();
            long diasAtrasados = ChronoUnit.DAYS.between(dataDevolucao , agora);

            double valorMulta = diasAtrasados * (filme.getPrecoLocacao() * 1.3);

            cliente.setValorPendente(cliente.getValorPendente() + valorMulta);
            cliente.setTotalMultas(cliente.getTotalMultas() + valorMulta);
        }
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