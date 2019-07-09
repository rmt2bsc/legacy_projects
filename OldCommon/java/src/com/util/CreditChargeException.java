
package com.util;

import java.util.ArrayList;


public class CreditChargeException extends RMT2Exception {

  public CreditChargeException() {
    super();
  }

  public CreditChargeException(String msg) {
    super(msg);
  }

  public CreditChargeException(int code) {
    super(code);
  }

  public CreditChargeException(String msg, int code) {
    super(msg, code);
  }

  public CreditChargeException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public CreditChargeException(String msg, int code, String objname, String methodname) {
     super(msg, code, objname, methodname);
  }

  public CreditChargeException(Exception e) {
     super(e);
  }
}

