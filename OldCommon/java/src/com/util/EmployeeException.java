package com.util;

import java.util.ArrayList;

public class EmployeeException extends RMT2Exception {
    
    private static final long serialVersionUID = 2716708141533086599L;

    public EmployeeException()   {
        super();
    }

    public EmployeeException(String msg)   {
        super(msg);
    }

    public EmployeeException(int code)   {
        super(code);
    }

    public EmployeeException(String msg, int code)   {
        super(msg, code);
    }

    public EmployeeException(Object _con, int _code, ArrayList _args) {
        super(_con, _code, _args);
	    }

    public EmployeeException(String msg, int code, String objectname, String methodname)   {
        super(msg, code, objectname, methodname);
    }

    public EmployeeException(Exception e) {
        super(e);
    }
}
