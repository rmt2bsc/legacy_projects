package com.util;

import java.util.ArrayList;

public class CreditorException extends RMT2Exception {

   public CreditorException()   {
			 super();
   }

   public CreditorException(String msg)   {
			 super(msg);
   }

   public CreditorException(int code)   {
			 super(code);
   }

   public CreditorException(String msg, int code)   {
			 super(msg, code);
   }

  public CreditorException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public CreditorException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public CreditorException(Exception e) {
     super(e);
   }
}
