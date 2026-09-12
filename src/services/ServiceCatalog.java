package services;

import config.DbConfig;
import entity.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCatalog {

    public List<Service> getAllServices() throws SQLException {
        List<Service> list = new ArrayList<>();
        String query = "SELECT id, description, cost FROM services ORDER BY id";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Service(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("cost")
                ));
            }
        }
        return list;
    }

    public boolean serviceExists(int serviceId) throws SQLException {
        String query = "SELECT id FROM services WHERE id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true / false return karega.
            }
        }
    }
}
