package modules.counts;

import java.util.Date;
import java.util.List;

import modules.model.Count;
import modules.model.CountDetail;

import com.InvalidDataException;
import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing Counts data from some external data source.
 * 
 * @author rterrell
 *
 */
public interface CountsDao extends CommonDao {

    /**
     * Retrieves all entries from the cnt_hdr table.
     * 
     * @return a {@link List} of {@link Count} objects
     * @throws DatabaseException
     */
    List<Count> fetchAllHeaders() throws DatabaseException;

    /**
     * Retrieves all entries from the cnt_dtl table that relate to the header,
     * <i>headerId</i>.
     * 
     * @param headerId
     *            The id of the Count header record
     * @return a {@link List} of {@link CountDetail} objects
     * @throws DatabaseException
     */
    List<CountDetail> fetchHeaderDetails(int headerId) throws DatabaseException;

    /**
     * Retrieves a list of transmited counts based on the status date being
     * greater than or equal to <i>daysBack</i> before the current date.
     * <p>
     * Transmitted count are those header records with a status of "X".
     * 
     * @param daysBack
     *            an int indicating the total number of days before the current
     *            date.
     * @return a {@link List} of {@link Count} objects
     * @throws DatabaseException
     */
    List<Count> fetchTransmittedHeaders(int daysBack) throws DatabaseException;

    /**
     * Changes the status of a count to Send.
     * <p>
     * It is required that the header and detail current status be "A" and "F",
     * respectively, prior to performing the update. A successful update
     * operation includes changing the statuses of both the cnt_hdr and cnt_dtl
     * tables to "S". Also prior to changing the status, it must be verified
     * that all of the detail rows associated with the header are in Finish
     * status. If any rows exist, all updates must be prevented and an error
     * message sent to the caller.
     * 
     * @param hdrId
     *            The id of the Header record.
     * @return The new Status code as it pertains to the Count Header.
     * @throws InvalidDataException
     * @throws DatabaseException
     */
    String changeStatusSend(int hdrId) throws InvalidDataException,
            DatabaseException;

    /**
     * Changes the status of a count to reopen.
     * <p>
     * It is required that the header and detail current statuses be "S" prior
     * to performing the update. A successful update operation includes changing
     * the statuses of both the cnt_hdr and cnt_dtl tables to "A" and "F",
     * respectively.
     * 
     * @param hdrId
     *            The id of the Header record.
     * @return The new Status code as it pertains to the Count Header
     * @throws DatabaseException
     */
    String changeStatusReopen(int hdrId) throws DatabaseException;

    /**
     * Deletes a count that is related to a particular associate from the
     * database.
     * 
     * @param hdrId
     *            THe id of the count header
     * @param assocNo
     *            The id of the associate.
     * @return the total number of rows effected by the delete.
     * @throws DatabaseException
     */
    int delete(int hdrId, int assocNo) throws DatabaseException;

    /**
     * Undergoes the process of creating a new or modifying an existing count
     * record.
     * <p>
     * When updating a count, the count detail records are simply moved to a
     * different count header that already exists in the database. Creating a
     * count involves establishing a brand new count header and subsequently
     * moving all of the count detail records belonging to <i>oldHdrId</i> to
     * the new count header. In both cases, the originating count header is
     * removed from the database once it is determined it has no more associated
     * detail records.
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
     */
    int update(int oldHdrId, int division, Date newInvDate, int assocNo,
            int currentUserId) throws DatabaseException;

}
