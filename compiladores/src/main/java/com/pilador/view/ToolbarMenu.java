package com.pilador.view;

import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class ToolbarMenu extends JToolBar {

    private JButton[] btns = new JButton[8];

    public ToolbarMenu() {
        setPreferredSize(new Dimension(900, 70));
        setFloatable(false);

        btns[0] = createButton("Novo", "Ctrl+N", "newIcon.png");
        add(btns[0]);
        btns[1] = createButton("Abrir", "Ctrl+O", "openIcon.png");
        add(btns[1]);
        btns[2] = createButton("Salvar", "Ctrl+S", "saveIcon.png");
        add(btns[2]);
        btns[3] = createButton("Copiar", "Ctrl+C", "copyIcon.png");
        add(btns[3]);
        btns[4] = createButton("Colar", "Ctrl+V", "pasteIcon.png");
        add(btns[4]);
        btns[5] = createButton("Cortar", "Ctrl+X", "cutIcon.png");
        add(btns[5]);
        btns[6] = createButton("Compilar", "F7", "compileIcon.png");
        add(btns[6]);
        btns[7] = createButton("Equipe", "F1", "teamIcon.png");
        add(btns[7]);
    }

    private JButton createButton(String text, String toolTip, String iconName) {
        String iconPath = "compiladores/src/main/resources/images/" + iconName;
        Icon icon = new ImageIcon(iconPath);
        JButton btn = new JButton(text + " [" + toolTip + "] ", icon);
        btn.setToolTipText(toolTip);

        // TESTAR COM/SEM CONDIÇÃO JComponent.WHEN_IN_FOCUSED_WINDOW
        char keyEventChar = extractKeyEvent(toolTip);
        int indexAscii = getKeyEvent(keyEventChar);
        KeyStroke keyStroke;
        if (indexAscii == KeyEvent.VK_F1 || indexAscii == KeyEvent.VK_F7)
            keyStroke = KeyStroke.getKeyStroke("F" + keyEventChar);
        else
            keyStroke = KeyStroke.getKeyStroke(indexAscii, InputEvent.CTRL_DOWN_MASK);

        // btn.getInputMap().put(keyStroke, text);
        // btn.getActionMap().put( );
        return btn;
    }

    private char extractKeyEvent(String toolTip) {
        return toolTip.charAt(toolTip.length() - 1);
    }

    private int getKeyEvent(char keyChar) {

        int keyEvent;

        switch (keyChar) {
            case 'N':
                keyEvent = KeyEvent.VK_N;
                break;
            case 'O':
                keyEvent = KeyEvent.VK_O;
                break;
            case 'S':
                keyEvent = KeyEvent.VK_S;
                break;
            case 'C':
                keyEvent = KeyEvent.VK_C;
                break;
            case 'V':
                keyEvent = KeyEvent.VK_V;
                break;
            case 'X':
                keyEvent = KeyEvent.VK_X;
                break;
            case '7':
                keyEvent = KeyEvent.VK_F7;
                break;
            case '1':
                keyEvent = KeyEvent.VK_F1;
                break;
            default:
                keyEvent = 0;
                break;
        }

        return keyEvent;
    }

    // butExcluir.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X,
    // InputEvent.CTRL_DOWN_MASK), “evento”);

    // private JButton createToolBarButton(String text, String toolTip, String
    // iconPath, ActionListener action) {
    // JButton button = new JButton(text);
    // button.setToolTipText(toolTip);
    // button.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(toolTip.charAt(toolTip.length()
    // - 1)));
    // button.addActionListener(action);
    // // Configurar o ícone aqui, se necessário
    // return button;
    // }

    // private JToolBar createToolBar() {

    // // Botões para mexer nos arquivos
    // JButton newButton = createToolBarButton("Novo", "Ctrl+N", "new.png", e ->
    // newFile());
    // JButton openButton = createToolBarButton("Abrir", "Ctrl+O", "open.png", e ->
    // openFile());
    // JButton saveButton = createToolBarButton("Salvar", "Ctrl+S", "save.png", e ->
    // saveFile());
    // toolBar.add(newButton);
    // toolBar.add(openButton);
    // toolBar.add(saveButton);

    // // Atalhos de texto
    // JButton copyButton = createToolBarButton("Copiar", "Ctrl+C", "copy.png", e ->
    // editorArea.copy());
    // JButton pasteButton = createToolBarButton("Colar", "Ctrl+V", "paste.png", e
    // -> editorArea.paste());
    // JButton cutButton = createToolBarButton("Recortar", "Ctrl+X", "cut.png", e ->
    // editorArea.cut());
    // toolBar.add(copyButton);
    // toolBar.add(pasteButton);
    // toolBar.add(cutButton);

    // // Botões de compilar
    // JButton compileButton = createToolBarButton("Compilar", "F7", "compile.png",
    // e -> compileProgram());
    // JButton teamButton = createToolBarButton("Equipe", "F1", "team.png", e ->
    // showTeamInfo());
    // toolBar.add(compileButton);
    // toolBar.add(teamButton);

    // return toolBar;
    // }

    // private void compileProgram() {
    // messageArea.setText("Compilação de programas ainda não foi implementada.");
    // }

    // private void showTeamInfo() {
    // messageArea.setText("Equipe: Nome1, Nome2, Nome3");
    // }
}
