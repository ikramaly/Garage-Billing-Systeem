package services;


import config.DbConfig;
import entity.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// this class will be show the all details of the garageService
public class InvoiceService {
    public void addInvoice(Invoice invoice) throws SQLException {
        Connection conn = DbConfig.getConnection();
        String Query = "INSERT INTO invoices(customer_id,vehicle_id,service_id) values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(Query);
        ps.setInt(1,invoice.getCustomerId());
        ps.setInt(2,invoice.getVehicleId());
        ps.setInt(3,invoice.getServiceId());
        ps.executeUpdate();//it means this method will be update the database table
        ps.close();
        conn.close();
    }
    // this method for showing the all details of the invoices;
    // in this method there is no 'VOID' keyword use thats why it should return something.
    public List<Invoice> getAllInvoices() throws SQLException{
        List<Invoice> list = new ArrayList<>();
        Connection conn = DbConfig.getConnection();
        String query = "SELECT * FROM invoices";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery(); // now table data in rs.
        while (rs.next()){
            list.add(new Invoice(rs.getInt("id"),rs.getInt("customer_id"),rs.getInt("vehicle_id"),rs.getInt("service_id")));
        }
        return list;
    };
}
