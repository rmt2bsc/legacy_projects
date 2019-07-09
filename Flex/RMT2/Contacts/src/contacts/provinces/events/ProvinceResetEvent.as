package contacts.provinces.events {
	
	import flash.events.Event;

	public class ProvinceResetEvent extends Event 	{
		
		public static const EVENT_NAME : String = "provinceResetEvent";
		
		
		public function ProvinceResetEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		

	}
}