package contacts.provinces.events {
	
	import flash.events.Event;

	public class ProvinceQueryRequestEvent extends Event 	{
		
		public static const EVENT_NAME : String = "provinceRequestEvent";
		
		private var _request : Object;
		
		public function ProvinceQueryRequestEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 	{
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