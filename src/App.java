import entity.Customers;
import entity.Service;
import entity.Vehicle;
import services.BillingService;
import services.CustomerServices;
import services.ServiceCatalog;
import services.VehicleService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final CustomerServices customerServices = new CustomerServices();
    private static final VehicleService vehicleService = new VehicleService();
    private static final ServiceCatalog serviceCatalog = new ServiceCatalog();
    private static final BillingService billingService = new BillingService();

    public static void main(String[] args) {
        System.out.println("====================================================================================================");
        System.out.println(" -----------------------------> GARAGE BILLING SYSTEM <----------------------------------------------");
        System.out.println("=====================================================================================================");

        while (true) {
            printMenu();
            int choice = readInt("Enter your choice: ");  // humne starting me hi readInt() call kar diya jisme humne as a argument string de
                                                        // rakhi hai, aur vo usko print kar chuka hai readInt() ke according.

            try {
                switch (choice) {
                    case 1 -> addCustomer();
                    case 2 -> showCustomers();
                    case 3 -> addVehicle();
                    case 4 -> showVehicles();
                    case 5 -> showServices();
                    case 6 -> generateInvoice();
                    case 7 -> billingService.showAllInvoices();
                    case 8 -> {
                        System.out.println("Thank you for using Garage Billing System.");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("\n----------------------------------------- MENU ---------------------------------------------");
        System.out.println("1. Add Customer");
        System.out.println("2. View Customers");
        System.out.println("3. Add Vehicle");
        System.out.println("4. View Vehicles");
        System.out.println("5. View Available Services");
        System.out.println("6. Generate Invoice");
        System.out.println("7. View All Invoices");
        System.out.println("8. Exit");
        System.out.println("--------------------------------------------------------------------------------------------------");
    }

    private static void addCustomer() throws SQLException {
        String name = readNonEmpty("Enter customer name: "); // it means this name should not be empty.........
        String phone = readNonEmpty("Enter phone number: "); // same here

        customerServices.addCustomer(new Customers(0, name, phone)); // id 0 is passing only for constructor purpose for database table there is no
                                                                        // no need.
        System.out.println("Customer added successfully.");
    }

    private static void showCustomers() throws SQLException {
        List<Customers> customers = customerServices.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("\n--------------- CUSTOMERS ---------------");
        for (Customers customer : customers) {
            System.out.println(customer);
        }
    }

    private static void addVehicle() throws SQLException {
        showCustomers();
        int customerId = readInt("Enter customer ID: ");

        if (!customerServices.customerExists(customerId)) {
            throw new IllegalArgumentException("Customer does not exist.");
        }

        String numberPlate = readNonEmpty("Enter vehicle number plate: ");
        String model = readNonEmpty("Enter vehicle model: ");

        vehicleService.addVehicle(new Vehicle(0, customerId, numberPlate, model));
        System.out.println("Vehicle added successfully.");
    }

    private static void showVehicles() throws SQLException {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        System.out.println("\n--------------- VEHICLES ---------------");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    private static void showServices() throws SQLException {
        List<Service> services = serviceCatalog.getAllServices();

        if (services.isEmpty()) {
            System.out.println("No services found.");
            return;
        }

        System.out.println("\n----------- AVAILABLE SERVICES -----------");
        for (Service service : services) {
            System.out.println("ID: "+service.getId()+" | "+service.getDescription()+" | Cost: Rs."+service.getCost());
        }
    }

    private static void generateInvoice() throws SQLException {
        showCustomers();
        int customerId = readInt("Enter customer ID: ");

        if (!customerServices.customerExists(customerId)) {
            throw new IllegalArgumentException("Customer does not exist.");
        }

        List<Vehicle> vehicles = vehicleService.getVehiclesByCustomer(customerId);
        if (vehicles.isEmpty()) {
            throw new IllegalArgumentException("This customer has no vehicle. Add a vehicle first.");
        }

        System.out.println("\nVehicles of selected customer:");
        for (Vehicle vehicle : vehicles) {
            System.out.println("ID: " + vehicle.getId() +" | Number Plate: " + vehicle.getNumberPlate() +" | Model: " + vehicle.getModel());
        }
        int vehicleId = readInt("Enter vehicle ID: ");
        if (!vehicleService.vehicleBelongsToCustomer(vehicleId, customerId)) {
            throw new IllegalArgumentException("Invalid vehicle ID for this customer.");
        }
        showServices();
        System.out.println("Enter service IDs one by one. Enter 0 when finished.");

        List<Integer> serviceIds = new ArrayList<>();
        while (true) {
            int serviceId = readInt("Service ID (0 to finish): ");
            if (serviceId == 0) {
                break;
            }
            if (!serviceIds.contains(serviceId)) {
                serviceIds.add(serviceId);
            } else {
                System.out.println("This service is already selected.");
            }
        }

        billingService.createInvoice(customerId, vehicleId, serviceIds);
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim(); // it will the trim white space
            try {
                return Integer.parseInt(input); // then ye string ko again integer value me convert karke return kar rha hai.
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim(); // it will the trim white space
            if (!input.isEmpty()) { //
                return input;
            }
            System.out.println("This field cannot be empty.");
        }
    }
}
