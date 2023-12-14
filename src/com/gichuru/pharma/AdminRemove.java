package com.gichuru.pharma;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminRemove extends CommonGUIForm{
    private JPanel pnlPanel;
    private JLabel lblWelcome;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JButton btnSearch;
    private JLabel lblRole;
    private JTextField txtRole;
    private JButton btnRemove;
    private JButton btnBack;
    private JButton btnExit;
    private JLabel lblName;
    private JTextField txtName;
    private JLabel lblPhone;
    private JTextField txtPhone;
    private JButton btnLogout;
    private JButton btnHome;

    public AdminRemove(){
        setContentPane(pnlPanel);
        pack();
        txtRole.setEditable(false);
        txtPhone.setEditable(false);
        txtName.setEditable(false);
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchUser();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Admin();
                dispose();
            }
        });
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser();
            }
        });
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
    }
    private void searchUser(){
        try {
            if (txtUsername.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter a Username");
            } else {
                Connection con= DB.getCon();
                assert con != null;
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM tbl_user where username='"+txtUsername.getText()+"'");
                while (rs.next()){
                    txtUsername.setEditable(false);
                    txtRole.setText(rs.getString("role"));
                    txtName.setText(rs.getString("name"));
                    txtPhone.setText(rs.getString("phone"));
                }
                rs.close();
                st.close();
                con.close();

            }

        }catch (Exception e){
            System.out.println(e);
        }

    }
    private void removeUser(){
        if (txtUsername.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Error Getting Details to Remove");
        }else {
            try {
                Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/db_victor_gichuru_167026?useSSL=false", "root", "kali");
                PreparedStatement pst=con.prepareStatement("delete * from tbl_user where username=?");
                pst.setString(1,txtUsername.getText());
                ResultSet rs= pst.executeQuery();
                JOptionPane.showMessageDialog(null,"User Removed Successfully");

            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        new AdminRemove();
    }

}

