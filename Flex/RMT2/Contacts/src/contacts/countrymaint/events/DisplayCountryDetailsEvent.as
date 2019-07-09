package contacts.countrymaint.events {
	
	import com.event.RMT2Event;
	
	import contacts.countrymaint.valueobjects.Country;

	public class DisplayCountryDetailsEvent extends RMT2Event  {
		
		public static const EVENT_NAME : String = "displayCountryDetails";
		
		private var _model : Country;
		
		
		
		public function DisplayCountryDetailsEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
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