package contacts.countrymaint.events {
	
	import contacts.countrymaint.valueobjects.Country;
	
	import flash.events.Event;

	public class CountrySelectedEvent extends Event {
		
		public static const EVENT_NAME : String = "countrySelectedFromList";
		
		private var _id : int;
		
		private var _name : String;
		
		private var _code : String;
		
		
		public function CountrySelectedEvent(countryId : int, countryName : String, countryCode : String, type : String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
			this._id = countryId;
			this._name = countryName;
			this._code = countryCode;
		}

		public function get id() : int {
			return this._id;
		}		
		
		public function get name() : String {
			return this._name;
		}
		
		public function get code() : String {
			return this._code;
		}
	}
}