package ca.bcit.infosys.access;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ca.bcit.infosys.timesheet.Timesheet;

public class TimesheetManager implements java.io.Serializable{
	
	
	
	
	List<Timesheet>  timesheetList =  new ArrayList<Timesheet>(); 
	public void add(Timesheet timesheet){
		timesheetList.add(timesheet);
	}
	
	public void update(Timesheet timesheet){
		int index = timesheetList.indexOf(timesheet);
		timesheetList.set(index, timesheet);
	}

	
	public List<Timesheet> getTimesheets() {
		return timesheetList;
	}

}