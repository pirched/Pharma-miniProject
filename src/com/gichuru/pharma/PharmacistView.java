package com.gichuru.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PharmacistView extends CommonGUIForm{
    private JButton btnLogout;
    private JButton btnBack;
    private JButton btnExit;
    private JTable tblView;
    private JScrollPane sclPane;
    private JPanel pnlPanel;

    public PharmacistView() {
        setContentPane(pnlPanel);

        tblView.setModel(new DefaultTableModel());
        viewDrugs();
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Pharmacist();
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
        setSize(1000,350);
    }
    private void viewDrugs(){
        try{

            Connection con = DB.getCon();
            assert con != null;
            PreparedStatement pst = con.prepareStatement("select * from tbl_drugs");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                return false;
                }
            };

            model.addColumn("Drug Name");
            model.addColumn("Manufacturer");
            model.addColumn("Form");
            model.addColumn("Quantity");
            model.addColumn("Unit Price");
            model.addColumn("Supplier");
            model.addColumn("Purchase Date");
            model.addColumn("Expiry Date");

            while (rs.next()) {
                Object[] row = {
                        rs.getString("drugName"),
                        rs.getString("manufacturer"),
                        rs.getString("form"),
                        rs.getString("quantity"),
                        rs.getString("unitPrice"),
                        rs.getString("supplier"),
                        rs.getString("purchaseDate"),
                        rs.getString("expireDate"),
                };
            model.addRow(row);
            }
            tblView.setModel(model);


            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
        System.out.println(e);
            }
    }

    public static void main(String[] args) {
        new PharmacistView();
    }
}
