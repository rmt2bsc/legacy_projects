package contacts.countrymaint.valueobjects {
	public class Country {
		
		public function Country() {
			return;
		}
		
		private var _countryId : int;
		private var _countryName : String;
		private var _code : String;
		private var _voidInd : String;
		
		
		public function get countryId() : int {
			return this._countryId;
		}
		
		public function set countryId(value : int) : void {
			this._countryId = value;
		}
		
		public function get countryName() : String {
			return this._countryName;
		}
		
		public function set countryName(value : String) : void {
			this._countryName = value;
		}

		public function get code() : String {
			return this._code;
		}
		
		public function set code(value : String) : void {
			this._code = value;
		}
		
		public function get voidInd() : String {
			return this._voidInd;
		}
		
		public function set voidInd(value : String) : void {
			this._voidInd = value;
		}
	}
}