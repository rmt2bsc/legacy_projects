package contacts.lookupcodes.valueobjects {
	
	/**
	 * 
	 * @author rterrell
	 * 
	 */	
	public class LookupUpdateResultsVO {
		
		/**
		 * 
		 */		
		private var _rec : LookupDetailsVO;
		
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
		public function LookupUpdateResultsVO(rec : LookupDetailsVO = null, status : XML = null)	{
			this._rec = rec;
			this._status = status;
		}

		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get lookupRec() : LookupDetailsVO {
			return this._rec;
		}
		
		/**
		 * 
		 * @param rec
		 * 
		 */		
		public function set lookupRec(rec : LookupDetailsVO) : void {
			this._rec = rec;
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