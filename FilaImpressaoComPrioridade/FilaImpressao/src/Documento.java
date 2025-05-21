public class Documento implements Comparable<Documento> {
    private static int contadorGlobal = 0;
    private int ordemChegada;
    private String nome;
    private int prioridade; 

    public Documento(String nome, int prioridade) {
        this.nome = nome;
        this.prioridade = prioridade;
        this.ordemChegada = contadorGlobal++;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int compareTo(Documento outro) {
        if (this.prioridade != outro.prioridade) {
            return Integer.compare(this.prioridade, outro.prioridade);
        }
        return Integer.compare(this.ordemChegada, outro.ordemChegada);
    }

    @Override
    public String toString() {
        String tipo = (prioridade == 1) ? "Urgente" : "Normal";
        return "[" + nome + " | " + tipo + "]";
    }
}