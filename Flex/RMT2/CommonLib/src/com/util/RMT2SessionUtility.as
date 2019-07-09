package com.util {
	import com.event.SessionTokenEvent;
	import com.valueobject.SessionTokenVO;
	
	import flash.events.EventDispatcher;
	
	/**
	 * Contains functionality to create and fetch session tokens for a specified component.
	 */
	public class RMT2SessionUtility	{
		public function RMT2SessionUtility() {
			return;
		}
		
		/** 
		 * Requests the session token by dispatching a "sessionTokenRequest" event to any interested parties.   The interested party 
		 * should be listeneing for the event in the capture phase so that the target can retrieve the results.
		 * 
		 * @param target
		 *          an component descending from EventDispatcher
		 * @return SessionTokenVO instance.  null is returned if "target" equals null or if the session token is not obtainable.
		 */
		public static function getSessionToken(target : EventDispatcher) : SessionTokenVO {
			if (target == null) {
				return null;
			}
			var event : SessionTokenEvent = new SessionTokenEvent(SessionTokenEvent.EVENT_NAME, false);
			target.dispatchEvent(event);
			var session : SessionTokenVO = event.getToken();
			return session;
		}

	}
}