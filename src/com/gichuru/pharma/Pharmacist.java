package com.gichuru.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pharmacist extends CommonGUIForm{
    private JButton btnAddNew;
    private JButton btnRemoveExpired;
    private JButton btnSell;
    private JButton btnExit;
    private JButton btnBack;
    private JPanel pnlPanel;
    private JLabel lblWelcome;
    private JButton btnLogout;
    private JButton btnView;

    public Pharmacist(){
        super();
        setContentPane(pnlPanel);
        pack();

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PharmacistAdd user1=new PharmacistAdd();
                dispose();
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
        btnRemoveExpired.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PharmacistRemove user1=new PharmacistRemove();
                dispose();
            }
        });
        btnSell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PharmacistSell obj1=new PharmacistSell();
                dispose();
            }
        });
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PharmacistView();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Pharmacist user1=new Pharmacist();
    }
}
