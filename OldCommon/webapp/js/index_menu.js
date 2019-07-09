var MENU_ITEM_PREFIX = "m";

function enableMenuItem(menuItem) {
	var menuNo = getMenuItemNo(menuItem);
	 if (menuNo != document.body.id) {
		 var image = "images/" + menuItem.name + "m.jpg";
		 menuItem.src = image;
		 return;
	  }
   }

   function disableMenuItem(menuItem) {
      var menuNo = getMenuItemNo(menuItem);
	  if (menuNo != document.body.id) {
		  var image = "images/" + menuItem.name + ".jpg";
		  menuItem.src = image;
		  return;
	  }
   }   

   function getMenuItemNo(menuItem) {
       var menuNo = menuItem.name.substr(1, 1);
       return menuNo;
   }

   function selectPageMenuItem() {
      deSelectAll();
      var menuItem = eval(MENU_ITEM_PREFIX + document.body.id);
       menuItem.src = "images/" + menuItem.name + "s.jpg";
   }
   
   function deSelectAll() {
       var ndx = 1;
	   while (ndx < 6) {
		   var menuItem =  eval(MENU_ITEM_PREFIX + ndx);
		   if (menuItem == undefined) {
			   return;
		   }
		   menuItem.src = "images/" + menuItem.name + ".jpg";
		   ndx++;
	   }
   }

