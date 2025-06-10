import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

// Classe principal com interface gráfica utilizando JOptionPane
public class MainGUI {
    private static FilaImpressao fila = new FilaImpressao();  // Criação da fila de impressão

    public static void main(String[] args) {
        // Executa a interface gráfica de forma segura na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fila de Impressão com Prioridades");  // Cria a janela principal (JFrame)
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            // Área de saída onde as mensagens serão exibidas com emojis
            JTextPane outputArea = new JTextPane();
            outputArea.setEditable(false);
            outputArea.setContentType("text/plain");
            outputArea.setText("➡️ Aqui serão exibidos os documentos da fila de impressão.\n");
            JScrollPane scrollPane = new JScrollPane(outputArea); // Adiciona uma barra de rolagem à área de saída
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();   // Cria o painel de botões na parte inferior
            JButton btnAdicionar = new JButton("Adicionar Documento");
            JButton btnVisualizar = new JButton("Visualizar Fila");
            JButton btnImprimir = new JButton("Imprimir Próximo");
            buttonPanel.add(btnAdicionar);
            buttonPanel.add(btnVisualizar);
            buttonPanel.add(btnImprimir);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            // Listener do botão "Adicionar Documento"
            btnAdicionar.addActionListener(e -> {
                JTextField nomeField = new JTextField();
                String[] opcoes = {"Urgente", "Normal"};
                JComboBox<String> prioridadeBox = new JComboBox<>(opcoes);
                JPanel panel = new JPanel(new GridLayout(0, 1));   // Painel para o diálogo de entrada
                panel.add(new JLabel("Nome do Documento:"));
                panel.add(nomeField);
                panel.add(new JLabel("Prioridade:"));
                panel.add(prioridadeBox);
                int result = JOptionPane.showConfirmDialog(frame, panel, "Novo Documento",   // Caixa de diálogo para inserir o novo documento
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String nome = nomeField.getText();
                    int prioridade = prioridadeBox.getSelectedIndex() == 0 ? 1 : 2;  // Urgente = 1, Normal = 2
                    if (!nome.isEmpty()) {
                        fila.adicionarDocumento(nome, prioridade);  // Adiciona o documento à fila (inserção no heap de prioridade)
                        try {
                            StyledDocument doc = outputArea.getStyledDocument();  
                            Style normal = outputArea.addStyle("Normal", null);
                            StyleConstants.setForeground(normal, Color.BLACK);
                            doc.insertString(doc.getLength(), "📥 Documento adicionado: " + nome + "\n", normal);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "O nome do documento não pode estar vazio.");
                    }
                }
            });

            // Listener do botão "Visualizar Fila"
            btnVisualizar.addActionListener(e -> {
                outputArea.setText(""); 
                StyledDocument doc = outputArea.getStyledDocument();  // Estilos diferentes para documentos urgentes e normais
                Style estiloUrgente = outputArea.addStyle("Urgente", null);
                StyleConstants.setForeground(estiloUrgente, Color.RED);
                Style estiloNormal = outputArea.addStyle("Normal", null);
                StyleConstants.setForeground(estiloNormal, Color.BLACK);
                try {
                    doc.insertString(doc.getLength(), "📋 Fila de Impressão:\n\n", estiloNormal);
                    String[] linhas = fila.visualizarFilaFormatada().split("\\n");  // Recebe a lista formatada da fila e exibe linha por linha
                    for (String linha : linhas) {
                        if (linha.contains("⚠️")) {
                            doc.insertString(doc.getLength(), linha + "\n", estiloUrgente);
                        } else {
                            doc.insertString(doc.getLength(), linha + "\n", estiloNormal);
                        }
                    }
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            });

            // Listener do botão "Imprimir Próximo"
            btnImprimir.addActionListener(e -> {
                Documento docImprimir = fila.imprimirProximo(); // Remove o documento com maior prioridade do heap (menor número)
                StyledDocument doc = outputArea.getStyledDocument();
                Style estiloNormal = outputArea.addStyle("Normal", null);
                Style estiloUrgente = outputArea.addStyle("Urgente", null);
                StyleConstants.setForeground(estiloUrgente, Color.RED);
                StyleConstants.setForeground(estiloNormal, Color.BLACK);
                try {
                    if (docImprimir == null) {
                        doc.insertString(doc.getLength(), "🛑 Nenhum documento na fila.\n", estiloNormal);
                    } else {
                        Style estilo = docImprimir.getPrioridade() == 1 ? estiloUrgente : estiloNormal;
                        doc.insertString(doc.getLength(), "🖨️ Imprimindo: " + docImprimir + "\n", estilo);
                    }
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            });

            frame.setVisible(true); // Exibe a janela
        });
    }
}
