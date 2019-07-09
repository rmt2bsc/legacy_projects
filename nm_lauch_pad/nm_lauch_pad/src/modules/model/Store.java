package modules.model;

/**
 * The model class for Store entity.
 * <p>
 * The <i>id</i> property is synonomus to database column, store_no, belonging
 * to the store table.
 * 
 * @author rterrell
 *
 */
public class Store extends CommonModel {

    private String homeStore;

    private String addr1;

    private String addr2;

    private String city;

    private String state;

    private String zip;

    /**
     * Default constructor
     */
    public Store() {
        super();
        return;
    }

    /**
     * @return the homeStore
     */
    public String getHomeStore() {
        return homeStore;
    }

    /**
     * @param homeStore
     *            the homeStore to set
     */
    public void setHomeStore(String homeStore) {
        this.homeStore = homeStore;
    }

    /**
     * @return the addr1
     */
    public String getAddr1() {
        return addr1;
    }

    /**
     * @param addr1
     *            the addr1 to set
     */
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    /**
     * @return the addr2
     */
    public String getAddr2() {
        return addr2;
    }

    /**
     * @param addr2
     *            the addr2 to set
     */
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

}
