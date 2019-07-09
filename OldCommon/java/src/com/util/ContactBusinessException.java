package com.util;

import java.util.ArrayList;

import com.util.ContactException;



public class ContactBusinessException extends ContactException {

   public ContactBusinessException()   {
			 super();
   }

   public ContactBusinessException(String msg)   {
			 super(msg);
   }

   public ContactBusinessException(int code)   {
			 super(code);
   }

   public ContactBusinessException(String msg, int code)   {
			 super(msg, code);
   }

  public ContactBusinessException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public ContactBusinessException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public ContactBusinessException(Exception e) {
     super(e);
   }
}
