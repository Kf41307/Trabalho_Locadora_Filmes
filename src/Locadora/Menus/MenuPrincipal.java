package Locadora.Menus;

import java.util.Scanner;
import Locadora.Locadora;
import Locadora.Utilitario.ValidadorInput;

public class MenuPrincipal {

    private Locadora locadora;
    private Scanner scanner;

    private MenuFilmes menuFilmes;
    private MenuFuncionarios menuFuncionarios;
    private MenuClientes menuClientes;
    private MenuLocacao menuLocacao;
    private MenuReservas menuReservas;

    public MenuPrincipal(Locadora locadora, Scanner scanner) {
        this.locadora = locadora;
        this.scanner = scanner;

        this.menuFilmes = new MenuFilmes(locadora, scanner);
        this.menuFuncionarios = new MenuFuncionarios(locadora, scanner);
        this.menuClientes = new MenuClientes(locadora, scanner);
        this.menuLocacao = new MenuLocacao(locadora, scanner);
        this.menuReservas = new MenuReservas(locadora, scanner);
    }

    public void iniciar(){
        int opcao;
        do {
            System.out.println("\n--------------------------------");
            System.out.println("|      LOCADORA DE FILMES      |");
            System.out.println("|------------------------------|");
            System.out.println("|  1 - Filmes                  |");
            System.out.println("|  2 - Clientes                |");
            System.out.println("|  3 - Locações                |");
            System.out.println("|  4 - Reservas                |");
            System.out.println("|  5 - Funcionários            |");
            System.out.println("|  0 - Encerrar sistema        |");
            System.out.println("--------------------------------");
            opcao = ValidadorInput.lerInteiro(0, 5);

            switch (opcao) {
                case 1 -> menuFilmes.exibir();
                case 2 -> menuClientes.exibir();
                case 3 -> menuLocacao.exibir();
                case 4 -> menuReservas.exibir();
                case 5 -> menuFuncionarios.exibir();
            }
        } while (opcao != 0);

        System.out.println("Sistema encerrado.");

    }
}
