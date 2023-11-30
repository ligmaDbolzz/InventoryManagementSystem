# Inventory-Management-System
Absolutely, here is a basic README.md structure for your Java DAO project:

---

# ProductDAO Project

This Java project revolves around handling products via a Data Access Object (DAO) model. The project comprises classes and methods responsible for interacting with a database to perform CRUD (Create, Read, Update, Delete) operations on product information.

## Overview

The `ProductDAO` class serves as the primary controller to manage product data. It facilitates interactions between the application and the underlying database. The methods within `ProductDAO` encompass functionalities for checking product existence, retrieving all products, fetching detailed product information, adding, deleting, and editing products.

### Technologies Used

- Java
- JDBC (Java Database Connectivity)
- SQL (Structured Query Language)
- Swing (for GUI-based notifications)

## Features

1. **isProductExists** - Checks if a product exists in the database based on a given product code.
2. **allProductList** - Retrieves a list of all available products from the database.
3. **getFullProduct** - Fetches comprehensive details of a specific product, including data from related tables.
4. **addProduct** - Adds a new product to the database, including related information in associated tables.
5. **deleteProduct** - Removes a product and its related data from the database.
6. **editProduct** - Modifies an existing product with updated information.

## Setup

To use this project:

1. Ensure you have a compatible Java Development Kit (JDK) installed.
2. Set up a database and configure the `ConnectionFactory` class to establish a connection.
3. Compile and run the project in your preferred Java development environment.

## Usage

- Clone this repository.
- Import the project into your Java IDE.
- Configure the database connection parameters in the `ConnectionFactory` class.
- Run the project to interact with the product management functionalities.

## Important Notes

- Ensure proper database setup and configurations before executing the project.
- Error handling and logging mechanisms are integrated for better debugging and notification purposes.
- This project utilizes Swing-based pop-up notifications for user interaction feedback.

---

Feel free to expand upon this README by adding sections such as "Installation," "Contributing Guidelines," or any other pertinent information to enhance its usability for potential users or contributors.
