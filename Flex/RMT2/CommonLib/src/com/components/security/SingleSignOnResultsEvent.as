package com.components.security {
	
	import com.valueobject.SessionTokenVO;

	public class SingleSignOnResultsEvent extends AuthenticationSucessEvent {
		
		public static var EVENT_NAME : String = "userSingleSignOnResults";
		
		private var _loginRequired : Boolean;
		
		
		public function SingleSignOnResultsEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}


		public function set loginRequired(flag : Boolean) : void {
			this._loginRequired = flag;
		}
		
		public function get loginRequired() : Boolean {
			return this._loginRequired;
		}
	}
}