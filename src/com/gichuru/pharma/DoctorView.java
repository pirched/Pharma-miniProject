package com.gichuru.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

public class DoctorView extends CommonGUIForm{
    private JTable tblView;
    private JPanel pnlPanel;
    private JScrollPane scrlPane;
    private JButton btnBack;
    private JButton btnExit;
    private JButton btnLogout;

    public DoctorView(){
        setContentPane(pnlPanel);
        pack();
        tblView.setModel(new DefaultTableModel());
        displayDrugs();

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
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Doctor();
                dispose();
            }
        });
        setSize(1000,300);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tblView.getColumnCount(); i++) {
            tblView.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private void displayDrugs() {
        try {
            Connection con = DB.getCon();
            assert con != null;
            PreparedStatement pst = con.prepareStatement("select * from tbl_drugs");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Drug ID");
            model.addColumn("Drug Name");
            model.addColumn("Form");
            model.addColumn("Manufacturer");
            model.addColumn("Quantity");
            model.addColumn("Unit Price");
            model.addColumn("Purchase Date (YYYY-MM-DD)");
            model.addColumn("Expiry Date (YYYY-MM-DD)");
            while (rs.next()) {
                Object[] row = {
                        rs.getString("drugID"),
                        rs.getString("drugName"),
                        rs.getString("form"),
                        rs.getString("manufacturer"),
                        rs.getString("quantity"),
                        rs.getString("unitPrice"),
                        rs.getString("purchaseDate"),
                        rs.getString("expireDate")
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
        new DoctorView();
    }
}

