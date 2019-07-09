package com.components.pagination {
	
	import flash.events.Event;

	public class PaginationResultsEvent extends PaginationMetricChangeEvent	{
		
		public static const EVENT_NAME : String = "paginationResultsEvent";
		
		private var _pageResults : Object;
		
		public function PaginationResultsEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		
		public function get pageResults() : Object {
			return this._pageResults;
		}
		
		public function set pageResults(value : Object) : void {
			this._pageResults = value;
		}
	}
}