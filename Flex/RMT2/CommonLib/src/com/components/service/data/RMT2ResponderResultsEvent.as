package com.components.service.data {
	import com.event.RMT2Event;
	
	import mx.rpc.Fault;

	public class RMT2ResponderResultsEvent extends RMT2Event 	{

		public static const EVENT_NAME : String = "RMT2ResponderResultsEvent";
		
		private var _error : Boolean;
		
		private var _results : Object;
		
		private var _fault : Fault;
		
		private var _serviceId : String;
		
		private var _found : Boolean;
		
		private var _message : String;
		
		
		
		public function RMT2ResponderResultsEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)  	{
			super(type, bubbles, cancelable);
		}
		
		
		public function get error() : Boolean {
			return this._error;
		}
		
		public function set error(flag : Boolean) : void {
			this._error = flag;
		}
		
		public function get results() : Object {
			return this._results;
		}
		
		public function set results(data : Object) : void {
			this._results = data;
		}
		
		public function get fault() : Fault {
			return this._fault;
		}
		
		public function set fault(fault : Fault) : void {
			this._fault = fault;
		}
		
		public function get serviceId() : String {
			return this._serviceId;
		}
		
		public function set serviceId(srvcName : String) : void {
			this._serviceId = srvcName;
		}
		
		public function get found() : Boolean {
			return this._found;
		}
		
		public function set found(flag : Boolean) : void {
			this._found = flag;
		}
		
		public function get message() : String {
			return this._message;
		}
		
		public function set message(message : String) : void {
			this._message = message;
		}
		
	}
}