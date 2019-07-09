package modules.idt;

import java.util.List;

import modules.model.SkuItem;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing IDT data from some external data source.
 * 
 * @author rterrell
 *
 */
public interface IdtDao extends CommonDao {

    /**
     * Fetches a list of IDT reticketed items based on criteria contained in
     * <i>arg</i>.
     * 
     * @param arg
     *            An instance of {@link SkuItem} representing the selection
     *            criteria.
     * @return A List of {@link SkuItem} objects or null if criteria is not
     *         matched.
     * @throws DatabaseException
     */
    List<SkuItem> fetchReticketHeader(SkuItem arg) throws DatabaseException;

    /**
     * Fetches a single IDT sku record.
     * 
     * @param sku
     *            the sku number
     * @return an instance of {@link SkuItem} or null if the sku is not foud.
     * @throws DatabaseException
     */
    SkuItem fetchSku(long sku) throws DatabaseException;

    /**
     * Retruns an IDT sku number based on the selection criteria represented as
     * <i>arg</i>.
     * 
     * @param arg
     *            An instance of {@link SkuItem} representing the selection
     *            criteria.
     * @return The sku number found or null if sku is not found.
     * @throws DatabaseException
     */
    Long fetchShortSku(SkuItem arg) throws DatabaseException;

    /**
     * Updates the quantity of an IDT sku.
     * 
     * @param item
     *            an instance of {@link SkuItem} containg the sku selection
     *            criteria.
     * @param qty
     *            the quantity value to update
     * @return the total number of rows effected
     * @throws DatabaseException
     */
    int updateSkuQty(SkuItem item, int qty) throws DatabaseException;

    /**
     * Delete an IDT sku.
     * 
     * @param item
     *            An instance of {@link SkuItem} representing the selection
     *            criteria.
     * @return the total number of rows effected
     * @throws DatabaseException
     */
    int deleteSku(SkuItem item) throws DatabaseException;

}
