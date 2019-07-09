$PBExportHeader$n_rmt_sql.sru
$PBExportComments$SQL services
forward
global type n_rmt_sql from n_rmt_baseobject
end type
end forward

global type n_rmt_sql from n_rmt_baseobject
end type
global n_rmt_sql n_rmt_sql

type variables
protected:
  string                                         is_select
  string                                         is_from
  string                                         is_where
  string                                         is_groupby
  string                                         is_having
  string                                         is_orderby

public:
  string                                is_sql
  string                                is_oldsql 
  string                                is_othersql


end variables

forward prototypes
public function string of_performsearch (u_rmt_dw adw_target, u_rmt_dw adw_criteria)
public function string of_othersql ()
protected function integer of_disassemble (string as_sql)
protected function string of_assemble ()
protected function integer of_initsql ()
protected function string of_buildwhereclause (u_rmt_dw adw_criteria)
end prototypes

public function string of_performsearch (u_rmt_dw adw_target, u_rmt_dw adw_criteria);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_performSearch
//
//	Arguments:  adw_target datawindow
//             adw_criteria datawindow
//
//	Returns:  integer
//
//	Description: Constructs the search criterai from the datawindow, adw_criteria, 
//              and applies the search criteria to the target datawindow, adw_target.
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	1.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

string ls_where
//string ls_sql
//string ls_oldsql
int    li_rowcount


is_sql = adw_target.object.datawindow.table.select
is_oldsql = is_sql
this.of_disassemble(is_sql)
ls_where = this.of_buildwhereclause(adw_criteria)

   // Add where clause to sql select statement
if len(ls_where) > 0 then
  if len(this.is_where) > 0 and not isNull(this.is_where) then
	  this.is_where += " and " + ls_where
  else
	  this.is_where += " where " + ls_where
  end if
  
     // Add having clause to sql select statement
  if len(this.is_groupby) > 0 and not isNull(this.is_groupby) then
  	  if len(this.is_having) > 0 and not isNull(this.is_having) then
  	     this.is_having += " and " + ls_where
     else
	     this.is_having += " having " + ls_where
	  end if
  end if
end if

// Re-Assemble SQL Statement
is_sql = this.of_assemble()

// Add any other sql clause that could exist after 
// the where clause to the sql select statement
if len(is_othersql) > 0 then
	is_sql += is_othersql
end if

//messagebox("SQL", is_sql)

adw_target.object.datawindow.table.select = is_sql
if adw_target.of_retrieve() = 0 then
	messagebox(gnv_app.iapp_object.DisplayName, "No records were found using the search criteria!");
end if

adw_target.object.datawindow.table.select = is_oldsql

return is_sql


end function

public function string of_othersql ();


return ""
end function

protected function integer of_disassemble (string as_sql);string    ls_value
int       li_pos
int       li_prevpos
string    ls_sql


this.of_initsql()
ls_sql = lower(as_sql)

/************************
 *  Get Select Clause
 ************************/
li_pos = pos(ls_sql, "from ", 1)
if li_pos > 0 then
   ls_value = mid(ls_sql, 1, li_pos - 1)
   this.is_select = ls_value
end if
li_prevpos = li_pos


/************************
 *  Get From Clause
 ************************/
li_pos = pos(ls_sql, "where ", 1)
if li_pos <= 0 then
	li_pos = pos(ls_sql, "group by ", 1)
	if li_pos <= 0 then
		li_pos = pos(ls_sql, "order by ", 1)
   end if
end if

if li_pos <= 0 then           
	// From clause must be the remainder of the sql statement
   li_pos = pos(ls_sql, "from ", 1)
	ls_value = mid(ls_sql, li_pos)
	this.is_from = ls_value
	return 1
else
	// From clause exist in the middle of the sql statement
	ls_value = mid(ls_sql, li_prevpos, (li_pos - li_prevpos))  
end if
this.is_from = ls_value
li_prevpos = li_pos

/************************
 *  Get Where Clause
 ************************/
li_pos = pos(ls_sql, "group by ", 1)
if li_pos <= 0 then
	li_pos = pos(ls_sql, "order by ", 1)
end if

if li_pos <= 0 then           
	// Where clause must be the remainder of the sql statement
   li_pos = pos(ls_sql, "where ", 1)
	ls_value = mid(ls_sql, li_pos)
	this.is_where = ls_value
	return 1
else
	// Where clause exist in the middle of the sql statement
	ls_value = mid(ls_sql, li_prevpos, (li_pos - li_prevpos))  
end if
this.is_where = ls_value
li_prevpos = li_pos

/************************
 *  Get Group By Clause
 ************************/
li_pos = pos(ls_sql, "having ", 1)
if li_pos <= 0 then
	li_pos = pos(ls_sql, "order by ", 1)
end if

