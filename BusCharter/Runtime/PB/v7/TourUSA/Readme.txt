*********************************************************************
Release Notes for Greens Bus Charter Information System Version 1.0
(c) 2001-2002 RMT2 Business Systems Corp. All rights reserved.
Updated 03/17/02
*********************************************************************

This file contains information about Greens Bus Charter Information 
System Version 1.0 Build 51.

Section I:   General Information
Section II:  Installation Information
Section III: Backup and Restore Suggestions

==============================
Section I: General Information
==============================

Using Green's Bus Charter Information System Version 1.0
--------------------------------------------------------
Greens Bus Charter Information System Version 1.0 is a 
Client/Server based application designed and deployed to 
run on various Windows platforms including Windows 95, 98, 
NT (service pack @  minimum), Millenium, XP, and Windows 2000.  

Packaged with this version are the core application files 
and the runtime dynamic shared libraries necessary for 
successful operation.  The runtime files on the client 
and the server must be at the same build level. Using 
different build levels on client and server machines may 
result in unpredictable behavior or data corruption.

By applying this release, you agree to upgrade and apply the next
Generally Available public release.



====================================
Section II: Installation Information
====================================
Before installing Green's Bus Charter Information System Version 1.0
build 51, as a prerequisite Sybase SQL Anywhere 5.0 must be installed which
serves as the database management system that will house the application's
data.

Before you install this release, shut down any applications running 
on your system. Reboot your system after all the installations are 
complete. 

Installing Sybase SQL Anywhere
-------------------------------
1.  Insert installation CD into tray.

2.  Start Microsoft's File Explorer.  Target the drive of the CD and locate
    the file, "Setup.exe", under the SQLANY5504 folder and double click
    that file.

3.  Once the welcome screen is displayed, press the NEXT button.
   
4.  On the User Information Screen, fill in the Name and Company fields
    if they appear as blanks.   Press the NEXT button.

5.  Accept the default location on the Destination Location Screen and 
    press the NEXT Button.
   
6.  On the Setup Type Screen select "Standalone Engine" and press the NEXT
    button.
  
7.  On the Select Components Screen select the SQLAnyWhere option and deselect
    the options: Sybase Central and Open Client.   Press the NEXT Button.
    
8.  Press the NEXT button and wait until the file copy process is completed.

9.  Press the FINISH button.



Installing Green's Bus Charter Information System
--------------------------------------------------
1.  Locate the file, "Setup.exe", under the Greens folder.

2.  Once the Welcome Screen appears, press the NEXT button.

3.  Read the license agreement and press the "Yes" button to accept or
    press the "No" button to decline the license agreement.
    
4.  Accept the default destination where the software will be installed
    by pressing the NEXT button.
    
5.  Select "Typical" for the Setup Type and press the NEXT button.

6.  Press the NEXT button.

7.  Press the NEXT button. to start the file copying process.

8.  Press the FINISH button.

9.  For Winodws 95, 98, Millenium,and XP operating systems, select the 
    option, "Yes, I want to restart my computer now", and press the 
    FINISH button.   Once the computer is restarted, you can begin 
    using the software.
    
10. To run the application, click the Start button and navigate the menu as such:
    Programs->Green's Bus Charter Information System.




================================
Section III: Backup and Restore Suggestions
============================================

One of the main goals of this software is to create and maintain data pertinent to 
your business objectives and to use the data as tool to provide a window
to better manage your business' day-to-day activities at the touch of a button.
Offered in this section are simple suggestions on backing up your data.  The simplest way to
perform backups for this software is to create a compressed archive of the data files 
with WinZip Utility (inculded on the installation disk under the Utilities folder) and 
store the archive on a storage medium other than your local hard drive.  

The actual data files targeted for backup are:
   greenst.db
   greenst.log
   
To create a compressed archive of these files for backup 
-------------------------------------------------------
   1.  Using MS File Explorer, select the two files above under the folder, 
       C:\Program Files\RMT2 Business Systems Corp\Green's Bus Charter\data\.
       
   2.  Right click in the highlighted area and select the menu option, ADD TO ZIP.
   
   3.  Once the Winzip application is displayed, press the AGREE button.
   
   4.  The ADD dialog should be displayed.  Under the text, "Add to Archive", the 
       path where the data files are located should be displayed as:
       C:\Program Files\RMT2 Business Systems Corp\Green's Bus Charter\data\.
       Assign a name to the archive by appending a filename of your choice to the above
       path.   One suggestion is to create a descriptive filename in the format:
          <system name><date>_<time>.zip.
          
       An example would be GreensDB03182002_124702.zip  where GreensDB represents the 
       systen name,  03182002 represents the date the backup was made, and 124702 
       represents the time the backup was made.

   5.  Under the text, "Compression", select the Maximum (slowest) option.  Tiis will use the
       highest compression ratio, yielding much smaller files.
       
   6.  Click the ADD button to start the compression.
   
   7.  When the compression is complete, the two files should be listed in the WinZip window.
       The Archive can be found under the directory, 
       C:\Program Files\RMT2 Business Systems Corp\Green's Bus Charter\data\  as the filename
       you assigned in step 4.

   8.  Copy the archive to another storage medium other than you local hard drive such as
       a floppy disk, zip drive, CD-R, CD-RW, or Tape.
   
   
To decompress an archive for restoration of data
--------------------------------------------------
   1.  Locate the compressed archive and double click the archive.  
   
   2.  Press the AGREE button.  This should invoke the  WinZip utility displaying the 
       compressed data files.
       
   3.  Select the files, greenst.db and greenst.log.
   
   4.  Press the Extract toolbar button which will display the Extract dialog.
   
   5.  Under the text, "Folder/Drives:", select the target directory where the files should 
       be restored.   The directory should be: 
          C:\Program Files\RMT2 Business Systems Corp\Green's Bus Charter\data\.
          
   6.  Press the EXTRACT button to begin the decompression and restoration process.
   
   7.  The data files should be restored to the backup you selected.
       



**********************************************************************
(c) 2001-2002 RMT2 Business Systems Corp. All rights reserved. 
RMT2 Business Systems Corp. claim copyright in this 
Program and documentation as an unpublished work, versions of which 
were first licensed on the date indicated in the foregoing notice. 
Claim of copyright does not imply waiver of RMT2 Business Systems Corp's 
other rights.   See Notice of Proprietary Rights.

NOTICE OF PROPRIETARY RIGHTS

This computer program and documentation are confidential trade 
secrets and the property of RMT2 Business Systems Corp. 
Use, examination, reproduction, copying, disassembly, decompilation, 
transfer and/or disclosure to others, in whole or in part, are 
strictly prohibited except with the express prior written consent of 
RMT2 Business Systems Corp.
