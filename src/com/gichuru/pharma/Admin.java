package com.gichuru.pharma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin extends CommonGUIForm {
    private JPanel pnlAdmin;
    private JButton btnAdd;
    private JButton btnExit;
    private JButton btnRemove;
    private JButton btnUpdate;
    private JButton btnView;
    private JButton btnHome;
    private JButton btnLogout;
    private JLabel lblWelcome;

    public Admin() {
        setLayout(new GridLayout());
        setContentPane(pnlAdmin);
        pack();

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminAdd();
                dispose();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminRemove();
                dispose();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminUpdate();
                dispose();
            }
        });
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminView();
                dispose();
            }
        });
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Admin();

            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
    }
    public static void main(String[] args) {
        Admin obj1=new Admin();
    }

}

