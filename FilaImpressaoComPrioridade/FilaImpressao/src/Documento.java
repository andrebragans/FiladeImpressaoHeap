public class Documento implements Comparable<Documento> {
    private static int contadorGlobal = 0;
    private int ordemChegada;
    private String nome;
    private int prioridade; 

    //Construtor da classe Documento
    public Documento(String nome, int prioridade) {
        this.nome = nome;
        this.prioridade = prioridade;
        this.ordemChegada = contadorGlobal++;
    }

    // Métodos getters
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

    // Representação textual do documento (útil para exibir na GUI ou console)
    @Override
    public String toString() {
        String tipo = (prioridade == 1) ? "Urgente" : "Normal";
        return "[" + nome + " | " + tipo + "]";
    }
}
