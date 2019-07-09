
var  tabOn = "#CCCCCC";
var  tabOff = "#CCCCCC";
var  tabProfile = new Array();

/////////////////////////////////////////////////////////////////////////////////////
//     Function: tabProfileObject
//    Prototype: aTabName - id of the tab component.  aTabName must equal the root name
//                          of the image's filename.  Example: the root name of the
//                          filename, fh_tab_personal_1.gif, would be fh_tab_personal.
//               aImage -   Path of the image which only the root name of the image's
//                          file name is included.   Such as, "images/fh_tab_personal"
//                          which represents fh_tab_personal_1.gif.
//      Returns: n/a
//  Description: This function serves as a class representation of a tabProfile.
//               This is the blueprint tab profiles in order to track
//               the current state of selected and deselected tabs.
/////////////////////////////////////////////////////////////////////////////////////
function tabProfileObject(aTabName, aImage) {
  this.tabName = aTabName;
  this.tabImage = aImage;
}


/////////////////////////////////////////////////////////////////////////////////////
//     Function: createTabProfile
//    Prototype: aTabName - id of the tab component.  aTabName must equal the root name
//                          of the image's filename.  Example: the root name of the
//                          filename, fh_tab_personal_1.gif, would be fh_tab_personal.
//               aImage -   Path of the image which only the root name of the image's
//                          file name is included.   Such as, "images/fh_tab_personal"
//                          which represents fh_tab_personal_1.gif.
//      Returns: n/a
//  Description: This function is used to create tab profiles of type tabProfileObject.
//               Make calls to this function just before the body of an HTML Document
//               is created, or call from within another function that is triggered
//               from the onLoad event of the BODY tag.  Use this function for every
//               tab on the page.
/////////////////////////////////////////////////////////////////////////////////////
function createTabProfile(aTabName, aImage) {
  var temp = new tabProfileObject(aTabName, aImage);
  var tabCount = tabProfile.length;

  tabProfile[tabCount] = temp;
}



/////////////////////////////////////////////////////////////////////////////////////
//     Function: selectTab
//    Prototype: row - A refernence to <tr> html tag via the "id" property which
//                     houses all of the tabs.
//               tabNdx - index of the tab (beginning with 0)
//      Returns: n/a
//  Description: Selects and deselects the tabs of a form.  In order for this
//               function to work properly the tabs should be created using a table
//               tag with one row and n <td> cells for that row.  The Id for the
//               tab pages should be constructed as follows:  "tabPage" + index of
//               the tab.  For example, if tab0 is the first tab (0), the its
//               associated tabpge should be named, tabPage0.
//
/////////////////////////////////////////////////////////////////////////////////////
function selectTab(row, tabNdx) {
  var  selectedTab;
  var  tabObject;
  var  tabObjectName;
  var  tabPageObj;
  var  tabPageName;

  selectedTab = event.srcElement.id;

     // Deselect all tabs and hide all tab pages
  for(var ndx = 0; ndx < tabProfile.length; ndx++) {
      // Deselect tab
    for (ndx2 = 0; ndx2 < row.all.length; ndx2++) {
      if (row.all[ndx2].id == tabProfile[ndx].tabName) {
        tabObject = row.all[ndx2];
      }
    }
    if (typeof tabObject == "undefined") {
      continue;
    }
      // Select tab if equaled to the targeted tab, otherwise deselect
    if (tabObject.id == selectedTab) {
      tabObject.src = "images/"+ tabProfile[ndx].tabImage + "_1.gif";
    }
    else {
      tabObject.src = "images/"+ tabProfile[ndx].tabImage + "_0.gif";
    }

      // Deselect tabpage
    tabPageName = "tabPage" + ndx;
    tabPageObj = eval(tabPageName);
    tabPageObj.style.visibility = "hidden";
  }

    // Make visible the associated tab page.
  tabPageName = "tabPage" + tabNdx;
  tabPageObj = eval(tabPageName);
  tabPageObj.style.visibility = "visible";

}


