public abstract class Pessoa {

    private String nome; // Regex para o nome (somente letras, depois espaco, somente letras, depois ou espaco ou letras)
    private String cpf; // Validar com regular expressions
    private String telefone; // Validar tambem regex

    public Pessoa(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " , CPF: " + cpf + " , Telefone: " + telefone;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
