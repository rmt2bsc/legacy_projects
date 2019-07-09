package contacts.zipcode.events {
	
	import flash.events.Event;

	public class ZipcodeUpdateDetailsEvent extends Event 	{
		
		public static const EVENT_NAME : String = "updateZipcodeDetailsEvent";
		
		private var _zipcode : Object;
		
		public function ZipcodeUpdateDetailsEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) 	{
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