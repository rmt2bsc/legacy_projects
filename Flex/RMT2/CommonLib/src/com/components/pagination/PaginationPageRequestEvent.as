package com.components.pagination {
	
	import flash.events.Event;

	public class PaginationPageRequestEvent extends Event {
		
		public static const EVENT_NAME : String = "newPageRequest";
		
		private var _newPage : int;
		
		public function PaginationPageRequestEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}

		public function get newPage() : int {
			return this._newPage;
		}		
		
		public function set newPage(page : int) : void {
			this._newPage = page;
		}
	}
}