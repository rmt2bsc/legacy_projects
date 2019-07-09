package modules.rtv;

import java.util.List;

import modules.model.RtvHeader;
import modules.model.RtvItem;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing RTV data from some external data source.
 * 
 * @author rterrell
 *
 */
public interface RtvDao extends CommonDao {

    /**
     * Fetch RTV header records from the database.
     * 
     * @return List of {@link modules.model.RtvHeader} objects
     * @throws DatabaseException
     */
    List<RtvHeader> fetchHeader() throws DatabaseException;

    /**
     * Fetch all SKU items that are related to a RTV number.
     * 
     * @param rtvNo
     *            the RTV number
     * @return a List of {@link RtvItem} objects
     * @throws DatabaseException
     */
    List<RtvItem> fetchItems(long rtvNo) throws DatabaseException;

    /**
     * Updates the status of a RTV.
     * <p>
     * The change will effect only the header record.
     * 
     * @param rtvNo
     *            the RTV number.
     * @param newStatus
     *            the status indicator to change the RTV record.
     * @return the total number of rows efffected.
     * @throws DatabaseException
     */
    int updateStatus(long rtvNo, String newStatus) throws DatabaseException;

    /**
     * Updates the quantity sent for a transfer's sku item.
     * 
     * @param item
     *            the RTV SKU Item to change
     * @param qty
     *            the quantity value to update
     * @return he total number of rows effected
     * @throws DatabaseException
     */
    int updateSkuITem(RtvItem item, int qty) throws DatabaseException;

    /**
     * Sums the quantity of a RTV Header record.
     * 
     * @param rtvNo
     *            the RTV number
     * @return summed quantity for RTV
     * @throws DatabaseException
     */
    int fetchRtvNoQtySum(long rtvNo) throws DatabaseException;

}
