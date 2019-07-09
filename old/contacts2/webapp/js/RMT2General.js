var xactController = "/xactservlet";
var glController = "/glcustomerservlet";
var glCreditorController = "/glcreditorservlet";
var inventory = "/itemmasterservlet";

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: setRowStatus
//    Prototype: form - The form object that encapsulates all tareted input controls
//                          obj  - The input control that has changed.
//                          colRootName - A string that represents the root name of the input  control.   The input control may be
//                                                       named "desc1" or  "desc2", or "desc3" within a list of values.  The  root name would be
//                                                       considered, "desc".
//      Returns: n/a
//  Description: This function sets the rowStatus input control "M" when "obj" has changed.  The HTML page or JSP page
//                         must contain an input control  named as "rowStatus".   For those pages that contain multiple like
//                         rows which each row maintains its own row status, the input control  name should be prepended with
//                        "rowStatus" and a unique number appended.    For example, rowStatus1, rowstatus23.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function setRowStatus(form, obj, colRootName) {

  var suffixPos;
  var suffixValue;
  var colName;
  var rowStat

     //  If there is only one row, then the input control should
     //  be named, "rowStatus", without a unique number appended.
  if (obj.name.length == colRootName.length) {
    rowStatus.value = "M";
    return;
  }

     // Display an error message if the row status input control is not properly named.
  if (obj.name.length < colRootName.length) {
    alert("JS function, setRowStatus, is unable to extract the unique identifier from column, " + obj.name);
    return;
  }

    // If we have gotten this far, then we are dealing with row statuses on multiple rows

    // Parse out the unique number
  suffixPos = colRootName.length;
  suffixValue = obj.name.substr(suffixPos);

    // Get the reference of the targeted row status input control
  colName = form.name + ".rowStatus" + suffixValue;
  rowStat = eval(colName);

    // Set the value of rowStatusXXX to modified.
  rowStat.value = "M";

  return;

 /*
  alert("Object Name: " + obj.name + "\nRoot Name: " + colRootName);
  alert("Suffix Value: " + suffixValue);
  alert("Name of Target RowStatus: " + rowStat.name);
  alert("Before Value of Target RowStatus: " + rowStat.value);
  alert("After Value of Target RowStatus: " + rowStat.value);
*/

}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: activeChanged
//    Prototype: none
//      Returns: none
//  Description: This function sets a checkbox that is generally mapped to a database
//                         column that serves as an indicator and normally is represented with
//                         the value: 1 (on) and 0 (off).   If the object is selected then a
//                         value of 1 is assigned.  Otherwise, a value of 0 is assigned.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function activeChanged() {
    var obj = event.srcElement;

    if (obj.checked) {
      obj.value = "1";
    }
    else {
      obj.value = "0";
    }
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: logout
//    Prototype: none
//      Returns: none
//  Description: Submits the hidden form, logoutForm.   This hidden form shoud exist on
//                         every JSP page.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function logout() {
  this.logoutForm.submit();
}


function trim(str) {
  while("".indexOf(str.charAt(0)) == 0) {
    str = str.substring(1,str.length);
  }
  while("".indexOf(str.charAt(str.length - 1)) == 0) {
    str = str.substring(0,str.length - 1);
  }
  return str;
}

function spaces(total) {
  var value;
  for (var ndx = 0; ndx < total; ndx++) {
    value += " ";
  }
  return value;
}

 function disableRadioButtonGroup(rbGrp, flag, uncheckItem) {

    var count;

    if (typeof rbGrp == "undefined") {
      alert("Radio Button group is undefined");
      return;
    }

    count = rbGrp.length;
    for (var ndx = 0; ndx < count; ndx++) {
      rbGrp[ndx].disabled = flag;
      if (rbGrp[ndx].checked && uncheckItem) {
        rbGrp[ndx].checked = false;
      }
    }

    return;
  }
  
 function getSelectedRadioValue(rbGrp) {

    var count;

    if (typeof rbGrp == "undefined") {
      return "undefined";
    }

    count = rbGrp.length;
    if (count == null || count <= 1) {
       // Radio group contains only one member
       if (rbGrp.checked) {
          return rbGrp.value;
       }
       else {
          return null;
       }

    }
    for (var ndx = 0; ndx < count; ndx++) {
      if (rbGrp[ndx].checked) {
        return rbGrp[ndx].value;
      }
    }

    return null;
  }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: prevPage
