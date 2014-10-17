package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.access.EmployeeManager;
import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;

/**
 * Resources to display categories and choose one.
 * 
 * @author blink
 * @version 1
 *
 */
@Named("empControl")
@SessionScoped
public class EmployeeController implements Serializable {
	@Inject
	private EmployeeManager empManager;
	private List<Employee> empList;
	@Inject
	private Employee employee;
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	//@Inject
	//private Conversation conversation;
	@Inject
	private Credentials credential;

	public String login() {
		boolean result = empManager.verifyUser(credential);		
		if (result) {
			int type = empManager.getEmployee(credential.getUserName()).getType();
			if (type == 0){
				getEmployees();
				return "superShowUser";
			}
			else
				return "user";
		} else
			return "failure";
	}

	public void getEmployees() {
		
		empList = empManager.getEmployees();

		//return "superShowUser.xhtml";
	}

	public void deleteEmployee(Employee emp) {
		if (emp != null)
			empManager.deleteEmpoyee(emp);

	}

	public String newEmployee(int id, String name, String useName, int type) {
		Employee newEmp = new Employee();
		//conversation.begin();
		if (newEmp != null)
			empManager.addEmployee(newEmp);
		return "superShowUser";
	}

	public EmployeeManager getEmpManager() {
		return empManager;
	}

	public void setEmpManager(EmployeeManager empManager) {
		this.empManager = empManager;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public Credentials getCredential() {
		return credential;
	}

	public void setCredential(Credentials credential) {
		this.credential = credential;
	}
	
	public String update() {
		//conversation.end();
		return "superShowUser";
	}
	
}
