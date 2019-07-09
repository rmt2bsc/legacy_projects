package contacts.lookupcodes.events {

	import contacts.lookupcodes.valueobjects.LookupDetailsVO; 
	
	public class LookupUpdateEvent extends CommonCodeDetailsEvent {
		
		public static const EVENT_NAME : String = "updateLookupDetails";
		
		private var _mode : int;
		
		private var _formData : Object;
		
		private var _returnData : LookupDetailsVO;
		
		public function LookupUpdateEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false, codeType : int = 1) {
			super(type, bubbles, cancelable);
		}
		
		public function get formData() : Object {
			return this._formData;
		}
		
		public function set formData(value : Object) : void {
			this._formData = value;
		}
		
		public function get returnData() : LookupDetailsVO {
			return this._returnData;
		}
		
		public function set returnData(value : LookupDetailsVO) : void {
			this._returnData = value;
		}
	}
}
