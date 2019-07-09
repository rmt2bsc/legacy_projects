package com.event {

	import flash.events.Event; 
	
	public class UpdateFormChangesEvent extends RMT2Event  {
		
		public static var EVENT_NAME : String = "updateFormChanges";
		
		public static var MODE_ADD : int = 1;
				
		public static var MODE_UPDATE : int = 2;
		
		public static var MODE_DELETE : int = 3;
		
		private var _mode : int;
		
		private var _formData : Object;
		
		public function UpdateFormChangesEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
		public function get mode() : int {
			return this._mode;
		}
		
		public function set mode(value : int) : void {
			this._mode = value;
		} 
		
		public function get formData() : Object {
			return this._formData;
		}
		
		public function set formData(value : Object) : void {
			this._formData = value;
		}
	}
}
