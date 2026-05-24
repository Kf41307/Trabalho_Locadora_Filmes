import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepositorio {

    private List<Funcionario> funcionarios;

    public FuncionarioRepositorio(){
        funcionarios = new ArrayList<>();
    }

    public void adicionar(Funcionario f){
        funcionarios.add((f));
    }

    public void remover(Funcionario f){
        funcionarios.remove(f);
    }

    public void alterarTelefone(Funcionario f, String novoTelefone){ //Ja tem que estar validado o novoTelefone
        f.setTelefone(novoTelefone);
    }

    public void alterarSalario(Funcionario f, double novoSalario){
        f.setSalario(novoSalario);
    }

    //Alterar cargo?

    public List<Funcionario> listarFuncionarios(){
        return new ArrayList<>(funcionarios);
    }

}

