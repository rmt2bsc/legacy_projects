
package com.util;

import java.util.ArrayList;


public class PurchaseOrderException extends RMT2Exception {

  public PurchaseOrderException() {
    super();
  }

  public PurchaseOrderException(String msg) {
    super(msg);
  }

  public PurchaseOrderException(int code) {
    super(code);
  }

  public PurchaseOrderException(String msg, int code) {
    super(msg, code);
  }

  public PurchaseOrderException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public PurchaseOrderException(String msg, int code, String objname, String methodname) {
     super(msg, code, objname, methodname);
  }

  public PurchaseOrderException(Exception e) {
     super(e);
  }
}

