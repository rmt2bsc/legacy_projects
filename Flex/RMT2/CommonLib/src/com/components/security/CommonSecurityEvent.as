package com.components.security {
	
	import com.event.RMT2Event;

	/**
	 * 
	 * @author rterrell
	 * 
	 */	
	public class CommonSecurityEvent extends RMT2Event {
		
		/**
		 * 
		 */		
		public static const EVENT_NAME : String = "commonSecurityEvent";
		
		private var _userId : String;
		
		private var _message : String;
		
		private var _appCode : String;
		
		
		/**
		 * 
		 * @param type
		 * @param bubbles
		 * @param cancelable
		 * 
		 */		
		public function CommonSecurityEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get userId() : String {
			return this._userId;
		}		
		
		/**
		 * 
		 * @param uid
		 * 
		 */		
		public function set userId(uid : String) : void {
			this._userId = uid;
		}
		
		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get message() : String {
			return this._message;
		}		
		
		/**
		 * 
		 * @param msg
		 * 
		 */		
		public function set message(msg : String) : void {
			this._message = msg;
		}
		
		/**
		 * 
		 * @return 
		 * 
		 */		
		public function get appCode() : String {
			return this._appCode;
		}		
		
		/**
		 * 
		 * @param appCode
		 * 
		 */		
		public function set appCode(appCode : String) : void {
			this._appCode = appCode;
		}
	}
}