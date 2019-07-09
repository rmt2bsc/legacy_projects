package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

import com.servlet.RMT2Servlet;

import com.util.SystemException;
import com.util.DatabaseException;
import com.util.NotFoundException;

import com.constants.RMT2SystemExceptionConst;
import com.constants.RMT2ServletConst;
import com.constants.RMT2SqlConst;

//import com.api.RMT2DebugSessionApi;

import com.bean.RMT2ExceptionBean;



public class RMT2GuiServlet extends RMT2Servlet {

  protected Object                           guiObj;
  protected ObjectOutputStream os;
  protected ObjectInputStream    is;

  /////////////////////////////////////
  //     Constructor
  /////////////////////////////////////
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.className = "RMT2GuiServlet";
    this.target = null;
  }

  public void initServlet() throws SystemException {

		     //  Set MIME type for objects encoded with an ObjectOutputStream.
		this.content = "application/x-java-serialized-object";
		    //  No need to display HTML as a client response
		this.displayHTML = false;
		    //  Do not need the services of the PrintWriter.
		this.out = null;

  }

  ////////////////////////////////////////////////////////////////////////////////////
  // Process GET/POST Re-directed request
  ////////////////////////////////////////////////////////////////////////////////////
  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    super.processRequest(request, response);
    this.methodName = "processRequest(HttpServletRequest, HttpServletResponse)";
    try {
			this.acceptData(request, response);
			this.sendData(request, response);
		}
		catch (SystemException e) {
			throw new ServletException(e.getMessage());
		}
	}

  ////////////////////////////////////////////////////////////////////////////////////
  // Accept data from the client.
  ////////////////////////////////////////////////////////////////////////////////////
	public void acceptData(HttpServletRequest request, HttpServletResponse response) throws SystemException {

		//  TODO: Implement at the descendent level in order to accept data.
		//               Be sure to invoke ancestor method to endure that the input stream
		//               is initialized.
		try {
		  this.is = new ObjectInputStream(request.getInputStream());
		  return;
		}
		catch (IOException e) {
			throw new SystemException(e.getMessage(), -2000);
		}
	}
  ////////////////////////////////////////////////////////////////////////////////////
  // Send data back to the Client
  ////////////////////////////////////////////////////////////////////////////////////
	public void sendData(HttpServletRequest request, HttpServletResponse response)  throws SystemException {

		//  TODO: Implement at the descendent level in order to semd data back to .
		//               the client.  Be sure to invoke ancestor method to endure that the
		//               output stream is initialized.
		try {
		  this.os = new ObjectOutputStream(response.getOutputStream());
		  return;
		}
		catch (IOException e) {
			throw new SystemException(e.getMessage(), -2100);
		}
	}
}  // End Class