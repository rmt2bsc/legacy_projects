package modules.rtv.bi;

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
 * This is the iBatis implementation of {@link RtvDao} interface for Buyer
 * Initiated (BI) RTV.
 * 
 * @author rterrell
 *
 */
public class BIRtvIbatisDaoImpl extends AbstractIbatisDao implements RtvDao {

    private static final Logger logger = Logger
            .getLogger(BIRtvIbatisDaoImpl.class);

    /**
     * 
     */
    protected BIRtvIbatisDaoImpl() {
        super();
    }

    /**
     * Create a BIRtvIbatisDaoImpl object initialized with a SecurityToken.
     * 
     * @param token
     *            an instnace of {@link SecurityToken} which is the user's
     *            security token.
     */
    public BIRtvIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("BI RTV Administration DAO connection is initialized");
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
                    .queryForList("BIRTVAdmin.FetchHeader");
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all BI RTV header records failed due to a database error",
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
                    "BIRTVAdmin.FetchDetail", args);
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of all BI RTV SKU Items for RTV No., "
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
            rows = this.con.update("BIRTVAdmin.UpdateHeaderStatus", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred changing the status of the RTV header record for BI RTV Header ["
                    + item.getRtvNo() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while changing the status of the BI RTV header record for RTV No. ["
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
            rows = this.con.update("BIRTVAdmin.UpdateSkuItem", item);
            this.con.commitTransaction();
            return rows;
        } catch (SQLException e) {
            this.msg = "Error occurred updating quatintiy for BI RTV SKU item ["
                    + item.getsSku() + "]";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while updating the quantity for BI RTV SKU item ["
                        + item.getsSku() + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.rtv.RtvDao#fetchRtvNoQtySum(long)
     */
    @Override
    public int fetchRtvNoQtySum(long rtvNo) throws DatabaseException {
        RtvHeader arg = new RtvHeader();
        arg.setRtvNo(rtvNo);
        try {
            RtvHeader obj = (RtvHeader) this.con.queryForObject(
                    "BIRTVAdmin.FetchRtvNoSUmmedQty", arg);
            return obj.getQtySummed();
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of the summed quantity of BI RTV header record, "
                            + rtvNo + " failed due to a database error", e);
        }
    }

}
