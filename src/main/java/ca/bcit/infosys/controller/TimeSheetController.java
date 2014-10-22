package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	private Timesheet currentTimesheet;
	@Inject
	private TimesheetManager timesheetManager;
	private List<Timesheet> timesheetList;
	private Integer weekNum;
	private Integer year;
	private Employee currentEmp;
	@Inject
	private Conversation conversation;

	public TimeSheetController() {

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
		for (Timesheet timesheet : timesheetList) {
			if (!timesheet.getEmployee().equals(e))
				timesheetList.remove(timesheet);
		}
		return timesheetList;
	}

	public String displayCurrentTimesheet() {
		if (timesheetList != null) {
			this.currentTimesheet = timesheetList.get(timesheetList.size() - 1);
			return "displayTimesheet.xhtml";
		}
		else {
			return this.addTimesheet();
		} 
	}

	@Override
	public Timesheet getCurrentTimesheet(Employee e) {
		timesheetList = getTimesheets(e);
		if (timesheetList == null)
			return null;
		Calendar c = new GregorianCalendar();

		for (Timesheet timesheet : timesheetList) {
			if (c.get(Calendar.WEEK_OF_YEAR) == timesheet.getWeekNumber())
				return timesheet;
			else
				return null;
		}
		return null;
	}

	@Override
	public String addTimesheet() {
		// conversation.begin();
		//Calendar c = new GregorianCalendar();
        
		currentTimesheet = new Timesheet();
		weekNum = currentTimesheet.getWeekNumber();
		//year = c.get(Calendar.YEAR);
		currentTimesheet.setEmployee(empControl.getCurrentEmployee());
		return "newTimesheet.xhtml";
	}

	public String addRow() {

		currentTimesheet.addRow();

		return "";
	}

	public String editTimesheet() {

		return "displayTimesheet.xhtml";
	}

	public String delRow(TimesheetRow Row) {
		currentTimesheet.deleteRow(Row);
		return "displayTimesheet.xhtml";
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
		currentTimesheet.setWeekNumber(weekNum, year);
		currentTimesheet.getDetails().removeAll(list);
		timesheetManager.add(currentTimesheet);
		// if (conversation != null)
		// conversation.end();
		return "displayTimesheet.xhtml";
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

	public List<Timesheet> getTimesheetList() {
		return timesheetList;
	}

	public void setTimesheetList(List<Timesheet> timesheetList) {
		this.timesheetList = timesheetList;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public Integer getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Employee getCurrentEmp() {
		return currentEmp;
	}

	public void setCurrentEmp(Employee currentEmp) {
		this.currentEmp = empControl.getCurrentEmployee();
	}

}
