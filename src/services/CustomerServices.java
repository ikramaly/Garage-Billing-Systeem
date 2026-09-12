package services;

import config.DbConfig;
import entity.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServices {
    public void addCustomer(Customers customer) throws SQLException {
        String query = "INSERT INTO customers(name, phone) VALUES (?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.executeUpdate();
        }
    }

    public List<Customers> getAllCustomers() throws SQLException {
        List<Customers> list = new ArrayList<>();
        String query = "SELECT id, name, phone FROM customers ORDER BY id";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Customers(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone")
                ));
            }
        }
        return list;
    }

    public boolean customerExists(int customerId) throws SQLException {
        String query = "SELECT id FROM customers WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // agr hogi to true oterwise false.
            }
        }
    }
}
