package com.gichuru.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;

public class AdminUpdate extends CommonGUIForm{
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
    private JButton btnUpdate;
    private JLabel lblWelcome;
    private JPanel pnlPanel;
    private JLabel lblPhone;
    private JTextField txtPhone;
    private JButton btnSearch;
    private JButton btnLogout;
    private JButton btnHome;

    public AdminUpdate(){
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
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUser();
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
                searchUser();
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

    private void updateUser(){
        try {
            if (txtName.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPhone.getText().isEmpty() ||
                    Arrays.equals(txtPass.getPassword(), new char[0]) || Arrays.equals(txtPass2.getPassword(), new char[0])) {

                JOptionPane.showMessageDialog(this, "Please fill in all the required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);

                return;
            }
            String phoneNumber = txtPhone.getText();

                if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit phone number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(Arrays.equals(txtPass.getPassword(), txtPass2.getPassword())){

                    Connection con = DB.getCon();
                    assert con != null;
                    PreparedStatement pst = con.prepareStatement("update tbl_user set password=?, role=?, name=?, phone=? WHERE username=?");
                    pst.setString(1, new String(txtPass2.getPassword()));
                    pst.setString(2, Objects.requireNonNull(cmbRole.getSelectedItem()).toString());
                    pst.setString(3,txtName.getText());
                    pst.setString(4,txtPhone.getText());
                    pst.setString(5, txtUsername.getText());

                    pst.executeUpdate();
                    pst.close();
                    con.close();
                    JOptionPane.showMessageDialog(null,"UPDATE Success");
                } else {
                    JOptionPane.showMessageDialog(null,"Password fields do not Match! \n Try Again","Confirm Password Mismatch",JOptionPane.WARNING_MESSAGE);
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
    private void searchUser(){
        try {
            if (txtUsername.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter a Username");
            } else {
                Connection con = DB.getCon();
                assert con != null;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM tbl_user where username='"+txtUsername.getText()+"'");
                while (rs.next()){
                    txtUsername.setEditable(false);
                    txtName.setText(rs.getString("name"));
                    txtPhone.setText(rs.getString("phone"));
                    txtPass.setText(rs.getString("password"));
                    String role = rs.getString("role");
                    cmbRole.setSelectedItem(role);
                }
                rs.close();
                st.close();
                con.close();

            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
    public static void main(String[] args) {
        new AdminUpdate();
    }
}
