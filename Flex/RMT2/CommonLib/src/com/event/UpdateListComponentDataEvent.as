package com.event {

	import flash.events.Event; 
	
	public class UpdateListComponentDataEvent extends Event {
		
		public static var EVENT_NAME : String = "updateListComponentDataEvent";
		
		private var _updateData : Object;
		
		private var _isNew : Boolean;
		
		private var _isDeleted : Boolean;
		
		private var _itemToSelect : int;
		
		
		public function UpdateListComponentDataEvent (type : String, bubbles : Boolean=false, cancelable : Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
		public function get updateData() : Object {
			return this._updateData;
		}
		
		public function set updateData(value : Object) : void {
			this._updateData = value;
		}
		
		public function get isNew() : Boolean {
			return this._isNew;
		}
		
		public function set isNew(value : Boolean) : void {
			this._isNew = value;
		}
		
		public function get isDeleted() : Boolean {
			return this._isDeleted;
		}
		
		public function set isDeleted(value : Boolean) : void {
			this._isDeleted = value;
		}
		
		public function get itemToSelect() : int {
			return this._itemToSelect;
		}
		
		public function set itemToSelect(index : int) : void {
			this._itemToSelect = index;
		}
	}
}
