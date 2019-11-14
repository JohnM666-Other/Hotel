package com.hotel;

import java.sql.*;

public class JdbcApp {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            System.err.println(e.getMessage());
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hotels", "postgres", "1234")) {
            Statement stmt = conn.createStatement();
            conn.setAutoCommit(false);
            stmt.execute("drop schema if exists hotel2 cascade");
            stmt.execute("create schema hotel2");
            stmt.execute("create table hotel2.customers (id BIGINT PRIMARY KEY, firstname VARCHAR(32), secondname VARCHAR(32), email VARCHAR(32), birth_date DATE)");
            stmt.execute("create table hotel2.feedbacks (id BIGINT PRIMARY KEY, customer_id BIGINT references hotel2.customers(id), visit_date DATE, score INTEGER, text VARCHAR(128))");
            stmt.execute("create index on hotel2.customers(id)");
            PreparedStatement insertStmt = conn.prepareStatement("insert into hotel2.customers (id, firstname, secondname, email, birth_date) values(?, ?, ?, ?, ?)");

            for(int i = 0; i < 10; i++) {
                insertStmt.setInt(1, i);
                insertStmt.setString(2, "firstname" + i);
                insertStmt.setString(3, "secondname" + i);
                insertStmt.setString(4, "email" + i);
                insertStmt.setDate(5, Date.valueOf("2019-08-08"));
                insertStmt.execute();
            }

            insertStmt = conn.prepareStatement("insert into hotel2.feedbacks (id, customer_id, visit_date, score, text) values (?, ?, ?, ?, ?)");

            for(int i = 0; i < 10; i++) {
                insertStmt.setInt(1, i);
                insertStmt.setInt(2, i);
                insertStmt.setDate(3, Date.valueOf("2019-08-08"));
                insertStmt.setInt(4, i);
                insertStmt.setString(5, "text" + i);
                insertStmt.execute();
            }

            conn.commit();
            conn.setAutoCommit(true);

            ResultSet res = stmt.executeQuery("select * from hotel2.customers");

            while(res.next()) {
                System.out.print(res.getLong(1));
                System.out.print("\t");
                System.out.print(res.getString(2));
                System.out.print("\t");
                System.out.print(res.getString(3));
                System.out.print("\t");
                System.out.print(res.getString(4));
                System.out.print("\t");
                System.out.print(res.getDate(5));
                System.out.println();
            }

        } catch(SQLException e) {

            System.err.println(e.getMessage());
        }
    }
}
