package com.util;

import java.util.ArrayList;

public class CustomerException extends RMT2Exception {

   public CustomerException()   {
			 super();
   }

   public CustomerException(String msg)   {
			 super(msg);
   }

   public CustomerException(int code)   {
			 super(code);
   }

   public CustomerException(String msg, int code)   {
			 super(msg, code);
   }

  public CustomerException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public CustomerException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public CustomerException(Exception e) {
     super(e);
   }
}
