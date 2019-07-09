package contacts.addressbook.events {

	import contacts.addressbook.valueobjects.ZipcodeVO;
	
	import flash.events.Event;
	import flash.events.EventDispatcher; 
	
	public class ZipChangeEvent extends Event {
		
		public static const ZIP_CHANGE : String = "zipChange";
		
		private var data : ZipcodeVO;
		
		public var zipCode : String;
		
		public var callbackObj : EventDispatcher;
		
		
		public function ZipChangeEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
		public function getData() : ZipcodeVO {
			return this.data;
		}
		
		public function setData(value : ZipcodeVO) : void {
			this.data = value;
		}
	}
}
