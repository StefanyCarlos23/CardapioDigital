package ordenacao;

import model.Prato;

public class BubbleSort {
    public static void ordenar(Prato[] vetor, String criterio) {
        int n = vetor.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparar(vetor[j], vetor[j + 1], criterio) > 0) {
                    Prato temp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = temp;
                }
            }
        }
    }

    private static int comparar(Prato a, Prato b, String criterio) {
        return switch (criterio) {
            case "nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "preco" -> Double.compare(a.getPreco(), b.getPreco());
            case "tempo" -> Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }
}