package contacts.zipcode.valueobjects  {
	
	import com.components.pagination.PaginationData;
	
	import mx.collections.XMLListCollection;
	
	
	/**
	 * 
	 * @author rterrell
	 * 
	 */	
	public class ZipcodeServiceResultsVO {
		
		private var _pageMetrics : PaginationData;
		
		/**
		 * 
		 */		
		private var _zipList : XMLListCollection;
		
		/**
		 * 
		 */		
		private var _zipDetails : XML;
		
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
		public function ZipcodeServiceResultsVO( status : XML = null)	{
			this._status = status;
		}


		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get pageMetrics() : PaginationData {
			return this._pageMetrics;
		}
		
		/**
		 * 
		 * @param metrics
		 * 
		 */		
		public function set pageMetrics(metrics : PaginationData) : void {
			this._pageMetrics = metrics;
		}

		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get zipList() : XMLListCollection {
			return this._zipList;
		}
		
		/**
		 * 
		 * @param rec
		 * 
		 */		
		public function set zipList(list : XMLListCollection) : void {
			this._zipList = list;
		}
		
	
		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get zipDetails() : XML {
			return this._zipDetails;
		}
		
		
		/**
		 * 
		 * @param zip
		 * 
		 */		
		public function set zipDetails(zip : XML) : void {
			this._zipDetails = zip;
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