import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ClienteRepositorio {

    private Map<String, Cliente> clientes;

    public ClienteRepositorio(){
        clientes = new HashMap<>();
    }

    public void adicionar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
    }

    public void remover(String cpf) {
        clientes.remove(cpf);
    }

    public void atualizarTelefone(Cliente c, String novoTelefone){
        c.setTelefone(novoTelefone);
    }

    public Cliente buscarPorCpf(String cpf) {
        return clientes.get(cpf);
    }

    public boolean existeCpf(String cpf) {
        return clientes.containsKey(cpf);
    }

    public boolean existeTelefone(String telefone) {
        for (Cliente c : clientes.values()) {
            if (c.getTelefone().equals(telefone)) return true;
        }
        return false;
    }

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    public List<Cliente> listarEndividados(){
        List<Cliente> endividados = new ArrayList<>();

        for(Cliente c : clientes.values()){
            if(c.getValorPendente() > 0){
                endividados.add(c);
            }
        }
        return endividados;
    }

}
