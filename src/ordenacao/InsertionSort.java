package ordenacao;

import model.Prato;

public class InsertionSort {

    public static void ordenar(Prato[] vetor, String criterio) {
        if (vetor == null || vetor.length == 0) {
            return;
        }

        
        for (int i = 1; i < vetor.length; i++) {
            Prato atual = vetor[i];
            int j = i - 1;

            while (j >= 0 && comparar(vetor[j], atual, criterio) > 0) {
                vetor[j + 1] = vetor[j];
                j--;
            }

            vetor[j + 1] = atual;

        }

    }

    private static int comparar(Prato a, Prato b, String criterio) {
        return switch (criterio.toLowerCase()) {
            case "nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "preco", "preço" -> Double.compare(a.getPreco(), b.getPreco());
            case "tempo" -> Integer.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }
}