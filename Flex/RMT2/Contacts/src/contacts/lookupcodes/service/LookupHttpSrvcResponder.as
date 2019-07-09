package contacts.lookupcodes.service {
	
	import com.components.service.data.RMT2AbstractResponder;
	import com.components.service.data.RMT2ResponderResultsEvent;
	
	import contacts.lookupcodes.valueobjects.LookupDetailsVO;
	import contacts.lookupcodes.valueobjects.LookupUpdateResultsVO;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.AsyncToken;

	public class LookupHttpSrvcResponder extends RMT2AbstractResponder 	{
		
		public static const CLIENT_ACTION_CODELOOKUP : String = "lookup";
		
		public static const CLIENT_ACTION_SAVE : String = "save";
    		
    	public static const CLIENT_ACTION_DELETE : String = "delete";
		

		/**
		 * 
		 * 
		 */				
		public function LookupHttpSrvcResponder() 	{
			super();
		}
		
		
		
		/**
		 * Creates an event instance of type RMT2ResponderResultsEvent, assigns the service id to the event 
		 * instance,  and returns the event instance to the caller.  The implementor should override this class 
		 * with specific logic for retuning meaningful data to the caller via an instance of RMT2ResponderResultsEvent. 
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
				case CLIENT_ACTION_CODELOOKUP:
					var xmlList : XMLList = data.group;
					
					// Remove unwanted nodes in the tree in order to make life simpler for the tree control.
					var parent : XML = xmlList[0].parent();
					delete parent.header;
					delete parent.reply_status;
					
					// convert XMLList to XMLListCollection
					evt.results = new XMLListCollection(xmlList);
					break;
					
				case CLIENT_ACTION_SAVE:
				case CLIENT_ACTION_DELETE:
					// Capture the server's reply status
				    var replyXml : XML;
					if (data.hasOwnProperty("reply_status")) {
						replyXml = data.reply_status[0];	
					}
					
					// capture the lookup group and lookup code data that was just updated.
					var lookupXml : XMLList;
					if (data.hasOwnProperty("item")) {
						lookupXml = data.item;	
					}
					var vo : LookupDetailsVO = new LookupDetailsVO();
					vo.grpId = int(lookupXml[0].@group_id);
					vo.grpName = lookupXml[0].@label;
					var codeXml : XMLList = lookupXml[0].code;
					if (codeXml.length() > 0) {
						vo.codeId = int(codeXml[0].code_id);
						vo.codeAbbrvName = codeXml[0].shortdesc;
						vo.codeName = codeXml[0].longdesc;
					}
					
					var retData : LookupUpdateResultsVO = new LookupUpdateResultsVO(vo, replyXml);
					evt.results = retData;
					break;
					
				case CLIENT_ACTION_DELETE:
					break;
			}
			
			return evt;
		}
		
	}
}