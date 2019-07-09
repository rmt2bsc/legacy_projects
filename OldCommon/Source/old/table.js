/********************************************************************************
 * Name:         TableObject
 * Prototype:    TableObject(p_doc, p_parentform, p_colname1, p_columncount)
 * Retruns:      none
 * Description:  This function sets up a custom object of type "tableobject" 
 *               functions to store data from a HTML TABLE control.  The original
 *               state and the changed state of the Table's data is captured in
 *               the properties, origbuf and deltabuf.   p_doc is the <BODY> or
 *               <FRAME> reference which a variable of type TableObject is 
 *               declared.   p_parentform is the form that will contain the Table
 *               data to capture.   p_colname1 is the name of the first column in
 *               the table.  p_columncount is the total number of columns per row
 *               of the table.
 ********************************************************************************/

function  TableObject(p_doc, p_parentform, p_colname1, p_columncount) {
   
   var msg
   var cntl

 
   this.doc = p_doc
   this.parentform = p_parentform
   this.columnname1 = p_colname1
   this.columncount = p_columncount
   this.origbuf = new Array()
   this.deltabuf = new Array()
   this.data = ""
   this.modifiedrows = 0
   this.showConfirmMsg = false

   this.loadbuffer = loadBuffer
   this.getStartColPos = getStartColPos
   this.setupRowData = setupRowData
   this.getData = getData
   this.isNumeric = isNumeric
   this.getColName = getColName
   this.getColIndex = getColIndex
   this.packageData = packageData
   this.getColPos = getColPos

   cntrl = eval("p_parentform." + p_colname1)
   /*****************************************************
    *  If control exist and the length of the control
    *  is undefined, only one row exist for that control.
    *  Otherwise, the length of the control should 
    *  greater than one and the total number of rows is
    *  equal to the length of the control.
    *****************************************************/
   if (typeof cntrl != "undefined") {   
      if (typeof cntrl.length == "undefined") {
         this.rows = 1
      }
      else {
         this.rows = cntrl.length
      }
   }
   else {
      alert("The start column, " + p_colname1 + " ,cannot be found for object instantiation")
      return -1
   }

   this.colnames = new Array(p_columncount)
   this.col1tablepos = this.getColPos(p_parentform, p_colname1)

   for(var ndx = 0; ndx < this.colnames.length; ndx++) {
      this.colnames[ndx] = p_parentform.elements[this.col1tablepos + ndx].name
   }
}



/********************************************************************************
 * Name:         loadBuffer
 * Prototype:    loadBuffer(target)
 * Retruns:      integer
 * Description:  This function loads the data from the HTML Table into one of
 *               the TableObject data buffers.   "target" is a reference to one
 *               the TableObject data buffers, origbuf or deltabuf.   Returns
 *               the total number of rows loaded into the targeted buffer.
 ********************************************************************************/
    
function loadBuffer(target) {

    var cntrlname
    var cntrl 
    var rowstartpos = 0
    var rowdata
    var cur_control

    rowdata = new Array(this.colmncount)
    alert("Delta buf name: " + target)
    for(rowndx = 0; rowndx < this.rows; rowndx++) {
       ///////////////////////////////////////////////////////
       //  Determine first column's location for current row
       ///////////////////////////////////////////////////////
       rowstartpos = this.col1tablepos + (rowndx * this.columncount)
       
       ///////////////////////////////////////////////////////
       //        Retrieve the current column's value
       ///////////////////////////////////////////////////////
       for(colndx = 0; colndx < this.columncount; colndx++) {
          //rowdata[colndx] = this.parentform.elements[ rowstartpos + colndx].value
          cur_control = this.parentform.elements[ rowstartpos + colndx]
          switch (cur_control.type.toLowerCase()) {
             case "text":
             case "textarea":
             case "hidden":
                 rowdata[colndx] = cur_control.value
                 break
                            
             case "checkbox":
                 rowdata[colndx] = cur_control.checked
                 break
                            
             case "radio":
                 if (cur_control.checked) {
                    rowdata[colndx] = cur_control.value
                 }
                 else {
                    rowdata[colndx] = ""
                 }
                 break
                            
             case "select":
                 rowdata[colndx] = cur_control.options[cur_control.selectedIndex].value
                 break

             case "button":
                  break
          }  // end switch
       }
       target[rowndx] = new this.setupRowData(rowdata)
    }
    return target.length
}


