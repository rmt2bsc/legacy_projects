package contacts.lookupcodes.events {

	import contacts.lookupcodes.valueobjects.LookupDetailsVO;
	
	import flash.events.Event; 
	
	public class CommonCodeDetailsEvent extends Event {
		
		public static const TYPE_GRP : int = 1;
		
		public static const TYPE_CODE : int = 2;
		
		public static const MODE_ADD : int = 1;
				
		public static const MODE_UPDATE : int = 2;
		
		public static const MODE_DELETE : int = 3;
		
		private var _codeDetails : LookupDetailsVO;
		
		private var _codeType : int;
		
		private var _mode : int;
		
		
		public function CommonCodeDetailsEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false, codeType : int = 0) {
			super(type, bubbles, cancelable);
			this.codeType = codeType;
		}
		
		public function get codeType() : int {
			return this._codeType;
		}
		
		public function set codeType(codeType : int) : void {
			this._codeType = codeType;
		}

		public function get codeDetails() : LookupDetailsVO {
			return this._codeDetails;
		}
		
		public function set codeDetails(value : LookupDetailsVO) : void {
			this._codeDetails = value;
		}
		
		public function get mode() : int {
			return this._mode;
		}
		
		public function set mode(mode : int) : void {
			this._mode = mode;
		}
	}
}
