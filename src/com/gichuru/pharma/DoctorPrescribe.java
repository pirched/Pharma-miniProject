package com.gichuru.pharma;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.*;

public class DoctorPrescribe extends CommonGUIForm {
    private JButton btnExit;
    private JButton btnBack;
    private JPanel pnlPanel;
    private JLabel lblDrug;
    private JTextField txtDrug;
    private JLabel lblExpire;
    private JTextField txtExpire;
    private JLabel lblDofPrescription;
    private JButton btnSearch;
    private JLabel lblQuantity;
    private JTextField txtQuantity;
    private JLabel lblPrescription;
    private JTextField txtPrescription;
    private JButton btnPrint;
    private JButton btnClearr;
    private JLabel lblNotes;
    private JTextArea txtNotes;
    private JTextField txtManufacturer;
    private JLabel lblManufacturer;
    private JLabel lblForm;
    private JTextField txtForm;
    private DatePicker dtPicker;
    private JTextField txtPatient;
    private JLabel lblPatient;
    private JButton btnLogout;

    public DoctorPrescribe(){
        super();
        setContentPane(pnlPanel);
        pack();
        setVisible(true);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Doctor();
                dispose();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDrug();
            }
        });
        btnClearr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDrug.setEditable(true);
                txtDrug.setText("");
                txtManufacturer.setText("");
                txtQuantity.setText("");
                txtForm.setText("");
                txtExpire.setText("");
                txtQuantity.setText("");
                txtPrescription.setText("");
                dtPicker.setDate(null);
                txtNotes.setText("");
                txtPatient.setText("");
            }
        });
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printReceipt();
            }
        });
        btnLogout.addComponentListener(new ComponentAdapter() {
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
    }

    private void searchDrug(){
        if (txtDrug.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter Drug to Prescribe");
        }else{
            try {
                Connection con = DB.getCon();
                assert con != null;
                PreparedStatement pst=con.prepareStatement("select * from tbl_drugs where drugName=?");
                pst.setString(1,txtDrug.getText());
                ResultSet rs=pst.executeQuery();

                while(rs.next()){
                    txtManufacturer.setText(rs.getString("manufacturer"));
                    txtForm.setText(rs.getString("form"));
                    txtExpire.setText(rs.getString("expireDate"));
                }
                txtExpire.setEditable(false);
                txtForm.setEditable(false);
                txtManufacturer.setEditable(false);
                txtDrug.setEditable(false);


                pst.close();
                con.close();
            }catch (Exception e){
                System.out.println(e);
            }
        }

    }
    private void printReceipt(){

        if(txtQuantity.getText().isEmpty() || txtPrescription.getText().isEmpty() ||
                txtQuantity.getText().isEmpty() || dtPicker.getDate() == null){
            JOptionPane.showMessageDialog(null,"Fill all required Fields Appropriately");
        } else {
            try {

                Connection con = DB.getCon();
                assert con != null;
                try {
                    double quantity = Integer.parseInt(txtQuantity.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Quantity and Unit Price.\n PS: Without Decimal Places", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PreparedStatement pst = con.prepareStatement("select * from tbl_drugs where drugName=?");
                pst.setString(1, txtDrug.getText());
                ResultSet rs = pst.executeQuery();
                String id = null;
                while (rs.next()) {
                    id = rs.getString("drugID");
                }


                PreparedStatement pst2 = con.prepareStatement("insert into tbl_prescribeddrugs(drugID, drugName, manufacturer, expireDate, quantity, prescription, prescribeDate, notes, form, patientName) values(?,?,?,?,?,?,?,?,?,?)");
                pst2.setString(1, id);
                pst2.setString(2, txtDrug.getText());
                pst2.setString(3, txtManufacturer.getText());
                pst2.setString(4, txtExpire.getText());
                pst2.setString(5, txtQuantity.getText());
                pst2.setString(6,txtPrescription.getText());
                pst2.setDate(7, Date.valueOf(dtPicker.getDate()));
                pst2.setString(8, txtNotes.getText());
                pst2.setString(9, txtForm.getText());
                pst2.setString(10, txtPatient.getText());

                pst2.executeUpdate();

                pst.close();
                rs.close();
                pst2.close();
                con.close();
                JOptionPane.showMessageDialog(null,"SUCCESS");

                txtDrug.setEditable(true);
                txtDrug.setText("");
                txtManufacturer.setText("");
                txtQuantity.setText("");
                txtForm.setText("");
                txtExpire.setText("");
                txtQuantity.setText("");
                txtPrescription.setText("");
                dtPicker.setDate(null);
                txtNotes.setText("");
                txtPatient.setText("");

            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
    public static void main(String[] args) {
        new DoctorPrescribe();
    }
}
