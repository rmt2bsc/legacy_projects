package com.util;
   
import java.util.ArrayList;

public class XactException extends RMT2Exception {

  public XactException() {
    super();
  } 

  public XactException(String msg) {
    super(msg);
  }

  public XactException(int code) {
    super(code);
  }

  public XactException(String msg, int code) {
    super(msg, code);
  }

  public XactException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public XactException(String msg, int code, String objname, String methodname) {
    super(msg, code, objname, methodname);
  }

  public XactException(Exception e) {
    super(e);
  }
}

