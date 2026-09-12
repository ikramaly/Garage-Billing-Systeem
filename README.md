# Garage Billing System

Console-based Java + JDBC + MySQL project for managing customers, vehicles, garage services and invoices.

## Features
- Add and view customers
- Add and view vehicles
- View available garage services
- Generate invoices for one or multiple services
- Validate customer/vehicle/service relationships
- View detailed invoices using SQL JOINs
- JDBC resource management with try-with-resources
- Transaction handling and batch insert while generating invoices

## Project Structure
```text
src
├── App.java
├── config
│   └── DbConfig.java
├── entity
│   ├── Customers.java
│   ├── Vehicle.java
│   ├── Service.java
│   └── Invoice.java
└── services
    ├── CustomerServices.java
    ├── VehicleService.java
    ├── ServiceCatalog.java
    ├── InvoiceService.java
    └── BillingService.java
```

## Setup
1. Start MySQL.
2. Run `database.sql`.
3. Check the database username/password in `src/config/DbConfig.java`.
4. Add MySQL Connector/J to the project classpath.
5. Run `App.java`.
