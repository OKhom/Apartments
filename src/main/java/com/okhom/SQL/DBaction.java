package com.okhom.SQL;

import java.sql.*;

public class DBaction {

    public static void initDB(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS Apartments");
            st.execute("CREATE TABLE Apartments(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "city VARCHAR(32) NOT NULL," +
                    "district VARCHAR(32) DEFAULT NULL," +
                    "address VARCHAR(128) NOT NULL," +
                    "room_number INT DEFAULT NULL," +
                    "square INT NOT NULL," +
                    "price INT NOT NULL)");
        }
    }

    public static void insert(Connection conn, String city, String district, String address, int roomNumber, int square, int price) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Apartments(city, district, address, room_number, square, price) VALUES(?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, city);
            ps.setString(2, district);
            ps.setString(3, address);
            ps.setInt(4, roomNumber);
            ps.setInt(5, square);
            ps.setInt(6, price);
            ps.executeUpdate();
        }
    }

    public static void delete(Connection conn, String field, String value) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM apartments WHERE " + field + " = ?")) {
            ps.setString(1, value);
            ps.executeUpdate();
        }
    }

    public static void delete(Connection conn, String field, String value1, String value2) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM apartments WHERE " + field + " BETWEEN ? AND ?")) {
            ps.setString(1, value1);
            ps.setString(2, value2);
            ps.executeUpdate();
        }
    }

    public static void update(Connection conn, String fieldUpd, String valueUpd, String field, String value1, String value2) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE apartments " +
                "SET " + fieldUpd + " = ? WHERE " + field + " BETWEEN ? AND ?")) {
            ps.setString(1, valueUpd);
            ps.setString(2, value1);
            ps.setString(3, value2);
            ps.executeUpdate();
        }
    }

    public static void update(Connection conn, String fieldUpd, int valueUpd, String field, String value1, String value2) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE apartments " +
                "SET " + fieldUpd + " = ? WHERE " + field + " BETWEEN ? AND ?")) {
            ps.setInt(1, valueUpd);
            ps.setString(2, value1);
            ps.setString(3, value2);
            ps.executeUpdate();
        }
    }

    public static void select(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments")) {
            try (ResultSet rs = ps.executeQuery()) {
                printQuery(rs);
            }
        }
    }

    public static void select(Connection conn, String field, String value) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments WHERE " + field + " LIKE ?")) {
            ps.setString(1, "%" + value + "%");
            try (ResultSet rs = ps.executeQuery()) {
                printQuery(rs);
            }
        }
    }

    public static void select(Connection conn, String field, String value1, String value2) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Apartments WHERE " + field + " BETWEEN ? AND ?")) {
            ps.setString(1, value1);
            ps.setString(2, value2);
            try (ResultSet rs = ps.executeQuery()) {
                printQuery(rs);
            }
        }
    }

    private static void printQuery(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();

        for (int i = 1; i <= md.getColumnCount(); i++) {
            System.out.print(md.getColumnName(i) + "\t\t");
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= md.getColumnCount(); i++) {
                System.out.print(rs.getString(i) + "\t\t");
            }
            System.out.println();
        }
    }
}
