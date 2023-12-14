package com.gichuru.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PharmacistRemove extends CommonGUIForm {
    private JButton btnSubmit;
    private JButton btnBack;
    private JButton btnExit;
    private JPanel pnlPanel;
    private JButton btnSearch;
    private JTextField txtDrug;
    private JLabel lblDrug;
    private JLabel lblExpire;
    private JTextField txtExpire;
    private JLabel lblPurchase;
    private JTextField txtPurchase;
    private JLabel lblQuantity;
    private JTextField txtQuantity;
    private JLabel lblDays;
    private JTextField txtDays2Expire;
    private JButton btnClearr;
    private JButton btnLogout;

    public PharmacistRemove() {
        super();
        setContentPane(pnlPanel);
        pack();
        txtExpire.setEditable(false);
        txtQuantity.setEditable(false);
        txtPurchase.setEditable(false);
        txtDays2Expire.setEditable(false);


        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Pharmacist();
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
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchData();
            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeData();
            }
        });

        btnClearr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDrug.setText("");
                txtExpire.setText("");
                txtQuantity.setText("");
                txtPurchase.setText("");
                txtDays2Expire.setText("");
            }
        });
    }

    private void fetchData() {
        if (txtDrug.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "The Drug Name is Required for Search");
        } else {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_victor_gichuru_167026?useSSL=false", "root", "kali");
                PreparedStatement pst = con.prepareStatement("SELECT * FROM tbl_drugs where drugName='"+txtDrug.getText()+"'");
                ResultSet rs = pst.executeQuery();
                PreparedStatement pst2 = con.prepareStatement("SELECT DATEDIFF(expireDate, purchaseDate) AS days2expire from tbl_drugs;");
                ResultSet rs2= pst2.executeQuery();
                while (rs.next()){
                    txtDrug.setEditable(false);
                    txtExpire.setText(rs.getString("expireDate"));
                    txtQuantity.setText(rs.getString("quantity"));
                    txtPurchase.setText(rs.getString("purchaseDate"));
                    if (rs2.next()){
                        int daysToShip = rs2.getInt("days2expire");
                        txtDays2Expire.setText(String.valueOf(daysToShip));
                    }
                }
                pst.close();
                pst2.close();
                rs.close();
                rs2.close();
                con.close();

            } catch (Exception e) {
                System.out.println(e);
                System.out.println("ERROR!!!");
            }
        }
    }





    private void removeData() {
        if (txtDrug.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error...Search for a Drug to Remove");
        } else {
            try {
                Connection con = DB.getCon();
                assert con != null;
                PreparedStatement pst = con.prepareStatement("SELECT * FROM tbl_drugs WHERE drugName = ?");
                pst.setString(1, txtDrug.getText());
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    PreparedStatement prst = con.prepareStatement("INSERT INTO tbl_removeddrugs SELECT * FROM tbl_drugs WHERE drugName = ?");
                    prst.setString(1, txtDrug.getText());
                    prst.executeUpdate();

                    PreparedStatement prqst = con.prepareStatement("DELETE FROM tbl_drugs WHERE drugName = ?");
                    prqst.setString(1, txtDrug.getText());
                    prqst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Drug removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Drug not found.");
                }

                rs.close();
                pst.close();
                con.close();

                txtDrug.setEditable(true);
                txtDrug.setText("");
                txtExpire.setText("");
                txtQuantity.setText("");
                txtPurchase.setText("");
                txtDays2Expire.setText("");
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Error Removing Drug.");
            }
        }
    }




    public static void main(String[] args) {
        new PharmacistRemove();
    }
}