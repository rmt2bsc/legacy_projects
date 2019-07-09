<html>
  <head>
    <title>Transaction Search Criteria</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">  
    <link rel=STYLESHEET type="text/css" href="/css/RMT2Table.css">
	<link rel=STYLESHEET type="text/css" href="/css/RMT2General.css">
  </head>
  
  <script Language="JavaScript" src="/js/RMT2General.js"></script>
  <script Language="JavaScript" src="/js/RMT2Menu.js"></script>
  
  <script Language="JavaScript">
     var xactCatgImgs = new Array();
	 
	 function handleAction(_action) {
	   SelectForm.clientAction = _action;
	   SelectForm.target = "EditFrame";
	   SelectForm.submit();
	  }
	  
     function initPage() {
	    loadImages();
	 }
	 
	 function loadImages() {
       xactCatgImgs[0] = document.img_cf_0;
	   xactCatgImgs[1] = document.img_cf_2;
	   xactCatgImgs[2] = document.img_cf_3;
	   xactCatgImgs[3] = document.img_cf_4;
	   xactCatgImgs[4] = document.img_cf_8;
	   focusImage(0);
	 }
	 
	 function focusImage(_img) {
	     for(ndx=0; ndx < xactCatgImgs.length; ndx++) {
		    xactCatgImgs[ndx].style.borderStyle = "none";
	     }
		 //  Get current transaction category id that is
		 curCatg = document.SearchForm.XactCategoryId.value;
		 obj = eval("document.img_cf_" + curCatg);
		 obj.style.borderStyle = "solid"
	 }
  </script>
    
  <body bgcolor="#FFFFFF" text="#000000" onLoad="initPage()">
       <form name="SelectForm" method="POST" action="/xactservlet">
	     <h3><strong>Select Cash Flow Transaction Type</strong></h3>
		 
         <div style="border-style:solid;border-color:#999999;width:30%">
         <table width="100%" bgcolor="#CCCCCC" cellpadding="1" cellspacing="1" border= "0">
	  		 <caption align="left" style="color:#0000FF ">
  	  		     <strong>Select  cash flow transaction type</strong>
	  		 </caption>	 
			 <tr>
				 <td valign="top">
					 <a href="javascript:handleAction()"> 
						<img name="img_cf_0" src="/images/menuitem_all_cashflow.gif" style="border:none" alt="Search All Transaction Types">
					 </a>       
				 </td>
				 <td valign="top">
					 <a href="javascript:handleAction()"> 
						<img name="img_cf_4" src="/images/menuitem_sales.gif" style="border:none" alt="Search Sales Transaction Type">
					 </a>       
				 </td>							
				 <td valign="top">
					 <a href="javascript:handleAction()">  
						<img name="img_cf_2" src="/images/menuitem_cashin.gif" style="border:none" alt="Search Cash Receipts Transaction Type">
					 </a>       
				 </td>														
				 <td valign="top">
					 <a href="javascript:handleAction()">  
						<img name="img_cf_3" src="/images/menuitem_cashout.gif" style="border:none" alt="Search Cash Disbursements Transaction Type">
					 </a>       
				 </td>																					 
				 <td valign="top">
					 <a href="javascript:handleAction()">  
						<img name="img_cf_8" src="/images/menuitem_purchases.gif" style="border:none" alt="Search Purchases Transaction Type">
					 </a>       
				 </td>																					 				   
			 </tr>
		     <tr>
			     <td><img src="/images/clr.gif" height="10"></td>
			 </tr>						 			 
		 </table>
	   </div>
	 <input name="clientAction" type="hidden">
   </form>
 </body>
</html>
