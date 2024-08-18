package com.pilador.view;

import java.awt.Component;

import javax.swing.JSplitPane;

public class DividerPane extends JSplitPane{

        public DividerPane(Component x, Component y) {
             super(JSplitPane.VERTICAL_SPLIT, x, y);
             setResizeWeight(0.75);
        }

}
