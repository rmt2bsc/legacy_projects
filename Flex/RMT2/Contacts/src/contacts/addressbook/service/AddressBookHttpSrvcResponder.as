package contacts.addressbook.service {
	
	import com.components.service.data.RMT2AbstractResponder;
	import com.components.service.data.RMT2ResponderResultsEvent;
	
	import contacts.addressbook.valueobjects.SingleContactResultsVO;
	
	import mx.rpc.AsyncToken;

	public class AddressBookHttpSrvcResponder extends RMT2AbstractResponder 	{
		
		public static const CLIENT_ACTION_CODELOOKUP : String = "lookup";
		
		public static const CLIENT_ACTION_SEARCH : String = "doSearch";
		
		public static const CLIENT_ACTION_EDIT : String = "edit";
    	    
	    public static const CLIENT_ACTION_SAVE : String = "save";
	    
	    public static const CLIENT_ACTION_DELETE : String = "delete";
	    
	    public static const CLIENT_ACTION_GETZIP : String = "fetchzip";
	    
	    
	    
	    
		
		public function AddressBookHttpSrvcResponder() 	{
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
					this.processLookupRequest(data, evt);
					break;
					
				case CLIENT_ACTION_SEARCH:
				    this.processSearchRequest(data, evt);
					break;
					
				case CLIENT_ACTION_EDIT:
				    this.processEditRequest(data, evt);
					break;
					
				case CLIENT_ACTION_SAVE:
					this.processSaveRequest(data, evt);
					break;
					
				case CLIENT_ACTION_GETZIP:
					this.processZipRequest(data, evt);
					break;
			}
			
			return evt;
		}
		
		
		private function processLookupRequest(data : Object, evt : RMT2ResponderResultsEvent) : void {
			evt.results = data;
			return;
		}
		
		private function processSearchRequest(data : Object, evt : RMT2ResponderResultsEvent) : void {
			var xmlList : XMLList = data.contact;
			
			// Determine if at least one contact exist in XML
			if (xmlList.length() <= 0) {
				// Assign an empty list so that the card renderer can handle 
				var emptyXml : XML = <contact><empty/><contact_name>No Addresses Found</contact_name></contact>;
				xmlList[0] = emptyXml;
				evt.found = false;
			}
			else {
				evt.found = true;
			}
			evt.results = xmlList;
			return;
		}
		
		private function processEditRequest(data : Object, evt : RMT2ResponderResultsEvent) : void {
			var contactType : String;
			var entity : XML;
			var addr : XML;
			
			if (data.hasOwnProperty("person_list")) {
				entity = new XML(data.person_list[0]);
				addr = new XML(data.person_list[0].address);		
				contactType = "per";
			}
			if (data.hasOwnProperty("business_list")) {
				entity = new XML(data.business_list[0]);	
				addr = new XML(data.business_list[0].address);
				contactType = "bus";
			}
			var vo : SingleContactResultsVO = new SingleContactResultsVO(entity, addr, contactType); 
			evt.results = vo;
			return;
		}
		
	
		private function processSaveRequest(data : Object, evt : RMT2ResponderResultsEvent) : void {
			if (data.hasOwnProperty("reply_status")) {
				var reply : XML = new XML(data.reply_status[0]);
				evt.results = reply;
			}
			return;
		}
		
		
		private function processZipRequest(data : Object, evt : RMT2ResponderResultsEvent) : void {
			if (data.hasOwnProperty("zip_short")) {
		   		var zipXml : XML = new XML(data.zip_short[0]);
		    	evt.results = zipXml;
			}
			return;
		}
		
		
	
	
	
		
	}
}