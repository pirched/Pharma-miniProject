package com.gichuru.pharma;


import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Login extends CommonGUIForm {
    private JLabel lblUser;
    private JTextField txtUser;
    private JLabel lblPass;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JButton btnExit;
    private JLabel lblLogin;
    private JPanel pnlPanel;

    public Login(){
        super();

        setContentPane(pnlPanel);


        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=txtUser.getText();
                String  password=String.valueOf(txtPass.getPassword());
                user=login(username, password);
                if (user != null){
                    if (Objects.equals(user.getRole(), "Admin")){
                        new Admin();
                        System.out.println("after auth");
                        dispose();
                    } else if (Objects.equals(user.getRole(),"Pharmacist")) {
                        new Pharmacist();
                        dispose();
                    } else if (Objects.equals(user.getRole(),"Doctor")){
                        new Doctor();
                        dispose();
                    }

                } else {
                    JOptionPane.showMessageDialog(null,"Authentication Failed, \nTry Again");
                }
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public User user;
    private User login(String username, String password){
        User user= null;
        try {
            Connection con = DB.getCon();
            assert con != null;
            PreparedStatement pst= con.prepareStatement("select * from tbl_user where username=? and password=?");
            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                user= new User();
                user.setUsername(rst.getString("username"));
                user.setPassword(rst.getString("password"));
                user.setRole(rst.getString("role"));
            }
            pst.close();
            con.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

        return user;
    }
}
