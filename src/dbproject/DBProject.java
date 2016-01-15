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
    Scanner in = new Scanner(System.in);

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
        System.out.println("Please provide unique ID for the user");
        int id = in.nextInt();
        System.out.println("Please type in first name");
        String firstName = in.next();
        System.out.println("Please type in surname");
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
                int x = rs.getInt(idTable);
                String s = rs.getString(nameTable);
                String f = rs.getString(surnameTable);
                System.out.println(x +" "+ s +" "+f);
            }
        }
    }
    public void removeEntryFromDB() throws SQLException{
        System.out.println("Select user ID that should be removed.");
        int id = in.nextInt();
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM EXAMPLE");
            while(rs.next()){
                if(rs.getInt("ID")==id){
                    rs.deleteRow();
                }
            }
        }
    }
}
