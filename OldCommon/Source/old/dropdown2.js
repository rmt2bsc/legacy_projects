var dropLoad = true

function goThere() {
	var destination = document.office.earl.options[document.office.earl.selectedIndex].value
	if (destination != "none") {
		if (destination == ""){
			alert('The requested page is not available at this time')}
			else top.location = destination}
}

function makeForm() {
	document.write('<form name="office">');
	document.write('<select name="earl" size="1" onChange="goThere()">');
	document.write('<option value="none">Select from list </option>');
	document.write('<option value="none">--------------------- </option>');
	document.write('<option value="">Altoona, PA </option>');
	document.write('<option value="../employment/location/agpsd.html">Atlanta, GA </option>');
	document.write('<option value="../employment/location/atpsd.html">Austin, TX </option>');
	document.write('<option value="../employment/location/bapsd.html">Baltimore, MD </option>');
	document.write('<option value="../employment/location/cnpsd.html">Charlotte, NC </option>');
	document.write('<option value="../employment/location/chpsd.html">Chicago, IL </option>');
	document.write('<option value="../employment/location/cypsd.html">Cincinnati, OH </option>');
	document.write('<option value="../employment/location/scpsd.html">Columbia, SC </option>');
	document.write('<option value="../employment/location/cbpsd.html">Columbus, OH </option>');
	document.write('<option value="../employment/location/dfpsd.html">Dallas, TX </option>');
	document.write('<option value="../employment/location/rmpsd.html">Denver, CO </option>');
	document.write('<option value="../employment/location/sfpsd.html">Ft. Lauderdale, FL </option>');
	document.write('<option value="../employment/location/gvlop.html">Greenville, SC </option>');
	document.write('<option value="">Hagerstown, MD </option>');
	document.write('<option value="">Harrisburg, PA </option>');
	document.write('<option value="../employment/location/htpsd.html">Houston, TX </option>');
	document.write('<option value="">Irvine, CA </option>');
	document.write('<option value="../employment/location/jfpsd.html">Jacksonville, FL </option>');
	document.write('<option value="../employment/location/kcpsd.html">Kansas City, MO </option>');
	document.write('<option value="">Kansas City, MO (STI) </option>');
	document.write('<option value="../employment/location/lapsd.html">Los Angeles, CA </option>');
	document.write('<option value="../employment/location/mtops.html">Memphis, TN </option>');
	document.write('<option value="../employment/location/mmpsd.html">Minneapolis, MN </option>');
	document.write('<option value="../employment/location/ntpsd.html">Nashville, TN </option>');
	document.write('<option value="../employment/location/orpsd.html">Orlando, FL </option>');
	document.write('<option value="../employment/location/svpsd.html">Palo Alto, CA </option>');
	document.write('<option value="../employment/location/dvpsd.html">Philadelphia, PA </option>');
	document.write('<option value="../employment/location/papsd.html">Phoenix, AZ </option>');
	document.write('<option value="../employment/location/pbpsd.html">Pittsburgh, PA </option>');
	document.write('<option value="">Pittsburgh, PA (STI) </option>');
	document.write('<option value="../employment/location/popsd.html">Portland, OR </option>');
	document.write('<option value="../employment/location/rtpsd.html">Raleigh, NC </option>');
	document.write('<option value="../employment/location/rvpsd.html">Richmond, VA </option>');
	document.write('<option value="../employment/location/roops.html">Roanoke, VA </option>');
	document.write('<option value="">Salt Lake City, UT </option>');
	document.write('<option value="">San Francisco, CA </option>');
	document.write('<option value="../employment/location/cipsd.html">San Juan, PR </option>');
	document.write('<option value="../employment/location/pspsd.html">Seattle, WA </option>');
	document.write('<option value="../employment/location/slpsd.html">St. Louis, MO </option>');
	document.write('<option value="../employment/location/tfops.html">Tallahassee, FL </option>');
	document.write('<option value="../employment/location/tbpsd.html">Tampa, FL </option>');
	document.write('<option value="../employment/location/twpsd.html">Virginia Beach, VA </option>');
	document.write('<option value="../employment/location/dcpsd.html">Washington, DC </option>');
	document.write('<option value="../employment/location/trpsd.html">Winston-Salem, NC </option>');
	document.write('</select> ');
	document.write('</form>');
}