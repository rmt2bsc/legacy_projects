package contacts.addressbook.events {

	import flash.events.Event; 
	
	public class DisplayContactDetailsEvent extends Event {
		
		public static const EVENT_NAME : String = "displayContactDetails";
		
		public static const NULL_CONTACT : int = -1;
		
		public static const NEW_CONTACT : int = 0;
		
		private var _contactId : int;
		
		private var _contactType : String;
		
		private var _nullContact : Boolean;
		
		
		public function DisplayContactDetailsEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
			super(type, bubbles, cancelable);
			this._nullContact = false;
		}
		
		public function get contactId() : int {
			return this._contactId;
		}
		
		public function set contactId(value : int) : void {
			this._contactId = value;
		}
		
		public function get contactType() : String {
			return this._contactType;
		}
		
		public function set contactType(value : String) : void {
			this._contactType = value;
		}
		
		public function get nullContact() : Boolean {
			return this._nullContact;
		}
		
		public function set nullContact(value : Boolean) : void {
			this._nullContact = value;
		}
	}
}
