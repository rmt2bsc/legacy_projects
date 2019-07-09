package com.components.pagination {
	
	import flash.events.Event;

	public class ResetPaginationComponentEvent extends Event 	{
		
		public static const EVENT_NAME : String = "resetPaginationEvent";
		
		
		public function ResetPaginationComponentEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		

	}
}