package com.event {
	
	import flash.events.Event;

	public class RMT2Event extends Event {
		
		public static const EVENT_NAME : String = "RMT2Event";
		
		private var _itemIndex : int;
		
		
		public function RMT2Event(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}


		public function get itemIndex() : int {
			return this._itemIndex;
		}
		
		public function set itemIndex(index : int) : void {
			this._itemIndex = index;
		}
		
		
	}
}