package modules.report;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import modules.model.ReportDetail;
import modules.model.ReportHeader;
import modules.model.ReportParam;

import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;

/**
 * This is the iBatis implementation of {@link ReportDao} interface for
 * Print/Reprint Report Administration module.
 * 
 * @author rterrell
 *
 */
class ReportIbatisDaoImpl extends AbstractIbatisDao implements ReportDao {

    private static final Logger logger = Logger
            .getLogger(ReportIbatisDaoImpl.class);

    /**
     * Create a ReportIbatisDaoImpl object.
     */
    protected ReportIbatisDaoImpl() {
        super();
    }

    /**
     * Create ReportIbatisDaoImpl equipped with a security token.
     * 
     * @param token
     *            An instance of {@link SecurityToken}
     */
    protected ReportIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("Print/Reprint Report Administration DAO connection is initialized");
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.report.ReportDao#fetchHeader()
     */
    @Override
    public List<ReportHeader> fetchHeader() throws DatabaseException {
        try {
            List<ReportHeader> list = this.con
                    .queryForList("ReportsAdmin.FetchHeader");
            if (list.size() == 0) {
                return null;
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of Reporting Category items failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.report.ReportDao#fetchDetails(java.lang.String)
     */
    @Override
    public List<ReportDetail> fetchDetails(String catgId)
            throws DatabaseException {
        ReportDetail parm = new ReportDetail();
        parm.setCatgTag(catgId);
        try {
            List<ReportDetail> list = this.con.queryForList(
                    "ReportsAdmin.FetchDetails", parm);
            if (list.size() == 0) {
                return null;
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of Reporting Detail configuration items failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see modules.report.ReportDao#fetchParams(java.lang.String)
     */
    @Override
    public List<ReportParam> fetchParams(String rptId) throws DatabaseException {
        ReportParam parm = new ReportParam();
        parm.setRptTag(rptId);
        try {
            List<ReportParam> list = this.con.queryForList(
                    "ReportsAdmin.FetchParams", parm);
            if (list.size() == 0) {
                return null;
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The retrieval of Report Parameter configuration items failed due to a database error",
                    e);
        }
    }

}
