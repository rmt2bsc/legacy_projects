package modules.idt;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import modules.model.SkuItem;

import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;

/**
 * This is the iBatis implementation of {@link IdtDao} interface for IDT
 * Administration data store.
 * 
 * @author rterrell
 *
 */
class IdtIbatisDaoImpl extends AbstractIbatisDao implements IdtDao {

    private static final Logger logger = Logger
            .getLogger(IdtIbatisDaoImpl.class);

    /**
     * Default constructor.
     */
    protected IdtIbatisDaoImpl() {
        return;
    }

    /**
     * Create IdtIbatisDaoImpl equipped with a security token.
     * 
     * @param token
     *            An instance of {@link SecurityToken}
     */
    protected IdtIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("IDT Administration DAO connection is initialized");
    }

    /**
     * Fetches a list of IDT reticketed items based on criteria contained in
     * <i>arg</i>.
     * <p>
     * This method implementation targets the <i>IdtAdmin.FetchHeader</i> iBatis
     * select statement tag.
     * 
     * @param arg
     *            An instance of {@link SkuItem} representing the selection
     *            criteria.
     * @return A List of {@link SkuItem} objects or null if criteria is not
     *         matched.
     * @throws DatabaseException
     */
    @Override
    public List<SkuItem> fetchReticketHeader(SkuItem arg)
            throws DatabaseException {
        if (arg == null) {
            arg = new SkuItem();
        }
        try {
            List<SkuItem> list = this.con.queryForList("IdtAdmin.FetchHeader",
                    arg);
            if (list.size() == 0) {
                return null;
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of IDT reticketed items failed due to a database error",
                    e);
        }
    }

    /**
     * Fetches a single IDT sku record.
     * <p>
     * This method implementation targets the <i>IdtAdmin.FetchSku</i> iBatis
     * select statement tag.
     * 
     * @param skuNo
     *            the sku number
     * @return an instance of {@link SkuItem} or null if the sku is not foud.
     * @throws DatabaseException
     */
    @Override
    public SkuItem fetchSku(long skuNo) throws DatabaseException {
        SkuItem item = new SkuItem();
        item.setSku(skuNo);
        try {
            SkuItem sku = (SkuItem) this.con.queryForObject(
                    "IdtAdmin.FetchSku", item);
            return sku;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of IDT SKU failed due to a database error",
                    e);
        }
    }

    /**
     * Retruns an IDT sku number based on the selection criteria represented as
     * <i>arg</i>.
     * <p>
     * This method implementation targets the <i>IdtAdmin.FetchShortSku</i>
     * iBatis select statement tag.
     * 
     * @param arg
     *            An instance of {@link SkuItem} representing the selection
     *            criteria.
     * @return The sku number found or null if sku is not found.
     * @throws DatabaseException
     */
    @Override
    public Long fetchShortSku(SkuItem arg) throws DatabaseException {
        try {
            Long sku = (Long) this.con.queryForObject("IdtAdmin.FetchShortSku",
                    arg);
            return sku;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of IDT short SKU number failed due to a database error",
                    e);
        }
    }

    /**
     * Updates the quantity of an IDT sku.
     * <p>
     * This method implementation targets the <i>IdtAdmin.UpdateNewQuantity</i>
     * iBatis update statement tag.
     * 
     * @param item
     *            an instance of {@link SkuItem} containg the sku selection
     *            criteria.
     * @param qty
     *            the quantity value to update
     * @return the total number of rows effected
     * @throws DatabaseException
     */
    @Override
    public int updateSkuQty(SkuItem item, int qty) throws DatabaseException {
        item.setQty(qty);
        try {
            this.con.startTransaction();
            int rows = this.con.update("IdtAdmin.UpdateNewQuantity", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The quantity update of an IDT SKU number ["
                            + item.getSku()
                            + "] failed due to a database error", e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while updating IDT reticketing and sku items";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /**
     * Delete an IDT sku.
     * <p>
     * This method implementation targets the <i>IdtAdmin.DeleteReticketItem</i>
     * and <i>IdtAdmin.DeleteSkuItem</i> iBatis delete statement tags.
     * 
     * @param item
     *            An instance of {@link SkuItem} representing the selection
     *            criteria.
     * @return the total number of rows effected
     * @throws DatabaseException
     */
    @Override
    public int deleteSku(SkuItem item) throws DatabaseException {
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.delete("IdtAdmin.DeleteReticketItem", item);
            rows += this.con.delete("IdtAdmin.DeleteSkuItem", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The quantity update of an IDT SKU number ["
                            + item.getSku()
                            + "] failed due to a database error", e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while deleting IDT reticketing and sku items";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

}
