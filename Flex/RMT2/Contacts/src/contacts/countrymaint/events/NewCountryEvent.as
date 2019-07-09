package contacts.countrymaint.events {
	
	import contacts.countrymaint.valueobjects.Country;
	
	import flash.events.Event;

	public class NewCountryEvent extends Event {
		
		public static const EVENT_NAME : String = "newCountryDetails";
		
		private var _model : Country;
		
		public function NewCountryEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		public function get model() : Country {
			return this._model;
		}		
		
		public function set model(data : Country) : void {
			this._model = data;
		}
	}
}