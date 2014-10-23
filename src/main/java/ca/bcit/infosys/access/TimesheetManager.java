package ca.bcit.infosys.access;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ca.bcit.infosys.timesheet.Timesheet;

public class TimesheetManager implements java.io.Serializable{
	

	static private List<Timesheet>  timesheetList =  new ArrayList<Timesheet>(); 
	
	
	
	public TimesheetManager() {
		System.out.println("TimesheetManager");
	}

	public void add(Timesheet timesheet){
		timesheetList.add(timesheet);
	}
	
	public void update(Timesheet timesheet){
		int index = timesheetList.indexOf(timesheet);
		if(index != -1)
		timesheetList.set(index, timesheet);
		else timesheetList.add(timesheet);
	}

	
	public List<Timesheet> getTimesheets() {
		return timesheetList;
	}

}