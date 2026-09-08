package services;

import entity.Invoice;

import java.sql.SQLException;
import java.util.List;

public class BillingService {

    public CustomerServices customerServices = new CustomerServices();
    public  InvoiceService invoiceService = new InvoiceService();

    public void createInvoice(int customerId, int vehicleId, List<Integer> serviceIdes) throws SQLException{
        for (int serviceId: serviceIdes){
            invoiceService.addInvoice(new Invoice(0,customerId,vehicleId,serviceId));
        }
        System.out.println("Invoice generated successfully.");
    }

    public void showAllInvoices() throws SQLException{
        List<Invoice> invoices = invoiceService.getAllInvoices();
        for (Invoice invoice : invoices){
            System.out.println(invoice);
        }
    }
}
