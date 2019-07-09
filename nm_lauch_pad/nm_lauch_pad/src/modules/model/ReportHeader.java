package modules.model;

/**
 * Model class representing the report_hdr table.
 * 
 * @author rterrell
 *
 */
public class ReportHeader extends CommonModel {

    private String catgTag;

    private String catgDesc;

    /**
     * Create a ReportHeader object.
     */
    public ReportHeader() {
        super();
    }

    /**
     * Return the report category tag name.
     * 
     * @return the catgTag
     */
    public String getCatgTag() {
        return catgTag;
    }

    /**
     * Set the report category tag name.
     * 
     * @param catgTag
     *            the catgTag to set
     */
    public void setCatgTag(String catgTag) {
        this.catgTag = catgTag;
    }

    /**
     * Return the report category description.
     * 
     * @return the catgDesc
     */
    public String getCatgDesc() {
        return catgDesc;
    }

    /**
     * Set the report category description.
     * 
     * @param catgDesc
     *            the catgDesc to set
     */
    public void setCatgDesc(String catgDesc) {
        this.catgDesc = catgDesc;
    }

}
