package modules.priceaudit;

import java.util.List;

import modules.model.PriceAuditSkuItem;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing Price Audit related data from some external data
 * source.
 * 
 * @author rterrell
 *
 */
public interface PriceAuditDao extends CommonDao {

    /**
     * Fetches all active price audit records
     * 
     * @return a List of {@link PriceAuditSkuItem} objects
     * @throws DatabaseException
     */
    List<PriceAuditSkuItem> fetchAllActive() throws DatabaseException;

    /**
     * Updates a price audit item from the database.
     * 
     * @param item
     *            an instance of {@link PriceAuditSkuItem} containing the
     *            parameters used as selection criteria.
     * @return the total number of records effected.
     * @throws DatabaseException
     */
    int updateItem(PriceAuditSkuItem item) throws DatabaseException;

    /**
     * Deletes a price audit item from the database.
     * 
     * @param item
     *            an instance of {@link PriceAuditSkuItem} containing the
     *            parameters used as selection criteria.
     * @return the total number of records effected.
     * @throws DatabaseException
     */
    int deleteItem(PriceAuditSkuItem item) throws DatabaseException;

}