/********************************************************************************
 * Name:         getStartColPos
 * Prototype:    getStartColPos()
 * Retruns:      integer
 * Description:  This returns the position of the first column which is declared
 *               in the constructor.   The position is relative to the elements[]
 *               property of p_form.
 ********************************************************************************/
function  getStartColPos() {
   return getColPos(this.parentform, this.columnname1)
}
 

/********************************************************************************
 * Name:         getColPos
 * Prototype:    getColPos(p_form, p_colname)
 * Retruns:      integer
 * Description:  This function returns the position of the column, p_colname, 
 *               which exists in the form, p_form.   The position is relative to
 *               the elements[] property of p_form.  If the column is not found 
 *               then a code of -1 is returned to the caller.
 ********************************************************************************/
function  getColPos(p_form, p_colname) {
    for (ndx = 0; ndx < p_form.elements.length; ndx++) {
        if (p_colname == p_form.elements[ndx].name)
           return ndx
    }

    return -1
}


/********************************************************************************
 * Name:         setupRowData
 * Prototype:    setupRowData(p_data)
 * Retruns:      none
 * Description:  This function serves as an object that maintains the column
 *               values for a given row of the HTML Table control.   Once
 *               setupRowData is instantiated, an array property by the name of
 *               "data" is created to maintain a list of the current row's column
 *               values.   p_data is passed as input which its array items will be
 *               copied to "data".   The setupRowData object is assigned to each
 *               row of the origbuf and deltabuf buffers which creates a two a 
 *               dimensional matrix to mirror an HTML Table control.   
 *               For example: "this.origbuf[row].data[col]" .   IF p_data has no 
 *               data, then this.data is set to null.
 ********************************************************************************/
function  setupRowData(p_data) {
    var  items

   
    items = p_data.length  // get number of columns
    if (items <= 0) {
       this.data = null
       return
    }
    this.data = new Array(items)
    for(var ndx = 0; ndx < items; ndx++) {
       this.data[ndx] = p_data[ndx]
    }
}


/************************************************************************************
 * Name:         getData
 * Prototype:    getData(p_row, p_col, buffer)
 * Retruns:      String
 * Description:  This function gets data from the data buffer specified by "buffer".
 *               p_row is the row of the buffer, and p_col is the column number or 
 *               column name.  Valid values for "buffer" are "orig" for original
 *               buffer and "delta" for delta buffer.  An message is displayed
 *               and null is returned to the caller if an error occurs.
 ***********************************************************************************/
function getData(p_row, p_col, buffer) {
    var  data
    var  targetbuf
    var  colname
    var  temp
    var  ValidColumn = new Boolean(true)

    /////////////////////////////
    //   Validate parameters  
    /////////////////////////////
    if (p_row == null) {
       alert("Row number must be supplied when retrieving data from the data buffer")
       return null
    }
    if (!this.isNumeric(p_row)) {
       alert("Row number must be numeric when retrieving data from the data buffer")
       return null
    }
    if (p_row < 0) {
       alert("Row number must be greater than or equal to 0 when retrieving data from the data buffer")
       return null
    }
    if (p_col == null) {
       alert("Column number or name must be supplied when retrieving data from the data buffer")
       return null
    }
    if (this.isNumeric(p_col)) {
       if (this.getColName(p_col) == null) {
          ValidColumn = false
       }
    }
    else {
       temp = this.getColIndex(p_col)
       if (temp == null) {
          ValidColumn = false
       }
    }
    if (!ValidColumn) {
       alert("Column " + p_col + " is Invalid.  Cannot retrieve data from the data buffer")
       return null
    } 
    
    if (buffer == null || buffer == "delta") {
       targetbuf = this.deltabuf
    }
    else {
       if (buffer == "orig") {
          targetbuf = this.origbuf
       }
       else {
          alert("Invalid Data Buffer specified.")
          return null
       }
    }
  
    ///////////////////////////
    //        Get Data
    ///////////////////////////
    return targetbuf[p_row].data[p_col]
}
      

