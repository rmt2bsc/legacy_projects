package modules.report;

/**
 * Class containing constants regarding the Reprint reporting module.
 * 
 * @author rterrell
 *
 */
public class ReportConstants {

    /**
     * Tag value for report received acknowlegement from the report server.
     */
    public static final String RPT_ACK_RECEIVED = "@@@Received";

    /**
     * Tag value for acknowledging the beginning of the report sent by the
     * report server.
     */
    public static final String RPT_ACK_START = "@@@StartOfReport";

    /**
     * Tag value for acknowledging the end of the report sent by the report
     * server.
     */
    public static final String RPT_ACK_END = "@@@EndOfReport";

    /**
     * Tag value for acknowledging that report server encounterd an error while
     * processing the report request.
     */
    public static final String RPT_ACK_ERROR = "@@@ErrorEncountered";

    /**
     * Code for portrait report orientation.
     */
    public static final String ORIENT_PORTRAIT = "P";

    /**
     * Code for landscape report orientation.
     */
    public static final String ORIENT_LANDSCAPE = "L";

    /**
     * Code value for Print or Display argument type.
     */
    public static final String ARGTYPE_PRINT_OR_DISPLAY = "PrintOrDisplay";

    /**
     * Code value for Print or PC Number argument type.
     */
    public static final String ARGTYPE_PC_NO = "PCNumber";

    /**
     * Code value for Print or Days Out argument type.
     */
    public static final String ARGTYPE_DAYSOUT = "DaysOut";

    /**
     * Code value for Print or Transfer Number argument type.
     */
    public static final String ARGTYPE_XFER_NO = "XferNumber";

    /**
     * Code value for Department argument type.
     */
    public static final String ARGTYPE_DEPT = "Dept";

    /**
     * Code value for Date argument type.
     */
    public static final String ARGTYPE_DATE = "Date";

    /**
     * Code value for RTV Number argument type.
     */
    public static final String ARGTYPE_RTV_NO = "RTVNumber";

    /**
     * Code value for Start Date argument type.
     */
    public static final String ARGTYPE_DATE_START = "StartDate";

    /**
     * Code value for End Date argument type.
     */
    public static final String ARGTYPE_DATE_END = "EndDate";

    /**
     * Description for Print or Display argument type.
     */
    public static final String ARGDESC_PRINT_OR_DISPLAY = "Print or Display";

    /**
     * Description for Print or PC Number argument type.
     */
    public static final String ARGDESC_PC_NO = "Price Change Number";

    /**
     * Description for Print or Days Out argument type.
     */
    public static final String ARGDESC_DAYSOUT = "Days Out";

    /**
     * Description for Print or Transfer Number argument type.
     */
    public static final String ARGDESC_XFER_NO = "Transfer Number";

    /**
     * Description for Department argument type.
     */
    public static final String ARGDESC_DEPT = "Department";

    /**
     * Description for Date argument type.
     */
    public static final String ARGDESC_DATE = "Date";

    /**
     * Description for RTV Number argument type.
     */
    public static final String ARGDESC_RTV_NO = "RTV Number";

    /**
     * Description for Start Date argument type.
     */
    public static final String ARGDESC_DATE_START = "Start Date";

    /**
     * Description for End Date argument type.
     */
    public static final String ARGDESC_DATE_END = "End Date";

}
