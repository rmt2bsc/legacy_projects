package contacts.provinces.events {
	
	import flash.events.Event;

	public class ProvinceUpdateEvent extends Event 	{
		
		public static const EVENT_NAME : String = "provinceUpdateEvent";
		
		private var _province : Object;
		
		public function ProvinceUpdateEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		
		public function get province() : Object {
			return this._province;
		} 
		
		public function set province(value : Object) : void {
			this._province = value;
		}
	}
}