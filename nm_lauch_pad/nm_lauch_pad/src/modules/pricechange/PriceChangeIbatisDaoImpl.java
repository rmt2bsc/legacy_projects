/**
 * 
 */
package modules.pricechange;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import modules.model.PriceChange;
import modules.model.PriceChangeSkuItem;

import org.apache.log4j.Logger;

import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;
import com.nv.util.GeneralUtil;

/**
 * The iBatis DAO implementation of {@link PriceChangeDao}
 * 
 * @author rterrell
 *
 */
class PriceChangeIbatisDaoImpl extends AbstractIbatisDao implements
        PriceChangeDao {

    private static final Logger logger = Logger
            .getLogger(PriceChangeIbatisDaoImpl.class);

    /**
     * Creates a PriceChangeIbatisDaoImpl without a database connection
     */
    protected PriceChangeIbatisDaoImpl() {
        logger.info("PriceChangeIbatisDaoImpl DAO initialized");
        return;
    }

    /**
     * Create a PriceChangeIbatisDaoImpl object initialized with a
     * SecurityToken.
     * 
     * @param token
     *            an instnace of {@link SecurityToken} which is the user's
     *            security token.
     */
    public PriceChangeIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("Price Change Administration DAO connection is initialized");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.pricechangeadmin.PriceChangeDao#fetchAllActivePriceChanges
     * ()
     */
    @Override
    public List<PriceChange> fetchAllActive() throws DatabaseException {
        try {
            List<PriceChange> list = this.con
                    .queryForList("PriceChgAdmin.FetchActive");
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all active price changes failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.pricechangeadmin.PriceChangeDao#fetchActivePriceChangesBySku
     * (String)
     */
    @Override
    public List<PriceChange> fetchActiveBySku(String sku)
            throws DatabaseException {
        // Split the SKU and build PriceChange as a input parameter
        Map<String, Integer> splitSku = GeneralUtil.splitLongSku(sku);
        PriceChange model = new PriceChange();
        model.setSkuLower(splitSku.get(GeneralUtil.SKU_KEY_LOWER));
        model.setSkuUpper(splitSku.get(GeneralUtil.SKU_KEY_UPPER));

        // Fetch active price changes by the above sku
        try {
            List<PriceChange> list = this.con.queryForList(
                    "PriceChgAdmin.FetchActiveBySku", model);
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all active price changes by the specified SKU ["
                            + sku + "]failed due to a database error", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.pricechangeadmin.PriceChangeDao#fetchPriceChangeItemDetails
     * (com.neimanmarcus.pricechangeadmin.PriceChangeModel)
     */
    @Override
    public List<PriceChangeSkuItem> fetchItemDetails(PriceChange item)
            throws DatabaseException {
        try {
            List<PriceChangeSkuItem> list = this.con.queryForList(
                    "PriceChgAdmin.FetchItemDetails", item);
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all active price change item details by price chanege number ["
                            + item.getId() + "] failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.pricechangeadmin.PriceChangeDao#updatePriceChangeStatus
     * (int, java.lang.String)
     */
    @Override
    public int updateStatus(int pcNo, String newStatus)
            throws DatabaseException {
        PriceChange item = new PriceChange();
        item.setId(pcNo);
        item.setStatus(newStatus);
        item.setCurrDate(new Date());
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.update("PriceChgAdmin.UpdateStatusHeader", item);
            rows += this.con.update("PriceChgAdmin.UpdateStatusDetails", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred closing price changes header and detail records by price change number ["
                    + item.getId() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit of work for the \"Closing Price Change\" update operation using price change number ["
                        + item.getId() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.pricechangeadmin.PriceChangeDao#updateItemDetailQuantity
     * (int, long, long, int)
     */
    @Override
    public int updateItemDetailQuantity(int pcNo, long sku, long overFlowSku,
            int qty) throws DatabaseException {
        PriceChangeSkuItem item = new PriceChangeSkuItem();
        item.setPcNo(pcNo);
        item.setSku(sku);
        item.setOverFlowSku(overFlowSku);
        item.setMarkQty(qty);

        int rows = 0;
        String msgDetails = "price change number [" + item.getPcNo()
                + "] sku [" + item.getSku() + "] and over flow sku ["
                + item.getOverFlowSku() + "]";
        try {
            this.con.startTransaction();
            rows = this.con.update("PriceChgAdmin.UpdateItemQty", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred updating the price change item detail quantity for "
                    + msgDetails;
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit of work for the \"Updating Price Change Quantity\" update operation using "
                        + msgDetails;
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.pricechangeadmin.PriceChangeDao#updateItemDetailsBySku
     * (int, java.lang.String, java.util.List)
     */
    @Override
    public int updateItemDetailsBySku(int pcNo, String newStatus,
            List<PriceChangeSkuItem> items) throws DatabaseException {
        PriceChange pcItem = new PriceChange();
        pcItem.setId(pcNo);
        pcItem.setStatus(newStatus);
        pcItem.setCurrDate(new Date());
        pcItem.setItems(items);

        // // Add item details to PriceChange instance which contains
        // // all the skus for the selecte price change number.
        // for (SkuItem item : items) {
        // pcItem.addItem(item);
        // }

        // Perform update
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.update("PriceChgAdmin.UpdateStatusDetailsBySku",
                    pcItem);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred closing price changes header and detail records by price change number ["
                    + pcItem.getId() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit of work for the \"Closing Price Change\" update operation using price change number ["
                        + pcItem.getId() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

}
