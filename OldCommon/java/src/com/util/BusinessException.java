
package com.util;

import java.util.ArrayList;


public class BusinessException extends RMT2Exception {

  public BusinessException() {
    super();
  }

  public BusinessException(String msg) {
    super(msg);
  }

  public BusinessException(int code) {
    super(code);
  }

  public BusinessException(String msg, int code) {
    super(msg, code);
  }

  public BusinessException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public BusinessException(String msg, int code, String objname, String methodname) {
     super(msg, code, objname, methodname);
  }

  public BusinessException(Exception e) {
     super(e);
  }
}

