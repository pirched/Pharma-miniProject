package com.gichuru.pharma;
import javax.swing.*;
import java.awt.*;

public class CommonGUIForm extends JFrame {

    public CommonGUIForm() {
        super("Pharma");

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        Image icon= Toolkit.getDefaultToolkit().getImage("src/com/gichuru/pharma/cross.png");

        setContentPane(backgroundPanel);
        setIconImage(icon);
        setLayout(new GridLayout());
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
