/**
 * Moves selected items from the form select control, src, to its destination select control, dest.
 * An error message popup is displayed in the event src and dest are not valid select controls.
 */
function moveSelected(src, dest, highlight) {
	// Both src and dest must be valid select controls
	if (!src) {
		alert("Source object is invalid");
		return;
	}
	if (!dest) {
		alert("Destination object is invalid");
		return;
	}
	if (!isSelectControl(src)) {
		alert("Source object, " + src.name + ", must be of type HTML Select");
		return;
	}
	if (!isSelectControl(dest)) {
		alert("Destination object, " + dest.name + ", must be of type HTML Select");
		return;
	}
	
	// Determine if each selection is to be highlighted when it reaches it destination
	if (!highlight) {
		highlight = false;
	}
	
	// Get first selected option
	var ndx = src.selectedIndex;
	while (ndx != -1) {
		// Move selected option to its destination
		dest[dest.length] = new Option(src[ndx].text, src[ndx].value, false, highlight);
		// Remove selected option from the source
		src[ndx] = null;
		// Get next selected option.
		ndx = src.selectedIndex;
	}
	
	
}


/**
 *  Returns true if obj is in deed a HTML form select input control.  Otherwise, false is returned.
 */
function isSelectControl(obj) {
	return (obj.type == "select-one" || obj.type == "select-multiple");
}

/**
 * Selects all options in the select control provided.   Returns 0 when obj is invalid 
 * or empty, -1 if object is not a HTML select control, or the total number of options 
 * selected.
 */
function selectAllOpts(obj) {
	if (!obj) {
  		return 0;
    }
    if (!isSelectControl(obj)) {
		return -1;
	}
	var ndx;
	var total = 0;
	for (ndx = 0; ndx < obj.length; ndx++) {
	    obj.options[ndx].selected = true;
	    total++;
	}
	return total;
}

/**
 * Retrieves the selected value from a group of liked-named radio buttons.
 */
function getSelectedRadio(obj) {
		if (!obj)  {
				return "undefined";
		}
		var ndx;
		for (ndx = 0; ndx < obj.length; ndx++) {
				if (obj[ndx].type != "radio") {
					continue;
				}
				if (obj[ndx].checked) {
						return obj[ndx].value;
				}
		}
		return null
}