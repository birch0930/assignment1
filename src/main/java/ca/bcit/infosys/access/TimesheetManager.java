package ca.bcit.infosys.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import ca.bcit.infosys.timesheet.Timesheet;



public class TimesheetManager implements java.io.Serializable{
	

	static private List<Timesheet>  timesheetList =  new ArrayList<Timesheet>(); 
	
	
	
	public TimesheetManager() {
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

	
	@SuppressWarnings("unchecked")
	public List<Timesheet> getTimesheets() {
		
		Collections.sort(timesheetList);

		return timesheetList;
	}

}