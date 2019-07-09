package com.event {
	
	public class FetchFirstPageEvent extends RMT2Event {
		
		public static const EVENT_NAME : String = "fetchFirstPageEvent";
		
		public function FetchFirstPageEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)  {
			//TODO: implement function
			super(type, bubbles, cancelable);
		}
		
	}
}