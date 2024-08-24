package com.pilador.view;

import java.awt.Dimension;

import javax.swing.JLabel;

public class StatusBar extends JLabel {



    public StatusBar() {
        setPreferredSize(new Dimension(900, 25));
        setMinimumSize(new Dimension(900, 25));
    }

    public void setStatusBarText(String text) {
        this.cleanStatusBar();
        setText(text);
    }

    public void cleanStatusBar() {
        setText("");
    }
}
