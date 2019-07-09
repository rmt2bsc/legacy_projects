package contacts.addressbook.events {

	import flash.events.Event; 
	
	public class UpdateContactEvent extends Event {
		
		public static const EVENT_NAME : String = "updateContactDetails";
		
		public static const DML_MODIFY : int = 1;
		
		public static const DML_DELETE : int = -1;
		
		private var _mode : int;
		
		
		public function UpdateContactEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
		public function get mode() : int {
			return this._mode;
		}
		
		public function set mode(value : int) : void {
			this._mode = value;
		}
	}
}
