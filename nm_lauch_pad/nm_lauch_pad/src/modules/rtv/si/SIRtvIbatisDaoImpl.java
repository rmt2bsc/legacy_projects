package modules.rtv.si;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import modules.model.RtvHeader;
import modules.model.RtvItem;
import modules.rtv.RtvDao;

import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;

/**
 * This is the iBatis implementation of {@link RtvDao} interface for Store
 * Initiated (SI) RTV.
 * 
 * @author rterrell
 *
 */
public class SIRtvIbatisDaoImpl extends AbstractIbatisDao implements RtvDao {

    private static final Logger logger = Logger
            .getLogger(SIRtvIbatisDaoImpl.class);

    /**
     * 
     */
    protected SIRtvIbatisDaoImpl() {
        super();
    }

    /**
     * Create a SIRtvIbatisDaoImpl object initialized with a SecurityToken.
     * 
     * @param token
     *            an instnace of {@link SecurityToken} which is the user's
     *            security token.
     */
    public SIRtvIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("SI RTV Administration DAO connection is initialized");
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.rtv.RtvDao#fetchHeader()
     */
    @Override
    public List<RtvHeader> fetchHeader() throws DatabaseException {
        try {
            List<RtvHeader> list = this.con
                    .queryForList("SIRTVAdmin.FetchHeader");
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all SI RTV header records failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.rtv.RtvDao#fetchItems(long)
     */
    @Override
    public List<RtvItem> fetchItems(long rtvNo) throws DatabaseException {
        RtvHeader args = new RtvHeader();
        args.setRtvNo(rtvNo);
        try {
            List<RtvItem> list = this.con.queryForList(
                    "SIRTVAdmin.FetchDetail", args);
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all SI RTV SKU Items for RTV No., "
                            + rtvNo + ", failed due to a database error", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.rtv.RtvDao#updateStatus(long, java.lang.String)
     */
    @Override
    public int updateStatus(long rtvNo, String newStatus)
            throws DatabaseException {
        RtvHeader item = new RtvHeader();
        item.setRtvNo(rtvNo);
        item.setStatus(newStatus);
        item.setCurrDate(new Date());
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.update("SIRTVAdmin.UpdateHeaderStatus", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred changing the status of the RTV header record for SI RTV Header ["
                    + item.getRtvNo() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while changing the status of the SI RTV header record for RTV No. ["
                        + item.getRtvNo() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.rtv.RtvDao#updateSkuITem(modules.model.RtvItem, int)
     */
    @Override
    public int updateSkuITem(RtvItem item, int qty) throws DatabaseException {
        item.setQty(qty);
        int rows = 0;
        try {
            this.con.startTransaction();
            rows = this.con.update("SIRTVAdmin.UpdateSkuItem", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred updating quatintiy for SI RTV SKU item ["
                    + item.getsSku() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while updating the quantity for SI RTV SKU item ["
                        + item.getsSku() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /**
     * Stub method
     * 
     * @param rtvNO
     *            the RTV number
     * @return Always returns zero
     * @throws DatabaseException
     */
    @Override
    public int fetchRtvNoQtySum(long rtvNO) throws DatabaseException {
        return 0;
    }

}
