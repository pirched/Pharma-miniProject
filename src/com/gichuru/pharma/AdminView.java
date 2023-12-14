package com.gichuru.pharma;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminView extends CommonGUIForm{
    private JPanel pnlPanel;
    private JTable tblView;
    private JScrollPane scrlPane;
    private JButton btnBack;
    private JButton btnExit;
    private JButton btnLogout;

    public AdminView() {
        setContentPane(pnlPanel);
        pack();
        tblView.setModel(new DefaultTableModel());
        displayUsers();
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
                dispose();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    private void displayUsers() {
        try {
            Connection con = DB.getCon();
            assert con != null;
            PreparedStatement pst = con.prepareStatement("select * from tbl_user");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make all cells uneditable
                    return false;
                }
            };
            model.addColumn("Name");
            model.addColumn("Username");
            model.addColumn("Password");
            model.addColumn("Role");
            model.addColumn("Phone Number");
            while (rs.next()) {
                Object[] row = {
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("phone"),
                };
                model.addRow(row);
            }
            tblView.setModel(model);
            tblView.getColumnModel().getColumn(2).setCellRenderer(new PasswordRenderer());
            tblView.getColumnModel().getColumn(2).setCellEditor(new PasswordEditor());

            rs.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private static class PasswordRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            // Mask the password with asterisks
            if (value != null) {
                super.setValue("*".repeat(value.toString().length()));
            } else {
                super.setValue(null);
            }
        }
    }
    private static class PasswordEditor extends DefaultCellEditor {
        public PasswordEditor() {
            super(new JPasswordField());
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            final Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
            if (c instanceof JTextComponent) {
                SwingUtilities.invokeLater(((JTextComponent) c)::selectAll);
            }
            return c;
        }
    }

    public static void main(String[] args) {
        new AdminView();
    }
}
