package com.event {

	import flash.events.Event; 
	
	public class UpdateReplyEvent extends Event {
		
		public static var EVENT_NAME : String = "updateReply";
		
		private var _msg : String;
		
		private var _status : String;
		
		private var _code : int;
		
		private var _returnData : Object;
		
		
		public function UpdateReplyEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
		public function get msg() : String {
			return this._msg;
		}
		
		public function set msg(value : String) : void {
			this._msg = value;
		} 
		
		public function get status() : String {
			return this._status;
		}
		
		public function set status(value : String) : void {
			this._status = value;
		}
		
		public function get code() : int {
			return this._code;
		}
		
		public function set code(value : int) : void {
			this._code = value;
		}  
		
		public function get returnData() : Object {
			return this._returnData;
		}
		
		public function set returnData(value : Object) : void {
			this._returnData = value;
		}
	}
}
