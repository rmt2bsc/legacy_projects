package com.util;

import com.util.ContactException;

import java.util.ArrayList;


public class ContactPersonException extends ContactException {

   public ContactPersonException()   {
			 super();
   }

   public ContactPersonException(String msg)   {
			 super(msg);
   }

   public ContactPersonException(int code)   {
			 super(code);
   }

   public ContactPersonException(String msg, int code)   {
			 super(msg, code);
   }

  public ContactPersonException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public ContactPersonException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public ContactPersonException(Exception e) {
     super(e);
   }
}
