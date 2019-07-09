package contacts.provinces.valueobjects {
	
	import mx.collections.XMLListCollection;
	
	
	/**
	 * 
	 * @author rterrell
	 * 
	 */	
	public class ProvinceServiceResultsVO {
		
		/**
		 * 
		 */		
		private var _provinceList : XMLListCollection;
		
		/**
		 * 
		 */		
		private var _status : XML;
		
		/**
		 * 
		 * @param rec
		 * @param status
		 * 
		 */		
		public function ProvinceServiceResultsVO( status : XML = null)	{
			this._status = status;
		}

		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get provinceList() : XMLListCollection {
			return this._provinceList;
		}
		
		/**
		 * 
		 * @param rec
		 * 
		 */		
		public function set provinceList(list : XMLListCollection) : void {
			this._provinceList = list;
		}
		
		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get status() : XML {
			return this._status;
		}
		
		/**
		 * 
		 * @param status
		 * 
		 */		
		public function set status(status : XML) : void {
			this._status = status;
		}
	}
}