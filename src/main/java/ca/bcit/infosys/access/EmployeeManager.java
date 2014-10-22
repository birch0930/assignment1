package ca.bcit.infosys.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Employee employee;
	private Credentials credential;
	public Credentials getCredential() {
		return credential;
	}

	public void setCredential(Credentials credential) {
		this.credential = credential;
	}

	//private boolean submitSuccess;
	public EmployeeManager() {
	}

	private static ArrayList<Employee> empInfo = new 

ArrayList<Employee>(
			Arrays.asList(
					new Employee("user1", 1, "u1", 0),
					new Employee("user2", 1, "u2", 1),
					new Employee("user3", 1, "u3", 1),
					new Employee("user4", 1, "u4", 1),
					new Employee("user5", 1, "u5", 1))
					);
	/**
	 * Return Employee table for all Employees.
	 * 
	 * @return  List<Employee>of all records of all Employees from 

Employee table
	 * 
	 */

	@Override
	public List<Employee> getEmployees() {
		return empInfo;
	}
	
	/**
	 * Find Inventory record from database.
	 * 
	 * @param id
	 *            primary key of record to be returned.
	 * @return the Inventory record with key = id, null if not 

found.
	 */

	@Override
	public Employee getEmployee(String name) {
		for(int i = 0; i < empInfo.size(); i++){
			if(empInfo.get(i).getUserName().equalsIgnoreCase

(name))
				return empInfo.get(i);
		}
		return null;
	}

	@Override
	public Map<String, String> getLoginCombos() {
		Map<String, String> combos = null;
		combos = new HashMap<String, String>();
		combos.put("u1","aaa");
		combos.put("u2","bbb");
		combos.put("u3","ccc");
		combos.put("u4","ddd");
		combos.put("u5","eee");

		return combos;
	}

	@Override
	public Employee getCurrentEmployee() {
		// TODO Auto-generated method stub
		return employee;
	}

	@Override
	public Employee getAdministrator() {
		return empInfo.get(0);
	}

	@Override
	public boolean verifyUser(Credentials credential) {
		Map<String, String> combos = getLoginCombos();
		if(combos.containsKey(credential.getUserName())){
			String pw = combos.get(credential.getUserName());
			if(credential.getPassword().equals(pw)) {
				employee = getEmployee(credential.getUserName());
				return true;
			}
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
		empInfo.remove(employee);
	}

	@Override
	public void addEmployee(Employee newEmployee) {
		empInfo.add(newEmployee);
		/*for(Employee emp : empInfo){
			System.out.println(emp.getName());
		}*/
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public String changePassword() {
		

		return "superShowUser";
	}
}
