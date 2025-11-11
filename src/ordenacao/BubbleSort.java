package ordenacao;

import model.Prato;

public class BubbleSort {

    public static void ordenar(Prato[] vetor, String criterio) {
        if (vetor == null || vetor.length == 0) {
            return;
        }
        
        int n = vetor.length;
        boolean houveTroca;

        for (int i = 0; i < n - 1; i++) {

            houveTroca = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (comparar(vetor[j], vetor[j + 1], criterio) > 0) {
                    Prato temp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = temp;
                    houveTroca = true;
                }

            }

            if (!houveTroca) {
                break;
            }
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