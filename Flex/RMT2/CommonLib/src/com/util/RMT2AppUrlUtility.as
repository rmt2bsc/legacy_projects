package com.util {
	import mx.resources.IResourceManager;
	import mx.utils.StringUtil;
	
	
	/**
	 * Builder class for constructing URL's from ResourceBundles. 
	 */
	public class RMT2AppUrlUtility	{

		public static const CONFIG_COMMON : String = "CommonConfig";
		
		private var _rootUrl : String;
		
		private var _contextUrl : String;
		
		private var _fullUrl : String;
		
				
		public function RMT2AppUrlUtility()	{
			return;
		}

		 
		/**
		 * This method parses a URL for the host name and the root context.  If the URL cannot be parsed, then host 
		 * name will equal "http://localhost".
		 *  
		 * @parm url
		 *             The URL String to parse.
		 * @return Boolean
		 *             Returns true if <i>url</i> conforms to the HTTP protocol and false for any other protocol type.
		 * @throws Error
		 *             When <i>url</i> is null or equal to spaces.
		 */
		public function getHostInfo(url : String) : Boolean {
			var msg : String = null;
			var validURL : Boolean = (url != null && !RMT2Utility.isAllSpaces(url)); 
			if (!validURL) {
				msg = "Server host information is unobtainable due to the input URL is invalid or null"; 
				throw new Error(msg);
			} 
			
			// Determine Host name
			var httpUrlType : Boolean = false;
			var hostName : String = null;
			var ctxRoot : String = null;
		    var pattern1 : RegExp = new RegExp("http://[^/]*/");
		    if (pattern1.test(url) == true) {
		       hostName = pattern1.exec(url).toString();
		       
		       // Get Context root name
		       var tempUrl : String = url.substr(7, url.length);
			   var tempUrlArr : Array = tempUrl.split("/");
  			    ctxRoot = tempUrlArr[1];
  			    httpUrlType = true;
		    } 
		    else {
		    	// URL is something other than HTTP protocol...default host name to localhost.
		       hostName = "http://localhost/"
		       httpUrlType = false;
		    }
		    
		    // Allow results to be available to the caller.   
		    this._fullUrl = url;
		    this._rootUrl = hostName;
		    this._contextUrl = ctxRoot;
		    
		    return httpUrlType;
		}
		
		
		
		public function buildUrl(r : IResourceManager, appContextName : String, urlKey : String, urlBundle : String = RMT2AppUrlUtility.CONFIG_COMMON) : String {
			if (r == null) {
				throw new Error("Unable to construct URL from application configuration due to the ResourceBundle instance is invalid or null");
			}
			if (urlKey == null || urlKey == "") {
				throw new Error("Unable to construct URL from application configuration due to the required URL key does not have a value");				
			}
			
			// Gather pertinent data from common configuration
			var protocol : String = r.getString(RMT2AppUrlUtility.CONFIG_COMMON, "protocol");
			var hostName : String = r.getString(RMT2AppUrlUtility.CONFIG_COMMON, "hostName");
			var hostPort : int = r.getInt(RMT2AppUrlUtility.CONFIG_COMMON, "hostPort");
			
			// Build various URL's
			this._contextUrl = "/" + appContextName + r.getString(urlBundle, urlKey);
			this._rootUrl = protocol + "://" + hostName + ":" + hostPort;
			this._fullUrl = (this._rootUrl == null || this._rootUrl == "" ? null : this._rootUrl + this._contextUrl);	
			return this._fullUrl;
		}
		
		public function get rootUrl() : String {
			return this._rootUrl;
		}
		
		public function get contextUrl() : String {
			return this._contextUrl;
		}
		
		public function get fullUrl() : String {
			return this._fullUrl;
		}
		
	}
}