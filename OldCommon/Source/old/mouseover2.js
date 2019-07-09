var imgLoad = true

function imgOn(imgName,msg) {
	if (document.images) {
		document[imgName].src = eval(imgName + "on.src");
		window.status=msg;}
}

function imgOff(imgName,msg) {
	if (document.images) {
		document[imgName].src = eval(imgName + "off.src");
		window.status=msg;}
}

function msover(img1,ref1) {
	if (document.images) {
		document.images[img1].src = ref1;}
}
        
function msout(img1,ref1) {
	if (document.images) {
		document.images[img1].src = ref1;}
}


if (document.images) {
	img1on = new Image();  img1on.src = "../images/toolbar/dot_on.gif";
	img2on = new Image();  img2on.src = "../images/toolbar/dot_on.gif";
	img3on = new Image();  img3on.src = "../images/toolbar/dot_on.gif";
	img4on = new Image();  img4on.src = "../images/toolbar/employment_on.gif";
	img5on = new Image();  img5on.src = "../images/toolbar/profile_on.gif";
	img6on = new Image();  img6on.src = "../images/toolbar/services_on.gif";
	img7on = new Image();  img7on.src = "../images/toolbar/invest_on.gif";
	img8on = new Image();  img8on.src = "../images/toolbar/servapps_on.gif";
	img9on = new Image();  img9on.src = "../images/toolbar/servnet_on.gif";
	img10on = new Image();  img10on.src = "../images/toolbar/servsys_on.gif";
	img11on = new Image();  img11on.src = "../images/toolbar/servout_on.gif";
	img12on = new Image();  img12on.src = "../images/toolbar/servgen_on.gif";
	img13 = new Image();  img13.src = "../images/toolbar/servmenuapps.gif";
	img14 = new Image();  img14.src = "../images/toolbar/servmenusys.gif";
	img15 = new Image();  img15.src = "../images/toolbar/servmenuout.gif";
	img16 = new Image();  img16.src = "../images/toolbar/servmenugen.gif";
	img17 = new Image();  img17.src = "../images/toolbar/servmenunet.gif";
	img18on = new Image();  img18on.src = "../images/toolbar/servintro_on.gif";
	img1off = new Image(); img1off.src = "../images/toolbar/dot_off.gif";
	img2off = new Image(); img2off.src = "../images/toolbar/dot_off.gif";
	img3off = new Image(); img3off.src = "../images/toolbar/dot_off.gif";
	img4off = new Image(); img4off.src = "../images/toolbar/employment_off.gif";
	img5off = new Image(); img5off.src = "../images/toolbar/profile_off.gif";
	img6off = new Image(); img6off.src = "../images/toolbar/services_off.gif";
	img7off = new Image(); img7off.src = "../images/toolbar/invest_off.gif";
	img8off = new Image();  img8off.src = "../images/toolbar/servapps_off.gif";
	img9off = new Image();  img9off.src = "../images/toolbar/servnet_off.gif";
	img10off = new Image();  img10off.src = "../images/toolbar/servsys_off.gif";
	img11off = new Image();  img11off.src = "../images/toolbar/servout_off.gif";
	img12off = new Image();  img12off.src = "../images/toolbar/servgen_off.gif";
	img13off = new Image();  img13off.src = "../images/toolbar/servmenuoff.gif";
	img18off = new Image();  img18off.src = "../images/toolbar/servintro_off.gif";
}