package com.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.Hashtable;

import com.util.SystemException;
import com.util.NotFoundException;
import com.util.BusinessException;

import com.constants.RMT2ServletConst;

import com.api.DataProviderConnectionApi;
import com.api.db.DatabaseException;
import com.api.db.DbSqlConst;

import com.bean.db.DatabaseConnectionBean;

import com.action.CodeStatesAction;

import com.servlet.AbstractServlet;
import java.io.*
;
public class TestServlet extends AbstractBaseServlet {
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void initServlet() throws SystemException {
    return;
  }

  ////////////////////////////////////////////
  // Process GET/POST Re-directed request
  ////////////////////////////////////////////
  public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO: Test action and respond appropriately
    try {
        
        String fn = request.getParameter("firstname");
        String ln = request.getParameter("lastname");
//        DataInputStream inStream = new DataInputStream(request.getInputStream());
//        String inputLine;
//        while ((inputLine = inStream.readLine()) != null) {
//            System.out.println(inputLine);
//        }
//        inStream.close();
        
        
        DataOutputStream outStream = new DataOutputStream(response.getOutputStream());
        outStream.writeBytes("This is the response: " + ln + ", " + fn);
        outStream.flush();
        outStream.close();
    }
    catch (Exception e) {
      throw new ServletException(e.getMessage());
    }
  }


}  // End Class