package com.event {
	
	import flash.events.Event;

	public class AcceptFormChangesEvent extends Event {
		
		public static const EVENT_NAME : String = "acceptFormChanges";
		
		private var _model : Object;
		
		public function AcceptFormChangesEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		public function get model() : Object {
			return this._model;
		}		
		
		public function set model(data : Object) : void {
			this._model = data;
		}
	}
}