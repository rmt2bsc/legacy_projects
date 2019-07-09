package contacts.provinces.events {
	
	import flash.events.Event;

	public class ProvinceEditEvent extends Event 	{
		
		public static const EVENT_NAME : String = "provinceEditEvent";
		
		private var _province : Object;
		
		public function ProvinceEditEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 	{
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