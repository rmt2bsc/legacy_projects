$PBExportHeader$w_rmt_about.srw
forward
global type w_rmt_about from w_rmt_response
end type
type dw_about from u_rmt_dw within w_rmt_about
end type
type p_logo from picture within w_rmt_about
end type
type cb_1 from u_rmt_cb_ok within w_rmt_about
end type
end forward

global type w_rmt_about from w_rmt_response
int Height=1508
boolean TitleBar=true
string Title="About"
long BackColor=80269524
dw_about dw_about
p_logo p_logo
cb_1 cb_1
end type
global w_rmt_about w_rmt_about

event ue_postopen;
dw_about.of_setupdateable(false)
dw_about.insertrow(0)
dw_about.object.developer[1] = gnv_app.of_getDeveloper()
dw_about.object.product_name[1] = gnv_app.iapp_object.displayname
dw_about.object.dev_company [1] = gnv_app.of_getDevCompany()
dw_about.object.version[1] = gnv_app.of_getVersion()
dw_about.object.build[1] = gnv_app.of_getBuild()
dw_about.object.dev_email[1] = gnv_app.of_getDevEMail()
dw_about.object.dev_website[1] = gnv_app.of_getCompanyWebSite()
dw_about.object.dev_date[1] = gnv_app.of_getDevDate()
dw_about.object.dev_time[1] = gnv_app.of_getDevTime()
dw_about.object.licensed_to[1] = gnv_app.of_getLicensedTo()
p_logo.PictureName = gnv_app.of_getLogo()
end event

event ue_preopen;// overidden
end event

on w_rmt_about.create
int iCurrent
call super::create
this.dw_about=create dw_about
this.p_logo=create p_logo
this.cb_1=create cb_1
iCurrent=UpperBound(this.Control)
this.Control[iCurrent+1]=this.dw_about
this.Control[iCurrent+2]=this.p_logo
this.Control[iCurrent+3]=this.cb_1
end on

on w_rmt_about.destroy
call super::destroy
destroy(this.dw_about)
destroy(this.p_logo)
destroy(this.cb_1)
end on

type dw_about from u_rmt_dw within w_rmt_about
int X=713
int Y=16
int Width=1673
int Height=1132
boolean BringToTop=true
string DataObject="d_about"
boolean Border=false
BorderStyle BorderStyle=StyleBox!
boolean VScrollBar=false
boolean LiveScroll=false
end type

type p_logo from picture within w_rmt_about
int X=50
int Y=48
int Width=512
int Height=328
boolean BringToTop=true
boolean Border=true
BorderStyle BorderStyle=StyleLowered!
boolean FocusRectangle=false
end type

type cb_1 from u_rmt_cb_ok within w_rmt_about
int X=937
int Y=1232
int Width=421
int TabOrder=20
boolean BringToTop=true
end type

