function addTableRow(src) {
    var tr;
    var td;
    var sHTML;
    var rowID;

        // Determine next RowID
    rowID = this.tblOrders.rows.length;
    if (rowID == 1 && typeof this.OrderRequestForm.rowstatus == "undefined") {
      tblOrders.deleteRow(0);
      rowID = 0;
    }
        // Insert Row
    tr = tblOrders.insertRow();
    tr.bgColor = "#FFFFFF";

        // Add selection Checkbox control
    td = tr.insertCell();
    td.bgColor = "#0099FF";
    sHTML = "<div align=center><input type=checkbox name=pcCBX value=" + rowID + "></div>"
    sHTML += " <input type=hidden name=pcCredOrderKey>"
    sHTML += " <input type=hidden name=rowstatus value=N>"
    td.innerHTML = sHTML;

        // Add Order Number
    td = tr.insertCell();
    sHTML = "<input type=text name=pcOrderNum size=15 maxlength=20 defaultvalue=" + rowID + " style=border:none style=font-size:x-small onBlur=flagChange(defaultvalue) onChange=flagChange(defaultvalue)>"
    td.innerHTML = sHTML;

        // Add Order Amount
    td = tr.insertCell();
    sHTML = "<input type=text name=pcAmount size=27 maxlength=11 value=0 defaultvalue=" + rowID + " style=text-align:right style=border:none style=font-size:x-small style=text-align:right onBlur=flagChange(defaultvalue) onChange=sumOrders(defaultvalue)>"
    td.innerHTML = sHTML;
    tr.scrollIntoView(false);
  }

  //////////////////////////////////////////////////////////////////
  //  Form requires the following objects and labled as stated:
  //      1. checkbox - selCbx
  //      2. text or hidden control - rowstatus
  //////////////////////////////////////////////////////////////////
  function deleteRows(form, table) {
    var rows;
    var row;
    var selcount = 0;
    var ordertotal = 0;

       // Determine total rows to examine
    rows = this.table.rows.length;
    selcount = 0;
    ///////////////////////////////////////////////////
    // If no rows exist display an error message and //
    // exit function without further processing      //
    ///////////////////////////////////////////////////
    if (rows == 1 && typeof this.form.rowstatus == "undefined") {
      alert("Cannot perform Delete.  No valid rows exist to be deleted!");
      return;
    }
    if (rows == 1) {
      if (this.form.selCbx.checked) {
          this.form.rowstatus.value = "D";
          row = this.table.rows[0];
          row.style.display = "none";
          selcount++;
          ordertotal = 0;
      }
    }
    else {
       ordertotal = 0;
       for (var ndx = 0; ndx < this.table.rows.length; ndx++) {
          row = this.table.rows[ndx];
          if (this.form.selCbx[ndx].checked && row.style.display != "none") {
             this.form.rowstatus[ndx].value = "D";
             row.style.display = "none";
             this.form.selCbx[ndx].checked = false;
             selcount++;
          }
          else {
             if (row.style.display != "none") {
               ordertotal = parseFloat(ordertotal) + parseFloat(this.form.pcAmount[ndx].value)
             }
          }
       }
    }
    if (selcount <= 0) {
      alert("At least one item must selected in order to successfully perform a deletion");
      return;
    }
    this.OrderTotal.innerText = ordertotal;
  }


  //////////////////////////////////////////////////////////////////
  //  Form requires the following objects and labled as stated:
  //      1. checkbox - selCbx
  //      2. text or hidden control - rowstatus
  //////////////////////////////////////////////////////////////////
  function updateRowStatus(form, table, currentrow) {
    var row;
    var src;
    src = event.srcElement;
    if (currentrow >= 1 && this.table.rows.length > 1) {
      row = currentrow - 1;
      this.form.rowstatus[row].value = "M"
      if (src.name == "pcAmount")
        this.sumOrders(currentrow);
    }
    else {
      if (currentrow = 1 && this.table.rows.length == 1) {
        this.form.rowstatus.value = "M"
        if (src.name == "pcAmount")
          this.sumOrders(currentrow);
      }
    }
    if (src.name == "pcOrderNum") {
      for (var ndx = 0; ndx < src.value.length; ndx++) {
        if ("0123456789".indexOf(src.value.charAt(ndx)) < 0) {
          alert("Order Number must be numeric!");
        }
      }
    }
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
