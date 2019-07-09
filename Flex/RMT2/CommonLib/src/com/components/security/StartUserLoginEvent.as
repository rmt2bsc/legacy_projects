package com.components.security {
	
	import com.event.RMT2Event;

	public class StartUserLoginEvent extends RMT2Event {
		
		public static const EVENT_NAME : String = "startUserLogin";
		
		
		public function StartUserLoginEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}
	}
}