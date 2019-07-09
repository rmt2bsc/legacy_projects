package contacts.lookupcodes.events {

	import contacts.lookupcodes.valueobjects.LookupDetailsVO;
	
	import flash.events.Event; 
	
	public class DisplayCodeDetailsEvent extends CommonCodeDetailsEvent {
		
		public static const EVENT_NAME : String = "displayLookupDetails";
		
		
		public function DisplayCodeDetailsEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false, codeType : int = 1) {
			super(type, bubbles, cancelable);
			this.codeType = codeType;
		}
	}
}
