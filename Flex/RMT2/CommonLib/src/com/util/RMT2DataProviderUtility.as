package com.util {
	import mx.collections.ArrayCollection;
	import mx.controls.NavBar;
	import mx.events.ItemClickEvent;
	
	
	 
	public class RMT2DataProviderUtility {
		
		public function RMT2DataProviderUtility() {
			return;
		}
		
		public function getNavBarItemIndex(navBar : NavBar, itemLabel : String) : int {
			var ndx : int;
			var array : Array = (navBar.dataProvider as ArrayCollection).source;
			for (ndx = 0; ndx < array.length; ndx++) {
				if (itemLabel == array[ndx].label) {
					return ndx;
				}
			}
			return -1;
		}
		
		public function turnOnSelectedNavBarItem(navBar : NavBar, selectedNdx : int) : void { 
			var array : Array = (navBar.dataProvider as ArrayCollection).source;
			for (var ndx : int = 0; ndx < array.length; ndx++) {
				var curItem = navBar.getChildAt(ndx);
				if (selectedNdx == ndx) {
					this.turnOnItem(curItem);	
				}
				else {
					this.turnOffItem(curItem);	
				}
			}  
		}
		
		private function turnOnItem(item : Object) : void {   
			item.setStyle("color", 0x2a80d5);
		}
			
		private function turnOffItem(item : Object) : void {   
		    item.setStyle("color", 0x000000);
		}
		
		
		private function highlightSelectedListItem(e : ItemClickEvent) : void {     
		    for(var ndx : int = 0; ndx < e.target.numChildren; ndx++) {
		       var curItem = e.target.getChildAt(ndx);
		       ndx == e.index ? curItem.setStyle("color", 0x2a80d5) : curItem.setStyle("color", 0x000000);     
		    }
		}
			
			
	}
}