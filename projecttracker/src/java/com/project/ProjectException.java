
package com.project;


import com.util.RMT2Exception;

public class ProjectException extends RMT2Exception {
    private static final long serialVersionUID = -8851874846044566159L; 

public ProjectException() {
    super();
  }

  public ProjectException(String msg) {
    super(msg);
  }

  public ProjectException(Exception e) {
    super(e);
  }
  
  public ProjectException(String msg, Throwable e) {
      super(msg, e);
    }
}

