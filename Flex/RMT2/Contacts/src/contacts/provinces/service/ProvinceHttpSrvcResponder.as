package contacts.provinces.service {
	
	import com.components.service.data.RMT2AbstractResponder;
	import com.components.service.data.RMT2ResponderResultsEvent;
	
	import contacts.provinces.valueobjects.ProvinceServiceResultsVO;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.AsyncToken;



	/**
	 * 
	 * @author appdev
	 * 
	 */	
	public class ProvinceHttpSrvcResponder extends RMT2AbstractResponder 	{
		
    	public static const CLIENT_ACTION_SEARCH : String = "search";
			
//		public static const CLIENT_ACTION_ADD : String = "add";
//		
//		public static const CLIENT_ACTION_EDIT : String = "edit";
		
		public static const CLIENT_ACTION_COUNTRY_LIST : String = "list";
		
		public static const CLIENT_ACTION_SAVE : String = "save";
			
		public static const CLIENT_ACTION_DELETE : String = "delete";
    	
		

		/**
		 * Default constructor
		 * 
		 */				
		public function ProvinceHttpSrvcResponder() 	{
			super();
		}
		
		
		
		/**
		 * Event handler for Country Maintenance Http Service list, save, and delete requests.   Creates an event instance 
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
			if (evt.error && evt.fault != null) {
				return evt;
			}
			
			switch (token[RMT2AbstractResponder.PROP_NAME_SERVICE_ID]) {
				case CLIENT_ACTION_COUNTRY_LIST:
					evt.results =data.country;
					break;
					
				case CLIENT_ACTION_SEARCH:
				    var vo : ProvinceServiceResultsVO = new ProvinceServiceResultsVO();
					if (data.hasOwnProperty("reply_status")) {
						replyStatus = data.reply_status[0];
						vo.status = replyStatus;
					}
					if (data.hasOwnProperty("state")) {
						vo.provinceList =  new XMLListCollection(data.state);
					}
					evt.results = vo;
					break;
					
				case CLIENT_ACTION_SAVE:
				case CLIENT_ACTION_DELETE:
					if (data.hasOwnProperty("reply_status")) {
						replyStatus = data.reply_status[0];
					}
					evt.results = replyStatus;
					break;					
			}
			
			return evt;
		}
		
	}
}