package com.components.security {
	
	import com.valueobject.SessionTokenVO;

	public class AuthenticationSucessEvent extends CommonSecurityEvent {
		
		public static var EVENT_NAME : String = "authenticationSuccess";
		
		private var _session : SessionTokenVO;
		
		
		
		public function AuthenticationSucessEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		public function get session() : SessionTokenVO {
			return this._session;
		}		
		
		public function set session(obj : SessionTokenVO) : void {
			this._session = obj;
		}
	}
}