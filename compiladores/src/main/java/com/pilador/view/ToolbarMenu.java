package com.pilador.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.pilador.controller.KeyEventController;

public class ToolbarMenu extends JToolBar {

    private JButton[] btns = new JButton[8];
    private KeyEventController controller;

    public ToolbarMenu( KeyEventController controller) {
        this.controller = controller;
        setPreferredSize(new Dimension(900, 70));
        setFloatable(false);

        // ! TROCAR ICONES, MENOR
        btns[0] = createButton("Novo", "Ctrl+N");
        btns[1] = createButton("Abrir", "Ctrl+O");
        btns[2] = createButton("Salvar", "Ctrl+S");
        btns[3] = createButton("Copiar", "Ctrl+C");
        btns[4] = createButton("Colar", "Ctrl+V");
        btns[5] = createButton("Cortar", "Ctrl+X");
        btns[6] = createButton("Compilar", "F7");
        btns[7] = createButton("Equipe", "F1");

        for (JButton btn : btns) {
            add(btn);
        }
    }

    private JButton createButton(String text, String toolTip) {
        String iconPath = "compiladores/src/main/resources/images/" + text + ".png";
        Icon icon = new ImageIcon(iconPath);
        JButton btn = new JButton(text + " [" + toolTip + "] ", icon);
        btn.setToolTipText(toolTip);

        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);

        // TESTAR COM/SEM CONDIÃ‡ÃƒO JComponent.WHEN_IN_FOCUSED_WINDOW
        char keyEventChar = extractKeyEvent(toolTip);
        int indexAscii = getKeyEvent(keyEventChar);
        KeyStroke keyStroke;
        if (indexAscii == KeyEvent.VK_F1 || indexAscii == KeyEvent.VK_F7)
            keyStroke = KeyStroke.getKeyStroke("F" + keyEventChar);
        else
            keyStroke = KeyStroke.getKeyStroke(indexAscii, InputEvent.CTRL_DOWN_MASK);

        btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, text);
        
        AbstractAction action = getAction(text);

        btn.getActionMap().put(text, action);
        
        btn.addActionListener(e -> action.actionPerformed(e));

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

    private AbstractAction getAction(String text) {
        return new AbstractAction (text) {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (text) {
                    case "Novo":
                        controller.newFile();
                        break;
                    case "Abrir":
                        System.out.println("Abrir");
                        break;
                    case "Salvar":
                        System.out.println("Salvar");
                        break;
                    case "Copiar":
                        System.out.println("Copiar");
                        break;
                    case "Colar":
                        System.out.println("Colar");
                        break;
                    case "Cortar":
                        System.out.println("Cortar");
                        break;
                    case "Compilar":
                        controller.compileProgram();
                        break;
                    case "Equipe":
                        controller.showTeamInfo();
                        break;
                }
            }
        };
    }
    
    public JButton getNewBtn(){
        return btns[0];
    }

    public JButton geOpenBtn(){
        return btns[1];
    }

    public JButton getSaveBtn(){
        return btns[2];
    }

    public JButton getCopyBtn(){
        return btns[3];
    }

    public JButton getPasteBtn(){
        return btns[4];
    }

    public JButton getCutBtn(){
        return btns[5];
    }

    public JButton getCompileBtn(){
        return btns[6];
    }

    public JButton getTeamBtn(){
        return btns[7];
    }
} 