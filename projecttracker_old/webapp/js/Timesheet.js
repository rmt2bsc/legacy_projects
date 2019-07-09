
	/**
	 * Perpares an HTML/JSP form to edit a timesheet (_timesheet).
	 * Sets _timesheetId value to an input element on _form and submits
	 * _form to the server for processing.
	 *
	 * @param _frame The target frame for output.
	 * @param _form The form that is to be submitted.
	 * @param _command The command to be served.
	 * @param _timesheetId The id of the timesheet to edit.
	 */
	function editTimesheet(_frame, _form, _command, _timesheetId) {
		_form.Id.value = _timesheetId;
	    // Calling script is required to include RMT2General.js i norder to make the following call.
		handleAction(_frame, _form, _command);
	}
	
	   
	   function doOldSearch(frame, form, obj) {
	      var root = "<%=APP_ROOT%>/unsecureRequestProcessor/";
	      form.action= root + "Timesheet.List";
	      handleAction(frame, form, obj);
	   }
	   
	   
	   function doSubmit(frame, form, obj) {
	      returnValue = confirm("Timeheet will no longer be editable once submitted.  Are you sure?");
	      if (returnValue == false) {
	         return;
	      }
	      handleAction(frame, form, obj);
	   }	   