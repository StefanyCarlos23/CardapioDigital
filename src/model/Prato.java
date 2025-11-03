package model;

public class Prato {
    private String nome;
    private double preco;
    private int tempoPreparo;

    public Prato(String nome, double preco, int tempoPreparo) {
        this.nome = nome;
        this.preco = preco;
        this.tempoPreparo = tempoPreparo;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getTempoPreparo() { return tempoPreparo; }

    @Override
    public String toString() {
        return String.format("%s - R$%.2f - %d min", nome, preco, tempoPreparo);
    }
}
