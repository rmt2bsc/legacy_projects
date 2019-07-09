package com.valueobject {
	
	import mx.collections.ArrayCollection;
	
	/**
	 * Tracks detail information about the user's session.
	 */ 
	public class SessionTokenVO extends UserTokenVO {
		
		private var _sessionId : String;
		
		private var _hostProtocol : String;
		
		private var _hostName : String;
		
		private var _hostPort : int;
		
		private var _appContext : String;
		
		private var _appName : String;
		
		private var _loginState : String;
		
		
		public function SessionTokenVO(loginId : String, sessionId : String) : void {
			super(loginId);
			this._sessionId = sessionId;
		}
		
		public function get sessionId() : String {
			return this._sessionId;
		}
		
		public function set sessionId(value : String) : void {
			this._sessionId = value;
		}
		
		public function get hostProtocol() : String {
			return this._hostProtocol;
		}
		
		public function set hostProtocol(value : String) : void {
			this._hostProtocol = value;
		}
		
		public function get hostName() : String {
			return this._hostName;
		}
		
		public function set hostName(value : String) : void {
			this._hostName = value;
		}

		public function get hostPort() : int {
			return this._hostPort;
		}
		
		public function set hostPort(value : int) : void {
			this._hostPort = value;
		}
		
		public function get appContext() : String {
			return this._appContext;
		}
		
		public function set appContext(value : String) : void {
			this._appContext = value;
		}
		
		public function get hostUrl() : String {
			var host : String = "";
			if (this._hostProtocol != null && this._hostName != null) {
				host = this._hostProtocol + "://" + this._hostName;
				if (this._hostPort > 0) {
					host += ":" + this._hostPort;
				}
//				host += "/";
			}
			return host;
		}
		
		public function get appName() : String {
			return this._appName;
		}
		
		public function set appName(value : String) : void {
			this._appName = value;
		}
		
		public function get loginState () : String {
			return  this._loginState
		}
	}
}