package ca.bcit.infosys.access;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.enterprise.context.ConversationScoped;
import javax.sql.DataSource;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;

/**
 * Handle CRUD actions for Product class.
 * 
 * @author blink
 * @version 1.0
 * 
 */
@ConversationScoped
public class TimesheetManager implements Serializable {
	/** dataSource for connection pool on JBoss AS 7 or higher. */
	@Resource(mappedName = "java:jboss/datasources/TIMESHEET")
	private DataSource dataSource;

	

	/**
     * Find Inventory record from database.
     * 
     * @param id
     *            primary key of record to be returned.
     * @return the Inventory record with key = id, null if not found.
     */
    public Employee find(int id) {
        Connection connection = null;
        Statement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM Inventory where ProductID = '"
                                    + id + "'");
                    if (result.next()) {
                        
                    } else {
                        return null;
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in find " + id);
            ex.printStackTrace();
            return null;
        }
		return null;
    }

    /**
     * Persist Product record into inventory database. productId must be unique.
     * 
     * @param product
     *            the record to be persisted.
     */
    public void persist(Employee employee) {
        //order of fields in INSERT statement
        final int productID = 1;
        final int productName = 2;
        final int productDescription = 3;
        final int categoryID = 4;
        final int price = 5;
        final int reorderLevel = 6;
        final int discontinued = 7;
        final int leadTime = 8;
        final int quantity = 9;
        
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
//                    stmt = connection
//                            .prepareStatement("INSERT INTO Categories "
//                                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
//                    stmt.setInt(productID, product.getId());
//                    stmt.setString(productName, product.getName());
//                    stmt.setString(productDescription, 
//                            product.getDescription());
//                    stmt.setInt(categoryID, product.getCategory().getId());
//                    stmt.setFloat(price, product.getPrice());
//                    stmt.setInt(reorderLevel, product.getReorderLevel());
//                    stmt.setInt(discontinued, product.isDiscontinued() ? 1 : 0);
//                    stmt.setString(leadTime, product.getLeadTime());
//                    stmt.setInt(quantity, product.getQuantity());
//                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in persist " + employee);
            ex.printStackTrace();
        }
    }

    /**
     * merge Product record fields into existing inventory database record.
     * 
     * @param product
     *            the record to be merged.
     */
    public void merge(Employee employee) {
        //order of fields in UPDATE statement
        final int productName = 1;
        final int productDescription = 2;
        final int categoryID = 3;
        final int price = 4;
        final int reorderLevel = 5;
        final int discontinued = 6;
        final int leadTime = 7;
        final int quantity = 8;
        final int productID = 9;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.prepareStatement("UPDATE Inventory "
                            + "SET ProductName = ?, "
                            + "ProductDescription = ?, CategoryID = ?, "
                            + "Price = ?, ReorderLevel = ?, "
                            + "Discontinued = ?, LeadTime = ?, "
                            + "Quantity = ? WHERE ProductID =  ?");
//                    stmt.setString(productName, product.getName());
//                    stmt.setString(productDescription, 
//                            product.getDescription());
//                    stmt.setInt(categoryID, product.getCategory().getId());
//                    stmt.setFloat(price, product.getPrice());
//                    stmt.setInt(reorderLevel, product.getReorderLevel());
//                    stmt.setInt(discontinued, product.isDiscontinued() ? 1 : 0);
//                    stmt.setString(leadTime, product.getLeadTime());
//                    stmt.setInt(quantity, product.getQuantity());
//                    stmt.setInt(productID, product.getId());
//                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in merge " + employee);
            ex.printStackTrace();
        }
    }

    /**
     * Remove product item from database.
     * 
     * @param product
     *            record to be removed from database
     */
    public void remove(Employee employee) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "DELETE FROM Inventory WHERE ProductID =  ?");
                    stmt.setInt(1, employee.getEmpNumber());
                    stmt.executeUpdate();
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in remove " + employee);
            ex.printStackTrace();
        }
    }
    /**
     * Return Products table for a given category.
     * 
     * @return Product[] of all records of a given category from Inventory table
     * @param categoryID the ID of the category to select
     */
    public Employee[] getByCategory(int categoryID) {
        ArrayList<Employee> inventory = new ArrayList<Employee>();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.prepareStatement(
                            "SELECT * FROM Inventory WHERE CategoryID = ? "
                            + "ORDER BY ProductID");
                    stmt.setInt(1, categoryID);
                    ResultSet result = stmt.executeQuery();
                    while (result.next()) {
//                        inventory.add(new Employee(
//                                result.getInt("ProductID"),
//                                result.getString("ProductName"), 
//                                result.getString("ProductDescription"),
//                                categoryManager.find(
//                                        result.getInt("CategoryID")), 
//                                result.getFloat("Price"), 
//                                result.getInt("ReorderLevel"), 
//                                result.getInt("Discontinued") == 0 ? false
//                                        : true, 
//                                result.getString("LeadTime"),
//                                result.getInt("Quantity")));
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAll");
            ex.printStackTrace();
            return null;
        }

        Employee[] invarray = new Employee[inventory.size()];
        return inventory.toArray(invarray);
    }

    /**
     * Return Inventory table as array of Product.
     * 
     * @return Product[] of all records in Inventory table
     */
    public Employee[] getAll() {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        Connection connection = null;
        Statement stmt = null;
        try {
            try {
                connection = dataSource.getConnection();
                try {
                    stmt = connection.createStatement();
                    ResultSet result = stmt
                            .executeQuery("SELECT * FROM Inventory "
                                    + "ORDER BY ProductID");
                    while (result.next()) {
//                        inventory.add(new Product(
//                                result.getInt("ProductID"),
//                                result.getString("ProductName"), 
//                                result.getString("ProductDescription"),
//                                categoryManager.find(
//                                        result.getInt("CategoryID")), 
//                                result.getFloat("Price"), 
//                                result.getInt("ReorderLevel"), 
//                                result.getInt("Discontinued") == 0 ? false
//                                        : true, 
//                                result.getString("LeadTime"),
//                                result.getInt("Quantity")));
                    }
                } finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAll");
            ex.printStackTrace();
            return null;
        }

        Employee[] invarray = new Employee[employees.size()];
        return employees.toArray(invarray);
    }

}
