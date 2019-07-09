package com.components.pagination {
	public class PaginationData 	{
		
		private var _totalRows : int;
		private var _totalPages : Number;
		private var _curPage : int;
		private var _maxPagesPerGroup : int;
		
		
		public function PaginationData() {
			return;
		}
		
		public function set totalRows(value : int) : void {
			this._totalRows = value;
		}
		
		public function get totalRows() : int {
			return this._totalRows;
		}

		public function set totalPages(value : Number) : void {
			this._totalPages = value;
		}
		
		public function get totalPages() : Number {
			return this._totalPages;
		}
		
		public function set curPage(value : int) : void {
			this._curPage = value;
		}
		
		public function get curPage() : int {
			return this._curPage;
		}
		
		public function set maxPagesPerGroup(value : int) : void {
			this._maxPagesPerGroup = value;
		}
		
		public function get maxPagesPerGroup() : int {
			return this._maxPagesPerGroup;
		}
		
		public function getTotalPageGroups() : int {
			return this._totalPages / this._maxPagesPerGroup;
		}
		
		public function isMoreThanOnePage() : Boolean {
			return (this._totalPages > 1);
		}
		
		public function calcPageSize() : int {
			return this._totalRows / this._totalPages;
		}
	}
}