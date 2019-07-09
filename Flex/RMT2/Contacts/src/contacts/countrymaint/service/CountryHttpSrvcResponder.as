package contacts.countrymaint.service {
	
	import com.components.service.data.RMT2AbstractResponder;
	import com.components.service.data.RMT2ResponderResultsEvent;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.AsyncToken;



	/**
	 * 
	 * @author appdev
	 * 
	 */	
	public class CountryHttpSrvcResponder extends RMT2AbstractResponder 	{
		
		public static const CLIENT_ACTION_LIST : String = "list";
		
		public static const CLIENT_ACTION_SAVE : String = "save";
    		
    	public static const CLIENT_ACTION_DELETE : String = "delete";
    	
		

		/**
		 * Default constructor
		 * 
		 */				
		public function CountryHttpSrvcResponder() 	{
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
			
			if (evt.error && evt.fault != null) {
				return evt;
			}
			
			switch (token[RMT2AbstractResponder.PROP_NAME_SERVICE_ID]) {
				case CLIENT_ACTION_LIST:
					var xmlList : XMLList = data.country;
					var xmlColl : XMLListCollection = new XMLListCollection(xmlList);
					evt.results = xmlColl;
					break;
					
				case CLIENT_ACTION_SAVE:
				case CLIENT_ACTION_DELETE:
					// Capture the server's reply status
				    var replyXml : XML = null;
					if (data.hasOwnProperty("reply_status")) {
						replyXml = data.reply_status[0];	
					}
					evt.results = replyXml;
					break;
			}
			
			return evt;
		}
		
	}
}