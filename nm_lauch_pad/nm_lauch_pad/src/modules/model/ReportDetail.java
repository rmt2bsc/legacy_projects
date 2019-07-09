package modules.model;

/**
 * Model class representing the report_dtl table.
 * 
 * @author rterrell
 *
 */
public class ReportDetail extends ReportHeader {

    private String rptTag;

    private String rptDesc;

    private String rptProg;

    private String rptOrientation;

    /**
     * Create a ReportDetail object.
     */
    public ReportDetail() {
        super();
    }

    /**
     * Return the report tag.
     * 
     * @return the rptTag
     */
    public String getRptTag() {
        return rptTag;
    }

    /**
     * Set the report tag.
     * 
     * @param rptTag
     *            the rptTag to set
     */
    public void setRptTag(String rptTag) {
        this.rptTag = rptTag;
    }

    /**
     * Return the report tag description.
     * 
     * @return the rptDesc
     */
    public String getRptDesc() {
        return rptDesc;
    }

    /**
     * Set the report tag description.
     * 
     * @param rptDesc
     *            the rptDesc to set
     */
    public void setRptDesc(String rptDesc) {
        this.rptDesc = rptDesc;
    }

    /**
     * Return the executing report program name.
     * 
     * @return the rptProg
     */
    public String getRptProg() {
        return rptProg;
    }

    /**
     * Set the executing report program name.
     * 
     * @param rptProg
     *            the rptProg to set
     */
    public void setRptProg(String rptProg) {
        this.rptProg = rptProg;
    }

    /**
     * Return the orientation of the report layout.
     * 
     * @return the rptOrientation
     */
    public String getRptOrientation() {
        return rptOrientation;
    }

    /**
     * Set the orientation of the report layout.
     * 
     * @param rptOrientation
     *            the rptOrientation to set
     */
    public void setRptOrientation(String rptOrientation) {
        this.rptOrientation = rptOrientation;
    }

}
