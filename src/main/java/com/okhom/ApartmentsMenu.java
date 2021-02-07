package com.okhom;

import com.okhom.SQL.DBaction;

import java.sql.*;
import java.util.Scanner;

public class ApartmentsMenu {
    private static String field;

    public static void menuDB(Connection conn, Scanner sc) throws SQLException {
        while (true) {
            System.out.println("\nSelect your action:");
            System.out.println("\t1. view Apartments");
            System.out.println("\t2. add Apartments");
            System.out.println("\t3. delete Apartments");
            System.out.println("\t4. update Apartments");
            System.out.println("\t0. exit");
            System.out.print(" -> ");

            String input = sc.nextLine();

            switch (input) {
                case "1" -> viewMenu(conn, sc);
                case "2" -> addMenu(conn, sc);
                case "3" -> deleteMenu(conn, sc);
                case "4" -> updateMenu(conn, sc);
                case "0" -> {
                    return;
                }
                default -> System.out.println("Entered value is invalid. Try again...");
            }
        }
    }

    private static void addMenu(Connection conn, Scanner sc) throws SQLException {
        try {
            System.out.print("Enter City of apartment: ");
            String city = sc.nextLine();
            System.out.print("Enter District of apartment: ");
            String district = sc.nextLine();
            System.out.print("Enter Address of apartment: ");
            String address = sc.nextLine();
            System.out.print("Enter room number of apartment: ");
            String roomNumberS = sc.nextLine();
            int roomNumber = Integer.parseInt(roomNumberS);
            System.out.print("Enter square of apartment: ");
            String squareS = sc.nextLine();
            int square = Integer.parseInt(squareS);
            System.out.print("Enter price of apartment: ");
            String priceS = sc.nextLine();
            int price = Integer.parseInt(priceS);

            DBaction.insert(conn, city, district, address, roomNumber, square, price);
        } catch (NumberFormatException nfe) {
            System.out.println("You entered non integer value for 'room number' or 'square' or 'price'. Try again...");
        }
    }

    private static void viewMenu(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Select view mode:");
        System.out.println("\t1. select Apartments by 'ID'");
        System.out.println("\t2. select Apartments by 'city'");
        System.out.println("\t3. select Apartments by 'district'");
        System.out.println("\t4. select Apartments by 'address'");
        System.out.println("\t5. select Apartments by 'room_number'");
        System.out.println("\t6. select Apartments by 'square'");
        System.out.println("\t7. select Apartments by 'price'");
        System.out.println("\t0. show all Apartments");
        System.out.print(" -> ");

        String input = sc.nextLine();

        field = selectKey(input);
        if (field.equals("invalid")) System.out.println("Entered value is invalid. Try again...");
        else if (field.equals("all")) DBaction.select(conn);
        else {
            System.out.println("Enter '" + field + "' of Apartment for viewing ");
            if (selectMode(input) == 1) {
                String value = sc.nextLine();
                DBaction.select(conn, field, value);
            } else if (selectMode(input) == 2) {
                System.out.print("\tfrom: ");
                String value1 = sc.nextLine();
                System.out.print("\t  to: ");
                String value2 = sc.nextLine();
                DBaction.select(conn, field, value1, value2);
            }
        }
    }

    private static void deleteMenu(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Select delete mode:");
        System.out.println("\t1. delete Apartments by 'ID'");
        System.out.println("\t2. delete Apartments by 'city'");
        System.out.println("\t3. delete Apartments by 'district'");
        System.out.println("\t4. delete Apartments by 'address'");
        System.out.println("\t5. delete Apartments by 'room_number'");
        System.out.println("\t6. delete Apartments by 'square'");
        System.out.println("\t7. delete Apartments by 'price'");
        System.out.print(" -> ");

        String input = sc.nextLine();

        field = selectKey(input);
        if (field.equals("invalid") || field.equals("all")) System.out.println("Entered value is invalid. Try again...");
        else {
            System.out.println("Enter '" + field + "' of Apartment for deleting ");
            if (selectMode(input) == 1) {
                String value = sc.nextLine();
                DBaction.delete(conn, field, value);
            } else if (selectMode(input) == 2) {
                System.out.print("\tfrom: ");
                String value1 = sc.nextLine();
                System.out.print("\t  to: ");
                String value2 = sc.nextLine();
                DBaction.delete(conn, field, value1, value2);
            }
        }
    }

    private static void updateMenu(Connection conn, Scanner sc) throws SQLException {
        System.out.println("Select updated field:");
        System.out.println("\t2. update 'city' at Apartments");
        System.out.println("\t3. update 'district' at Apartments");
        System.out.println("\t4. update 'address' at Apartments");
        System.out.println("\t5. update 'room_number' at Apartments");
        System.out.println("\t6. update 'square' at Apartments");
        System.out.println("\t7. update 'price' at Apartments");
        System.out.print(" -> ");

        String input = sc.nextLine();

        field = selectKey(input);
        if (field.equals("invalid") || field.equals("all") || field.equals("ID")) System.out.println("Entered value is invalid. Try again...");
        else {
            System.out.println("Enter 'ID' of Apartment for updating ");
            System.out.print("\tfrom: ");
            String value1 = sc.nextLine();
            System.out.print("\t  to: ");
            String value2 = sc.nextLine();
            System.out.println("Enter new value '" + field + "' of Apartment for updating: ");
            String value = sc.nextLine();
            if (selectMode(input) == 1) {
                DBaction.update(conn, field, value, "ID", value1, value2);
            } else if (selectMode(input) == 2) {
                try {
                    DBaction.update(conn, field, Integer.parseInt(value), "ID", value1, value2);
                } catch (NumberFormatException nfe) {
                    System.out.println("You entered non integer value for 'room number' or 'square' or 'price'. Try again...");
                }
            }
        }
    }

    private static String selectKey(String input) {
        String field;
        switch (input) {
            case "1" -> field = "ID";
            case "2" -> field = "city";
            case "3" -> field = "district";
            case "4" -> field = "address";
            case "5" -> field = "room_number";
            case "6" -> field = "square";
            case "7" -> field = "price";
            case "0" -> field = "all";
            default -> field = "invalid";
        }
        return field;
    }

    private static int selectMode(String input) {
        int mode;
        switch (input) {
            case "2", "3", "4" -> mode = 1;
            case "1", "5", "6", "7" -> mode = 2;
            default -> mode = 0;
        }
        return mode;
    }
}
