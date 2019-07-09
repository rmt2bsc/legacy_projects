package contacts.zipcode.service {
	
	import com.components.pagination.PaginationData;
	import com.components.service.data.RMT2AbstractResponder;
	import com.components.service.data.RMT2ResponderResultsEvent;
	
	import contacts.zipcode.valueobjects.ZipcodeServiceResultsVO;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.AsyncToken;



	/**
	 * 
	 * @author appdev
	 * 
	 */	
	public class ZipcodeHttpSrvcResponder extends RMT2AbstractResponder 	{
		
		public static const CLIENT_ACTION_SEARCH : String = "search";
			
		public static const CLIENT_ACTION_TIMEZONE_LIST : String = "fetchtimezones";
		
		public static const CLIENT_ACTION_VIEW : String = "view";
		
    	
		

		/**
		 * Default constructor
		 * 
		 */				
		public function ZipcodeHttpSrvcResponder() 	{
			super();
		}
		
		
		
		/**
		 * Event handler for zip code Maintenance Http Service list, view, and fetchtimezones requests.   Creates an event instance 
		 * of type RMT2ResponderResultsEvent, assigns the service id to the event instance,  and returns the event instance 
		 * to the caller.  The implementor should override this class with specific logic for retuning meaningful data to the caller 
		 * via an instance of RMT2ResponderResultsEvent. 
		 *  
		 * @param data
		 *          The actual payload to be processed.
		 * @param token
		 *          An instance of AsyncToken
		 * @return
		 *          An instance of RMT2ResponderResultsEvent which will indicate if the call is returned as a success or a failure.
		 * 
		 */		
		protected override function processResponse(data : Object, token : AsyncToken) : RMT2ResponderResultsEvent {
			var evt : RMT2ResponderResultsEvent = super.processResponse(data, token);
			var replyStatus : XML = null;
			var vo : ZipcodeServiceResultsVO = new ZipcodeServiceResultsVO();
			if (evt.error && evt.fault != null) {
				return evt;
			}
			
			switch (token[RMT2AbstractResponder.PROP_NAME_SERVICE_ID]) {
				case CLIENT_ACTION_TIMEZONE_LIST:
					if (data.hasOwnProperty("timezone_data")) {
						var tzXML : XMLList = data.timezone_data;
					}
					evt.results = tzXML;
					break;
					
				case CLIENT_ACTION_SEARCH:
					if (data.hasOwnProperty("reply_status")) {
						replyStatus = data.reply_status[0];
						vo.status = replyStatus;
					}
					if (data.hasOwnProperty("zip_short")) {
						var list : XMLListCollection =  new XMLListCollection(data.zip_short);
						vo.zipList = list;
					}
					if (data.hasOwnProperty("pagination_info")) {
						var pageInfo : XMLList = data.pagination_info;
						var pageMetrics : PaginationData = new PaginationData();
						pageMetrics.curPage = pageInfo.page_no;
						pageMetrics.maxPagesPerGroup = pageInfo.page_max_links;
						pageMetrics.totalPages = pageInfo.page_count;
						pageMetrics.totalRows = pageInfo.query_row_count;
						vo.pageMetrics = pageMetrics;
					}
					
					evt.results = vo;
					break;
					
				case CLIENT_ACTION_VIEW:
					if (data.hasOwnProperty("reply_status")) {
						replyStatus = data.reply_status[0];
						vo.status = replyStatus;
					}
					
					if (data.hasOwnProperty("zip_full")) {
						var zipDetails : XML = data.zip_full[0];
						vo.zipDetails = zipDetails;
					}
					evt.results = vo;
					break;					
			}
			
			return evt;
		}
		
	}
}