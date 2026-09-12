package entity;

import java.sql.Timestamp;

public class Invoice {
    private int id;
    private int customerId;
    private int vehicleId;
    private int serviceId;
    private Timestamp date;

    public Invoice(int id, int customerId, int vehicleId, int serviceId) {
        this(id, customerId, vehicleId, serviceId, null);
    }

    public Invoice(int id, int customerId, int vehicleId, int serviceId, Timestamp date) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.serviceId = serviceId;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getVehicleId() { return vehicleId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }
    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public Timestamp getDate() { return date; }
    public void setDate(Timestamp date) { this.date = date; }

    @Override
    public String toString() {
        return "Invoice{id=" + id + ", customerId=" + customerId +
                ", vehicleId=" + vehicleId + ", serviceId=" + serviceId +
                ", date=" + date + "}";
    }
}
