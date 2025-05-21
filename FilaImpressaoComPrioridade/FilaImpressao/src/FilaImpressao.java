import java.util.*;

public class FilaImpressao {
    private PriorityQueue<Documento> fila;

    public FilaImpressao() {
        fila = new PriorityQueue<>();
    }

    public void adicionarDocumento(String nome, int prioridade) {
        fila.offer(new Documento(nome, prioridade));
    }

    public Documento imprimirProximo() {
        return fila.poll();
    }

    public String visualizarFilaFormatada() {
        if (fila.isEmpty()) return "(fila vazia)";
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