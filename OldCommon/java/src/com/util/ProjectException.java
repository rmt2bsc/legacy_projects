
package com.util;

import java.util.ArrayList;

public class ProjectException extends RMT2Exception {

  public ProjectException() {
    super();
  }

  public ProjectException(String msg) {
    super(msg);
  }

  public ProjectException(int code) {
    super(code);
  }

  public ProjectException(String msg, int code) {
    super(msg, code);
  }

  public ProjectException(Object _con, int _code, ArrayList _args) {
			super(_con, _code, _args);
	}

  public ProjectException(String msg, int code, String objname, String methodname) {
    super(msg, code, objname, methodname);
  }

  public ProjectException(Exception e) {
    super(e);
  }
}

