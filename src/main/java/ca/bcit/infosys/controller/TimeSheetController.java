package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.access.EmployeeManager;
import ca.bcit.infosys.access.TimesheetManager;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetCollection;
import ca.bcit.infosys.timesheet.TimesheetRow;

/**
 * Resources to display categories and choose one.
 * 
 * @author blink
 * @version 1
 *
 */
@Named("sheetControl")
@SessionScoped
public class TimeSheetController implements Serializable, TimesheetCollection {

	@Inject
	private EmployeeController empControl;
	@Inject
	private EmployeeManager employeeManager;
	@Inject
	private Timesheet currentTimesheet;
	@Inject
	private TimesheetManager timesheetManager;
	// @Inject
	private List<Timesheet> timesheetList;
	// @Inject
	private Employee employee;
	static private String userName;

	// @Inject
	// private Conversation conversation;

	public TimeSheetController() {

	}

	private void init() {
		if (userName != null && !userName.equals("")) {
			employee = employeeManager.getEmployee(userName);
			currentTimesheet = getCurrentTimesheet(employee);
		}
	}

	@Override
	public List<Timesheet> getTimesheets() {
		timesheetList = timesheetManager.getTimesheets();
		return timesheetList;
	}

	@Override
	public List<Timesheet> getTimesheets(Employee e) {
		timesheetList = getTimesheets();
		if (timesheetList == null)
			return null;
		List<Timesheet> temp = new ArrayList<Timesheet>();
		for (Timesheet timesheet : timesheetList) {
			if (!timesheet.getEmployee().equals(e))
				temp.add(timesheet);
		}
		timesheetList.removeAll(temp);
		return timesheetList;
	}

	public String displayCurrentTimesheet() {
		// if (!conversation.isTransient())
		// conversation.end();
		// init();
		currentTimesheet = getCurrentTimesheet(employee);
		if (currentTimesheet == null) {
			return this.addTimesheet();
		}
		// System.out.println(timesheetList.size());
		// this.currentTimesheet =timesheetList.get(timesheetList.size()-1);
		return "displayTimesheet";
	}

	@Override
	public Timesheet getCurrentTimesheet(Employee e) {

		employee = employeeManager.getEmployee(userName);
		timesheetList = getTimesheets(e);
		if (timesheetList == null)
			return null;
		return timesheetList.get(timesheetList.size() - 1);
		// Calendar c = new GregorianCalendar();
		//
		// for (Timesheet timesheet : timesheetList) {
		// if (c.get(Calendar.WEEK_OF_YEAR) == timesheet.getWeekNumber())
		// return timesheet;
		// else
		// return null;
		// }
		// return null;
	}

	@Override
	public String addTimesheet() {
		// if (conversation.isTransient())
		// conversation.begin();
		employee = employeeManager.getEmployee(userName);
		currentTimesheet = new Timesheet();
		currentTimesheet.setEmployee(employee);

		return "newTimesheet.xhtml";
	}

	public void addRow() {
		// init();
		System.out.println(userName);
		currentTimesheet.addRow();
		timesheetManager.update(currentTimesheet);
		timesheetList = getTimesheets(currentTimesheet.getEmployee());
	}

	public String previous() {
		// init();
		int weekNum = currentTimesheet.getWeekNumber();
		System.out.println("previous" + timesheetList.size());
		int index = timesheetList.indexOf(currentTimesheet);
		index--;
		if (index >= 0) {
			Timesheet nt = timesheetList.get(index);
			if (nt != null)
				currentTimesheet = nt;
		}
		// for (Timesheet timesheet : timesheetList) {
		// if (timesheet.getWeekNumber() == --weekNum){
		// currentTimesheet = timesheet;
		// return "";
		// }
		// if (timesheet.getWeekNumber() < weekNum)
		// currentTimesheet = timesheet;
		// }

		return "";
	}

	public String nextTimesheet() {
		// init();
		int weekNum = currentTimesheet.getWeekNumber();
		System.out.println("next" + timesheetList.size());

		int index = timesheetList.indexOf(currentTimesheet);
		index++;
		if (index < timesheetList.size()) {
			Timesheet nt = timesheetList.get(index);
			if (nt != null)
				currentTimesheet = nt;
		}
		// for (Timesheet timesheet : timesheetList) {
		// if (timesheet.getWeekNumber() == ++weekNum){
		// currentTimesheet = timesheet;
		// return "";
		// }
		// if (timesheet.getWeekNumber() > weekNum)
		// currentTimesheet = timesheet;
		// }
		return "";
	}

	public String editTimesheet(TimesheetRow row) {
		// System.out.println(row.isEditable());
		init();
		row.setEditable(true);
		timesheetManager.update(currentTimesheet);
		return "";
	}

	public String updateTimesheet() {
		// init();
		for (TimesheetRow timesheetRow : currentTimesheet.getDetails()) {
			if (timesheetRow.isEditable())
				timesheetRow.setEditable(false);
		}
		timesheetManager.update(currentTimesheet);
		timesheetList = getTimesheets(currentTimesheet.getEmployee());
		return "displayTimesheet.xhtml";
	}

	public String delRow(TimesheetRow Row) {
		// init();
		currentTimesheet.deleteRow(Row);
		timesheetManager.update(currentTimesheet);
		timesheetList = getTimesheets(currentTimesheet.getEmployee());
		return "displayTimesheet";
	}

	public String saveTimesheet() {
		List<TimesheetRow> list = new ArrayList<TimesheetRow>();
		for (TimesheetRow timesheetRow : currentTimesheet.getDetails()) {
			if (timesheetRow.getProjectID() == null
					|| timesheetRow.getProjectID().equals("")
					|| timesheetRow.getWorkPackage() == null
					|| timesheetRow.getWorkPackage().equals(""))
				list.add(timesheetRow);
		}

		currentTimesheet.getDetails().removeAll(list);

		// currentTimesheet.setWeekNumber(weekNum, year);

		timesheetManager.add(currentTimesheet);
		timesheetList = getTimesheets(currentTimesheet.getEmployee());
		// if (!conversation.isTransient())
		// conversation.end();
		return "displayTimesheet";
	}

	public Timesheet getCurrentTimesheet() {
		return currentTimesheet;
	}

	public void setCurrentTimesheet(Timesheet currentTimesheet) {
		this.currentTimesheet = currentTimesheet;
	}

	public EmployeeController getEmpControl() {
		return empControl;
	}

	public void setEmpControl(EmployeeController empControl) {
		this.empControl = empControl;
	}

	public TimesheetManager getTimesheetManager() {
		return timesheetManager;
	}

	public void setTimesheetManager(TimesheetManager timesheetManager) {
		this.timesheetManager = timesheetManager;
	}

	// public Conversation getConversation() {
	// return conversation;
	// }
	//
	// public void setConversation(Conversation conversation) {
	// this.conversation = conversation;
	// }

	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Timesheet> getTimesheetList() {
		return timesheetList;
	}

	public void setTimesheetList(List<Timesheet> timesheetList) {
		this.timesheetList = timesheetList;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
