package dbproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBProject {

    private final String username = "username";
    private final String password = "test123.";
    private Connection con;
    private final String idTable = "ID";
    private final String nameTable = "NAME";
    private final String surnameTable = "SURNAME";

    public static void main(String[] args) throws SQLException {
        DBProject db = new DBProject();
        db.connectToDB();
        db.writeToDB();
        db.readFromDB();
    }

    public void connectToDB() throws SQLException {
        con = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/lab1",
                username,
                password);
        
    }
    public void writeToDB()throws SQLException{
        Scanner in = new Scanner(System.in);
        System.out.println("Please provide unique ID for the user");
        int id = in.nextInt();
        System.out.println("Please type in your first name");
        String firstName = in.next();
        System.out.println("Please type in your surname");
        String surName = in.next();
        try (Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM EXAMPLE");
            rs.moveToInsertRow();
            rs.updateInt(idTable, id);
            rs.updateString(surnameTable, surName);
            rs.updateString(nameTable, firstName);
            rs.insertRow();
        }
    }
    public void readFromDB()throws SQLException{
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM EXAMPLE");
            while (rs.next()) {
                int x = rs.getInt("ID");
                String s = rs.getString("NAME");
                String f = rs.getString("SURNAME");
                System.out.println(x +" "+ s +" "+f);
            }
        }
    }
}
