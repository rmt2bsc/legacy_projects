package modules;

import java.sql.SQLException;

import modules.model.Store;

import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;

/**
 * The DAO implementation of the {@link GeneralDao}
 * 
 * @author rterrell
 *
 */
public class GeneralDaoImpl extends AbstractIbatisDao implements GeneralDao {

    /**
     * Default constructor
     */
    protected GeneralDaoImpl() {
        super();
        return;
    }

    /**
     * Create a GeneralDaoImpl object initialized with a SecurityToken.
     * 
     * @param token
     *            an instnace of {@link SecurityToken} which is the user's
     *            security token.
     */
    public GeneralDaoImpl(SecurityToken token) {
        super(token);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.neimanmarcus.GeneralDao#fetch(int)
     */
    @Override
    public Store fetchStore(int storeNo) throws DatabaseException {
        try {
            Store arg = new Store();
            arg.setId(storeNo);
            Store s = (Store) this.con
                    .queryForObject("General.FetchStore", arg);
            return s;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "An error occurred fetching of Store using store number, "
                            + storeNo, e);
        }
    }

    /**
     * Checks the existence of the IDT administration flag in the tran_ctr
     * table.
     * <p>
     * The value of the column, trantype, should be equal to the String value,
     * <i>Display_IDT</i>, in order for the method to return true.
     * 
     * @param value
     *            The value to search
     * @return true when <i>value<i> equals "Display_IDT". Otherwise, false is
     *         returned.
     * @throws DatabaseException
     *             Genrael database access errors.
     */
    @Override
    public boolean hasIdtAdminAccess(String value) throws DatabaseException {
        try {
            Integer rc = (Integer) this.con.queryForObject(
                    "General.FetchIdtFlag", value);
            return (rc != null);
        } catch (SQLException e) {
            throw new DatabaseException(
                    "An error occurred fetching IDT Admin access flag", e);
        }
    }

}
