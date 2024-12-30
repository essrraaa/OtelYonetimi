package otelyonetimi.dao;

import otelyonetimi.db.VeritabaniBaglantisi;
import otelyonetimi.model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    public static List<Invoice> getInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT c.customer_id, c.name, r.room_number, co.total_amount, c.check_in_date, co.check_out_date " +
                "FROM customers c " +
                "INNER JOIN checkouts co ON c.customer_id = co.customer_id " +
                "INNER JOIN rooms r ON c.room_id = r.room_id";

        try (Connection connection = VeritabaniBaglantisi.baglantiGetir();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                invoices.add(new Invoice(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("room_number"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getDate("check_in_date").toString(),
                        resultSet.getDate("check_out_date").toString()
                ));
            }
        } catch (SQLException e) {
            System.err.println("SQL Hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return invoices;
    }
}
