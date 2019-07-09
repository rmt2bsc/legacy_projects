
package com.util;

import java.util.ArrayList;


public class CashReceiptsException extends RMT2Exception {

  public CashReceiptsException() {
    super();
  }

  public CashReceiptsException(String msg) {
    super(msg);
  }

  public CashReceiptsException(int code) {
    super(code);
  }

  public CashReceiptsException(String msg, int code) {
    super(msg, code);
  }

  public CashReceiptsException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public CashReceiptsException(String msg, int code, String objname, String methodname) {
     super(msg, code, objname, methodname);
  }

  public CashReceiptsException(Exception e) {
     super(e);
  }
}

