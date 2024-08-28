package com.pilador.view;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class StatusBar extends JLabel {

    public StatusBar() {
        setPreferredSize(new Dimension(900, 25));
        setMinimumSize(new Dimension(900, 25));
        setBorder(new EmptyBorder(0,10,0,0));
    }

    public void setStatusBarText(String text) {
        cleanStatusBar();
        super.setText(text);
    }

    public void cleanStatusBar() {
        setText("");
    }
}
