var REQTYPE_MS_LATER = "Msxml2.XMLHTTP";
var REQTYPE_MS_OLDER = "Microsoft.XMLHTTP";

/**
  * Helper function that drives the process of making an asynchronous call from
  * the client using an instance of AjaxHandler.  The user of this function is
  * required to set up and pass a valid instance of RequestConfig as an input
  * parameter for successful operation.
  */
function processAjaxRequest(reqConfig) {
	var ajax = null;
	try {
		ajax = new AjaxHandler(reqConfig);
		ajax.sendRequest();
	}
	catch (e) {
		alert(e);
	}
}


/**
 * This is the nucleus of this api which provides basic functionality
 * from creating, sending, and processing the results of a valid
 * XMLHttpRequest object.    The user of this function is required to
 * set up and pass a valid instance of RequestConfig as an input
 * parameter for successful operation.
 *
 * Usage:
 *  function getOrders() {
 * 		var ajax = null;
 *      // Setup request configuration object
 *  	RequestConfig config = new RequestConfig();
 *  	config.method = "POST";
 *  	config.serviceId = "getOrders";
 *  	config.resourceURL = "http://localhost:8080/orderServlet";
 *    config.customParmHandler = setupOrderParms;
 *    config.customResponseHandler = processResults;
 *    config.renderHTML = false;
 *    // Execute service.
 *		try {
 *			ajax = new AjaxHandler(config);
 *			ajax.sendRequest();
 *		}
 *		catch (e) {
 *			alert(e);
 *		}
 *  }
 *
 *  // Handler for setting up service parameters.
 *  function setupOrderParms() {
 *      var orderId = document.getElementById("orderIdSelection");
 *      return "orderId=" + orderId;
 *  }
 *
 *  // Handler for processing the results of an Ajax call.
 *  function processResults(config, xmlData) {
 *      // Add logic that will process xml data here...
 *      return;
 *  }
 *
 *  If Ajax service call requires specific or custom parameter processing,
 *  create a javascript function that with an empty paramter list and
 *  return a String of key/value pairs separated by the "&".   If special
 *  processing is required to processing the end results of the Ajax call,
 *  create a javascript function conforming to the signature,
 *       function funcName(config, data).
 *  the parameter, config, is required to be an instance of RequestConfig and
 *  data the XML data returned from the Ajax call.
 */
function AjaxHandler(reqConfig) {
	if (reqConfig == null) {
		throw "Ajax Api could not be instantiated.  Configuration data object is invalid";
	}

	var config = reqConfig;
	var xmlHttp = null;
	var isIE;


  /**
   * The driver of this api class that creates a XMLHttpRequest object,
   * determines the resource URL, assembles the parameter list of the
   * target service URL, assigns the state change handler, opens the
   * connection, and sends the request.
   *
   */
	this.sendRequest = function () {
		 // create request object
		xmlHttp = createXMLHttpRequest();
		var url = config.resourceURL;
		
		 // build list of URL parameters.
		if (config.customParmHandler) {
			var parmList = config.customParmHandler();
			if (parmList != null) {
			    if (url.indexOf("?") == -1) {
			    	url = url + "?" + parmList;
			    }
			    else {
			    	url = url + "&" + parmList;
			    }
			}
		}

        // Assign state change handler
		xmlHttp.onreadystatechange = getStateChangeHandler;

	    // Prepare request for transmission
	    if (!isIE) {
	        // Mozilla security-permissions workaround for cross-domain server access
			try {
				netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead");
			}
			catch (e) {
				alert("Permission UniversalBrowserRead denied for Mozilla browser type");
		    }
	    }
		xmlHttp.open(config.method, url, config.asynchronous);
		 // Send the request.
		xmlHttp.send(null);
	}


	/**
	 * Function that creates a XMLHttpRequest object base on the browser type
	 */
	var createXMLHttpRequest = function () {
		var obj;

		// Create XMLHttpRequest object for non-Microsoft browsers
		if (window.XMLHttpRequest) {
			obj = new XMLHttpRequest();
			isIE = false;
		} 
		else {
			if (window.ActiveXObject) {
				try {
		      // Try to create XMLHttpRequest in later versions of Internet Explorer
					obj = new ActiveXObject(REQTYPE_MS_LATER);
					isIE = true;
				}
				catch (e1) {
		      // Failed to create required ActiveXObject
					try {
        		// Try version supported by older versions of Internet Explorer
						obj = new ActiveXObject(REQTYPE_MS_OLDER);
						isIE = true;
					}
					catch (e2) {
    	    	// Unable to create an XMLHttpRequest by any means
						throw "XMLHttpRequest Creatation Error: Unable to create an XMLHttpRequest";
					}
				}
			}
		}
		return obj;
	}


 /* Polls the server for the state of the Ajax call waiting for the
  * specified XMLHttpRequest to complete.  Once complet, the XML response
  * is gathered and passed to the given handler function, if available.
  * Otherwise, HTML is rendered to the client based on the XML results.
  * If a custom response handler is assigned to process the results,
  * it is required that its prototype conform to the following:
  *   function funcName(config, data) where config is of type RequestConfig
  * and data is the XML data returned by the Ajax call.
  */
	var getStateChangeHandler = function () {
       // If the request's status is "complete"
		if (xmlHttp.readyState == 4) {
	   		// Check that we received a successful response from the server
			if (xmlHttp.status == 200) {
				try {
					if (config.customResponseHandler) {
						// Pass the XML payload of the response to the caller via its custom handler function.
						config.customResponseHandler(xmlHttp.responseXML);
					} else {
						// Render HTML component
						if (config.renderHTML) {
							var renderer = new AjaxRenderer(config, xmlHttp.responseXML);
							renderer.renderHtml();
							return;
						}
						throw "State Change Handler could not decide on how to process the response";
					}
				}
				catch (e) {
					throw e;
				}
			} else {
		        // An HTTP problem has occurred
				throw "An error occurred after the request completed.  HTTP error status: " + xmlHttp.status + ", HTTP error message: " + xmlHttp.statusText;
			}
		}
	}
} // end function AjaxHandler

