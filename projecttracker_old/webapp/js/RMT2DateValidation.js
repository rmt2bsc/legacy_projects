/**
 * DHTML date validation script conatining a group of functions that accepts a date 
 * string seperated into month, day and year integers and validates each date component 
 * seperately.  This library is only capable of validating dates in the American format 
 * (mm/dd/yyyy). 
 */
 
 
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=2100;

/**
 * User-defined date message array class.
 */
function ValidationMessages() {
	this[0] = "Date does not have a value";
	this[-1] = "The date format should be : mm/dd/yyyy";
	this[-2] = "Month should be a value between 1 and 12";
	this[-3] = "Day should be a value between 1 and 31";
	this[-4] = "Year must be between " + minYear + " and " + maxYear;
	this[-5] = "Date must be composed of numerical characters";
	this[-6] = "Start date cannot be greater than the end date";
	this[-7] = "The begin and/or end dates are invalid.  Check format and/or its character composition";
}

/**
 * Retrieves an error message pertaining using the return code of a date validation operation.  The 
 * error message lives as an array element and is indexed by its error code.
 *
 * @parm errorCode - The error code from an date validation operation which is used as an index 
 *                   to locate its corresponding message text.
 * @return The message.
 */
function getErrorMessage(errorCode) {
	if (isNumeric(errorCode) == false) {
		return null;
	}
	var messages = new ValidationMessages();
	var txt = messages[errorCode];
	if (!txt) {
		txt = "Error message code, " + errorCode + ", could not be obtained, becuase it is not a valid Date validation error code";
	}
	return txt;
}

/**
 * This method is used to validate a HTML form field as an American Date (mm/dd/yyyy).
 * When the date is found to be invalid, its input field will retain focus on the form 
 * as an error (dateField).   The target date field is considered to be acceptable
 * when its value is a valid date, or when its value is equal to "".
 * 
 * @parm dateField - This is a HTML input field control.
 * @parm showMsg - Set to true to display error messages.  Set to false to ignore error messages.
 * @returns 1=date is valid 
 *          0=date value is empty
 *			-1=incorrect date format 
 *			-2=invalid month 
 *			-3 invalid day 
 *			-4=year was not between 1900 and 2100
 *			-5=date composed of non-numeric characters, 
 */
function validateDateField(dateField, showMsg){
	var rc = validateDate(dateField.value);
	if ( rc != 1){
		dateField.focus();
		if (showMsg == true) {
			var msg = getErrorMessage(rc);
			if (msg != null) {
				alert(msg);
			}
		}
	}
    return rc;
 }
 

/**
 * Verifies if the given String value is a valid date in the American format (mm/dd/yyyy).
 *
 * @parm dtStr - A date String.
 * @returns 1=date is valid 
 *          0=date value is empty
 *			-1=incorrect date format 
 *			-2=invalid month 
 *			-3 invalid day 
 *			-4=year was not between 1900 and 2100
 *			-5=date composed of non-numeric characters, 
 */
function validateDate(dtStr) {
	// Do not process date if value is empty.
	if (dtStr == "") {
		return 0;
	}
	
	var daysInMonth = DaysArray(12);
	var pos1 = dtStr.indexOf(dtCh);
	var pos2 = dtStr.indexOf(dtCh, pos1 + 1);
	var strMonth = dtStr.substring(0, pos1);
	var strDay = dtStr.substring(pos1 + 1, pos2);
	var strYear = dtStr.substring(pos2 + 1);
	strYr = strYear;
	if (strDay.charAt(0) == "0" && strDay.length > 1) {
		strDay = strDay.substring(1);
	}
	if (strMonth.charAt(0) == "0" && strMonth.length > 1) {
		strMonth = strMonth.substring(1);
	}
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0) == "0" && strYr.length > 1) {
			strYr = strYr.substring(1);
		}
	}
	month = parseInt(strMonth);
	day = parseInt(strDay);
	year = parseInt(strYr);
	
	// The date format should be : mm/dd/yyyy
	if (pos1 == -1 || pos2 == -1) {
		return -1;
	}
	
	// Date must be composed of numerical characters.
	if (dtStr.indexOf(dtCh, pos2 + 1)!= -1 || isValidDateInt(stripCharsInBag(dtStr, dtCh)) == false) {
		return -5;
	}
	
	// Month should be a value between 1 and 12
	if (strMonth.length < 1 || month < 1 || month > 12){
		return -2;
	}
	// Day should be a value between 1 and 31.  Consideration is given for leap year.
	if (strDay.length < 1 || day < 1 || day > 31 || (month == 2 && day > daysInFebruary(year)) || day > daysInMonth[month]){
		return -3;
	}
	// Year must be between minYear and maxYear.
	if (strYear.length != 4 || year == 0 || year < minYear || year > maxYear) {
		return -4;
	}
	return 1;
}


/**
 * Verifies that all characters in between the slashes of a date String are numerical values.
 * 
 * @parm strValue -  The String of characters to validate.
 * @return true when all characters are valid numbers and false if one or 
 *         more characters are non-numerical.
 */
function isValidDateInt(strValue){
	var i;
    for (i = 0; i < strValue.length; i++){   
        // Check that current character is number.
		var rc = isNumeric(strValue.charAt(i))
        if (!rc) {
			return false;
		}
    }
    // All characters are numbers.
    return true;
}

