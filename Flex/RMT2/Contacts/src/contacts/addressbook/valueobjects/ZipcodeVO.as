package contacts.addressbook.valueobjects {
	
	public class ZipcodeVO {
		private var zipId : int;
		
		private var zipcode : String;
		
		private var city : String;
		
		private var state : String;
		
		private var areaCode : String;
		
		private var cityAliasName : String;
		
		private var cityAlisaAbbrv : String;
		
		private var countyName : String;
		
		
		public function ZipcodeVO(id : int, zip : String, city : String, state : String, areaCode : String) {
			this.zipId = id;
			this.zipcode = zip;
			this.city = city;
			this.state = state;
			this.areaCode = areaCode;
		}

		public function getZipId() : int {
			return this.zipId;
		}
		
		public function getZipcode() : String {
			return this.zipcode;
		}
		
		public function getCity() : String {
			return this.city;
		}
		
		public function getState() : String {
			return this.state;
		}
		
		public function getAreaCode() : String {
			return this.areaCode;
		}
	}
}