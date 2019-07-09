package com.event {
	import com.valueobject.SessionTokenVO;

	public class SessionTokenEvent extends RMT2Event {
		
		public static const EVENT_NAME : String = "sessionTokenRequest";
		
		private var _loginId : String;
		
		private var token : SessionTokenVO;
		
		public function SessionTokenEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		public function get loginId() : String {
			return this._loginId;
		}
		
		public function set loginId(loginId : String) : void {
			this._loginId =  loginId;
		}
		
		public function getToken() : SessionTokenVO {
			return this.token;
		}		
		
		public function setToken(token : SessionTokenVO) : void {
			this.token = token;
		}
	}
}