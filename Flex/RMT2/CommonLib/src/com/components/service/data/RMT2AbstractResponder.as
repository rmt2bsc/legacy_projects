package com.components.service.data {

	import flash.events.EventDispatcher;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.Fault;
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;




	/**
	 * Custom implementation of IResponder interface.   
	 * <p>
	 * The two methods required by IResponder are  implemented using the template pattern.   
	 * The template logic for the IResponder methods, result and fault,  identifies the ResultEvent 
	 * and FaultEvent for the respective methods, processes the message, and notifies the caller 
	 * with the response.  The only requirement that this implementation imposes on the user is to 
	 * provide specific logic to process the incoming data and return the response as an insatnce of 
	 * RMT2ResponderResultsEvent by implementing the method, processResponse(Object, AsyncToken).
	 * <p>
	 * <b>Usage</b><br>
	 * <pre>
	 *      private function verifyUser(e : Event) : void {	
	 *          // "this.parms" is a XML model component used to capture request parameters
	 *          this.pams.clientAction = "verifyauthentication"; 
	 *          this.pams.UID = "admin"; 
	 *          this.pams.appcode = "contacts"; 
	 *          var contextUrl : String = "/contacts/xmlStreamProcessor/Security.Authentication"; 
	 *          var rootUrl : String = "http://localhost:8080"; 
	 *          
	 *          //  Setup RMT2HttpService object
	 *          var http : RMT2HttpService = new RMT2HttpService(rootUrl);
	 * 		 http.setupURL(contextUrl);
	 * 
	 *          // Execute the send method of the RMT2HttpService object and get back an instance of AsyncToken 
	 *          var token : AsyncToken = http.send(this.pams); 	
	 * 
	 *          // Add properties, "caller" and "serviceId", dynamically to AsyncToken instance.
	 *          token[RMT2AbstractResponder.PROP_NAME_CALLER]	 = e.target; 
	 *          token[RMT2AbstractResponder.PROP_NAME_SERVICE_ID] = this.pams.clientAction;
	 *  
	 *          // Assoicate responder with AsyncToken instance. 
	 *          token.addResponder(new TestResponder()); 
	 *      }
	 *  </pre>
	 * 
	 * @author rterrell
	 * 
	 */
	public class RMT2AbstractResponder implements IResponder {
		
		/**
		 * The proerty name that identifies the caller of the data service. 
		 */
		public static const PROP_NAME_CALLER : String = "caller";
		
		/**
		 * The property name that identifies the service this responder is assoicated with.
		 */
		public static const PROP_NAME_SERVICE_ID : String = "serviceId";
		
		/**
		 * This property helps remedy a bug in the Flex SDK which is characterized as the responder is being called 2x.
		 */
		public static const PROP_NAME_EXECUTED : String = "executed";
		
		
		
		/**
		 * Creates a RMT2AbstractResponder object
		 * 
		 */
		public function RMT2AbstractResponder() {
			return;
		}

		/**
		 * This method is fired when the data service call returns successfully.   
		 * <p>
		 * It basically verifies that <i>data</i> is in deed of type ResultEvent, processes the 
		 * response, and sends a notification to the call indicating that the service call has 
		 * completed successfully.
		 * 
		 * @param data
		 *          An arbitrary object which at runtime should be an instance of type ResultEvent.
		 * 
		 */
		public function result(data : Object) : void {
			var evt : ResultEvent = null;
			if (data is ResultEvent) {
				evt = data as ResultEvent;
			}
			else {
				return;
			}
			
			// Bug preventative logic to prevent this responder from being executed twice.  This issue should have been fixed in SDK 3.5 and higer.
			if (evt.token.hasOwnProperty(RMT2AbstractResponder.PROP_NAME_EXECUTED)) {
				var hasExcecuted : Boolean = evt.token[RMT2AbstractResponder.PROP_NAME_EXECUTED] as Boolean;
				if (hasExcecuted) {
					return;
				}
			}
			
			var caller : EventDispatcher = null;
			var serviceId : String = null;
			var results : RMT2ResponderResultsEvent = this.processResponse(evt.result, evt.token);
			results.error = false;
			results.fault = null;
			this.notifyCallerWithResponse(evt.token, results);
			evt.token[RMT2AbstractResponder.PROP_NAME_EXECUTED] = true;
			return;
		}
		
		/**
		 * This method is fired whtn the data service call fails.   
		 * <p>
		 * It basically verifies that <i>info</i> is in deed of type FaultEvent, processes the fault, and sends a 
		 * notification to the call indicating that the service call has completed with a failure.
		 * 
		 * @param info
		 *          An arbitrary object which at runtime should be an instance of type FaultEvent.
		 * 
		 */		
		public function fault(info : Object) : void {
			var evt : FaultEvent = null;
			if (info is FaultEvent) {
				evt = info as FaultEvent;
			}
			
			// Bug preventative logic to prevent this responder from being executed twice.  This issue should have been fixed in SDK 3.5 and higer.
			if (evt.token.hasOwnProperty(RMT2AbstractResponder.PROP_NAME_EXECUTED)) {
				var hasExcecuted : Boolean = evt.token[RMT2AbstractResponder.PROP_NAME_EXECUTED] as Boolean;
				if (hasExcecuted) {
					return;
				}
			}
			
			var serviceId : String = null;
			var results : RMT2ResponderResultsEvent = this.processResponse(evt.fault, evt.token);
			results.error = true;
			results.results = null;
			results.found = false;
			
			// Create a pre-formatted message for the message property of the RMT2ResponderResultsEvent instance.
			var msg : String = "A data service error occurred regarding client action [" + results.serviceId + "] for component [" + evt.token[RMT2AbstractResponder.PROP_NAME_CALLER].name + "].\n\n";
			msg += "\nFalut Code: " + evt.fault.faultCode;
			msg += "\nMessage: " + evt.fault.faultString;
			if (evt.fault.rootCause != null) {
				msg += "\nError Type: " +   evt.fault.rootCause.type;
				msg += "\nRoot Cause: " +   evt.fault.rootCause.text;	
			}
			msg += "\nOther Cause: Server may be down!";
			results.message = msg;
			
			this.notifyCallerWithResponse(evt.token, results);
			
			evt.token[RMT2AbstractResponder.PROP_NAME_EXECUTED] = true;
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
		protected function processResponse(data : Object, token : AsyncToken) : RMT2ResponderResultsEvent {
			var evt : RMT2ResponderResultsEvent = new RMT2ResponderResultsEvent(RMT2ResponderResultsEvent.EVENT_NAME, true);
			if (token.hasOwnProperty(RMT2AbstractResponder.PROP_NAME_SERVICE_ID)) {
				evt.serviceId = token.serviceId;
			}
			if (data is Fault) {
				evt.fault = data as Fault;
				evt.error = true;
				evt.found = false;
			}
			return evt;
		}
		
		
		/**
		 * Dispatches an event to caller indicating that the service call has completed.   The event that is dispatched is of 
		 * type RMT2ResponderResultsEvent and will only be fired if <i>token</i>'s property, caller, is valid.
		 * 
		 * @param token
		 *         An instance of AsyncToken
		 * @param evt
		 *         An instance of RMT2ResponderResultsEvent containing data retrieved from server or a fault message.
		 * 
		 */		
		private function notifyCallerWithResponse(token : AsyncToken, evt : RMT2ResponderResultsEvent) : void {
			var caller : EventDispatcher = null;
			if (token.hasOwnProperty(RMT2AbstractResponder.PROP_NAME_CALLER) && token.caller != null && token.caller is EventDispatcher) {
				caller = token.caller;
				caller.dispatchEvent(evt);
			}
		}

		
		
	}
}