package modules.useradmin;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import modules.authentication.UserAuthenticationException;
import modules.model.Assoc;

import org.apache.log4j.Logger;

import com.informix.util.dateUtil;
import com.nv.db.AbstractIbatisDao;
import com.nv.db.DatabaseException;
import com.nv.security.SecurityToken;

/**
 * The iBatis DAO implementation of {@link AssocDao}
 * 
 * @author rterrell
 *
 */
class AssocIbatisDaoImpl extends AbstractIbatisDao implements AssocDao {

    private static final Logger logger = Logger
            .getLogger(AssocIbatisDaoImpl.class);

    /**
     * Create a AssocIbatisDaoImpl initialized with a logger only.
     */
    private AssocIbatisDaoImpl() {
        logger.info("AssocIbatisDaoImpl DAO initialized");
        return;
    }

    /**
     * Create a AbstractIbatisDao initialized with a user's security token.
     */
    protected AssocIbatisDaoImpl(SecurityToken token) {
        super(token);
        logger.info("Associates DAO connection is initialized");
    }

    @Override
    public Integer authenticateUser(Assoc user)
            throws UserAuthenticationException {
        Object level = null;
        user.setPassword(user.getPassword().toLowerCase());
        try {
            level = this.con.queryForObject("UserAdmin.authenticate", user);
            if (level == null) {
                this.msg = "User Id/Password is incorrect";
                logger.error(this.msg);
                throw new UserAuthenticationException(this.msg);
            }
            return (Integer) level;
        } catch (SQLException e) {
            throw new UserAuthenticationException(
                    "The user authentication process failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.neimanmarcus.useradmin.AssocDao#fetch(java.lang.Integer)
     */
    @Override
    public Assoc fetch(int assocId) throws DatabaseException {
        try {
            Assoc user = new Assoc();
            user.setId(assocId);
            user = (Assoc) this.con.queryForObject("UserAdmin.fetch", user);
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The user authentication process failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.neimanmarcus.useradmin.AssocDao#fetchAssociates()
     */
    @Override
    public List<Assoc> fetchAllUsers() throws DatabaseException {
        try {
            List<Assoc> users = this.con.queryForList("UserAdmin.list");
            return users;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "The user authentication process failed due to a database error",
                    e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.useradmin.AssocDao#addUser(com.neimanmarcus.useradmin
     * .AssocModel)
     */
    @Override
    public Integer addUser(Assoc user) throws DatabaseException {
        try {
            Date ts = new Date();
            user.setLastChange(ts);
            user.setLastLogin(ts);
            user.setPassword(user.getPassword().toLowerCase());
            this.con.insert("UserAdmin.add", user);
            return new Integer(user.getId());
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Error occurred attempting to add user, " + user.getId()
                            + " to the database", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.useradmin.AssocDao#modifyUser(com.neimanmarcus.useradmin
     * .AssocModel)
     */
    @Override
    public Integer modifyUser(Assoc user) throws DatabaseException {
        try {
            Date ts = new Date();
            user.setLastChange(ts);
            user.setPassword(user.getPassword().toLowerCase());
            int rows = this.con.update("UserAdmin.update", user);
            return rows;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Error occurred attempting to add user, " + user.getId()
                            + " to the database", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.neimanmarcus.useradmin.AssocDao#deleteUser(com.neimanmarcus.useradmin
     * .AssocModel)
     */
    @Override
    public Integer deleteUser(Assoc user) throws DatabaseException {
        try {
            int rows = this.con.delete("UserAdmin.delete", user);
            return rows;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Error occurred attempting to delete user, " + user.getId()
                            + " from the database", e);
        }
    }

}
