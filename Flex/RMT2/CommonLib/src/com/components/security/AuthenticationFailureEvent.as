package com.components.security {
	
	import flash.events.Event;

	public class AuthenticationFailureEvent extends CommonSecurityEvent {
		
		public static const EVENT_NAME : String = "authenticationFailure";
		
		
		
		public function AuthenticationFailureEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}
	}
}