//    Prototype: none
//      Returns: none
//  Description: Navigates to the previous page
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function prevPage() {
  history.go(-1);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: scrollToLastRow
//    Prototype: HTML Table object
//      Returns: none
//  Description: Navigates to the last row of a html table object (tbl)
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function scrollToLastRow(tbl) {
 var rowObj;
 var lastRow;

 lastRow = tbl.rows.length - 1;
 if (lastRow > 0) {
	 rowObj = tbl.rows(lastRow);
	 rowObj.scrollIntoView(false);
 }
 return;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: scrollToFirstRow
//    Prototype: HTML Table object
//      Returns: none
//  Description: Navigates to the first row of a html table object (tbl)
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function scrollToFirstRow(tbl) {
 var rowObj;
 var lastRow;

 lastRow = tbl.rows.length - 1;
 if (lastRow > 0) {
	 firstRow = 0;
	 rowObj = tbl.rows(firstRow);
	 rowObj.scrollIntoView(false);
 }
 return;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: handleAction
//    Prototype: _target - Target were response should be directed.
//                    _form - The form that is to be submitted.
//                    _action -  The action that is to be taken.  This is generally the name of the input control that triggered the action.
//      Returns: none
//  Description: Basically controls  the submission of a form.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function handleAction(_target, _form, _action) {
      _form.clientAction.value = _action;
      _form.target = _target;
      _form.submit();
    }
	

 function handlePrintAction(frame, form, obj, newAction) {
    obj = "print";
    changeAction(frame, form, obj, newAction);
 }	
 
 function changeAction(frame, form, obj, newAction) {
   form.action = newAction;
   handleAction(frame, form, obj);
 }	     
	
function hideSplash(_splash, _main) {
	var splash;
	var main;
	
	splash = document.getElementById(_splash);
	main = document.getElementById(_main);
	
    splash.style.visibility = "hidden";
	main.style.visibility = "visible";
}	

/**
 *  Opens a separate browser window.
 */
function openNewBrowser(url) {
	// Ensure that "http" protocol is prefixed to URL
	if (url.indexOf("http") < 0) {
		url = "http://" + url;	
	}
	window.open(url);
}


/* 
	Dynamically adds an input field element to the given HTML form.
	@param form the given HTML form element 
	@param fType the type of input field such as hidden, text
	@param fieldName the name of the hidden input field 
	@param fieldValue the (string) value of the hidden input field
*/
function createFormField(fForm, fType, fName, fValue) {
	if (!document.getElementById(fName)) {
		var inputElement = document.createElement("input");
		inputElement.setAttributeNode(createHtmlAttribute("type", fType));
		inputElement.setAttributeNode(createHtmlAttribute("id", fName)); 
		inputElement.setAttributeNode(createHtmlAttribute("name", fName)); 
		inputElement.setAttributeNode(createHtmlAttribute("value", fValue)); 
		fForm.appendChild(inputElement); 
	}
	else {
		document.getElementById(fName).value = fValue;
	}
	return;
}

/* 
	Creates an html attribute. 
	@param name the name of the attribute. 
	@param value the (string) value of the attribute. 
	@return the newly created html attribute
*/
function createHtmlAttribute(name, value) { 
	var attribute = document.createAttribute(name); 
	attribute.nodeValue = value; 
	return attribute;
}

/**
 * Identifies the javascript function that is to invoked when the enter key is pressed.  
 * See "processEnterKey(e)" function.
 */
function init() {
  document.onkeypress = processEnterKey;
}

/**
 * Submits a form when the enter key is pressed.  In order for this function to work 
 * properly, the javascript function, submitForm(), must be implemented somewhere within 
 * the scope of the page containing the form that is to be submitted.  Next, the following
 * line of code must be placed in the action handler for onLoad event of the HTML body tag:
 *    "document.onkeypress = processEnterKey;"
 * 
 * This function works best if you put an invisible submit button on the form to prevent the 
 * "ding" sound from occurring when the enter key is pressed and some other input controll 
 * has focus.   <input type="submit" style="height:0px; width:0px; border:0px;">
 */
function processEnterKey(e)	{
  if (null == e) {
    e = window.event ;
  }
  if (e.keyCode == 13)  {
    submitForm() ;
  }
}

/**
 * Because of the security enhancements in IE7, you can't close a window unless 
 * it is opened by a script. so the walkaround will be to let the browser thinks 
 * that this page is opened using a script then closing the window. below is the 
 * implementation.  The code is used to open a window in this case it's not defined 
 * into the current window.  In this way, we let the browser think that the current 
 * window is opened using javascript, so when the window.close() is executed it will 
 * close it without the message being displayed.  This has been tested for IE7, IE8,
 * and Firefox 3.0.8.
 *
 */
function close_without_prompt() {   
	 window.open('','_self',''); 
	 window.close();
}