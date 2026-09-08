package services;

import config.DbConfig;
import entity.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomerServices {
    // is method se hum new customer add karege.
    public  void addCustomer(Customers customers) throws SQLException{
        Connection conn = DbConfig.getConnection();
        PreparedStatement ps =
                conn.prepareStatement("INSERT INTO customers(name,phone) values (?,?)");
        ps.setString(1,customers.getName());
        ps.setString(2,customers.getPhone());

        ps.executeUpdate();//ab query update ho jayegi.
        ps.close(); // interface ko use karne ke baad close kar do
        conn.close(); // interface ko use karne ke baad close kar do
    }
    // ab mujhe sabhi customers bhi chahiye.
    public List<Customers> getAllCustomers() throws SQLException{
        List<Customers> list = new ArrayList<>();
        Connection conn = DbConfig.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs =  st.executeQuery("select * from customers");// iska matlb db se puri table ka data aa jayega

        while (rs.next()){
            list.add(new Customers(rs.getInt("id"),rs.getString("name"),rs.getString("phone")));
        }
        return list; // Database se cutomer ka data humne list me store kar diya jisse
        // jab bhi hum display krana chahe kra skte hai ek sath,
    };

}
