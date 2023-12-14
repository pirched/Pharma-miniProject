package com.gichuru.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Objects;

public class AdminAdd extends CommonGUIForm{
    private JTextField txtName;
    private JLabel lblName;
    private JLabel lblRole;
    private JComboBox cmbRole;
    private JTextField txtUsername;
    private JLabel lblUsername;
    private JLabel lblPass;
    private JPasswordField txtPass;
    private JPasswordField txtPass2;
    private JLabel lblPass2;
    private JButton btnExit;
    private JButton btnBack;
    private JButton btnSubmit;
    private JLabel lblWelcome;
    private JPanel pnlPanel;
    private JLabel lblPhone;
    private JTextField txtPhone;
    private JButton btnLogout;
    private JButton btnHome;

    public AdminAdd(){
        super();
        setContentPane(pnlPanel);
        pack();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
                dispose();

            }
        });
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewUser();
            }
        });
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
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

    private void addNewUser(){
        try {
            if (txtName.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPhone.getText().isEmpty() ||
                    Arrays.equals(txtPass.getPassword(), new char[0]) || Arrays.equals(txtPass2.getPassword(), new char[0])) {

                JOptionPane.showMessageDialog(this, "Please fill in all the required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);

                return;
            }
            String phoneNumber = txtPhone.getText();
            if (usernameUnique(txtUsername.getText())) {
                if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit phone number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(Arrays.equals(txtPass.getPassword(), txtPass2.getPassword())){

                    Connection con = DB.getCon();
                    assert con != null;
                    PreparedStatement pst = con.prepareStatement("insert into tbl_user(username, password, role, name, phone) values(?,?,?,?,?) ");
                    pst.setString(1,txtUsername.getText());
                    pst.setString(2, new String(txtPass2.getPassword()));
                    pst.setString(3, Objects.requireNonNull(cmbRole.getSelectedItem()).toString());
                    pst.setString(4,txtName.getText());
                    pst.setString(5,txtPhone.getText());

                    pst.executeUpdate();
                    pst.close();
                    con.close();
                    JOptionPane.showMessageDialog(null,"Success");
                } else {
                    JOptionPane.showMessageDialog(null,"Password fields do not Match! \n Try Again","Confirm Password Mismatch",JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.",
                        "Duplicate Username", JOptionPane.ERROR_MESSAGE);
            }



        }catch (Exception e){
            System.out.println(e);
        }
    }
    private boolean usernameUnique(String username){
        try {
            Connection con = DB.getCon();
            assert con != null;
            PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM tbl_user WHERE username = ?");
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            return rs.next() && rs.getInt(1) == 0;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
    public static void main(String[] args) {
        AdminAdd obj1= new AdminAdd();
    }
}