/************************************************************************************
 * Name:         getColName
 * Prototype:    getColName(index)
 * Retruns:      String
 * Description:  This function gets the name of a column positioned at "index".
 *               if index is invalid or out of range, then null is returned to the 
 *               caller.
 ***********************************************************************************/
function getColName(index) {
    var  ndx

    if (index >= 0 &&  index <= this.colnames.length) {
       return this.colnames[index]
    }
    else {
       return null
    }
}

/************************************************************************************
 * Name:         getColIndex
 * Prototype:    getColIndex(name)
 * Retruns:      Integer
 * Description:  This function gets the position of a column identified by "name".
 *               Null is returned to the caller if "name" cannot be found.
 ***********************************************************************************/
function getColIndex(name) {
    var  ndx

    for (ndx = 0; ndx < this.colnames.length; ndx++) {
       if (this.colnames[ndx].toLowerCase() == name.toLowerCase()) {
          return ndx
       }
    }
    return null
}
   

/************************************************************************************
 * Name:         isNumeric
 * Prototype:    isNumeric(val)
 * Retruns:      Boolean
 * Description:  Determines if "val" is numeric.  Returns true if numeric, and false
 *               otherwise.
 ***********************************************************************************/
function isNumeric(val) {
    var  chr
    var  ndx
              
    for(ndx = 0; ndx < val.length; ndx++) {
       chr = val.charAt(ndx)
       if (chr >= "0" && chr <= "9") {
          continue 
       }
       else {
          if (chr == "." || chr == "," || chr == "$" || chr == "+" || chr == "-") {
             continue
          }
          else {
             return false
          }
       }
    }
    return true
 }


/********************************************************************************************
 * Name:         compareData
 * Prototype:    compareData()
 * Retruns:      Integer
 * Description:  Determine if any changes were made by comparing the contents of the
 *               original data buffer to the delta data buffer.  Afterwards, package
 *               the data into a pipe-tilde delimited string where rows are delimited by
 *               '~' and columns are delimited by '|'.   Returns a modified row count.
 *******************************************************************************************/
function packageData() {
    var  row
    var  col
    var  expr
    var  DataUnchanged
    var  deltacount = 0
    var  validcolumn

    ///////////////////////////////////
    // Load Changes into delta buffer
    ///////////////////////////////////
    this.loadbuffer(this.deltabuf)                     

    //////////////////////////////////////////////
    //  Determine modified rows and package data
    //////////////////////////////////////////////
    this.data = ""
    for(row = 0; row < this.origbuf.length; row++) {
       expr = ""
       for(col = 0; col < this.columncount; col++) {
          expr += "this.getData(row, " + col + ", \"orig\") == this.getData(row, " + col + ", \"delta\") "
          if (col < (this.columncount - 1)) {
             expr += " && \n"
          }
       }
       DataUnchanged = eval(expr)
       if (DataUnchanged) {
          continue
       }
       else {
           /////////////////////////////////////////////////
           //  Package Data into a pipe delimited string  
           /////////////////////////////////////////////////
           if (typeof this.doc.prePackageData == "function") {
              this.doc.prePackageData(this, row)
           }
           for(col = 0; col < this.columncount; col++) {
              colvalue = this.getData(row, col, "delta")
              if (typeof this.doc.isValidColumn == "function") {
                 validcolumn = this.doc.isValidColumn(this, row, col, colvalue)
              }
              else {
                 validcolumn = true
              }
              if (validcolumn) {
                 this.data += colvalue
                 if (col < (this.columncount - 1)) {
                    this.data += "|"
                 }
              }
              else {
                 this.data = "error"
                 return -1    // error occured
              }
           }
           if (typeof this.doc.postPackageData == "function") {
              this.doc.postPackageData(this, row)
           }
           deltacount++
           this.data += "~"   // Mark end of row with tilde character

       }  // end for columns
    }  // end for rows

    if (typeof this.doc.preSaveData == "function") {  
        this.doc.preSaveData(this)
    }
    if (typeof this.doc.saveData == "function") {  
        this.doc.saveData(this)
    }
    if (typeof this.doc.postSaveData == "function") {  
        this.doc.postSaveData(this)
    }

    if (deltacount > 0) {
       this.modifiedrows = deltacount
    }

    alert(this.data)
    return deltacount
}


