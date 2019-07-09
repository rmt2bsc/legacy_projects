/**
  * This object contains and manages the configuration information that 
  * is required by the AjaxBaseApi. 
  */
function RequestConfig() {
	 // The method used to submit request (POST or GET).  Get is the default choice
	 //  since most request will be fetching data from the server.
	this.method = "GET";
	 // Boolean indicating whether the request is meant to be sent asynchronously.  By default it is true.
	this.asynchronous = true;
	 // The id of the service
	this.serviceId = null; 
	 // The URL of the resource that is to be called.
	this.resourceURL = null;
	 // A pointer to a custom function that is to create and manage URL parameters.
	this.customParmHandler = null;
	 // The pointer to a custom function that is to process the XML response of the Ajax call.
	this.customResponseHandler = null;
	 // Instructs whether the API should render results as HTML.  When true, HTML
	 // is rendered.  Otherwise, results are returned as is, usually XML.
	 // Set to true by default.
	this.renderHTML = true;
	 // The HTML component that is to be rendered.
	this.component = null;
	 // Boolean indicating whether the rendered component is enclosed in a table.
	this.groupComponent = false;
	 // Boolean indicating if the enclosing table of the rendered component contains a border.
	this.groupBorder = false;
	 // Indicates the number of rows required to build enclosing table for rendered component
	this.groupRows = 0;
	 // Indicates the number of columns required to build enclosing table for rendered component	 
	this.groupCols = 0;
	 // The id of the XML element containing the code value for a key/value pair.
	 // This is usually used for select, radio buttons or checkbox input controls
	this.codeValueId;
	 // The id of the XML element containt the code value for a key/value pair.
	 // This is usually used for select, radio buttons or checkbox input controls
	this.displayValueId;
}
