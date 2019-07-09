
var METHOD_GET = "GET";
var METHOD_POST = "POST";

/**
 * To cope with the differences in object creation syntax used by various browsers
 * we will need to create the XMLHttpRequest object in one of two ways.
 * For any browser, except Internet Explorer, we can create an XMLHttpRequest object like this:
 *
 *   var requester = new XMLHttpRequest();
 *
 * However, in Internet Explorer, XMLHttpRequest is implemented as an ActiveX object.
 * For IE, an object is created like this:
 *
 *   var requester = new ActiveXObject("Microsoft.XMLHTTP");
 *
 * Note: this also means that if users have ActiveX objects disabled in Internet Explorer,
 * they will be unable to use XMLHttpRequest even if JavaScript is enabled.
 */
	function getHTTPObject() {
		var requester;
		try {
		 requester = new XMLHttpRequest();
		}
		catch (error) {
		 try  {
			 requester = new ActiveXObject("Microsoft.XMLHTTP");
		 }
		 catch (error)  {
			 return false;
		 }
		}

		return requester;
	}


  /**
   *  This mehod is designed to transport data to and from the serverusing a valid XMLHttpRequest object
   *
   * _reqObj - This is the initialized XMLHttpRequest object.
   * _method - The method of the request.  "GET" or "POST".
   * _url - The resource that is to serve the request.  This resource must reside on the same domain as the page
   *           that requests it.
   * _parms - CGI Parameters
   * _stateHandler - A function that is set up to handle the event when the readyState varialbe changes.
   *
   */
	function requestData(_reqObj, _method, _url, _parms, _stateHandler) {
     var actualMethod;
     var actualParms = null;

     // We must have a valid request object in order to continue.
     if (!_reqObj) {
			 return -1;
		 }

		 // A state handler must be provided by client if we want to perform request asynchronously.
		 // This handler should contain logic responsible for decoding the server's response.
		 if (_stateHandler != null) {
			 _reqObj.onreadystatechange = _stateHandler;
		 }

		 //  If method is not supplied, default to "GET"
     if (!_method) {
			 _method = METHOD_GET;
		 }

		 // Detemine method of request and if CGI parameters
		 // should be taken into account of the request
		 switch (_method) {
			 case METHOD_GET:
			    if (_parms != null && _parms.length > 0) {
						_url += "?" + _parms;
					}
					actualMethod = METHOD_GET;
					actualParms = null;
			    break;

			 case METHOD_POST:
			    actualMethod = METHOD_POST;
			    if (_parms != null && _parms.length > 0) {
						actualParms = _parms;
					}
					break;
		 }

		 // Send request.
    _reqObj.open(actualMehotd, _url);
		_reqObj.send(actualParms);

	}