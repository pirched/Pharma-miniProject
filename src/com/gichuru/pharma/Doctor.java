package com.gichuru.pharma;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Doctor extends CommonGUIForm {
    private JButton btnDoc;
    private JButton btnExit;
    private JButton btnBack;
    private JPanel pnlPanel;
    private JLabel lblDoc;
    private JButton btnView;
    private JButton btnLogout;

    public Doctor() {
        super();
        setContentPane(pnlPanel);


        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        setVisible(true);
        btnDoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorPrescribe();
                dispose();
            }
        });
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorView();
            }
        });
    }

    public static void main(String[] args) {
        Doctor obj1=new Doctor();
    }
}
