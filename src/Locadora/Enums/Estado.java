package Locadora.Enums;

public enum Estado {
    DISPONIVEL, ALUGADO, RESERVADO;

    @Override
    public String toString() {
        return switch (this){
            case DISPONIVEL -> "Disponível";
            case ALUGADO -> "Alugado";
            case RESERVADO -> "Reservado";
        };
    }

}