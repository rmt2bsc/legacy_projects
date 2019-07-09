package modules.priceaudit;

import java.sql.SQLException;
import java.util.List;

import modules.model.PriceAuditSkuItem;

import org.apache.log4j.Logger;

import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;

/**
 * The iBatis DAO implementation of {@link PriceAuditDao}
 * 
 * @author rterrell
 *
 */
class PriceAuditIbatisDaoImpl extends AbstractIbatisDao implements
        PriceAuditDao {

    private static final Logger logger = Logger
            .getLogger(PriceAuditIbatisDaoImpl.class);

    /**
     * Creates a PriceAuditIbatisDaoImpl without a database connection
     */
    protected PriceAuditIbatisDaoImpl() {
        logger.info("PriceAuditIbatisDaoImpl DAO initialized");
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
    public PriceAuditIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("Price Change Administration DAO connection is initialized");
    }

    /**
     * Fetches all active price audit records from the item_audit table and
     * returns the results as a List of PriceAuditSkuItem objects.
     * 
     * @return a List of {@link PriceAuditSkuItem} objects
     * @throws DatabaseException
     */
    @Override
    public List<PriceAuditSkuItem> fetchAllActive() throws DatabaseException {
        try {
            List<PriceAuditSkuItem> list = this.con
                    .queryForList("PriceAuditAdmin.FetchActive");
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all active price audit failed due to a database error",
                    e);
        }
    }

    /**
     * Updates a single price audit item record from the item_audit table and
     * returns the total number of rows that were effected by the operation.
     * 
     * @param item
     *            an instance of {@link PriceAuditSkuItem} containing the
     *            parameters used as selection criteria.
     * @return the total number of records effected.
     * @throws DatabaseException
     */
    @Override
    public int updateItem(PriceAuditSkuItem item) throws DatabaseException {
        int rc = 0;
        try {
            this.con.startTransaction();
            rc = this.con.update("PriceAuditAdmin.UpdateItem", item);
            this.con.commitTransaction();
            return rc;
        } catch (SQLException e) {
            this.msg = "The update operation of the selected price audit record failed failed due to a database error";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit of work for the \"Closing Price Audit\" update operation";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /**
     * Deletes a single price audit item record from the item_audit table and
     * returns the total number of rows that were effected by the operation.
     * 
     * @param item
     *            an instance of {@link PriceAuditSkuItem} containing the
     *            parameters used as selection criteria.
     * @return the total number of records effected.
     * @throws DatabaseException
     */
    @Override
    public int deleteItem(PriceAuditSkuItem item) throws DatabaseException {
        int rc = 0;
        try {
            rc = this.con.delete("PriceAuditAdmin.DeleteItem", item);
            return rc;
        } catch (SQLException e) {
            this.msg = "The delete operation of the selected price audit record failed failed due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

}
