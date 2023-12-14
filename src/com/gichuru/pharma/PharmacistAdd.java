package com.gichuru.pharma;


import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class PharmacistAdd extends CommonGUIForm {

    private JTextField txtDname;
    private JTextField txtManufacturer;
    private JTextField txtQuantity;
    private JButton btnBack;
    private JButton btnExit;
    private JLabel lblDname;
    private JLabel lblManufacturer;
    private JLabel lblExpire;
    private JLabel lblQuantity;
    private JTextField txtUnitPrice;
    private JLabel lblUnitPrice;
    private JTextField txtSupplier;
    private JLabel lblSupplier;
    private JLabel lblDateofPurchase;
    private JLabel lblComments;
    private JTextArea txtArea;
    private JButton btnSubmit;
    private JPanel pnlPanel;
    private DatePicker dtExp;
    private DatePicker dtPurchase;
    private JButton btnClear;
    private JTextField txtForm;
    private JLabel lblForm;
    private JComboBox cmbForm;
    private JLabel lblWelcome;
    private JButton btnLogout;


    public PharmacistAdd() {
        super();
        setContentPane(pnlPanel);
        pack();



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
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertIntoDatabase();

            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearInputFields();
            }
        });
    }
    private void insertIntoDatabase(){
        try {
            if (txtDname.getText().isEmpty() || txtManufacturer.getText().isEmpty() || txtQuantity.getText().isEmpty() || txtUnitPrice.getText().isEmpty() ||
                    txtSupplier.getText().isEmpty()  ||
                    dtExp.getDate() == null || dtPurchase.getDate() == null) {


                JOptionPane.showMessageDialog(this, "Please fill in all the required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);


                return;
            }
            Connection con = DB.getCon();
            assert con != null;
            try {
                double quantity = Integer.parseInt(txtQuantity.getText());
                double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Quantity and Unit Price.\n PS: Without Decimal Places", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement pst = con.prepareStatement("insert into tbl_drugs(drugName, manufacturer, expireDate,quantity, unitPrice, supplier, purchaseDate, notes, form) values(?,?,?,?,?,?,?,?,?) ");

            pst.setString(1, txtDname.getText());
            pst.setString(2, txtManufacturer.getText());
            pst.setDate(3, Date.valueOf(dtExp.getDate()));
            pst.setString(4, String.valueOf(Integer.parseInt(txtQuantity.getText())));
            pst.setDouble(5, Double.parseDouble(txtUnitPrice.getText()));
            pst.setString(6, txtSupplier.getText());
            pst.setDate(7, Date.valueOf(dtPurchase.getDate()));
            pst.setString(8, txtArea.getText());
            pst.setString(9, (String) cmbForm.getSelectedItem());

            pst.executeUpdate();
            pst.close();
            con.close();
            JOptionPane.showMessageDialog(null,"Successful insert of Record","SUCCESS!!!",JOptionPane.INFORMATION_MESSAGE);
            clearInputFields();

        } catch (Exception e){
            System.out.println(e);
            System.out.println("ERROR!!!");

        }


    }
    private void clearInputFields() {
        txtDname.setText("");
        txtManufacturer.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");
        txtSupplier.setText("");
        txtArea.setText("");
        dtExp.setDate(null);
        dtPurchase.setDate(null);
        cmbForm.setSelectedItem("");
    }

    public static void main(String[] args) {
        new PharmacistAdd();
    }

}