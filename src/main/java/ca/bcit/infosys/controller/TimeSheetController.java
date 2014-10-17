package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.access.TimesheetManager;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetCollection;


/**
 * Resources to display categories and choose one.
 * @author blink
 * @version 1
 *
 */
@Named("sheetControl")
@ConversationScoped
public class TimeSheetController  implements Serializable, TimesheetCollection{


	@Inject private EmployeeController empControl;
	@Inject private Timesheet currentTimesheet;
	@Inject private TimesheetManager timesheetManager;
	 private List<Timesheet> timesheetList;
	 
	@Inject private Conversation conversation;

	public TimeSheetController() {
		
	}
	
	@Override
	public List<Timesheet> getTimesheets() {
		timesheetList  = timesheetManager.getTimesheets();
		return timesheetList;
	}

	@Override
	public List<Timesheet> getTimesheets(Employee e) {
		timesheetList = getTimesheets();
		if(timesheetList == null) return null;
		for (Timesheet timesheet : timesheetList) {
			if(!timesheet.getEmployee().equals(e))
				timesheetList.remove(timesheet);
		}
		return timesheetList;
	}



	@Override
	public Timesheet getCurrentTimesheet(Employee e) {
		timesheetList =  getTimesheets(e);
		if(timesheetList == null) return null;
		 Calendar c = new GregorianCalendar();
	
		for (Timesheet timesheet : timesheetList) {
			if(  c.get(Calendar.WEEK_OF_YEAR) == timesheet.getWeekNumber() )
				return timesheet;			
			}
		return null;
	}

	@Override
	public String addTimesheet() {
		conversation.begin();
		currentTimesheet = new Timesheet();
		System.out.println(empControl.getCurrentEmployee().getName());
		currentTimesheet.setEmployee(empControl.getCurrentEmployee());
		return "newTimesheet.xhtml";
	}
	
	public String addRow(){
		currentTimesheet.addRow();
		System.out.println("add");
		return "newTimesheet.xhtml";
	}
	
	public String saveTimesheet(){
		timesheetManager.add(currentTimesheet);
		conversation.end();
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


	
	
}
