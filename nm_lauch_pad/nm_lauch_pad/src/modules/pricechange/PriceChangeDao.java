package modules.pricechange;

import java.util.List;

import modules.model.PriceChange;
import modules.model.PriceChangeSkuItem;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing Price Change related data from some external data
 * source.
 * 
 * @author rterrell
 *
 */
public interface PriceChangeDao extends CommonDao {

    /**
     * Fetches all active price change records
     * 
     * @return a List of {@link PriceChange} objects
     * @throws DatabaseException
     */
    List<PriceChange> fetchAllActive() throws DatabaseException;

    /**
     * Fetches active price changes by a given SKU number.
     * <p>
     * Prior to fetching the data, the implementation will need to parse
     * <i>sku</i> into upper and lower parts. The lower half should be the
     * lowest 8 digits. The upper half should be everythin greater than the
     * lowers 8 digits, which is considered to be the overflow sku value. When
     * the <i>sku</i> is 8 digits or less, then the upper half is required to
     * equal "0".
     * 
     * @param sku
     *            a numeric String between 1 and 13 digits.
     * @return a List of {@link PriceChange} objects related to <i>sku</i>
     * @throws DatabaseException
     */
    List<PriceChange> fetchActiveBySku(String sku) throws DatabaseException;

    /**
     * Fetches the detail records pertaining to a given price change number.
     * 
     * @param item
     *            an instance of {@link PriceChange} where the <i>id</i>
     *            property contains the price change number used to fetch the
     *            detail records.
     * @return a List of {@link PriceChangeSkuItem} objects related to the price
     *         change number (<i>id</i> property) in <i>item</i>.
     * @throws DatabaseException
     */
    List<PriceChangeSkuItem> fetchItemDetails(PriceChange item)
            throws DatabaseException;

    /**
     * Updates the status of a price change.
     * <p>
     * The change will effect both the header and detail records pertaining to
     * the price change.
     * 
     * @param pcNo
     *            the price change number.
     * @param newStatus
     *            the status indicator to change the price change record to. For
     *            closting a price change enter "C". For reopening a price
     *            change enter "O".
     * @return the total number of rows efffected.
     * @throws DatabaseException
     */
    int updateStatus(int pcNo, String newStatus) throws DatabaseException;

    /**
     * Updates the quantity of the Price Change Detail Item based on price
     * change number, sku and over flow sku.
     * 
     * @param pcNo
     *            the price change number
     * @param sku
     *            the lower part of the sku
     * @param overFlowSku
     *            the upper part of the sku
     * @param qty
     *            the quantity amount to update the item with
     * @return the total numbe of rows effected.
     * @throws DatabaseException
     */
    int updateItemDetailQuantity(int pcNo, long sku, long overFlowSku, int qty)
            throws DatabaseException;

    /**
     * Updates the status of all line items belonging to a <i>pcNo</i> based on
     * a set of particular related sku's.
     * 
     * @param pcNo
     *            the price change number
     * @param newStatus
     *            the status indicator to change the price change line item
     *            record to. For closting a line item enter "C". For oopening a
     *            line item enter "O".
     * @param items
     *            a List of {@link PriceChangeSkuItem} objects containing the
     *            sku and over flow sku information used to target specific line
     *            items to apply the updates.
     * @return the total number of rows effected.
     * @throws DatabaseException
     */
    int updateItemDetailsBySku(int pcNo, String newStatus,
            List<PriceChangeSkuItem> items) throws DatabaseException;

}
