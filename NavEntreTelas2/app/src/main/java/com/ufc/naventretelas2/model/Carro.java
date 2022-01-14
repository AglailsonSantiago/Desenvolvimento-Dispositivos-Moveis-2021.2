package com.ufc.naventretelas2.model;

public class Carro {
    private static int contadorId = 0;

    private String id;
    private String nome;
    private String marca;
    private String placa;
    private String ano;

    public Carro(String id, String nome, String marca, String placa, String ano) {

        this.id = id;

        this.nome = nome;
        this.marca = marca;
        this.placa = placa;
        this.ano = ano;
    }

    public Carro(){}

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return nome;
    }
}
