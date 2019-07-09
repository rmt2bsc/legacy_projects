$PBExportHeader$n_appmanager.sru
forward
global type n_appmanager from n_rmt_appmanager
end type
end forward

global type n_appmanager from n_rmt_appmanager
end type
global n_appmanager n_appmanager

type variables
int    ii_DEFAULTMINHOURFACTOR = 5  // SCR11 RMT040402

str_company_info   istr_data
end variables
forward prototypes
public function integer of_load_company_info ()
public function str_company_info of_get_company_info ()
end prototypes

public function integer of_load_company_info ();// Loads company info to be used though out the application.
//  CR 001

datastore    lds

lds = create datastore
lds.dataobject = "d_company_info"
lds.settransobject(sqlca)

if lds.retrieve() <= 0 then
	messagebox(iapp_object.dwMessageTitle, "Compnay Data retrieval failed!")
	return -1
end if

istr_data.name = lds.getItemString(1, "name")
istr_data.addr1 = lds.getItemString(1, "addr1")
istr_data.addr2 = lds.getItemString(1, "addr2")
istr_data.city = lds.getItemString(1, "city")
istr_data.state = lds.getItemString(1, "state")
istr_data.zip = lds.getItemString(1, "zip")
istr_data.phone = lds.getItemString(1, "phone")
istr_data.fax = lds.getItemString(1, "fax")
istr_data.cell = lds.getItemString(1, "cell")
istr_data.email = lds.getItemString(1, "email")
istr_data.website = lds.getItemString(1, "website")
istr_data.owner_name = lds.getItemString(1, "owner_name")

return 1
end function
public function str_company_info of_get_company_info ();
//CR 001
return this.istr_data
end function
on n_appmanager.create
call super::create
end on

on n_appmanager.destroy
call super::destroy
end on

event constructor;call super::constructor;int       li_result
string    ls_msg


// The file name of the application INI file
li_result = this.of_SetAppIniFile ("bussys.ini")   /* RMT 04212003 */
if li_result <> 1 then  // file is locked by another application, file is not found, or a null reference was assigned as a parameter to of_setAppIniFile()
   messagebox("Application Error", "Application will abort!", stopsign!)
	halt
end if
   	
// Name of the application
iapp_object.DisplayName = profilestring(this.of_getAppIniFile(), "Proprietary", "displayname", "")

// Microhelp functionality
this.of_SetMicroHelp (true)

// The application version
this.of_SetVersion (profilestring(this.of_getAppIniFile(), "Proprietary", "version", ""))

// The application build
this.of_Setbuild (profilestring(this.of_getAppIniFile(), "Proprietary", "build", ""))

// The application logo (bitmap file name)
this.of_SetLogo (profilestring(this.of_getAppIniFile(), "Proprietary", "logo", ""))

// Application copyright message
this.of_SetCopyright (profilestring(this.of_getAppIniFile(), "Proprietary", "copyrightdate", "1999"))

// Application developed date
this.of_SetDevDate (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_date", ""))

// Application copyright message
this.of_SetDevTime (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_time", ""))

// Application Developer name
this.of_SetDeveloper (profilestring(this.of_getAppIniFile(), "Proprietary", "developer", ""))

// Application Developer Company
this.of_SetDevCompany (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_company", ""))

// Developer E-Mail
this.of_SetDevEMail (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_email", ""))

// Developer Web Site
this.of_SetDevWebSite (profilestring(this.of_getAppIniFile(), "Proprietary", "dev_company_website", ""))

// Licensed Company
this.of_SetLicensedTo (profilestring(this.of_getAppIniFile(), "Proprietary", "licensed_to", ""))

// Get Backup Information
this.of_setDBPath(profilestring(this.of_getAppIniFile(), "Backup", "DBPath", ""))
this.of_setDBName(profilestring(this.of_getAppIniFile(), "Backup", "DBName", ""))
this.of_setBackupPath(profilestring(this.of_getAppIniFile(), "Backup", "BackupPath", ""))
this.of_setNextBackupCount( long(profilestring(this.of_getAppIniFile(), "Backup", "NextBackupCount", "")) )



end event

event ue_open;call super::ue_open;n_rmt_utility   lnv_util

lnv_util.of_sound("sound\Train.wav")

//if this.of_logondlg() <> 1 then
//	 halt
//end if




end event

event ue_connect;call super::ue_connect;// Logon to the database
sqlca.dbms = profilestring(gnv_app.of_getappinifile(), "Database", "DBMS", "");
sqlca.dbparm = profilestring(gnv_app.of_getappinifile(), "Database", "DBParm", "");
sqlca.userid = profilestring(gnv_app.of_getappinifile(), "Database", "UserID", "");
sqlca.dbpass = profilestring(gnv_app.of_getappinifile(), "Database", "Password", "");
connect using sqlca;

if sqlca.sqlcode <> 0 then
	messagebox(gnv_app.iapp_object.DisplayName, "Problem logging on to the database.~r~nSQL Error: " + sqlca.sqlerrtext)
	halt
end if

this.of_load_company_info()   // CR 001
end event

