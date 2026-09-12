package services;

import config.DbConfig;
import entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {
    // -------------- isme bhi same aise hi work ho rha hai jaise humne Customers class me kiya hai,
    public void addVehicle(Vehicle vehicle) throws SQLException {
        String query = "INSERT INTO vehicles(customer_id, number_plate, model) VALUES (?, ?, ?)";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, vehicle.getCustomerId());
            ps.setString(2, vehicle.getNumberPlate());
            ps.setString(3, vehicle.getModel());
            ps.executeUpdate();
        }
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String query = "SELECT id, customer_id, number_plate, model FROM vehicles ORDER BY id";

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("number_plate"),
                        rs.getString("model")
                ));
            }
        }
        return list;
    }
//------------------- we want to check customer vehicle with the help of custoemr id ---------------------------
    public List<Vehicle> getVehiclesByCustomer(int customerId) throws SQLException {
        List<Vehicle> list = new ArrayList<>();
        String query = "SELECT id, customer_id, number_plate, model FROM vehicles WHERE customer_id = ? ORDER BY id"; // order by id isliye lika Qki
        // customer ke vehicles 1 se jyada bhi to ho skte hai.
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) { // try with resources concept.
                while (rs.next()) {
                    list.add(new Vehicle(
                            rs.getInt("id"),
                            rs.getInt("customer_id"),
                            rs.getString("number_plate"),
                            rs.getString("model")
                    ));
                }
            }
        }
        return list;
    }
//------------------- we want to check that vehicles belongs to customer or not------------------------------------------
    public boolean vehicleBelongsToCustomer(int vehicleId, int customerId) throws SQLException {
        String query = "SELECT id FROM vehicles WHERE id = ? AND customer_id = ?"; // it means hum kisi bhi random vehicle ko customer se check kar skte hai ki
        // belog karta hai ki nhi, isliye vehicle ki id, customer_id dono dege.
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, vehicleId);
            ps.setInt(2, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true/false
            }
        }
    }
}
