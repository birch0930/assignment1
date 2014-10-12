package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
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
@ConversationScoped
public class EmployeeController implements Serializable {
	@Inject
	private EmployeeManager empManager;
	private List<Employee> empList;
	@Inject
	private Credentials credential;
	@Inject
	private Conversation conversation;

	public String login() {
		boolean result = empManager.verifyUser(credential);
		if (result) {
			int type = empManager.getCurrentEmployee().getType();
			if (type == 0)
				return "super";
			else
				return "user";
		} else
			return "failure";
	}

	public String getEmployees() {
		empList = empManager.getEmployees();
		System.out.println(empList);
		return "superShowUser";
	}

	public void deleteEmployee(Employee emp) {
		if (emp != null)
			empManager.deleteEmpoyee(emp);

	}

	public String newEmployee(Employee emp) {
		if (emp != null)
			empManager.addEmployee(emp);
		return "superShowUser";
	}

	public String editEmployee(Employee emp) {
		if (emp != null)
			empManager.editEmployee(emp);
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

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
}
