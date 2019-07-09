package contacts.zipcode.events {
	
	import flash.events.Event;

	public class ZipcodeQueryRequestEvent extends Event 	{
		
		public static const EVENT_NAME : String = "zipcodeRequestEvent";
		
		private var _request : Object;
		
		public function ZipcodeQueryRequestEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		
		public function get request() : Object {
			return this._request;
		} 
		
		public function set request(value : Object) : void {
			this._request = value;
		}
	}
}