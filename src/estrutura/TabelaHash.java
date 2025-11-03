package estrutura;

import model.Prato;
import java.util.LinkedList;

public class TabelaHash {
    private LinkedList<Prato>[] tabela;
    private int tamanho;

    @SuppressWarnings("unchecked")
    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++)
            tabela[i] = new LinkedList<>();
    }

    private int hash(String chave) {
        return Math.abs(chave.hashCode() % tamanho);
    }

    public void inserir(Prato prato) {
        int indice = hash(prato.getNome());
        tabela[indice].add(prato);
    }

    public Prato buscar(String nome) {
        int indice = hash(nome);
        for (Prato p : tabela[indice])
            if (p.getNome().equalsIgnoreCase(nome))
                return p;
        return null;
    }

    public boolean remover(String nome) {
        int indice = hash(nome);
        return tabela[indice].removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    public Prato[] exportarParaVetor() {
        LinkedList<Prato> todos = new LinkedList<>();
        for (LinkedList<Prato> lista : tabela)
            todos.addAll(lista);
        return todos.toArray(new Prato[0]);
    }
}