/**
 * Validates each character of the argument, strValue, which only permits the 
 * entry of characters 0 to 9, a decimal point and a negative sign.
 *
 * @parm strValue - The String of characters to validate.
 * @return true when all characters of strValue are numeric.  Otherwise, fasle 
 *         is returned.
 */
function isNumeric(strValue) {
   var strValidChars = "0123456789.-";
   var strChar;

   if (strValue.length == 0) {
	   return false;
   }

   //  test strValue consists of valid characters listed above
   for (i = 0; i < strValue.length; i++) {
      strChar = strValue.charAt(i);
      if (strValidChars.indexOf(strChar) == -1) {
         return false;
      }
   }
   return true;
}


/**
 * Extracts all characters from the date string that are not equal 
 * to the slash ("/") character.
 *
 * @return A string of all characters that exists before and after 
 *         the slashes in a date String.
 */
function stripCharsInBag(src, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < src.length; i++){   
        var c = src.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

/**
 * Determines if the spcified year is leap year and returns the total 
 * number of days for the month of February in the evaluated year.
 * February has 29 days in any year evenly divisible by four, EXCEPT
 * or centurial years which are not also divisible by 400 and are also 
 * divisible by 100.  Every 100 years is not a leap year, but every 400 
 * years is a leap year. 
 *
 * @return The total number of days in February for the year parameter.
 */
function daysInFebruary (year){
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}

/**
 * This function defines a user-defined Array object for Identifying the 
 * months of a year.  Logically, each index represents a month and each 
 * month element contains a value that represents the total number of days 
 * that are in that month.  For example, January is positioned at index 1 
 * and December is positionted at index 12.  The total number of days in 
 * January is 31 and can be referenced as DaysArray days[1].
 *
 * @parm monthCount - The total number of months to configure.  For example, 3 will 
 *           allocate space for Jan, Feb, and Mar.  4 will allocate space 
 *			 for Jan through Dec.
 */
function DaysArray(monthCount) {
	for (var ndx = 1; ndx <= monthCount; ndx++) {
		// Default as 31 day month
		this[ndx] = 31
		// Check if 30 day month
		if (ndx == 4 || ndx == 6 || ndx == 9 || ndx == 11) {
			this[ndx] = 30;
		}
		// Check if month is February.
		if (ndx == 2) {
			this[ndx] = 29;
		}
   } 
   return this
}
 
 /**
  * Returns the American date display mask as a String.
  */
function displayMask(){
    return "mm/dd/yyyy";
 }
 

/**
 * Calculates the difference in days between two String dates and optionally, displays an error message.
 * 
 * @parm startDateField - The HTML inut control representing the begin date.
 * @parm endDateField - The HTML inut control representing the end date.
 * @parm showMsg - Set to true to display error messages.  Set to false to ignore error messages.
 * @return int >= 0 for the number of days, -6 when end date is less than begin date, and 
 *            -7 for general date validation errors.
 */
function getDateFieldDiff(startDateField, endDateField, showMsg) {
	var rc = getDateDiff(startDateField.value, endDateField.value);
	if (rc < 1)  {
		if (showMsg == true) {
			var msg = getErrorMessage(rc);
			if (msg != null) {
			alert(msg);
			}
		}
	}
	return rc;
}

/**
 * Calculates the difference in days between two String dates.
 * 
 * @parm startDateStr - THe begin date in String format.
 * @parm endDateStr - The end date in String format.
 * @return int >= 0 for the number of days, -6 when end date is less than begin date, and 
 *            -7 for general date validation errors.
 */
function getDateDiff(startDateStr, endDateStr) {
	var startDate = createDate(startDateStr);
	var endDate = createDate(endDateStr);
	var rc = 0;
	
	if (startDate != null && endDate != null) {
		// Start date cannot be greater than the end date
		if (endDate.getTime() >= startDate.getTime()) {
			return ((endDate.getTime() - startDate.getTime()) / getOneDay());
		}
		rc = -6;
	}
	else {
		// General date validation error occurred.
		rc = -7;
	}
	return rc;
}

/**
 * Creates a javascript Date object from dateStr.   Input parameter, dateStr, must 
 * be in one of two formats: American (mm/dd/yyyy) or British (dd/mm/yyyy).
 *
 * @return A Date object or null if dateStr is or the incorrect format or invalid.
 */
function createDate(dateStr) {
	var pos1;
	var pos2;
	var strMonth;
	var strDay;
	var strYear;
	
	var rc = validateDate(dateStr);
	if (rc == 1) {
		pos1 = dateStr.indexOf(dtCh);
	    pos2 = dateStr.indexOf(dtCh, pos1 + 1);
	    strMonth = dateStr.substring(0, pos1);
	    strDay = dateStr.substring(pos1 + 1, pos2);
	    strYear = dateStr.substring(pos2 + 1);
		return new Date(strYear, (strMonth - 1), strDay);
	}
	return null;
	
}

/**
 * Calculates the total number of milliseconds that are equivalent to one day.
 * 
 * @return One day in  milliseconds.
 */
function getOneDay() {
	return Math.ceil((1000 * 60 * 60 * 24));
}



