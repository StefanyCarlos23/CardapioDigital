package main;

import model.Prato;
import estrutura.TabelaHash;
import ordenacao.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TabelaHash tabela = new TabelaHash(10);
        int opcao;

        do {
            System.out.println("\n=== CARDÁPIO DIGITAL ===");
            System.out.println("1. Inserir prato");
            System.out.println("2. Buscar prato");
            System.out.println("3. Remover prato");
            System.out.println("4. Exibir pratos ordenados");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt(); sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Preço: ");
                    double preco = sc.nextDouble();
                    System.out.print("Tempo de preparo (min): ");
                    int tempo = sc.nextInt();
                    tabela.inserir(new Prato(nome, preco, tempo));
                }
                case 2 -> {
                    System.out.print("Nome do prato: ");
                    String nome = sc.nextLine();
                    Prato p = tabela.buscar(nome);
                    System.out.println(p != null ? p : "Prato não encontrado!");
                }
                case 3 -> {
                    System.out.print("Nome do prato: ");
                    String nome = sc.nextLine();
                    System.out.println(tabela.remover(nome) ? "Removido!" : "Não encontrado!");
                }
                case 4 -> {
                    Prato[] vetor = tabela.exportarParaVetor();
                    if (vetor.length == 0) {
                        System.out.println("Nenhum prato cadastrado.");
                        break;
                    }

                    System.out.print("Ordenar por (nome/preco/tempo): ");
                    String criterio = sc.nextLine();
                    System.out.print("Algoritmo (1-Bubble / 2-Insertion / 3-Quick): ");
                    int alg = sc.nextInt();

                    switch (alg) {
                        case 1 -> BubbleSort.ordenar(vetor, criterio);
                        case 2 -> InsertionSort.ordenar(vetor, criterio);
                        case 3 -> QuickSort.ordenar(vetor, criterio);
                        default -> System.out.println("Opção inválida!");
                    }

                    System.out.println("\n--- Pratos Ordenados ---");
                    for (Prato p : vetor)
                        System.out.println(p);
                }
            }
        } while (opcao != 0);
    }
}