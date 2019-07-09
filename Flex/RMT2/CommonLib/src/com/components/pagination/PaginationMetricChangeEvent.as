package com.components.pagination {
	
	import flash.events.Event;

	public class PaginationMetricChangeEvent extends Event 	{
		
		public static const EVENT_NAME : String = "paginationMetricChangeEvent";
		
		private var _pageMetrics : PaginationData;
		
		public function PaginationMetricChangeEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}
		
		public function get pageMetrics() : PaginationData {
			return this._pageMetrics;
		}
		
		public function set pageMetrics(value : PaginationData) : void {
			this._pageMetrics = value;
		}
	}
}