if li_pos <= 0 then           
	// Group By clause must be the remainder of the sql statement
   li_pos = pos(ls_sql, "group by ", 1)
	ls_value = mid(ls_sql, li_pos)
	this.is_groupby = ls_value
	return 1
else
	// Group By clause exist in the middle of the sql statement
	ls_value = mid(ls_sql, li_prevpos, (li_pos - li_prevpos))  
end if
this.is_groupby = ls_value
li_prevpos = li_pos			
			
/************************
 *  Get Having Clause
 ************************/
li_pos = pos(ls_sql, "order by ", 1)

if li_pos <= 0 then           
	// Having clause must be the remainder of the sql statement
   li_pos = pos(ls_sql, "having ", 1)
	if li_pos <= 0 then
		return 1
	else 
  	   ls_value = mid(ls_sql, li_pos) 
 	   this.is_having = ls_value
		return 1
	end if
else
	// Having clause exist in the middle of the sql statement
	ls_value = mid(ls_sql, li_prevpos, (li_pos - li_prevpos))  
end if
this.is_having = ls_value
li_prevpos = li_pos

/************************
 *  Get Order By Clause
 ************************/
li_pos = pos(ls_sql, "order by ", 1)

if li_pos > 0 then           
	// Order By clause exists
	ls_value = mid(ls_sql, li_pos)			
	this.is_orderby = ls_value			
end if


return 1
end function

protected function string of_assemble ();

string   ls_sql


ls_sql = this.is_select
ls_sql += space(2) + this.is_from
if len(this.is_where) > 0 and not isNull(this.is_where) then
	ls_sql += space(2) + this.is_where
end if

if len(this.is_groupby) > 0 and not isNull(this.is_groupby) then
	ls_sql += space(2) + this.is_groupby
end if

if len(this.is_having) > 0 and not isNull(this.is_having) then
	ls_sql += space(2) + this.is_having
end if

if len(this.is_orderby) > 0 and not isNull(this.is_orderby) then
	ls_sql += space(2) + this.is_orderby
end if

return ls_sql
end function

protected function integer of_initsql ();this.is_select = ""
this.is_from = ""
this.is_where = ""
this.is_groupby = ""
this.is_having = ""
this.is_orderby = ""

return 1
end function

protected function string of_buildwhereclause (u_rmt_dw adw_criteria);//////////////////////////////////////////////////////////////////////////////
//
//	Event:  of_buildwhereclause
//
//	Arguments:  u_rmt_dw adw_criteria
//
//	Returns:  string
//
//	Description: Constucts the where clause from adw_criteria and returns the 
//              where clause to the caller
//
//////////////////////////////////////////////////////////////////////////////
//	
//	Revision History
//
//	Version
//	6.0   Initial version
//
//////////////////////////////////////////////////////////////////////////////

int   li_coltot
int   li_col
int   li_ndx
string ls_colname
string ls_dbcolname
string ls_value
string ls_where
string ls_coltype
dwitemstatus  l_dwstat


//ls_where = ""

// Check if row has been modified...if not, exit
adw_criteria.accepttext()
l_dwstat = adw_criteria.getitemstatus(1, 0, primary!)
if l_dwstat = notmodified! or l_dwstat = new! then
	return ""
end if

// CHeck if any column has changed.
li_ndx = 1
li_coltot = integer(adw_criteria.object.datawindow.column.count)
for li_col = 1 to li_coltot
	l_dwstat = adw_criteria.getitemstatus(1, li_col, primary!)
	choose case l_dwstat
		case newmodified!, datamodified!
			ls_colname = adw_criteria.describe("#" + string(li_col) + ".name")
			ls_dbcolname = adw_criteria.describe("#" + string(li_col) + ".dbname")
			ls_value = adw_criteria.inv_utility.of_getstringdata(adw_criteria, ls_colname, 1, false)
			ls_coltype = adw_criteria.describe("#" + string(li_col) + ".coltype")
	      if not isnull(ls_value) then
				if li_ndx > 1 then
					ls_where += ' and '
				end if
				
				choose case (lower(mid(ls_coltype,1, 3)))
					case "cha"
						ls_where += "upper(" + ls_dbcolname + ")"
						ls_where += " like "
     			   	ls_where +=  "~'" 
						ls_where += upper(ls_value) 
						ls_where += "%~'"
					case "dat", "tim"
						ls_where += ls_dbcolname
						ls_where += " = "
						if lower(mid(ls_coltype,1, 3)) = "dat" then
							ls_value = string(date(ls_value), "yyyy/mm/dd")
						end if
     			   	ls_where +=  "~'" + ls_value + "~'"
					case "dec", "int", "lon", "num"
						ls_where += ls_dbcolname
						ls_where += " = "
     			   	ls_where +=  ls_value
				end choose
				li_ndx++
			end if
	end choose
next

return ls_where
end function

on n_rmt_sql.create
TriggerEvent( this, "constructor" )
end on

on n_rmt_sql.destroy
TriggerEvent( this, "destructor" )
end on

