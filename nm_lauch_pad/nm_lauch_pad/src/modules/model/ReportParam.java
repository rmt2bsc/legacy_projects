package modules.model;

/**
 * Model class representing the report_param table.
 * 
 * @author rterrell
 *
 */
public class ReportParam extends ReportDetail {

    private int argSeqNo;

    private String argType;

    /**
     * Create a ReportParam object.
     */
    public ReportParam() {
        super();
    }

    /**
     * Return the argument sequence number.
     * 
     * @return the argSeqNo
     */
    public int getArgSeqNo() {
        return argSeqNo;
    }

    /**
     * Set the argument sequence number.
     * 
     * @param argSeqNo
     *            the argSeqNo to set
     */
    public void setArgSeqNo(int argSeqNo) {
        this.argSeqNo = argSeqNo;
    }

    /**
     * Return the argument type.
     * 
     * @return the argType
     */
    public String getArgType() {
        return argType;
    }

    /**
     * Set the argument type.
     * 
     * @param argType
     *            the argType to set
     */
    public void setArgType(String argType) {
        this.argType = argType;
    }

}
