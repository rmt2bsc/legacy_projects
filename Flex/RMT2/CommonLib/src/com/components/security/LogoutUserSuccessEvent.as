package com.components.security {
	
	import flash.events.Event;

	public class LogoutUserSuccessEvent extends CommonSecurityEvent {
		
		public static const EVENT_NAME : String = "logoutUserSuccessful";
		
		
		public function LogoutUserSuccessEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}
	}
}