# Legacy RMT2 Projects
This repository contains all of the legacy RMT2 applications such as accounting, contacts, authentication, multimedia, and projecttracker.

## Deploying existing .war files for legacy RMT2 web applications

Despite archiving all of the legacy RMT2 application source files in GitHub, these applications have not been maintained in a very long time and are difficult to build at this time.    In the event one or both the servers, which host these applications, crashes and renders the hard drive unusable to where the data and applications are irrecoverable, it is best to install the applications via their existing war deployments.

The war files of the legacy RMT2 web applications can be found in my dropbox location at: 
 **Dropbox\MyProjects\java\rmt2\apps\legacy**.  There are two (2)  compressed .rar files containing these applications and are identified as: 
* *legacy_webapps_deployments.rar* - accounting, project tracker, multimedia, authentication, and contacts applications.  Also includes the *_server.xml_* used by Tomcat to create virtual hosts.   *Requires Tomcat 5.5*.
* *legacy_document_imaging_webapps.rar* - multimedia deployment for attaching and managing documents to accounting and projecttracker transactions.  This installation does not use virtual hosts and *requires Tomcat 7.0*.  In the past, this web application resided on the database server.

  
