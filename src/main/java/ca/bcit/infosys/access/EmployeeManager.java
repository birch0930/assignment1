package ca.bcit.infosys.access;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeList;

/**
 * Handle CRUD actions for Product class.
 * 
 * @author blink
 * @version 1.0
 * 
 */

public class EmployeeManager implements EmployeeList {
	/** dataSource for connection pool on JBoss AS 7 or higher. */
	@Resource(mappedName = "java:jboss/datasources/TIMESHEET")
	private DataSource dataSource;
	private Employee employee;
	public EmployeeManager() {
	}



	/**
	 * Return Employee table for all Employees.
	 * 
	 * @return  List<Employee>of all records of all Employees from Employee table
	 * 
	 */

	@Override
	public List<Employee> getEmployees() {
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		Connection connection = null;
		Statement stmt = null;
		try {
			try {
				connection = dataSource.getConnection();
				try {
					stmt = connection.createStatement();
					ResultSet result = stmt
							.executeQuery("SELECT * FROM EMPLOYEE "
									+ "ORDER BY EMP_ID");
					while (result.next()) {
						Employee e = new Employee();
						e.setEmpNumber(result.getInt("EMP_ID"));
						e.setUserName(result.getString("USER_NAME"));
						// result.getString("PASSWORD");
						e.setName(result.getString("EMP_NAME"));
						e.setType(result.getInt("AUTHORITY"));
						employeeList.add(e);

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
		return employeeList;
	}
	
	/**
	 * Find Inventory record from database.
	 * 
	 * @param id
	 *            primary key of record to be returned.
	 * @return the Inventory record with key = id, null if not found.
	 */

	@Override
	public Employee getEmployee(String name) {
		Connection connection = null;
		Statement stmt = null;
		try {
			try {
				connection = dataSource.getConnection();
				try {
					stmt = connection.createStatement();
					ResultSet result = stmt
							.executeQuery("SELECT * FROM EMPLOYEE where EMP_NAME = '"
									+ name + "'");
					if (result.next()) {
						Employee e = new Employee();
						e.setEmpNumber(result.getInt("EMP_ID"));
						e.setUserName(result.getString("USER_NAME"));
						e.setName(result.getString("EMP_NAME"));
						e.setType(result.getInt("AUTHORITY"));
						return e;
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
			System.out.println("Error in find " + name);
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, String> getLoginCombos() {
		Connection connection = null;
		Statement stmt = null;
		Map<String, String> combos = null;
		try {
			combos = new HashMap<String, String>();
			try {
				connection = dataSource.getConnection();
				stmt = connection.createStatement();
				ResultSet result = stmt.executeQuery("SELECT * FROM EMPLOYEE ");
				while (result.next()) {
					combos.put(result.getString("USER_NAME"),
							result.getString("PASSWORD"));

				}
				return combos;
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		} catch (SQLException ex) {

			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Employee getCurrentEmployee() {
		// TODO Auto-generated method stub
		return employee;
	}

	@Override
	public Employee getAdministrator() {
		Connection connection = null;
		Statement stmt = null;
		
		try {
			try {
				connection = dataSource.getConnection();
				stmt = connection.createStatement();
				ResultSet result = stmt.executeQuery("SELECT * FROM EMPLOYEE WHERE  AUTHORITY = 0 ");
				if (result.next()) {
					Employee e = new Employee();
					e.setEmpNumber(result.getInt("EMP_ID"));
					e.setUserName(result.getString("USER_NAME"));
					e.setName(result.getString("EMP_NAME"));
					e.setType(result.getInt("AUTHORITY"));
					return e;
				} else {
					return null;
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		} catch (SQLException ex) {

			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean verifyUser(Credentials credential) {
		Map<String, String> combos = getLoginCombos();
		if(combos.containsKey(credential.getUserName())){
			String pw = combos.get(credential.getUserName());
			if(credential.getPassword().equals(pw)) return true;
			else return false;
		}
		return false;
	}

	@Override
	public String logout(Employee employee) {
		this.employee = null;
		return null;
	}

	@Override
	public void deleteEmpoyee(Employee employee) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			try {
				connection = dataSource.getConnection();
				try {
					stmt = connection
							.prepareStatement("DELETE FROM TIMESHEETS WHERE EMP_ID =  ?");
					stmt.setInt(1, employee.getEmpNumber());
					stmt.executeUpdate();
					
				} finally {
					if (stmt != null) {
						stmt.close();
					}
				}
				try {
				stmt = connection
						.prepareStatement("DELETE FROM EMPLOYEE WHERE EMP_ID =  ?");
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

	@Override
	public void addEmployee(Employee newEmployee) {
		// order of fields in INSERT statement
		final int EMP_ID = 1;
		final int USER_NAME = 2;
		final int PASSWORD = 3;
		final int EMP_NAME = 4;
		final int AUTHORITY = 5;

		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			try {
				connection = dataSource.getConnection();
				try {
					stmt = connection.prepareStatement("INSERT INTO EMPLOYEE "
							+ "VALUES (?, ?, ?, ?, ?)");
					stmt.setInt(EMP_ID, 0);
					stmt.setString(USER_NAME, newEmployee.getUserName());
					stmt.setString(PASSWORD, "123456");
					stmt.setString(EMP_NAME, newEmployee.getName());
					stmt.setInt(AUTHORITY, 1);
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
			System.out.println("Error in persist  employee");
			ex.printStackTrace();
		}

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}



	public Employee getEmployee() {
		return employee;
	}



	public void setEmployee(Employee employee) {
		this.employee = employee;
	}



	public void editEmployee(Employee emp) {
		// TODO Auto-generated method stub
		
	}

}
