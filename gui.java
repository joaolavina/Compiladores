import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class gui extends JFrame {

    private JTextArea editorArea;
    private JTextArea messageArea;
    private JLabel statusBar;
    private JFileChooser fileChooser;

    public gui() {
        setTitle("Compilador");
        setSize(910, 600);
        setMinimumSize(new Dimension(910, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Configurar layout principal
        setLayout(new BorderLayout());

        // Barra de Ferramentas
        JToolBar toolBar = createToolBar();
        toolBar.setPreferredSize(new Dimension(900, 70));
        add(toolBar, BorderLayout.NORTH);

        // Editor com numeraçao de linhas
        editorArea = new JTextArea();
        editorArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        editorArea.setLineWrap(true);
        JScrollPane editorScrollPane = new JScrollPane(editorArea);
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Area de mensagem
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Status
        statusBar = new JLabel("Arquivo: Nenhum arquivo aberto");
        statusBar.setPreferredSize(new Dimension(900, 25));
        add(statusBar, BorderLayout.SOUTH);

        // SplitPane entre editor e area de mensagens
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScrollPane, messageScrollPane);
        splitPane.setResizeWeight(0.75);
        add(splitPane, BorderLayout.CENTER);

        // Escolhedor de arquivo para abrir e salvar arquivos
        fileChooser = new JFileChooser();
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        // Botões para mexer nos arquivos
        JButton newButton = createToolBarButton("Novo", "Ctrl+N", "new.png", e -> newFile());
        JButton openButton = createToolBarButton("Abrir", "Ctrl+O", "open.png", e -> openFile());
        JButton saveButton = createToolBarButton("Salvar", "Ctrl+S", "save.png", e -> saveFile());
        toolBar.add(newButton);
        toolBar.add(openButton);
        toolBar.add(saveButton);

        // Atalhos de texto
        JButton copyButton = createToolBarButton("Copiar", "Ctrl+C", "copy.png", e -> editorArea.copy());
        JButton pasteButton = createToolBarButton("Colar", "Ctrl+V", "paste.png", e -> editorArea.paste());
        JButton cutButton = createToolBarButton("Recortar", "Ctrl+X", "cut.png", e -> editorArea.cut());
        toolBar.add(copyButton);
        toolBar.add(pasteButton);
        toolBar.add(cutButton);

        // Botões de compilar
        JButton compileButton = createToolBarButton("Compilar", "F7", "compile.png", e -> compileProgram());
        JButton teamButton = createToolBarButton("Equipe", "F1", "team.png", e -> showTeamInfo());
        toolBar.add(compileButton);
        toolBar.add(teamButton);

        return toolBar;
    }

    private JButton createToolBarButton(String text, String toolTip, String iconPath, ActionListener action) {
        JButton button = new JButton(text);
        button.setToolTipText(toolTip);
        button.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(toolTip.charAt(toolTip.length() - 1)));
        button.addActionListener(action);
        // Configurar o ícone aqui, se necessário
        return button;
    }

    private void newFile() {
        editorArea.setText("");
        messageArea.setText("");
        statusBar.setText("Arquivo: Nenhum arquivo aberto");
    }

    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                editorArea.read(reader, null);
                messageArea.setText("");
                statusBar.setText("Arquivo: " + file.getAbsolutePath());
            } catch (IOException e) {
                messageArea.setText("Erro ao abrir o arquivo.");
            }
        }
    }

    private void saveFile() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                editorArea.write(writer);
                messageArea.setText("");
                statusBar.setText("Arquivo: " + file.getAbsolutePath());
            } catch (IOException e) {
                messageArea.setText("Erro ao salvar o arquivo.");
            }
        }
    }

    private void compileProgram() {
        messageArea.setText("Compilação de programas ainda não foi implementada.");
    }

    private void showTeamInfo() {
        messageArea.setText("Equipe: Nome1, Nome2, Nome3");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gui gui = new gui();
            gui.setVisible(true);
        });
    }
}


//issoéumtestefoda