package contacts.addressbook.events {

	import contacts.addressbook.valueobjects.ZipcodeVO;
	
	import flash.events.Event; 
	
	public class ZipTargetUpdateEvent extends Event {
		
		public static const EVENT_NAME : String = "updateTargetZipChange";
		
		private var data : ZipcodeVO;
		
		public var zipCode : String;
		
		
		public function ZipTargetUpdateEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
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
