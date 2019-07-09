package com.employee;


import com.util.RMT2Exception;

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


    public EmployeeException(Exception e) {
        super(e);
    }
    
    public EmployeeException(String msg, Exception e) {
        super(msg, e);
    }
}
