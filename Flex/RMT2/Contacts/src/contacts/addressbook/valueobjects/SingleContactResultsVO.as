package contacts.addressbook.valueobjects {

	public class SingleContactResultsVO 	{
		
		private var _contact : XML;
		
		private var _address : XML;
		
		private var _contactType : String;
		
		
		public function SingleContactResultsVO(contact : XML, address : XML, contactType : String = null) 	{
			this._address = address;
			this._contact = contact;
			this._contactType = contactType;
		}
		
		
		public function get contact() : XML {
			return this._contact;
		}
		
		public function set contact(xml : XML) : void {
			this._contact = xml;
		}
		
		public function get address() : XML {
			return this._address;
		}
		
		public function set address(xml : XML) : void {
			this._address = xml;
		}
		
		public function get contactType() : String {
			return this._contactType;
		}
		
		public function set contactType(value : String) : void {
			this._contactType = value;
		}

	}
}