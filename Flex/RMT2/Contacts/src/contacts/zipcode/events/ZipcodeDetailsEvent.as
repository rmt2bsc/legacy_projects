package contacts.zipcode.events {
	
	import flash.events.Event;

	public class ZipcodeDetailsEvent extends Event 	{
		
		public static const EVENT_NAME : String = "zipcodeDetailsEvent";
		
		private var _zipcode : Object;
		
		public function ZipcodeDetailsEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		
		public function get zipcode() : Object {
			return this._zipcode;
		} 
		
		public function set zipcode(value : Object) : void {
			this._zipcode = value;
		}
	}
}