package Locadora.Menus;

import Locadora.ClassesPrincipais.*;
import Locadora.Enums.*;
import Locadora.Locadora;
import Locadora.Utilitario.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuFilmes {

    private Locadora locadora;
    private Scanner scanner;

    public MenuFilmes(Locadora locadora, Scanner scanner) {
        this.locadora = locadora;
        this.scanner = scanner;
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n--- FILMES ---");
            System.out.println("1 - Adicionar filme");
            System.out.println("2 - Remover filme");
            System.out.println("3 - Buscar filme por nome");
            System.out.println("4 - Listar todos os filmes");
            System.out.println("5 - Listar filmes disponíveis");
            System.out.println("6 - Listar filmes alugados");
            System.out.println("0 - Voltar");
            opcao = ValidadorInput.lerInteiro(0, 6);

            switch (opcao) {
                case 1 -> adicionarFilme();
                case 2 -> removerFilme();
                case 3 -> buscarFilme();
                case 4 -> mostrarFilmes();
                case 5 -> mostrarFilmesDisponiveis();
                case 6 -> mostrarFilmesAlugados();
            }
        } while (opcao != 0);
    }

    private void adicionarFilme() {
        System.out.print("Nome do filme: ");
        String nome = ValidadorInput.lerString();

        System.out.print("Ano de lançamento: ");
        int ano = ValidadorInput.lerInteiro(1900, LocalDate.now().getYear());

        System.out.print("Duração em minutos: ");
        int duracao = ValidadorInput.lerInteiro(1, 300);

        double preco = inputPreco();
        List<Genero> generos = inputGeneros();

        locadora.adicionarFilme(new Filme(nome, ano, duracao, generos, Estado.DISPONIVEL, preco, 1));
        System.out.println("Filme \"" + nome + "\" adicionado.");
    }

    private void removerFilme() {
        if(locadora.listarTodosFilmes().isEmpty()){
            System.out.println("Nenhum filme cadastrado");
            return;
        }

        System.out.print("Nome do filme para remover: ");
        String nome = scanner.nextLine();

        List<Filme> lista = locadora.buscarFilmePorNome(nome);
        if (lista.isEmpty()) {
            System.out.println("Nenhum filme encontrado com o nome \"" + nome + "\".");
            return;
        }

        int id = 1;
        if (lista.size() > 1) {
            System.out.println("Cópias encontradas:");
            for(Filme f : lista){
                System.out.println(f);
            }
            System.out.print("Informe o ID da cópia a remover: ");
            id = ValidadorInput.lerInteiro(1, lista.size());
        }

        locadora.removerFilme(nome, id);
        System.out.println("Filme \"" + nome + "\" removido");
    }

    private void buscarFilme() {
        System.out.print("Nome do filme: ");
        String nome = scanner.nextLine();
        List<Filme> lista = locadora.buscarFilmePorNome(nome);
        if(lista.isEmpty()){
            System.out.println("Nenhum filme encontrado com o nome \"" + nome + "\".");
        }else{
            for(Filme f : lista){
                System.out.println(f);
            }
        }
    }

    private double inputPreco() {
        while (true) {
            System.out.print("Preço por dia (R$): ");
            String entrada = scanner.nextLine().replace(",", ".");
            try {
                double preco = Double.parseDouble(entrada);
                if (preco > 0) return preco;
                System.out.println("O preço deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numérico válido.");
            }
        }
    }

    private Genero converterGenero(String s){
        return switch (s.toLowerCase()){
            case "ação", "acao" -> Genero.ACAO;
            case "comédia", "comedia" -> Genero.COMEDIA;
            case "drama" -> Genero.DRAMA;
            case "ficção científica", "ficcao cientifica" -> Genero.FICCAO_CIENTIFICA;
            case "terror" -> Genero.TERROR;
            case "romance" -> Genero.ROMANCE;
            case "suspense" -> Genero.SUSPENSE;
            case "guerra" -> Genero.GUERRA;
            case "crime" -> Genero.CRIME;
            case "mistério", "misterio" -> Genero.MISTERIO;
            case "infantil" -> Genero.INFANTIL;
            case "aventura" -> Genero.AVENTURA;
            default -> null;
        };
    }

    private List<Genero> inputGeneros(){ // Mudar essa
        List<Genero> generos = new ArrayList<>();

        System.out.println("Gêneros disponíveis: ");
        for (Genero g : Genero.values()){
            System.out.println("- " + g);
        }

        while (true){

            System.out.print("Digite um gênero ('fim' para parar): ");
            String input = scanner.nextLine().toLowerCase();

            if(input.equals("fim")){
                if(generos.isEmpty()){
                    System.out.println("Adicione pelo menos um gênero");
                    continue;
                }
                break;
            }

            Genero genero = converterGenero(input);
            if(genero == null){
                System.out.println("Não existe gênero \"" + input + "\"");
            }else if(generos.contains(genero)){
                System.out.println("Gênero \""+ input + "\" já adicionado");
            }else{
                generos.add(genero);
                System.out.println("Gênero adicionado");
            }
        }

        return generos;
    }

    private void mostrarFilmes(){
        List<Filme> todos = locadora.listarTodosFilmes();
        if(todos.isEmpty()){
            System.out.println("Nenhum filme cadastrado");
        }

        for(Filme f : todos){
            System.out.println(f);
        }
    }

    private void mostrarFilmesDisponiveis(){
        List<Filme> disponiveis = locadora.listarFilmesDisponiveis();
        if(disponiveis.isEmpty()){
            System.out.println("Nenhum filme disponível");
        }

        for(Filme f : disponiveis){
            System.out.println(f);
        }
    }

    private void mostrarFilmesAlugados(){
        List<Filme> alugados = locadora.listarFilmesAlugados();
        if(alugados.isEmpty()){
            System.out.println("Nenhum filme alugado");
        }

        for(Filme f : alugados){
            System.out.println(f);
        }
    }
}