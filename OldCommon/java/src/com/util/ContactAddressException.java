package com.util;

import com.util.ContactException;

import java.util.ArrayList;


public class ContactAddressException extends ContactException {

   public ContactAddressException()   {
			 super();
   }

   public ContactAddressException(String msg)   {
			 super(msg);
   }

   public ContactAddressException(int code)   {
			 super(code);
   }

   public ContactAddressException(String msg, int code)   {
			 super(msg, code);
   }

   public ContactAddressException(Object _con, int _code , ArrayList _args) {
			super(_con, _code, _args);
 	 }

   public ContactAddressException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public ContactAddressException(Exception e) {
     super(e);
   }
}
