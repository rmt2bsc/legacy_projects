package com.components.security {
	
	import flash.events.Event;

	public class LogoutUserFailureEvent extends CommonSecurityEvent {
		
		public static const EVENT_NAME : String = "logoutUserFailure";
		
		public function LogoutUserFailureEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}
	}
}