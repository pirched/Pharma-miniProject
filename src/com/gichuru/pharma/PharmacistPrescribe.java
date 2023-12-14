package com.gichuru.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PharmacistPrescribe extends CommonGUIForm{

    private JTextField txtDrugName;
    private JLabel lblDrugName;
    private JLabel lblPatient;
    private JTextField txtPatient;
    private JPanel pnlPanel;
    private JLabel lblForm;
    private JTextField txtForm;
    private JLabel lblQuantity;
    private JTextField txtQuantity;
    private JLabel lblUnitPrice;
    private JTextField txtUnitPrice;
    private JTextField txtPDate;
    private JLabel lblPDate;
    private JLabel lblEXDate;
    private JTextField txtEXDate;
    private JButton btnBack;
    private JButton btnExit;
    private JLabel lblComments;
    private JTextArea txtComments;
    private JTextField txtTotal;
    private JButton btnConfirm;
    private JButton btnLogout;
    double total;

    public PharmacistPrescribe(int selectedRow, DefaultTableModel tableModel){
        setContentPane(pnlPanel);
        pack();
        String drugName = tableModel.getValueAt(selectedRow, 0).toString();
        String patientName= tableModel.getValueAt(selectedRow, 1).toString();
        String form = tableModel.getValueAt(selectedRow, 2).toString();
        String quantity = tableModel.getValueAt(selectedRow, 4).toString();
        String unitPrice = tableModel.getValueAt(selectedRow, 5).toString();
        String PDate= tableModel.getValueAt(selectedRow, 6).toString();
        String EXDate= tableModel.getValueAt(selectedRow, 7).toString();
        txtDrugName.setText(drugName);
        txtPatient.setText(patientName);
        txtForm.setText(form);
        txtQuantity.setText(quantity);
        txtUnitPrice.setText(unitPrice);
        txtPDate.setText(PDate);
        txtEXDate.setText(EXDate);

        total = Double.parseDouble(quantity) * Double.parseDouble(unitPrice);
        String formattedTotal = String.format("%.2f", total);

        txtTotal.setText("Kshs." +formattedTotal);
        txtDrugName.setEditable(false);
        txtPatient.setEditable(false);
        txtForm.setEditable(false);
        txtQuantity.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtPDate.setEditable(false);
        txtEXDate.setEditable(false);
        txtTotal.setEditable(false);


        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPayment();

            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PharmacistSell();
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
    }
    private void confirmPayment(){
        if(txtTotal.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Fill in required fields");
        } else {
            try{
                Connection con=DB.getCon();
                assert con != null;
                PreparedStatement fetchIDPst = con.prepareStatement("SELECT prescriptionID FROM tbl_prescribeddrugs WHERE drugName=? AND patientName=?");
                fetchIDPst.setString(1, txtDrugName.getText());
                fetchIDPst.setString(2, txtPatient.getText());
                ResultSet rs = fetchIDPst.executeQuery();
                if (rs.next()){
                    int pID=rs.getInt("prescriptionID");
                    PreparedStatement pst = con.prepareStatement("UPDATE tbl_prescribeddrugs SET total=?, comments=? WHERE prescriptionID=?");

                    pst.setString(1, String.valueOf(total));
                    pst.setString(2,txtComments.getText());
                    pst.setString(3, String.valueOf(pID));
                    pst.executeUpdate();
                    pst.close();

                }
                fetchIDPst.close();
                con.close();
                JOptionPane.showMessageDialog(null,"Success !!!");

            }catch (Exception e){
                System.out.println(e);
            }
        }
    }



    public static void main(String[] args) {
        DefaultTableModel defaultTableModel = new DefaultTableModel();

        // For testing, you can use a default selected row index
        int defaultSelectedRow = 0;

        new PharmacistPrescribe(defaultSelectedRow, defaultTableModel);
    }
}

