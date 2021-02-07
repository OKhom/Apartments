package com.okhom;

import com.okhom.SQL.DBaction;

import java.sql.*;
import java.util.Scanner;

/**
 * Apartments (JDBC)
 *
 */
public class Main {
    static final String DB = "okdb01";
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/" + DB + "?serverTimezone=Europe/Kiev";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "OKm4dbr00t";

    static Connection conn;

    public static void main( String[] args ) {
        try (Scanner sc = new Scanner(System.in)) {
            try {
                conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
                System.out.print("You wish create new DB ([Y]es) or use existing DB (default [N]o)? ");
                String input = sc.nextLine();
                if (input.startsWith("Y") || input.startsWith("y")) {
                    DBaction.initDB(conn);
                    System.out.println("Database " + DB + " is created");
                }
                ApartmentsMenu.menuDB(conn, sc);
            } finally {
                if (conn != null) conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
