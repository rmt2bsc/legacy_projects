package modules.counts;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import modules.model.Count;
import modules.model.CountDetail;

import com.InvalidDataException;
import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;
import com.nv.util.GeneralUtil;

/**
 * An iBatis implementation of the interface, {@link CountsDao}.
 * 
 * @author rterrell
 *
 */
class CountsIbatisDaoImpl extends AbstractIbatisDao implements CountsDao {

    private static final Logger logger = Logger
            .getLogger(CountsIbatisDaoImpl.class);

    /**
     * Create CountsIbatisDaoImpl as the default constructor.
     */
    protected CountsIbatisDaoImpl() {
        return;
    }

    /**
     * Create a CountsIbatisDaoImpl object initialized with a SecurityToken.
     * 
     * @param token
     *            an instnace of {@link SecurityToken} which is the user's
     *            security token.
     */
    public CountsIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("Counts Administration DAO connection is initialized");
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.counts.CountsDao#fetchAllHeaders()
     */
    @Override
    public List<Count> fetchAllHeaders() throws DatabaseException {
        try {
            List<Count> list = this.con.queryForList("CountsAdmin.FetchHeader");
            return list;
        } catch (SQLException e) {
            this.msg = "The retrieval of all Counts Header records failed due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.counts.CountsDao#fetchHeaderDetails(int)
     */
    @Override
    public List<CountDetail> fetchHeaderDetails(int headerId)
            throws DatabaseException {
        Count arg = new Count();
        arg.setHdrId(headerId);
        try {
            List<CountDetail> list = this.con.queryForList(
                    "CountsAdmin.FetchDetail", arg);
            return list;
        } catch (SQLException e) {
            this.msg = "The retrieval of all detail records related to count header, "
                    + headerId + ", failed due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.counts.CountsDao#fetchTransmittedHeaders(int)
     */
    @Override
    public List<Count> fetchTransmittedHeaders(int daysBack)
            throws DatabaseException {
        Count arg = new Count();
        Calendar curDate = new GregorianCalendar();
        curDate.add(Calendar.DAY_OF_MONTH, daysBack * -1);
        arg.setInventoryDate(curDate.getTime());

        try {
            List<Count> list = this.con.queryForList(
                    "CountsAdmin.FetchHeaderTransmitted", arg);
            return list;
        } catch (SQLException e) {
            this.msg = "The retrieval of Transmitted Counts Header records failed due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.counts.CountsDao#changeStatusToSend(int)
     */
    @Override
    public String changeStatusSend(int hdrId) throws InvalidDataException,
            DatabaseException {
        // Verify there are no detail records with a status other than "F"
        // associated with the header record.
        Integer cnt = this.fetchDetailCountStatusExcluded(hdrId, "F");
        if (cnt > 0) {
            this.msg = "Unable to change Count status to \"Send\" due to there are detail counts that are not finished for header record, "
                    + hdrId;
            throw new InvalidDataException(this.msg);
        }

        // Perform the actual status update.
        try {
            String newStatus = "S";

            Count hdrArg = new Count();
            hdrArg.setHdrId(hdrId);
            hdrArg.setStatus("A");
            hdrArg.setNewStatus(newStatus);

            CountDetail dtlArg = new CountDetail();
            dtlArg.setHdrId(hdrId);
            dtlArg.setStatus("F");
            dtlArg.setNewStatus(newStatus);

            this.con.startTransaction();
            this.updateHeaderStatus(hdrArg);
            this.updateDetaiStatus(dtlArg);
            this.con.commitTransaction();
            return newStatus;
        } catch (SQLException e) {
            this.msg = "Unable to change Count status to \"Send\"";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while changing the Count status to \"Send\" for header id ["
                        + hdrId + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.counts.CountsDao#changeStatusToReopen(int)
     */
    @Override
    public String changeStatusReopen(int hdrId) throws DatabaseException {
        try {
            String oldStatus = "S";
            String newHeaderStatus = "A";
            String newDetailStatus = "F";

            Count hdrArg = new Count();
            hdrArg.setHdrId(hdrId);
            hdrArg.setStatus(oldStatus);
            hdrArg.setNewStatus(newHeaderStatus);

            CountDetail dtlArg = new CountDetail();
            dtlArg.setHdrId(hdrId);
            dtlArg.setStatus(oldStatus);
            dtlArg.setNewStatus(newDetailStatus);

            this.con.startTransaction();
            this.updateHeaderStatus(hdrArg);
            this.updateDetaiStatus(dtlArg);
            this.con.commitTransaction();
            return newHeaderStatus;
        } catch (SQLException e) {
            this.msg = "Unable to change Count status to \"Reopen\"";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while changing the Count status to \"Reopen\" for header id ["
                        + hdrId + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /**
     * Deletes an associate related count from the database targeting the
     * cnt_hdr and cnt_dtl tables.
     * <p>
     * First, all detail records are purged from the cnt_dlt table belonging to
     * <i>hdrId</i> and <i>assocNo</i>. Lastly, the header is deleted from the
     * system provided it does not have any more detail recrods.
     * 
     * @param hdrId
     *            THe id of the count header
     * @param assocNo
     *            The id of the associate.
     * @return the total number of rows effected by the delete.
     * @throws DatabaseException
     *             General database access errors or problem closing the
     *             transaction
     */
    @Override
    public int delete(int hdrId, int assocNo) throws DatabaseException {
        int delRows = 0;
        try {
            this.con.startTransaction();
            delRows = this.deleteDetails(hdrId, assocNo);
            Integer rows = this.fetchDetailCount(hdrId);
            if (rows == 0) {
                delRows += this.deleteHeader(hdrId);
            }
            this.con.commitTransaction();
            return delRows;
        } catch (SQLException e) {
            this.msg = "Unable to delete Count";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while deleting Count record for header id ["
                        + hdrId + "]";
                throw new DatabaseException(this.msg, e);
            }
        }
    }

    /**
     * Creates a new or modifies an existing count record.
     * <p>
     * Based on the inventory date and the division input values, an existence
     * check of the header record is performed. If a header record is found,
     * that record is designated as the destination header for the update
     * process. The details of the old header record, which is identified as
     * <i>odlHdrId</i>, are moved to the destination header. If the old header
     * is found to not have any more detail records, then the old header is
     * deleted from the system.
     * <p>
     * When a header record is not found as a result of the existence check, a
     * new count header is created. Next, the count details of the old header,
     * <i>oldHdrId</i>, are moved to the new header just created. Lastly, the
     * old header is deleted from the system provided it does not have any more
     * detail recrods.
     * <p>
     * Both update scenarios are performed as a transactional unit of work.
     * 
     * @param oldHdrId
     *            The header id of the count record to be modified.
     * @param division
     *            The division of the count record.
     * @param newInvDate
     *            The newly assigned inventory date
     * @param assocNo
     *            The id of the associate.
     * @param currentUserId
     *            The id of the user currently logged into the system.
     * @return the id of the header created or updated.
     * @throws DatabaseException
     *             General database access errors or problem closing the
     *             transaction
     */
    @Override
    public int update(int oldHdrId, int division, Date newInvDate, int assocNo,
            int currentUserId) throws DatabaseException {
        Long id = this.fetchMinimumHeaderId(newInvDate, division);
        Integer newHdrId = null;
        Integer hdrIdUpdated = null;
        int rows = 0;

        try {
            this.con.startTransaction();
            if (id == null) {
                Count c = new Count();
                c.setDivision(division);
                c.setInventoryDate(newInvDate);
                c.setCurrentUserId(currentUserId);

                newHdrId = this.insertHeader(c);
                this.updateDetail(oldHdrId, newHdrId.intValue(), assocNo);
                rows = this.fetchDetailCount(oldHdrId);
                if (rows == 0) {
                    this.deleteHeader(oldHdrId);
                }
                hdrIdUpdated = newHdrId;
            }
            else {
                rows = this.updateDetail(oldHdrId, id.intValue(), assocNo);
                rows = this.fetchDetailCount(oldHdrId);
                if (rows == 0) {
                    this.deleteHeader(oldHdrId);
                }
                hdrIdUpdated = id.intValue();
            }
            this.con.commitTransaction();
            return hdrIdUpdated;
        } catch (SQLException e) {
            this.msg = "Unable to update Count due to SQL Error";
            throw new DatabaseException(this.msg, e);
        } finally {
            try {
                this.con.endTransaction();
            } catch (SQLException e) {
                this.msg = "Error occurred ending transactional unit while updating Count record for header id ["
                        + oldHdrId + "]";
                throw new DatabaseException(this.msg, e);
            }
        }

    }

    /**
     * 
     * @param oldHdrId
     * @param newHdrId
     * @param assocNo
     * @return
     * @throws DatabaseException
     */
    private int updateDetail(int oldHdrId, int newHdrId, int assocNo)
            throws DatabaseException {
        CountDetail arg = new CountDetail();
        arg.setNewHdrId(newHdrId);
        arg.setHdrId(oldHdrId);
        arg.setAssocNo(assocNo);
        try {
            int rows = this.con.update("CountsAdmin.UpdateDetail", arg);
            return rows;
        } catch (SQLException e) {
            this.msg = "Error moving detail count records to new header record, "
                    + arg.getHdrId() + ", due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    private Integer fetchDetailCountStatusExcluded(int headerId, String status)
            throws DatabaseException {
        CountDetail arg = new CountDetail();
        arg.setHdrId(headerId);
        arg.setStatus(status);
        try {
            Integer c = (Integer) this.con.queryForObject(
                    "CountsAdmin.FetchDetailCount", arg);
            return c;
        } catch (SQLException e) {
            this.msg = "The count retrieval of header record by excluded status, "
                    + headerId + ", failed due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    private Integer fetchDetailCount(int headerId) throws DatabaseException {
        CountDetail arg = new CountDetail();
        arg.setHdrId(headerId);
        try {
            Integer c = (Integer) this.con.queryForObject(
                    "CountsAdmin.FetchDetailCount", arg);
            return c;
        } catch (SQLException e) {
            this.msg = "The count retrieval of header record, " + headerId
                    + ", failed due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    private Long fetchMinimumHeaderId(Date invDate, int division)
            throws DatabaseException {
        Count arg = new Count();
        arg.setInventoryDate(invDate);
        arg.setDivision(division);
        try {
            Long c = (Long) this.con.queryForObject(
                    "CountsAdmin.FetchMinimumHeaderByInvDateAndDivision", arg);
            return c;
        } catch (SQLException e) {
            this.msg = "The retrieval of Minimum Counts Header Id based on invenotory date and division failed due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    private Integer insertHeader(Count arg) throws DatabaseException {
        Date curDate = new Date();
        arg.setCurrDate(curDate);
        arg.setStartDate(curDate);
        arg.setStartTime(curDate);
        arg.setStatus("A");
        arg.setStatusDate(curDate);
        arg.setHdrId(0);
        try {
            Object rc = this.con.insert("CountsAdmin.InsertHeader", arg);
            Integer key = null;
            if (rc instanceof Integer) {
                key = (Integer) rc;
            }
            return key;
        } catch (SQLException e) {
            this.msg = "Unable to delete Count";
            throw new DatabaseException(this.msg, e);
        }
    }

    private int updateHeaderStatus(Count arg) throws DatabaseException {
        arg.setCurrDate(new Date());
        try {
            int rows = this.con.update("CountsAdmin.UpdateHeaderStatus", arg);
            return rows;
        } catch (SQLException e) {
            this.msg = "Error updating Count Header record, " + arg.getHdrId()
                    + ", due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    private int updateDetaiStatus(CountDetail arg) throws DatabaseException {
        arg.setCurrDate(new Date());
        try {
            int rows = this.con.update("CountsAdmin.UpdateDetailStatus", arg);
            return rows;
        } catch (SQLException e) {
            this.msg = "Error updating Count Detail record, " + arg.getHdrId()
                    + ", due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    private int deleteHeader(int hdrId) throws DatabaseException {
        Count arg = new Count();
        arg.setHdrId(hdrId);
        try {
            int rows = this.con.delete("CountsAdmin.DeleteHeader", arg);
            return rows;
        } catch (SQLException e) {
            this.msg = "Error deleting Count Header record, " + hdrId
                    + ", due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

    private int deleteDetails(int hdrId, int assocNo) throws DatabaseException {
        CountDetail arg = new CountDetail();
        arg.setHdrId(hdrId);
        arg.setAssocNo(assocNo);
        try {
            int rows = this.con.delete("CountsAdmin.DeleteDetail", arg);
            return rows;
        } catch (SQLException e) {
            this.msg = "Error deleting Count Detail record, " + hdrId
                    + ", due to a database error";
            throw new DatabaseException(this.msg, e);
        }
    }

}
