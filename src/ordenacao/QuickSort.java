package ordenacao;

import model.Prato;

public class QuickSort {

    public static void ordenar(Prato[] vetor, String criterio) {
        if (vetor == null || vetor.length == 0) {
            return;
        }
        quicksort(vetor, 0, vetor.length - 1, criterio);
    }

    private static void quicksort(Prato[] vetor, int inicio, int fim, String criterio) {
        if (inicio < fim) {
            int indicePivo = particionar(vetor, inicio, fim, criterio);
            quicksort(vetor, inicio, indicePivo - 1, criterio);
            quicksort(vetor, indicePivo + 1, fim, criterio);
        }
    }

    private static int particionar(Prato[] vetor, int inicio, int fim, String criterio) {
        Prato pivo = vetor[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (comparar(vetor[j], pivo, criterio) < 0) {
                i++;
                trocar(vetor, i, j);
            }
        }

        trocar(vetor, i + 1, fim);
        return i + 1;
    }

    private static void trocar(Prato[] vetor, int i, int j) {
        Prato temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }

    private static int comparar(Prato a, Prato b, String criterio) {
        return switch (criterio.toLowerCase()) {
            case "nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "preco" -> Double.compare(a.getPreco(), b.getPreco());
            case "tempo" -> Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }
}