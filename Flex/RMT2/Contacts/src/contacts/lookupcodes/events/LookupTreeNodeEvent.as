package contacts.lookupcodes.events {

	import contacts.lookupcodes.valueobjects.LookupDetailsVO;
	
	import flash.events.Event; 
	
	public class LookupTreeNodeEvent extends CommonCodeDetailsEvent {
		
		public static const EVENT_NAME : String = "lookupTreeNode";
		
		
		public function LookupTreeNodeEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false, codeType : int = 1) {
			super(type, bubbles, cancelable);
		}
	}
}
