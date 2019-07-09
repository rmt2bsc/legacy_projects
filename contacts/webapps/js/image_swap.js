// A random image rotation script. This script will change the sponsor image by 
// image, swapping every set period of time (5 seconds in this example) without 
// having to reload the page.
//
// How to use: Somewhere in between the HTML body tags, add the following lines 
// of code to display the initial image and to setup a time interval to change the image.
//

/*
     <script language="JavaScript">
				var ads1 = new adArray("images/baner.jpg","http://planmagic.com",
															 "images/baner2.jpg","http://simplythebest.net");
				var adNum1 = getAdNum(ads1); 
				document.write('<CENTER><TABLE CELLPADDING=0 CELLSPACING=1 BORDER=0><TR><TD '
											+'ALIGN=CENTER><FONT SIZE=2 FACE=Arial><B>SimplytheBest DHTML Scripts & JavaScripts is sponsored by: '
											+'</FONT></TD><TR></TR><TD><A HREF="' + ads1[adNum1].href + '" id="adLink1"><IMG SRC="' + ads1[adNum1].src +'" '
											+'WIDTH="100" HEIGHT="100" BORDER=0 id="adImage1"></A></TD></TR></TABLE></CENTER>');

				setTimeout("rotateSponsor('adImage1', 'adLink1', adNum1, ads1, 1)", INTERVAL);
			</script>
*/

//  Notice the initial setting of href property of the link tag and the src property of the 
//  image tag as well as their id's.   Each image/link combination is considered to be a "section".
//  You can add as many sections as needed in your code.   In order to create another section, add 
//  another script block just like above and change the names of ads1, adNum1, and the id's of the 
//  image and link tags to be unique.   By default, the swap time interval is set for 5 seconds. 
//  The interval can be changed by assigning a new value to the variable, INTERVAL.   For example,
//  to change the interval to 2 seconds, code the following: INTERVAL = 2000;

var INTERVAL = 5000;
var ads = [];

/**
 * Build an array of objects which each object contain elements that hold 
 * the complete path and filename of the image and its href reference.
 */
function adArray() {
	for (i = 0; i * 2 < adArray.arguments.length; i++) {
		this[i] = new Object();
		this[i].src = adArray.arguments[i * 2];
		this[i].href = adArray.arguments[i * 2 + 1];
	}
	this.length = i;
}

/**
 * Determines the current index for the array of images.
 *
 * ads - The target array of image/href's to manage.
 */
function getAdNum(ads) {
	dat = new Date();
	dat = (dat.getTime() + "").charAt(8);
	if (dat.length == 1) {
		ad_num = dat % ads.length;
	} else {
		ad_num = 0;
	}
	return ad_num;
}

/**
 * This function performs the actual swapping of the image.
 *
 * imgId - The id of the image that will hold the replacement image.
 * lnkId - The id of the link assoicated with imgId that will hold the replacement href resource.
 * adNum - The current index of the array of image objects we need to navigate from in order to 
 *         manage the next image.
 * adsArray - The array of images to manage.   This script is capable of managing an array of 
 *            image/href arrays which each image/href array element is considered to be a section.
 * section - An index representing the group or array which imgId and lnkId belong.
 */
function rotateSponsor(imgId, lnkId, adNum, adsArray, section) {
	ads[section] = adsArray;
	if (document.images) {
		adNum = (adNum + 1) % ads[section].length;
		document.getElementById(imgId).src = ads[section][adNum].src;
		document.getElementById(lnkId).href = ads[section][adNum].href;
		setTimeout("rotateSponsor('" + imgId + "', '" + lnkId + "', " + adNum + ", ads[" + section + "], " + section + ")", INTERVAL);
	}
}

