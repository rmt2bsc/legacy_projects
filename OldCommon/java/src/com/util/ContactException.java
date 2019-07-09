package com.util;

import java.util.ArrayList;

public class ContactException extends RMT2Exception {

   public ContactException()   {
			 super();
   }

   public ContactException(String msg)   {
			 super(msg);
   }

   public ContactException(int code)   {
			 super(code);
   }

   public ContactException(String msg, int code)   {
			 super(msg, code);
   }

  public ContactException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public ContactException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public ContactException(Exception e) {
     super(e);
   }
}
