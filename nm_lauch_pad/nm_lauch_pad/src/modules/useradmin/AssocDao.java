package modules.useradmin;

import java.util.List;

import modules.authentication.UserAuthenticationException;
import modules.model.Assoc;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * DAO Interface for accessing Associate related data from some external data
 * source
 * 
 * @author rterrell
 *
 */
public interface AssocDao extends CommonDao {

    /**
     * Authenticates the user by verifying security credntials against the RF
     * database.
     * 
     * @param user
     *            an instance of {@link Assoc} representing the user to
     *            authenticate.
     * @return Integer wrapper representing the user's security level
     * @throws UserAuthenticationException
     *             User could not be authenitcated or general database errors
     *             are present.
     */
    Integer authenticateUser(Assoc user) throws UserAuthenticationException;

    /**
     * Fetches an individual associate using the assoicate id.
     * 
     * @param assocId
     *            the id of the associate
     * @return an instance of {@link Assoc} or null if associate is not found.
     * @throws DatabaseException
     */
    Assoc fetch(int assocId) throws DatabaseException;

    /**
     * Fetches the entire list of Associtates.
     * 
     * @return a List of {@link Assoc}
     * @throws DatabaseException
     */
    List<Assoc> fetchAllUsers() throws DatabaseException;

    /**
     * Add a new user into the database.
     * 
     * @param user
     *            an instance of {@link Assoc} representing the user.
     * @return the id of the user
     * @throws DatabaseException
     */
    Integer addUser(Assoc user) throws DatabaseException;

    /**
     * Modifies an existing user.
     * 
     * @param user
     *            an instance of {@link Assoc} representing the user.
     * @return the total number of rows effected by the operation.
     * @throws DatabaseException
     */
    Integer modifyUser(Assoc user) throws DatabaseException;

    /**
     * Deletes an Associate fromt the database.
     * 
     * @param user
     *            the user model as an instance of {@link Assoc}
     * @return the total number of rows effected.
     * @throws DatabaseException
     */
    Integer deleteUser(Assoc user) throws DatabaseException;

}
