package com.event {
	
	import flash.events.Event;

	public class LoadFormChangesEvent extends Event {
		
		public static const EVENT_NAME : String = "loadFormChanges";
		
		private var _model : Object;
		
		public function LoadFormChangesEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
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