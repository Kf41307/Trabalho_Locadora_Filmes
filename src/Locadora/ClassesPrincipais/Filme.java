package Locadora.ClassesPrincipais;

import java.util.ArrayList;
import java.util.List;
import Locadora.Enums.*;

public class Filme {
    private String nome;
    private int ano;
    private int duracaoMinutos;
    private int id;
    private List<Genero> generos;
    private Estado estado;
    private double precoLocacao;
    private int copias;

    public Filme(String nome, int ano, int duracaoMinutos, List<Genero> generos, Estado estado, double precoLocacao, int copias) {
        this.nome = nome;
        this.ano = ano;
        this.duracaoMinutos = duracaoMinutos;
        this.generos = new ArrayList<>(generos);
        this.estado = estado;
        this.precoLocacao = precoLocacao;
        this.copias = copias;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoLancamento() {
        return ano;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getPrecoLocacao() {
        return precoLocacao;
    }
    public void setPrecoLocacao(double precoLocacao) {
        this.precoLocacao = precoLocacao;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }
    public int getCopias() {
        return copias;
    }


    @Override
    public String toString() {
        return "Título: " + nome +
                ", Lançamento: " + ano +
                ", Minutos: " + duracaoMinutos +
                ", Id: " + id +
                ", Gêneros: " + generos +
                ", Estado: " + estado +
                ", Preço por dia: " + precoLocacao +
                ", Total cópias: " + copias;
    }
}
