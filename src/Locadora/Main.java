package Locadora;

import Locadora.Menus.MenuPrincipal;
import java.util.Scanner;

public class Main {
    static void main() {

        Locadora locadora = new Locadora();
        locadora.carregarSistema();

        Scanner scanner = new Scanner(System.in);
        new MenuPrincipal(locadora, scanner).iniciar();

        locadora.salvarSistema();
        scanner.close();
    }
}
