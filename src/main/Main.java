
package main;

import model.Prato;
import estrutura.TabelaHash;
import ordenacao.*;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Classe principal do Sistema de Cardápio Digital
 * Gerencia o menu e interação com o usuário
 */
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final TabelaHash tabela = new TabelaHash(10);

    public static void main(String[] args) {
        int opcao;
        
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   BEM-VINDO AO CARDÁPIO DIGITAL!      ║");
        System.out.println("╚════════════════════════════════════════╝");

        do {
            exibirMenu();
            opcao = lerOpcaoMenu();

            switch (opcao) {
                case 1 -> inserirPrato();
                case 2 -> buscarPrato();
                case 3 -> removerPrato();
                case 4 -> exibirPratosOrdenados();
                case 0 -> System.out.println("\n👋 Saindo do sistema... Até logo!");
                default -> System.out.println("\n❌ Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);

        sc.close();
    }

    /**
     * Exibe o menu principal
     */
    private static void exibirMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│        CARDÁPIO DIGITAL - MENU      │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1. Inserir prato                   │");
        System.out.println("│  2. Buscar prato                    │");
        System.out.println("│  3. Remover prato                   │");
        System.out.println("│  4. Exibir pratos ordenados         │");
        System.out.println("│  0. Sair                            │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcaoMenu() {
        try {
            int opcao = sc.nextInt();
            sc.nextLine();
            return opcao;
        } catch (InputMismatchException e) {
            sc.nextLine();
            return -1;
        }
    }

    private static void inserirPrato() {
        System.out.println("\n═══ INSERIR NOVO PRATO ═══");
        
        try {
            System.out.print("Nome do prato: ");
            String nome = sc.nextLine();
            
            System.out.print("Preço (R$): ");
            double preco = sc.nextDouble();
            
            System.out.print("Tempo de preparo (min): ");
            int tempo = sc.nextInt();
            sc.nextLine();

            Prato novoPrato = new Prato(nome, preco, tempo);
            tabela.inserir(novoPrato);
            
            System.out.println("\nPrato cadastrado com sucesso!");
            System.out.println("   " + novoPrato);
            
        } catch (InputMismatchException e) {
            sc.nextLine();
            System.out.println("\nErro: Entrada inválida! Verifique os valores digitados.");
        } catch (IllegalArgumentException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    private static void buscarPrato() {
        System.out.println("\n═══ BUSCAR PRATO ═══");
        System.out.print("Nome do prato: ");
        String nome = sc.nextLine();

        Prato prato = tabela.buscar(nome);
        
        if (prato != null) {
            System.out.println("\nPrato encontrado:");
            System.out.println("┌────────────────────────────────────────────────┐");
            System.out.println("│ " + prato + " │");
            System.out.println("└────────────────────────────────────────────────┘");
        } else {
            System.out.println("\nPrato não encontrado no cardápio!");
        }
    }

    private static void removerPrato() {
        System.out.println("\n═══ REMOVER PRATO ═══");
        System.out.print("Nome do prato: ");
        String nome = sc.nextLine();

        boolean removido = tabela.remover(nome);
        
        if (removido) {
            System.out.println("\nPrato removido com sucesso!");
        } else {
            System.out.println("\nPrato não encontrado no cardápio!");
        }
    }

    private static void exibirPratosOrdenados() {
        System.out.println("\n═══ EXIBIR PRATOS ORDENADOS ═══");

        if (tabela.isEmpty()) {
            System.out.println("\nNenhum prato cadastrado no momento.");
            return;
        }

        Prato[] vetor = tabela.exportarParaVetor();

        System.out.println("\nEscolha o critério de ordenação:");
        System.out.println("  1. Nome");
        System.out.println("  2. Preço");
        System.out.println("  3. Tempo de preparo");
        System.out.print("Opção: ");
        
        String criterio = obterCriterio();
        if (criterio == null) {
            System.out.println("\nCritério inválido!");
            return;
        }

        System.out.println("\nEscolha o algoritmo de ordenação:");
        System.out.println("  1. BubbleSort");
        System.out.println("  2. InsertionSort");
        System.out.println("  3. QuickSort");
        System.out.print("Opção: ");
        
        int algoritmo = lerOpcaoMenu();

        long inicio = System.nanoTime();
        
        switch (algoritmo) {
            case 1 -> {
                BubbleSort.ordenar(vetor, criterio);
                System.out.println("\nOrdenado com BubbleSort");
            }
            case 2 -> {
                InsertionSort.ordenar(vetor, criterio);
                System.out.println("\nOrdenado com InsertionSort");
            }
            case 3 -> {
                QuickSort.ordenar(vetor, criterio);
                System.out.println("\nOrdenado com QuickSort");
            }
            default -> {
                System.out.println("\nAlgoritmo inválido!");
                return;
            }
        }
        
        long fim = System.nanoTime();
        double tempoMs = (fim - inicio) / 1_000_000.0;

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║           PRATOS ORDENADOS POR " + criterio.toUpperCase());
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.println("║ Nome                           | Preço    | Tempo ║");
        System.out.println("╟────────────────────────────────┼──────────┼───────╢");
        
        for (Prato prato : vetor) {
            System.out.println("║ " + prato + " ║");
        }
        
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.printf("Tempo de execução: %.4f ms\n", tempoMs);
        System.out.printf("Total de pratos: %d\n", vetor.length);
    }

    private static String obterCriterio() {
        int opcao = lerOpcaoMenu();
        return switch (opcao) {
            case 1 -> "nome";
            case 2 -> "preco";
            case 3 -> "tempo";
            default -> null;
        };
    }
}