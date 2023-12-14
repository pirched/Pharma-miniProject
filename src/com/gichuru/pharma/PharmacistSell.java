package com.gichuru.pharma;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PharmacistSell extends CommonGUIForm{

    private JScrollPane scrlPane;
    private JTable tblView;
    private JButton btnBack;
    private JButton btnExit;
    private JPanel pnlPanel;
    private JLabel lblWelcome;
    private JButton btnLogout;

    public PharmacistSell(){

        setContentPane(pnlPanel);
        pack();
        tblView.setModel(new DefaultTableModel());
        displayDrugs();

        tblView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblView.getSelectedRow();
                    if (selectedRow != -1) {
                        DefaultTableModel tableModel = (DefaultTableModel) tblView.getModel();
                        new PharmacistPrescribe(selectedRow, tableModel);
                        dispose();

                    }
                }
            }
        });
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
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
                new Pharmacist();
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
            PreparedStatement pst = con.prepareStatement("select * from tbl_prescribeddrugs");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Drug Name");
            model.addColumn("Patient Name");
            model.addColumn("Form");
            model.addColumn("Manufacturer");
            model.addColumn("Quantity");
            model.addColumn("Unit Price");
            model.addColumn("Prescription Date (YYYY-MM-DD)");
            model.addColumn("Expiry Date (YYYY-MM-DD)");
            while (rs.next()) {
                Object[] row = {
                        rs.getString("drugName"),
                        rs.getString("patientName"),
                        rs.getString("form"),
                        rs.getString("manufacturer"),
                        rs.getString("quantity"),
                        rs.getString("unitPrice"),
                        rs.getString("prescribeDate"),
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
        new PharmacistSell();
    }
}

