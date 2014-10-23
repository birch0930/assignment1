package ca.bcit.infosys.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
@Named
@ConversationScoped
public class TimeSheetRowController implements Serializable {
	@Inject TimeSheetController sheetControl;
	@Inject
	private TimesheetManager timesheetManager;	
	@Inject
	private Conversation conversation;
	
	
}
