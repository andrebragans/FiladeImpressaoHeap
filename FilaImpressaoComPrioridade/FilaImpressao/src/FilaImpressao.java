import java.util.*;

public class FilaImpressao {
    // Fila de prioridade de documentos (ordena pelo menor valor de prioridade)
    private PriorityQueue<Documento> fila;

    public FilaImpressao() {
        // Construtor inicializa a fila com comparador de prioridade
        fila = new PriorityQueue<>();
    }
    // Adiciona um novo documento à fila
    public void adicionarDocumento(String nome, int prioridade) {
        fila.offer(new Documento(nome, prioridade));
    }
    // Remove e retorna o próximo documento a ser impresso
    public Documento imprimirProximo() {
        return fila.poll();
    }

    public String visualizarFilaFormatada() {
        if (fila.isEmpty()) return "(fila vazia)";  // Verifica se a fila está vazia
        ArrayList<Documento> listaOrdenada = new ArrayList<>(fila);
        Collections.sort(listaOrdenada);
        StringBuilder sb = new StringBuilder();
        int pos = 1;
        for (Documento doc : listaOrdenada) {
            String marcador = doc.getPrioridade() == 1 ? "⚠️ " : "";
            sb.append(pos++).append(". ").append(marcador).append(doc.toString()).append("\n");
        }
        return sb.toString();
    }
}
