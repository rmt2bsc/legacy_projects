package modules;

/**
 * Value object for managing the details of a store list item.
 * 
 * @author rterrell
 *
 */
public class StoreListItem {
    private String server;

    private String storeNo;

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server
     *            the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the storeNo
     */
    public String getStoreNo() {
        return storeNo;
    }

    /**
     * @param storeNo
     *            the storeNo to set
     */
    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

}
