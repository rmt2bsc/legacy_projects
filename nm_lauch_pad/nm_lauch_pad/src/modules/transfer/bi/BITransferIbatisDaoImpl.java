package modules.transfer.bi;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import modules.model.Transfer;
import modules.model.TransferSkuItem;
import modules.transfer.TransferDao;

import org.apache.log4j.Logger;

import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;

/**
 * This is the iBatis implementation of {@link TransferDao} Buyer Initiated (BI)
 * Transfers.
 * 
 * @author rterrell
 *
 */
class BITransferIbatisDaoImpl extends AbstractIbatisDao implements TransferDao {

    private static final Logger logger = Logger
            .getLogger(BITransferIbatisDaoImpl.class);

    /**
     * Creates a BITransferIbatisDaoImpl object.
     */
    protected BITransferIbatisDaoImpl() {
        super();
    }

    /**
     * Create a BITransferIbatisDaoImpl object initialized with a SecurityToken.
     * 
     * @param token
     *            an instnace of {@link SecurityToken} which is the user's
     *            security token.
     */
    public BITransferIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("BI Transfer Administration DAO connection is initialized");
    }

    @Override
    public List<Transfer> fetchTransfers() throws DatabaseException {
        try {
            List<Transfer> list = this.con
                    .queryForList("BITransferAdmin.FetchHeader");
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all BI Transfers failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.neimanmarcus.sitransfer.TransferDao#fetchTransferItems(long)
     */
    @Override
    public List<TransferSkuItem> fetchTransferItems(long transferNo)
            throws DatabaseException {
        Transfer args = new Transfer();
        args.setTranferId(transferNo);
        try {
            List<TransferSkuItem> list = this.con.queryForList(
                    "BITransferAdmin.FetchDetail", args);
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all BI Transfers SKU Items for transfer number, "
                            + transferNo + ", failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.neimanmarcus.transfer.si.TransferDao#updateStatus(int,
     * java.lang.String)
     */
    @Override
    public int updateStatus(Transfer item, String newStatus)
            throws DatabaseException {
        item.setStatus(newStatus);
        item.setCurrDate(new Date());
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.update("BITransferAdmin.UpdateHeaderStatus", item);
            rows += this.con.update("BITransferAdmin.UpdateDetailStatus", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred changing the status of the transfer header and detail records by Transfer No. ["
                    + item.getTranferId() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while changing the status of the transfer header and detail records by Transfer No. ["
                        + item.getTranferId() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.transfer.si.TransferDao#updateTransfer(com.neimanmarcus
     * .model.Transfer)
     */
    @Override
    public int updateTransfer(Transfer item) throws DatabaseException {
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.update("BITransferAdmin.UpdateHeader", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred updating the header for Transfer No. ["
                    + item.getTranferId() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while updating the transfer header record by Transfer No. ["
                        + item.getTranferId() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.transfer.TransferDao#updateTransferItemQty(long,
     * java.lang.String, long, long, int)
     */
    @Override
    public int updateTransferItemQty(long transferNo, String transferStatus,
            long lowerSku, long overSku, int qty) throws DatabaseException {
        TransferSkuItem item = new TransferSkuItem();
        item.setTranferId(transferNo);
        item.setSku(lowerSku);
        item.setOverFlowSku(overSku);
        item.setTransferStatus(transferStatus);
        item.setQty(qty);
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.update("BITransferAdmin.UpdateTransferItem", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred updating Transfer quantity sent for SKU item ["
                    + item.getsSku() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while updating the quantity sent for SKU item ["
                        + item.getsSku() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

}
