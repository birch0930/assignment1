package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Employee newEmp;
	private Employee editEmp;
	public Employee getEditEmp() {
		return editEmp;
	}

	public void setEditEmp(Employee editEmp) {
		this.editEmp = editEmp;
	}

	private String newPassword;
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	private Map<String, String> credenCombo;
	public Map<String, String> getCredenCombo() {
		return credenCombo;
	}

	public void setCredenCombo(Map<String, String> credenCombo) {
		this.credenCombo = credenCombo;
	}

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
	@Inject private Employee currentEmployee;

	public String login() {
		boolean result = empManager.verifyUser(credential);		
		if (result) {
			int type = empManager.getEmployee(credential.getUserName()).getType();
			if (type == 0){
				getEmployees();
				getCredentials();
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
	
	public void getCredentials() {
		credenCombo = empManager.getLoginCombos();
		/*for(int i = 0; i < credenCombo.size(); i++){
			System.out.println(credenCombo.get(key));
		}*/
		
	}

	public void deleteEmployee(Employee emp) {
		if (emp != null)
			empManager.deleteEmpoyee(emp);
	}

	public String newEmployee() {
		//conversation.begin();
		if (employee != null)
			newEmp = new Employee(employee.getName(),employee.getEmpNumber(),employee.getUserName(),employee.getType());
			empManager.addEmployee(newEmp);

		//getEmployees();
		//conversation.end();
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

	public Employee getCurrentEmployee() {
		currentEmployee = empManager.getCurrentEmployee();
		return currentEmployee;
	}

	public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}
	
	public String add(){
		//conversation.begin();
		return "newEmp";
	}
	
	
	public String displayEditEmp (Employee e) {
		editEmp = e;
		//System.out.println(editEmp.getUserName());
		return "changePassword";		
	}
	

	public String changePassword(String newPassword) {
		empManager.getLoginCombos().put(getEditEmp().getUserName(), newPassword);
		/*System.out.println(getEditEmp().getUserName());
		for (Map.Entry<String, String> entry : empManager.getLoginCombos().entrySet())
		{

		    System.out.println(entry.getKey() + "/" + entry.getValue());
		}

		return "superShowUser";*/
	}
	

}
