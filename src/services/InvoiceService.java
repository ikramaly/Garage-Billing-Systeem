package services;

import config.DbConfig;
import entity.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    public void addInvoice(Invoice invoice) throws SQLException {
        String query = "INSERT INTO invoices(customer_id, vehicle_id, service_id) VALUES (?, ?, ?)"; // Invoice table me id & date auto increment hai.

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, invoice.getCustomerId());
            ps.setInt(2, invoice.getVehicleId());
            ps.setInt(3, invoice.getServiceId());
            ps.executeUpdate();
        }
    }

    public List<Invoice> getAllInvoices() throws SQLException {
        List<Invoice> list = new ArrayList<>();
        String query = "SELECT id, customer_id, vehicle_id, service_id, date FROM invoices ORDER BY id";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Invoice(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("vehicle_id"),
                        rs.getInt("service_id"),
                        rs.getTimestamp("date")
                ));
            }
        }
        return list;
    }

    public void printDetailedInvoices() throws SQLException {
        String query = "SELECT i.id, c.name AS customer_name, c.phone, " +
                "v.number_plate, v.model, s.description, s.cost, i.date " +
                "FROM invoices i " +
                "JOIN customers c ON i.customer_id = c.id " +
                "JOIN vehicles v ON i.vehicle_id = v.id " +
                "JOIN services s ON i.service_id = s.id " +
                "ORDER BY i.id";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            boolean found = false;
            System.out.println("\n==================== INVOICES ====================");
            while (rs.next()) {
                found = true;
                System.out.println("Invoice ID : " + rs.getInt("id"));
                System.out.println("Customer   : " + rs.getString("customer_name"));
                System.out.println("Phone      : " + rs.getString("phone"));
                System.out.println("Vehicle    : " + rs.getString("number_plate") + " (" + rs.getString("model") + ")");
                System.out.println("Service    : " + rs.getString("description"));
                System.out.println("Cost       : ₹" + rs.getDouble("cost"));
                System.out.println("Date       : " + rs.getTimestamp("date"));
                System.out.println("--------------------------------------------------");
            }
            if (!found) {
                System.out.println("No invoices found.");
            }
        }
    }
}
