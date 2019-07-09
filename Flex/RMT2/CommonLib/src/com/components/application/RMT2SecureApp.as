package com.components.application {
	 
	import com.components.security.AuthenticateUserEvent;
	import com.components.security.AuthenticationFailureEvent;
	import com.components.security.AuthenticationSucessEvent;
	import com.components.security.CommonSecurityEvent;
	import com.components.security.LogoutUserEvent;
	import com.components.security.LogoutUserFailureEvent;
	import com.components.security.LogoutUserSuccessEvent;
	import com.components.security.SingleSignOnResultsEvent;
	import com.event.SessionTokenEvent;
	import com.net.RMT2HttpService;
	import com.util.RMT2AppUrlUtility;
	import com.util.RMT2Utility;
	import com.valueobject.SessionTokenVO;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	[Event (name="authenticationFailure", type="com.components.security.AuthenticationFailureEvent")]
	[Event (name="authenticationSuccess", type="com.components.security.AuthenticationSucessEvent")]
	[Event (name="logoutUserSuccessful", type="com.components.security.LogoutUserSuccessEvent")]
	[Event (name="logoutUserFailure", type="com.components.security.LogoutUserFailureEvent")]
	
	
	public class RMT2SecureApp extends RMT2CommonApp	{
		
		private static const CLIENTACTION_LOGIN : String = "login";
		
		private static const CLIENTACTION_LOGOFF : String = "logoff";
		
		private static const CLIENTACTION_VERIFY_AUTH : String = "verifyauthentication";
		
		public static const STATE_STARTUP : String = "StartUp";
		
		public static const STATE_LOGIN : String = "Login";
			
		public static const STATE_LOGGEDOUT : String = "LoggedOut";
		
		private static const LOGIN_XML_MODEL : String = "assets/model/SrvcParms_Login.xml";
		
		private var session : SessionTokenVO;
		
		private var loginId : String;
		
		private var sessionId : String;
		
		private var fname : String;
		
		private var lname : String;
		
		private var _loginModel : Object;
		
		private var _parentApp : UIComponent;

		private var _stateLogin : String;
		
		private var _stateLoggedOut : String;
		
		private var _stateStartUp : String;

		


		public function RMT2SecureApp() {
			super();
			this._stateLoggedOut = RMT2SecureApp.STATE_LOGGEDOUT;
			this._stateLogin = RMT2SecureApp.STATE_LOGIN;
			this._stateStartUp = RMT2SecureApp.STATE_STARTUP;
			Application.application.addEventListener(AuthenticateUserEvent.EVENT_NAME, loginListener, true);
			Application.application.addEventListener(LogoutUserEvent.EVENT_NAME, logoutListener, true);
			Application.application.addEventListener(SessionTokenEvent.EVENT_NAME, getSessionTokenListener, true);
			Application.application.addEventListener(SingleSignOnCheckEvent.EVENT_NAME, verifySingleSignOnListener);
		}
		
		
		private function verifySingleSignOnListener(e : SingleSignOnCheckEvent) : void {
			// get user's session via call that will verify the user is in deed logged in to at least one system.
			this.verifySingleSignOn(e.userId, e.appCode);
			return;
		}
		
		
		protected function verifySingleSignOn(loginId : String, appCode : String) : void {
			RMT2Utility.clearProperties(this.loginModel);
			this.loginModel.clientAction = RMT2SecureApp.CLIENTACTION_VERIFY_AUTH;
			this.loginModel.UID = loginId;
			
			this.loginModel.appcode = appCode;
			var url : RMT2AppUrlUtility = new RMT2AppUrlUtility();
			var fullUrl : String = url.buildUrl(this.r, appCode, "securityUrl");
			var contextUrl : String = url.contextUrl;
			var rootUrl : String = url.rootUrl;
			
			var http : RMT2HttpService = new RMT2HttpService(rootUrl);
			var rc : String = http.setupURL(contextUrl, rootUrl, bindVerifySingleSignOnListener, bindSingleSignOnFaultListener);
			http.send(this.loginModel);	
		}
		
		private function bindVerifySingleSignOnListener(e : ResultEvent) : void {
			var reply : XMLList = e.result.reply_status;
			var header : XMLList = e.result.header;
			var evt : CommonSecurityEvent = null;
			
			this.session = null;
			var evtResults : SingleSignOnResultsEvent  = new SingleSignOnResultsEvent(SingleSignOnResultsEvent.EVENT_NAME, true);
			if (reply != null) {
				if (reply.return_status == "SUCCESS") {
					var payload : XMLList = e.result.user_auth.session_token;
					this.session = this.createSessionToken(payload);
					evtResults.loginRequired = false;
				}
				else {
					evtResults.loginRequired = true;
				}
			} 
			// Notify listeners of single sign on results
			evtResults.session = this.session;
			this.dispatchEvent(evtResults);
			
			// Notify listeners of authentiction sucess via single sign on operation.
			if (!evtResults.loginRequired) {
				var evtAuthSucess : AuthenticationSucessEvent = new AuthenticationSucessEvent(AuthenticationSucessEvent.EVENT_NAME, true);
				evtAuthSucess.session = this.session;
				this.dispatchEvent(evtAuthSucess);	
			}
			
		}
		
		private function bindSingleSignOnFaultListener(e : FaultEvent) : void {
			var title : String = "Server Communication Error";
			var msg : String = "An error occurred attempting to contact server for the purpose of verifying user single-sign on session.\n\n";
			msg += "\nFalut Code: " + e.fault.faultCode;
			msg += "\nMessage: " + e.fault.faultString;
			msg += "\nError Type: " +   e.fault.rootCause.type;
			msg += "\nRoot Cause: " +   e.fault.rootCause.text;
			msg += "\nOther Causes: Server could be down!";
			Alert.show(msg, title);
		}
		
		private function loginListener(e : AuthenticateUserEvent) : void {
			RMT2Utility.clearProperties(this.loginModel);
			this.loginModel.clientAction = RMT2SecureApp.CLIENTACTION_LOGIN;
			this.loginModel.UID = e.userId;
			this.loginModel.PW = e.password;
			this.loginModel.appcode = e.appCode;
			
			var url : RMT2AppUrlUtility = new RMT2AppUrlUtility();
			var fullUrl : String = url.buildUrl(this.r, e.appCode, "securityUrl");
			var contextUrl : String = url.contextUrl;
			var rootUrl : String = url.rootUrl;
			
			var http : RMT2HttpService = new RMT2HttpService(rootUrl);
			http.setupURL(contextUrl,  rootUrl, bindLoginResultsListener, bindHttpServiceFaultListener);
			http.send(this.loginModel);									
		}
		
		private function bindLoginResultsListener(e : ResultEvent) : void {
			var reply : XMLList = e.result.reply_status;
			var header : XMLList = e.result.header;
			var evt : CommonSecurityEvent = null;
			
			if (reply != null) {
				if (reply.return_status == "SUCCESS") {
					var payload : XMLList = e.result.session_token;
					this.session = this.createSessionToken(payload);
					
					// Notify any interested parties of user authentication success
					var en : String = AuthenticationSucessEvent.EVENT_NAME;
					var evt1 : AuthenticationSucessEvent = new AuthenticationSucessEvent(AuthenticationSucessEvent.EVENT_NAME, true);
					evt1.userId = header.user_id;
					evt1.message = reply.ext_message;
					evt1.session = this.session;
					this.dispatchEvent(evt1);
				}
				else {
					// Notify any interested parties of user authentication failure
					evt = new AuthenticationFailureEvent(AuthenticationFailureEvent.EVENT_NAME, true);
					evt.userId = header.user_id;
					evt.message = reply.ext_message;
					this.dispatchEvent(evt);
				}
			} 
		}
			
			
		private function logoutListener(e : LogoutUserEvent) : void {
			RMT2Utility.clearProperties(this.loginModel);
			this.loginModel.clientAction = RMT2SecureApp.CLIENTACTION_LOGOFF;
			this.loginModel.UID = e.userId;
			this.loginModel.appcode = e.appCode;
			this.loginModel.sessionid = e.sessionId;
			
			var url : RMT2AppUrlUtility = new RMT2AppUrlUtility();
			var fullUrl : String = url.buildUrl(this.r, e.appCode, "securityUrl");
			var contextUrl : String = url.contextUrl;
			var rootUrl : String = url.rootUrl;
			
			var http : RMT2HttpService = new RMT2HttpService(rootUrl);
			http.setupURL(contextUrl,  rootUrl, bindLogoutResultsListener, bindHttpServiceFaultListener);
			http.send(this.loginModel);		
		}

		
		private function bindLogoutResultsListener(e : ResultEvent) : void {
			var reply : XMLList = e.result.reply_status;
			var header : XMLList = e.result.header;
			var evt : CommonSecurityEvent = null;
			
			if (reply != null) {
				if (reply.return_status == "SUCCESS") {
					evt = new LogoutUserSuccessEvent(LogoutUserSuccessEvent.EVENT_NAME, true);
					evt.userId = header.userId;
					this.session = null;
					this.dispatchEvent(evt);		
				}
				else {
					evt = new AuthenticationFailureEvent(AuthenticationFailureEvent.EVENT_NAME, true);
					evt.userId = header.user_id;
					evt.message = reply.ext_message;
					this.dispatchEvent(evt);
				}
			} 
		}	
		
			
		private function bindHttpServiceFaultListener(e : FaultEvent) : void {
			trace(e.fault.faultCode);
			trace(e.fault.faultString);
			trace(e.fault.rootCause.text);
//			this.srvc.close();
		}
		
		
		private function createSessionToken(data : Object) : SessionTokenVO {
			var session : SessionTokenVO = new SessionTokenVO(data.login_id, data.session_id);
			session.firstName = data.fname;
			session.lastName = data.lname;
			session.hostProtocol = data.scheme;
			session.hostName = data.server_name;
			session.hostPort = parseInt(data.server_port);
			session.appContext = data.servlet_context;
			session.appName = data.orig_app_id;
			
			var roleNames : ArrayCollection = new ArrayCollection;
			var roles : XMLList = data.roles;
			for (var ndx : int = 0; ndx < roles.length(); ndx++) {
				var role : String = roles[ndx].app_role_code;
				roleNames.addItem(role);
			}
			session.roles = roleNames;
			return session;
		}
		
		
		public function getAppAuthenticationState() : String {
			var uiState : String = null;
			var authenticate : Boolean = this.r.getBoolean(CONFIG_COMMON, "authenticate");
			if (authenticate && this.session == null) {
				uiState = RMT2SecureApp.STATE_LOGIN;
			}
			else {
				uiState = "";
			}
			return uiState;
		}
		
			
		private function getSessionTokenListener(e : SessionTokenEvent) : void {
			e.setToken(this.session);
		}
		
		
		public function get loginModel() : Object {
			return this._loginModel;
		}
		
		public function set loginModel(model : Object) : void {
			this._loginModel = model;
		}
		
		public function get parentApp() : UIComponent {
			return this._parentApp;
		}
		
		public function set parentApp(app : UIComponent) : void {
			this._parentApp = app;
		}
		
		public function get stateLogin() : String {
			return this._stateLogin;
		}
		
		public function get stateLoggedOut() : String {
			return this._stateLoggedOut;
		}
		
		public function get stateStartUp() : String {
			return this._stateStartUp
		}
		
		
	}
}