package estrutura;

import model.Prato;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class TabelaHash {
    private LinkedList<Prato>[] tabela;
    private int tamanho;
    private int quantidadeElementos;
    
    private List<Prato> ordemInsercao;

    @SuppressWarnings("unchecked")
    public TabelaHash(int tamanho) {
        if (tamanho <= 0) {
            throw new IllegalArgumentException("Tamanho deve ser positivo");
        }
        this.tamanho = tamanho;
        this.quantidadeElementos = 0;
        this.ordemInsercao = new ArrayList<>();
        tabela = new LinkedList[tamanho];
        
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hash(String chave) {
        return Math.abs(chave.hashCode() % tamanho);
    }

    public void inserir(Prato prato) {
        if (prato == null) {
            throw new IllegalArgumentException("Prato não pode ser nulo");
        }
        
        if (buscar(prato.getNome()) != null) {
            throw new IllegalArgumentException("Prato já cadastrado!");
        }
        
        int indice = hash(prato.getNome());
        tabela[indice].add(prato);
        ordemInsercao.add(prato);
        quantidadeElementos++;
    }

    public Prato buscar(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }
        
        int indice = hash(nome);
        for (Prato p : tabela[indice]) {
            if (p.getNome().equalsIgnoreCase(nome.trim())) {
                return p;
            }
        }
        return null;
    }

    public boolean remover(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        
        int indice = hash(nome);
        boolean removido = tabela[indice].removeIf(p -> p.getNome().equalsIgnoreCase(nome.trim()));
        
        if (removido) {
            ordemInsercao.removeIf(p -> p.getNome().equalsIgnoreCase(nome.trim()));
            quantidadeElementos--;
        }
        return removido;
    }

    public Prato[] exportarParaVetor() {
        return ordemInsercao.toArray(new Prato[0]);
    }

    public int getQuantidadeElementos() {
        return quantidadeElementos;
    }

    public boolean isEmpty() {
        return quantidadeElementos == 0;
    }
}