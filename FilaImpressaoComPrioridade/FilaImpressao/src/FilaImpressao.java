import java.util.*;

// Classe que gerencia a fila de impressão utilizando heap de prioridade
public class FilaImpressao {
    // Uso do heap: PriorityQueue é uma implementação de heap (min-heap por padrão)
    private PriorityQueue<Documento> fila;

    // Construtor inicializa o heap com comparador baseado na prioridade do documento
    public FilaImpressao() {
        fila = new PriorityQueue<>();
    }
    // Adiciona documento à fila (inserção no heap)
    public void adicionarDocumento(String nome, int prioridade) {
        fila.offer(new Documento(nome, prioridade)); // O heap se reorganiza automaticamente
    }
    // Remove e retorna o próximo documento a ser impresso
    public Documento imprimirProximo() {
        return fila.poll();  // Remove o topo do heap
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
