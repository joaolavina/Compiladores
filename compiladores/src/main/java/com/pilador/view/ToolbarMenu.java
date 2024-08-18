package com.pilador.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolbarMenu extends JToolBar {

    public ToolbarMenu() {
        setPreferredSize(new Dimension(900, 70));
    }

    //     private JToolBar createToolBar() {
    //     JToolBar toolBar = new JToolBar();
    //     toolBar.setFloatable(false);

    //     // Botões para mexer nos arquivos
    //     JButton newButton = createToolBarButton("Novo", "Ctrl+N", "new.png", e -> newFile());
    //     JButton openButton = createToolBarButton("Abrir", "Ctrl+O", "open.png", e -> openFile());
    //     JButton saveButton = createToolBarButton("Salvar", "Ctrl+S", "save.png", e -> saveFile());
    //     toolBar.add(newButton);
    //     toolBar.add(openButton);
    //     toolBar.add(saveButton);

    //     // Atalhos de texto
    //     JButton copyButton = createToolBarButton("Copiar", "Ctrl+C", "copy.png", e -> editorArea.copy());
    //     JButton pasteButton = createToolBarButton("Colar", "Ctrl+V", "paste.png", e -> editorArea.paste());
    //     JButton cutButton = createToolBarButton("Recortar", "Ctrl+X", "cut.png", e -> editorArea.cut());
    //     toolBar.add(copyButton);
    //     toolBar.add(pasteButton);
    //     toolBar.add(cutButton);

    //     // Botões de compilar
    //     JButton compileButton = createToolBarButton("Compilar", "F7", "compile.png", e -> compileProgram());
    //     JButton teamButton = createToolBarButton("Equipe", "F1", "team.png", e -> showTeamInfo());
    //     toolBar.add(compileButton);
    //     toolBar.add(teamButton);

    //     return toolBar;
    // }

    // private JButton createToolBarButton(String text, String toolTip, String iconPath, ActionListener action) {
    //     JButton button = new JButton(text);
    //     button.setToolTipText(toolTip);
    //     button.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(toolTip.charAt(toolTip.length() - 1)));
    //     button.addActionListener(action);
    //     // Configurar o ícone aqui, se necessário
    //     return button;
    // }

    // private void compileProgram() {
    //     messageArea.setText("Compilação de programas ainda não foi implementada.");
    // }

    // private void showTeamInfo() {
    //     messageArea.setText("Equipe: Nome1, Nome2, Nome3");
    // }
}
