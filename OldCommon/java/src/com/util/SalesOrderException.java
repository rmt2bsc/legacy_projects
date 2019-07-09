package com.util;

import java.util.ArrayList;

public class SalesOrderException extends RMT2Exception {

   public SalesOrderException()   {
			 super();
   }

   public SalesOrderException(String msg)   {
			 super(msg);
   }

   public SalesOrderException(int code)   {
			 super(code);
   }

   public SalesOrderException(String msg, int code)   {
			 super(msg, code);
   }

  public SalesOrderException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public SalesOrderException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public SalesOrderException(Exception e) {
     super(e);
   }
}
