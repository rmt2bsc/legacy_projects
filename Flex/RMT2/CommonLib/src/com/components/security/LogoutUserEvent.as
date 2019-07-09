package com.components.security {
	
	import flash.events.Event;

	public class LogoutUserEvent extends CommonSecurityEvent {
		
		public static const EVENT_NAME : String = "logoutUser";
		
		private var _appName : String;
		
		private var _sessionId :  String;
		
		
		public function LogoutUserEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		
		public function get appName() : String {
			return this._appName;
		}		
		
		public function set appName(appName : String) : void {
			this._appName = appName;
		}
		
		public function get sessionId() : String {
       		return this._sessionId;
       }
       
       public function set sessionId(value : String) : void {
       		this._sessionId = value;
       }
	}
}