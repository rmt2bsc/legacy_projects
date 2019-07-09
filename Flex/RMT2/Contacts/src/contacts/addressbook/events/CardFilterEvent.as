package contacts.addressbook.events {

	import flash.events.Event; 
	
	public class CardFilterEvent extends Event {
		
		public static const LIST_FILTER : String = "cardFilter";
		
		public var selection : String;
		
		
		public function CardFilterEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
		public function getSelection() : String {
			return this.selection;
		}
		
		public function setSelection(value : String) : void {
			this.selection = value;
		}
	}
}
