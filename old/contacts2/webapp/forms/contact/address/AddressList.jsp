<td> 
	<%! String addr = "";%>
	<%! String addr2 = "";%>
	<%! String temp = null;%>
	<%! String brk = null;%>
	<%! String phoneNums = "";%>
	<% temp = address.getColumnValue("Addr1");
		 if (temp != null && !temp.equals("")) {
		   addr = temp;
		 }
		 temp = address.getColumnValue("Addr2");
		 if (temp != null && !temp.equals("")) {
		   addr += "<br>" + temp;
		 }
		 temp = address.getColumnValue("Addr3");
		 if (temp != null && !temp.equals("")) {
		   addr += "<br>" + temp;
		 }
		 temp = address.getColumnValue("Addr4");
		 if (temp != null && !temp.equals("")) {
		   addr += "<br>" + temp;
		 }
		 temp = address.getColumnValue("ZipCity");
		 if (temp != null && !temp.equals("")) {
		   addr2 =  temp;
		 }
		 temp = address.getColumnValue("ZipState");
		 if (temp != null && !temp.equals("")) {
		   addr2 += ", " + temp;
		 }
		 
		 // Only include zip if previous address components exist
		 if (!addr2.equals("")) {
		 		 temp = address.getColumnValue("AddrZip");
		 		 if (temp != null && !temp.equals("")) {
		   		  addr2 += " " + temp;
		 		 }
		     temp = address.getColumnValue("AddrZipext");
		     if (temp != null && !temp.equals("")) {
		        addr2 +=  "-" + temp;
		     }
		 }
	
		 if (addr.equals("") && addr2.equals("")) {
		   addr = "Not Available";
		 }
		 else if (addr.equals("") && !addr2.equals("")) {
		   addr = addr2;
		 }
		 else if (!addr.equals("") && !addr2.equals("")) {
		   addr += "<br>" + addr2;
		 }
	 %>
	<div align="left">
	<%out.print(addr);%>
	<% temp = null;
	   addr = "";
	   addr2 = "";
	%>
  </div>
</td>

<td valign="top"> 
    <% 
	 temp = address.getColumnValue("AddrPhoneHome");
	  if (temp != null && !temp.equals("")) {
		phoneNums = "Home - " + temp;
	  }
	  temp = address.getColumnValue("AddrPhoneWork");
	  if (temp != null && !temp.equals("")) {
		brk = (phoneNums.length() > 0 ? "<br>" : "");
		phoneNums += brk + "Work - " + temp;
	  }
	  temp = address.getColumnValue("AddrPhoneExt");
	  if (temp != null && !temp.equals("")) {
		brk = (phoneNums.length() > 0 ? "<br>" : "");
		phoneNums += brk + "Work Ext. - " + temp;
	  }
	  temp = address.getColumnValue("AddrPhoneFax");
	  if (temp != null && !temp.equals("")) {
		brk = (phoneNums.length() > 0 ? "<br>" : "");
		phoneNums += brk + "Fax - " + temp;
	  }
	  temp = address.getColumnValue("AddrPhonePager");
	  if (temp != null && !temp.equals("")) {
		brk = (phoneNums.length() > 0 ? "<br>" : "");
		phoneNums += brk + "Pager - " + temp;
	  }
	  temp = address.getColumnValue("AddrPhoneCell");
	  if (temp != null && !temp.equals("")) {
		brk = (phoneNums.length() > 0 ? "<br>" : "");
		phoneNums += brk + "Cellular - " + temp;
	  }
	  temp = address.getColumnValue("AddrPhoneMain");
	  if (temp != null && !temp.equals("")) {
		brk = (phoneNums.length() > 0 ? "<br>" : "");
		phoneNums += brk + "Main - " + temp;
	  }
	  if (phoneNums == null || phoneNums.equals("")) {
		out.println("Not Available");
	  }
	  else {
		out.println(phoneNums);
	  }
	  phoneNums = "";
   %>
 </td>