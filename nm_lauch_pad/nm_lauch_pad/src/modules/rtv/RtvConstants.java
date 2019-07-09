package modules.rtv;

/**
 * Constants for Return To Vendor (RTV) modules.
 * 
 * @author rterrell
 *
 */
public class RtvConstants {

    /*
     * SI RTV Type Code
     */
    public static final String RTV_TYPE_SI = "S";

    /**
     * BI RTV Type Code.
     */
    public static final String RTV_TYPE_BI = "B";

    /**
     * RTV status code for Suspend.
     */
    public static final String STATUS_SUSPEND = "S";

    /**
     * RTV status code for Open.
     */
    public static final String STATUS_OPEN = "O";

    /**
     * RTV status code for Closed.
     */
    public static final String STATUS_CLOSED = "C";

    /**
     * RTV status code for In Process.
     */
    public static final String STATUS_INPROCESS = "I";

    /**
     * RTV status code for System Suspend.
     */
    public static final String STATUS_SYSTEMSUSPEND = "Z";

    /**
     * Create a RTVConstants object
     */
    public RtvConstants() {
        return;
    }

}
