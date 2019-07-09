package contacts.zipcode.events {
	
	import flash.events.Event;

	public class ZipcodeResetEvent extends Event 	{
		
		public static const EVENT_NAME : String = "zipcodeResetEvent";
		
		
		public function ZipcodeResetEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		

	}
}