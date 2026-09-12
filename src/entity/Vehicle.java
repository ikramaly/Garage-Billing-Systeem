package entity;

public class Vehicle {
    private int id;
    private int customerId;
    private String numberPlate;
    private String model;

    public Vehicle(int id, int customerId, String numberPlate, String model) {
        this.id = id;
        this.customerId = customerId;
        this.numberPlate = numberPlate;
        this.model = model;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getNumberPlate() { return numberPlate; }
    public void setNumberPlate(String numberPlate) { this.numberPlate = numberPlate; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    @Override
    public String toString() {
        return "Vehicle{id=" + id + ", customerId=" + customerId +
                ", numberPlate='" + numberPlate + "', model='" + model + "'}";
    }
}
