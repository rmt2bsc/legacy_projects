package com.util;

import java.util.ArrayList;

public class ItemMasterException extends RMT2Exception {

   public ItemMasterException()   {
			 super();
   }

   public ItemMasterException(String msg)   {
			 super(msg);
   }

   public ItemMasterException(int code)   {
			 super(code);
   }

   public ItemMasterException(String msg, int code)   {
			 super(msg, code);
   }

  public ItemMasterException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

   public ItemMasterException(String msg, int code, String objectname, String methodname)   {
			 super(msg, code, objectname, methodname);
   }

   public ItemMasterException(Exception e) {
     super(e);
   }
}
