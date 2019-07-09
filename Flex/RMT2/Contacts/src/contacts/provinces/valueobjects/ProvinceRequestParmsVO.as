package contacts.provinces.valueobjects {
	
	public class ProvinceRequestParmsVO {
		
		private var clientAction : String;
		
		private var qry_StateCode : String;
		
		private var qry_StateName : String;
		
		private var qry_CountryId : String;
		
		
		public function ProvinceRequestParmsVO()	{
			return;
		}
		
		
		public function getClientAction() : String {
			return this.clientAction;
		}
		
		
		public function setClientAction(value : String) : void {
			this.clientAction = value;
		}
		
		public function getStateCode() : String {
			return this.qry_StateCode;
		}
		
		public function setStateCode(value : String) : void {
			this.qry_StateCode = value;
		}
		
		public function getStateName() : String {
			return this.qry_StateName;
		}
		
		public function setStateName(value : String) : void {
			this.qry_StateName = value;
		}
		
		public function getCountryId() : String {
			return this.qry_CountryId;
		}
		
		public function setCountryId(value : String) : void {
			this.qry_CountryId = value;
		}

	}
}