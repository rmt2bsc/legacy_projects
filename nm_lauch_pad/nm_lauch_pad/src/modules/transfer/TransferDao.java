package modules.transfer;

import java.util.List;

import modules.model.Transfer;
import modules.model.TransferSkuItem;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing Transfers from some external data source.
 * 
 * @author rterrell
 *
 */
public interface TransferDao extends CommonDao {

    /**
     * Fetch Transfer header records from the database.
     * 
     * @return List of {@link Transfer} objects
     * @throws DatabaseException
     */
    List<Transfer> fetchTransfers() throws DatabaseException;

    /**
     * Fetch all SKU items that are related to a transfer number.
     * 
     * @param transferNo
     *            the transfer number
     * @return a List of {@link TransferSkuItem} objects
     * @throws DatabaseException
     */
    List<TransferSkuItem> fetchTransferItems(long transferNo)
            throws DatabaseException;

    /**
     * Updates the status of a Transfer.
     * <p>
     * The change will effect both the header and detail records pertaining to
     * the transfer.
     * 
     * @param item
     *            a instance of {@link Transfer}
     * @param newStatus
     *            the status indicator to change the Transfer record.
     * @return the total number of rows efffected.
     * @throws DatabaseException
     */
    int updateStatus(Transfer item, String newStatus) throws DatabaseException;

    /**
     * Updates the transfer header record.
     * 
     * @param item
     *            an instance of {@link Transfer}
     * @return the total number of rows effected
     * @throws DatabaseException
     */
    int updateTransfer(Transfer item) throws DatabaseException;

    /**
     * Updates the quantity for a transfer's sku item.
     * 
     * @param transferNo
     *            the transfer number
     * @param transferStatus
     *            the status of the transfer header
     * @param lowerSku
     *            the lower portion of the SKU
     * @param overSku
     *            the over flow portion of the SKU
     * @param qty
     *            the quantity sent value
     * @return he total number of rows effected
     * @throws DatabaseException
     */
    int updateTransferItemQty(long transferNo, String transferStatus,
            long lowerSku, long overSku, int qty) throws DatabaseException;

}
