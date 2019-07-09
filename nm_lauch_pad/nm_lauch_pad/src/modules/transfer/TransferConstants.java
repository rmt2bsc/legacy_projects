package modules.transfer;

/**
 * Constants for Tranfer modules.
 * 
 * @author rterrell
 *
 */
public class TransferConstants {

    /*
     * SI Transfer Type Id
     */
    public static final int XFER_TYPE_SI = 100;

    /**
     * BI Transfer Type Id.
     */
    public static final int XFER_TYPE_BI = 101;

    /**
     * Transfer status code for Available.
     */
    public static final String STATUS_AVAILABLE = "A";

    /**
     * Transfer status code for Finished.
     */
    public static final String STATUS_FINISH = "F";

    /**
     * Transfer status code for Closed.
     */
    public static final String STATUS_COLSED = "C";

    /**
     * Transfer status code for Detail Received.
     */
    public static final String STATUS_DETAIL_RECV = "B";

    /**
     * Transfer status code for Summary Received.
     */
    public static final String STATUS_SUMMARY_RECV = "R";

    /**
     * Create a TransferConstants object
     */
    public TransferConstants() {
        return;
    }

}
