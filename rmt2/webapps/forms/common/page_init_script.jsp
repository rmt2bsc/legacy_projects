<script>
  /* Flag used to prevent form from being submitted twice.  This can happen 
     when the Login button has focus and the enter key is pressed, which also 
     triggers the clicked event of Login button.
  */     
  var formSubmitted;
  
	function init() {
		  // Recognize form as yet to be submitted.
			formSubmitted = false;
		  document.onkeypress = processKey;
      selectPageMenuItem();
    }

	
  /**
   * Submits a form when the enter key is pressed.  In order for this function to work 
   * properly, the javascript function, submitForm(), must be implemented somewhere within 
   * the scope of the page containing the form that is to be submitted.  Next, the following
   * line of code must be placed in the action handler for onLoad event of the HTML body tag:
	 *    "document.onkeypress = processKey;"
   */
	function processKey(e) {
	  if (null == e) {
	    e = window.event ;
	  }
	  if (e.keyCode == 13)  {
	    submitForm() ;
	  }
	}
	    
</script>