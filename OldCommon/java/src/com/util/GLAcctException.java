package com.util;

import java.util.ArrayList;

public class GLAcctException extends RMT2Exception {

   public GLAcctException()   {
			 super();
   }

   public GLAcctException(String msg)   {
			 super(msg);
   }

   public GLAcctException(int code)   {
			 super(code);
   }

   public GLAcctException(String msg, int code)   {
			 super(msg, code);
   }

  public GLAcctException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public GLAcctException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public GLAcctException(Exception e) {
     super(e);
   }
}
