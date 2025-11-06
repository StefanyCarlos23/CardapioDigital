package model;

import javafx.beans.property.*;

public class Prato {
    private final StringProperty nome;
    private final DoubleProperty preco;
    private final IntegerProperty tempoPreparo;

    public Prato(String nome, double preco, int tempoPreparo) {
        this.nome = new SimpleStringProperty(nome);
        this.preco = new SimpleDoubleProperty(preco);
        this.tempoPreparo = new SimpleIntegerProperty(tempoPreparo);
    }

    public String getNome() { 
        return nome.get(); 
    }
    
    public double getPreco() { 
        return preco.get(); 
    }
    
    public int getTempoPreparo() { 
        return tempoPreparo.get(); 
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public DoubleProperty precoProperty() {
        return preco;
    }

    public IntegerProperty tempoPreparoProperty() {
        return tempoPreparo;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public void setPreco(double preco) {
        this.preco.set(preco);
    }

    public void setTempoPreparo(int tempo) {
        this.tempoPreparo.set(tempo);
    }

    @Override
    public String toString() {
        return String.format("%s - R$%.2f - %d min", getNome(), getPreco(), getTempoPreparo());
    }
}