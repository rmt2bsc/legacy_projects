<HTML>
<!--  This is a template  -->

<!--    RGB Color Values

Color	     Red value  Green value   Blue value      Hex Value
----------------------------------------------------------------
Black	         0	    0	         0              000000 
White	        255	   255	        255             FFFFFF
Light Gray	192	   192          192             BFBFBF
Dark Gray	128	   128	        128             7F7F7F
Red	        255	    0	         0              FF0000
Dark Red	128	    0	         0              7F0000
Green	         0	   255	         0              00FF00
Dark Green	 0	   128   	 0              007F00 
Blue	         0	    0	        255             0000FF
Dark Blue	 0	    0	        128             00007F 
Magenta	        255	    0           255             FF00FF
Dark Magenta	128	    0       	128             7F007F
Cyan	         0	   255	        255             00FFFF
Dark Cyan	 0	   128	        128             007F7F
Yellow	        255	   255	         0              FFFF00
Brown	        128	   128	         0              7F7F00
-->

<HEAD>
<TITLE>Customer Survey Form.</TITLE>
<BASE HREF="http://home1.gte.net/royroy/">
<style type="text/css">
  h3.blue {color:blue}
  h4.red {color:red}
</style>

<script>
   function GetData(form) {
       str = "First Name: " + form.firstname.value + "\n";
       str += "Last Name: " + form.lastname.value + "\n";
       str += "Address: " + form.address.value + "\n";
       str += "City: " + form.city.value + "\n";
       str += "State: " + form.state.value + "\n";
       str += "Zip Code: " + form.zip.value + "\n";
       str += "Phone Number: " + form.phone.value + "\n";
       str += this.GetSystem(form);
       str += this.GetOS(form);
       str += this.GetShopping(form.shop);
       alert(str);
       return 1;
   }
   
   function GetSystem(form) {
       if (form.pent.checked) 
          return "Computer System is Pentium\n";
       if (form.pent2.checked)
          return "Computer System is Pentium II\n";
       if (form.mmx.checked)
          return "Computer System is Multimedia Extension\n";
       if (form.mac.checked)
          return "Computer System is Macintosh\n";
       if (form.solaris.checked)
          return "Computer System is Solaris\n";
       
       return "Computer System is Unknown\n";
   }

   function GetOS(form) {
       if (form.win3x.checked)
           return "Operating System is Windows 3.X\n";
       if (form.win95.checked)
           return "Operating System is Windows 95\n";
       if (form.win98.checked)
           return "Operating System is Windows 98\n";
       if (form.winnt.checked)
           return "Operating System is Windows NT\n";
       if (form.os2.checked)
           return "Operating System is IBM OS/2\n";
       if (form.unix.checked)
           return "Operating System is UNIX\n";
       if (form.other.checked)
           return "Operating System is Unknown\n";

       return "";
   }

   
   function GetShopping(shop) {
      for (ndx = 0; ndx < 5; ndx++) {
          if (shop[ndx].checked) {
              return "Normally shops for computer products via " + shop[ndx].value + "\n";
          }
      }
   }     
</script>

</HEAD>

<body>
   <h3 class="blue"><b>Customer Survey</b></h3><br>
   
   Please fill out the following form, including your personal information, to help us better
   serve you.  None of the adderss or other information in these forms will be publicly
   distributed withou your permission.   Thank You!
   
   <form name="form1" method=post action="mailto:royroy@gte.net">
      <p>
      <h4 class="red"><b>Please enter contact information</b></h4>
      <pre>
  <b>First Name:</b><input type=text name="firstname" size=25 maxlength=25>
  <b> Last Name:</b><input type=text name="lastname" size=25 maxlength=25>
  <b>   Address:</b><input type=text name="address" size=40 maxlength=40>
  <b>      City:</b><input type=text name="city" size=25 maxlength=25>
  <b>     State:</b><input type=text name="state" size=2 maxlength=2>
  <b>  Zip Code:</b><input type=text name="zip" size=5 maxlength=5>
  <b>     Phone:</b><input type=text name="phone" size=10 maxlength=10>
      </pre></p>

      <p>
      <h4 class="red"><b>Please check the type of computer(s) owned</b></h4>
      <input type=checkbox name="pent"> Pentium
      <input type=checkbox name="pent2"> Pentium II
      <input type=checkbox name="mmx"> MMX
      <input type=checkbox name="mac"> Macintosh
      <input type=checkbox name="solaris"> Solaris<br></p>
      <p>
      <h4 class="red"><b>Please check the Operating Systems(s) used</b></h4>
      <input type=checkbox name="win3x"> Windows 3.x
      <input type=checkbox name="win95"> Windows 95
      <input type=checkbox name="win98"> Windows 98
      <input type=checkbox name="winnt"> Windows NT
      <input type=checkbox name="os2"> OS/2
      <input type=checkbox name="unix"> UNIX
      <input type=checkbox name="other"> Other<br></p> 
      <p>
      <h4 class="red"><b>What is your favorite way to shop for computer products? (Please check one)</b></h4>
      <input type=radio name="shop" value="Mail Order Catalog"> Mail Order Catalog
      <input type=radio name="shop" value="Local Computer Store" checked> Local Computer Store
      <input type=radio name="shop" value="Computer Super Store"> Computer Super Store
      <input type=radio name="shop" value="Auctions"> Auctions
      <input type=radio name="shop" value="Internet/ World Wide Web"> Internet/ World Wide Web<br></p>
      <p>
      <h4 class="red"><b>What are your comments?</b></h4>
      <textarea name="comments" wrap=virtual rows=3 cols=150></textarea><p/>
      <input type=reset value="Clear"> 
      <script>
         GetData(document.form1); 
      </script>
      <input type=button value="Confirm" onClick="GetData(this.form)">
  </form>
</body>

</HTML>
