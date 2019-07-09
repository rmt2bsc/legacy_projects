package modules.report;

import java.util.List;

import modules.model.ReportDetail;
import modules.model.ReportHeader;
import modules.model.ReportParam;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing reporting configuration data.
 * 
 * @author rterrell
 *
 */
public interface ReportDao extends CommonDao {

    /**
     * Retrieve all category header records.
     * 
     * @return A {@link List} of {@link ReportHeader} instances or null when no
     *         records exist.
     * @throws DatabaseException
     */
    List<ReportHeader> fetchHeader() throws DatabaseException;

    /**
     * Retrieve all report records related to a particular category.
     * 
     * @param catgId
     *            The report category
     * @return A {@link List} of {@link ReportDetail} instances or null when no
     *         matches are found.
     * @throws DatabaseException
     */
    List<ReportDetail> fetchDetails(String catgId) throws DatabaseException;

    /**
     * Retreive all report parameter records related to a given report id.
     * 
     * @param rptId
     *            The report id.
     * @return A {@link List} of {@link ReportParam} instances.
     * @throws DatabaseException
     */
    List<ReportParam> fetchParams(String rptId) throws DatabaseException;

}
