package modules;

import modules.model.Store;

import com.nv.db.CommonDao;
import com.nv.db.DatabaseException;

/**
 * An all purpose DAO Interface for serving all modules of the entire Launch Pad
 * application.
 * 
 * @author rterrell
 *
 */
public interface GeneralDao extends CommonDao {

    public static final String IDT_ADMIN_FLAG_VALUE = "Display_IDT";

    /**
     * Fetch a stingle store record using a store number.
     * 
     * @param storeNo
     *            the id of the store to use as the search key.
     * @return an instance of {@link Store} or null if not found
     * @throws DatabaseException
     */
    Store fetchStore(int storeNo) throws DatabaseException;

    /**
     * Checks the existence of the IDT administration flag
     * 
     * @param value
     *            The value to search
     * @return true when flag exists and false otherwise.
     * @throws DatabaseException
     */
    boolean hasIdtAdminAccess(String value) throws DatabaseException;

}
