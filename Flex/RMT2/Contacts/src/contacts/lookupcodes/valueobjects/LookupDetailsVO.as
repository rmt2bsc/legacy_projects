package contacts.lookupcodes.valueobjects {
	
	public class LookupDetailsVO {
		
//		public const TYPE_GRP : int = 1;
//		public const TYPE_CODE : int = 2;
		
		private var _type : int;
		private var _grpId : int;
		private var _grpName : String;
		private var _codeId : int;
		private var _codeName : String;
		private var _codeAbbrvName : String;
		
		public function LookupDetailsVO() {
			
		}

		public function get type() : int {
			return this._type;
		}
		public function set type(type : int) : void {
			this._type = type;
		}
		
		public function get grpId() : int {
			return this._grpId;
		}
		public function set grpId(grpId : int) : void {
			this._grpId = grpId;
		}
		public function get grpName() : String {
			return this._grpName;
		}
		public function set grpName(grpName : String) : void {
			this._grpName = grpName;
		}
		public function get codeId() : int {
			return this._codeId;
		}
		public function set codeId(codeId : int) : void {
			this._codeId = codeId;
		}
		public function get codeName() : String {
			return this._codeName;
		}
		public function set codeName(codeName : String) : void {
			this._codeName = codeName;
		}
		public function get codeAbbrvName() : String {
			return this._codeAbbrvName;
		}
		public function set codeAbbrvName(codeAbbrvName : String) : void {
			this._codeAbbrvName = codeAbbrvName;
		}
	}
}