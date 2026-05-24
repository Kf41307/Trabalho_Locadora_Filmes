import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorInput {

    private static Pattern NOME_PATTERN = Pattern.compile("^[\\p{L}]+( [\\p{L}]+)+$"); // o \\p{L} pega todas as letras com acento também, substituindo o "[A-Za-z]"
    private static Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
    private static Pattern TELEFONE_PATTERN = Pattern.compile("^\\(\\d{2}\\) \\d{4,5}-\\d{4}$");

    private static Scanner scanner = new Scanner(System.in);

    private ValidadorInput() {
    } // Impede a instanciação

    public static String inputNomeValido() {
        String nome;
        do {
            System.out.print("Informe o nome completo: ");
            nome = scanner.nextLine();
            if (!NOME_PATTERN.matcher(nome).matches()) {
                System.out.println("Insira um nome completo válido (apenas letras e espaços).");
            }
        } while (!NOME_PATTERN.matcher(nome).matches());
        return nome;
    }

    public static String inputCpfValido(ClienteRepositorio clientes, boolean buscarCpf) {
        String cpf;
        do {
            System.out.print("Informe o CPF: ");
            cpf = scanner.nextLine();

            if (!CPF_PATTERN.matcher(cpf).matches()) {
                System.out.println("O formato do cpf deve ser: \"000.000.000-00\"");
            } else if (!buscarCpf && clientes.existeCpf(cpf)) {
                System.out.println("Já existe um cliente com esse cpf. Tente novamente.");
                cpf = " "; // Colocando para padrao nao valido para n dar match
            } else if (clientes.existeCpf(cpf)) {
                return cpf;
            }

        } while (!CPF_PATTERN.matcher(cpf).matches());

        return cpf;
    }

    public static String inputTelefoneValido(ClienteRepositorio clientes) {
        String telefone;

        do {
            System.out.println("Informe o telefone: ");
            telefone = scanner.nextLine();

            if (!TELEFONE_PATTERN.matcher(telefone).matches()) {
                System.out.println("Formato inválido. Use: (99) 99999-9999 ou (99) 9999-9999");
            } else if (clientes.existeTelefone(telefone)) {
                System.out.println("Já existe um cliente com esse telefone.");
                continue;
            }

        } while (!TELEFONE_PATTERN.matcher(telefone).matches());
        return telefone;
    }

    public static int lerInteiro(int min, int max) {
        int valor = -1;
        while (valor < min || valor > max) {
            System.out.print("Opção (" + min + "-" + max + "): ");
            if (scanner.hasNextInt()) {
                valor = scanner.nextInt();
                scanner.nextLine();
                if (valor < min || valor > max) {
                    System.out.println("Valor deve estar entre " + min + " e " + max + ".");
                }
            } else {
                System.out.println("Digite um número inteiro.");
                scanner.nextLine();
            }
        }
        return valor;
    }

}
