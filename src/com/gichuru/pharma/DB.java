package com.gichuru.pharma;

import java.sql.Connection;

import java.sql.DriverManager;

public class DB {
    public static Connection getCon(){
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbName?useSSL=false", "root", "kali");
            return con;

        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
