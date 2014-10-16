package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
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
		currentTimesheet = new Timesheet();
		currentTimesheet.setEmployee(empControl.getCurrentEmployee());
		return "addTimesheet";
	}

	
	public String saveTimesheet(){
		timesheetManager.add(currentTimesheet);
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

	
	
}
