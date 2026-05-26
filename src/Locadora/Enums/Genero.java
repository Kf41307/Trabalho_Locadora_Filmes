package Locadora.Enums;

public enum Genero {
    ACAO, COMEDIA, DRAMA, FICCAO_CIENTIFICA, TERROR, ROMANCE, SUSPENSE, GUERRA, CRIME, MISTERIO, INFANTIL, AVENTURA;


    @Override
    public String toString() {
        return switch (this){
            case ACAO -> "Ação";
            case COMEDIA -> "Comédia";
            case DRAMA -> "Drama";
            case FICCAO_CIENTIFICA -> "Ficção Científica";
            case TERROR -> "Terror";
            case ROMANCE -> "Romance";
            case SUSPENSE -> "Suspense";
            case GUERRA -> "Guerra";
            case CRIME -> "Crime";
            case MISTERIO -> "Mistério";
            case INFANTIL -> "Infantil";
            case AVENTURA -> "Aventura";
        };
    }
}
