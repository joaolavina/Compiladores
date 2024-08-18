package com.pilador.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class WindowFrame extends JFrame {

    public WindowFrame() {
        setTitle("Compilador");
        setSize(910, 600);
        setMinimumSize(new Dimension(910, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
    }
}
