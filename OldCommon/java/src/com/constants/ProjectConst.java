package com.constants;


public class ProjectConst  {
      /** List Timesheets action code */
      public static final String REQ_LIST = "list";
	  /** Edit Timesheet action code */
	  public static final String REQ_EDIT = "edit";
	  /** Add Timesheet action code */
	  public static final String REQ_ADD = "add";
	  /** Delete Timesheet action code */
	  public static final String REQ_DELETE = "delete";
	  /** Save Timesheet action code */
	  public static final String REQ_SAVE = "save";
	  /** Execute previous Timesheet search action code */
	  public static final String REQ_BACK = "back";
	  /** Client action to prepare selection criteria */
	  public static final String REQ_CRITERIA = "getcriteria";
	  
	  /** Build selection criteria and perform a search of Timesheet action code */
	  public static final String REQ_SEARCH = "search";
	  /**  New Timesheet  Search Console client action code */
	  public static final String REQ_NEWSEARCH = "newsearch";
	  /** Display Timesheet search screen using search criteria stored in session object client action code */
	  public static final String REQ_OLDSEARCH = "oldsearch";
	  /** Cancels Timesheet action code */
	  public static final String REQ_CANCEL = "cancel";
	  /** Submits a Timesheet */
	  public static final String REQ_FINALIZE = "finalize";
      /** Invoce Timesheet action code */
      public static final String REQ_INVOICE = "invoice";
      /** Bulk timesheet invoice action code */
      public static final String REQ_BULKINVOICE = "bulkinvoice";
      /** Bulk timesheet invice submit code */
      public static final String REQ_BULKINVOICESUBMIT = "bulkinvoicesubmit";
      /** Print Timesheet Details action code */
      public static final String REQ_PRINT = "printdetails";
      /** Print Timesheet Details action code */
      public static final String REQ_PRINTTIMESHEET = "printtimesheet";
      /** Approve Timesheet action code */
      public static final String REQ_APPROVE = "approve";
      /** Decline Timesheet action code */
      public static final String REQ_DECLINE = "decline";                  

	
	  /** Request attribute name mapping to timesheet list */
	  public static final String CLIENT_DATA_TIMESHEETS = "timesheets";
	  /** Request attribute name mapping to timesheet client list */
	  public static final String CLIENT_DATA_CLIENTS = "clients";
	  /** Request attribute name mapping to timesheet employee list */
	  public static final String CLIENT_DATA_EMPLOYEES = "employees";
	  /** Request attribute name mapping for master list of timesheet statuses */
	  public static final String CLIENT_DATA_STATUSES = "status";
	  /** Request attribute name mapping for the timesheet week dates */
	  public static final String CLIENT_DATA_DATES = "dates";
	  /** Request attribute name mapping for the date representing the week ending period */
	  public static final String CLIENT_DATA_PERIOD = "endperioddate";
	  /** Request attribute name mapping for project-task time for a timesheet */
	  public static final String CLIENT_DATA_TIME = "projecttasktime";
      /** Request attribute mapping for determining if timesheet is accessed by manager or an employee */
      public static final String CLIENT_DATA_MODE = "timesheetmode";
	  
	  /** Not submitted timesheet status code */
	  public static final int TIMESHEET_STATUS_DRAFT = 1;
	  /** Submitted timesheet status code */
	  public static final int TIMESHEET_STATUS_SUBMITTED = 2;
	  /** Received timesheet status code */
	  public static final int TIMESHEET_STATUS_RECEIVED = 3;
	  /** Approved timesheet status code */
	  public static final int TIMESHEET_STATUS_APPROVED = 4;
	  /** Declined timesheet status code */
	  public static final int TIMESHEET_STATUS_DECLINED = 5;
      /** Invoiced Timesheet status code */
      public static final int TIMESHEET_STATUS_INVOICED = 6;
      
      /** The mode of a timesheet when a non-managerial employee has access to the timesheet */
      public static final String TIMESHEET_MODE_EMPLOYEE = "E";
      /** The mode of a timesheet when a manager has access to the timesheet */
      public static final String TIMESHEET_MODE_MANAGER = "M";
      
      /** Full Time Employee Type */
      public static final int EMPLOYEE_TYPE_FULLTIME = 1;
      /** Part Time Employee Type */ 
      public static final int EMPLOYEE_TYPE_PARTTIME = 2;
      /** Contractor Employee Type */
      public static final int EMPLOYEE_TYPE_CONTRACTOR = 3;
      
	
}