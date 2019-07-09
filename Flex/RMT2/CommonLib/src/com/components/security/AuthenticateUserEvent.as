package com.components.security {
	
	import flash.events.Event;

	public class AuthenticateUserEvent extends CommonSecurityEvent {
		
		public static const EVENT_NAME : String = "authenticateUser";
		
		private var _password : String;
		
		
		public function AuthenticateUserEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		public function get password() : String {
			return this._password;
		}		
		
		public function set password(pw : String) : void {
			this._password = pw;
		}
	}
}