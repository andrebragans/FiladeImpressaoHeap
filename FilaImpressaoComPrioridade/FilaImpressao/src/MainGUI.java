import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class MainGUI {
    private static FilaImpressao fila = new FilaImpressao();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fila de Impressão com Prioridades");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());

            JTextPane outputArea = new JTextPane();
            outputArea.setEditable(false);
            outputArea.setContentType("text/plain");
            outputArea.setText("➡️ Aqui serão exibidos os documentos da fila de impressão.\n");
            JScrollPane scrollPane = new JScrollPane(outputArea);
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton btnAdicionar = new JButton("Adicionar Documento");
            JButton btnVisualizar = new JButton("Visualizar Fila");
            JButton btnImprimir = new JButton("Imprimir Próximo");
            buttonPanel.add(btnAdicionar);
            buttonPanel.add(btnVisualizar);
            buttonPanel.add(btnImprimir);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            btnAdicionar.addActionListener(e -> {
                JTextField nomeField = new JTextField();
                String[] opcoes = {"Urgente", "Normal"};
                JComboBox<String> prioridadeBox = new JComboBox<>(opcoes);
                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Nome do Documento:"));
                panel.add(nomeField);
                panel.add(new JLabel("Prioridade:"));
                panel.add(prioridadeBox);
                int result = JOptionPane.showConfirmDialog(frame, panel, "Novo Documento",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String nome = nomeField.getText();
                    int prioridade = prioridadeBox.getSelectedIndex() == 0 ? 1 : 2;
                    if (!nome.isEmpty()) {
                        fila.adicionarDocumento(nome, prioridade);
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

            btnVisualizar.addActionListener(e -> {
                outputArea.setText("");
                StyledDocument doc = outputArea.getStyledDocument();
                Style estiloUrgente = outputArea.addStyle("Urgente", null);
                StyleConstants.setForeground(estiloUrgente, Color.RED);
                Style estiloNormal = outputArea.addStyle("Normal", null);
                StyleConstants.setForeground(estiloNormal, Color.BLACK);
                try {
                    doc.insertString(doc.getLength(), "📋 Fila de Impressão:\n\n", estiloNormal);
                    String[] linhas = fila.visualizarFilaFormatada().split("\\n");
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

            btnImprimir.addActionListener(e -> {
                Documento docImprimir = fila.imprimirProximo();
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

            frame.setVisible(true);
        });
    }
}