
package com.util;

import java.util.ArrayList;


public class CashDisbursementsException extends RMT2Exception {

  public CashDisbursementsException() {
    super();
  }

  public CashDisbursementsException(String msg) {
    super(msg);
  }

  public CashDisbursementsException(int code) {
    super(code);
  }

  public CashDisbursementsException(String msg, int code) {
    super(msg, code);
  }

  public CashDisbursementsException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public CashDisbursementsException(String msg, int code, String objname, String methodname) {
     super(msg, code, objname, methodname);
  }

  public CashDisbursementsException(Exception e) {
     super(e);
  }
}

