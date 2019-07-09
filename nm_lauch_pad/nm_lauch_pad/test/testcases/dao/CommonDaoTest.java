package testcases.dao;


import org.junit.After;
import org.junit.Before;

import com.nv.security.SecurityToken;
import com.nv.security.UserSecurityManager;


/**
 * @author rterrell
 *
 */
public class CommonDaoTest {

    protected SecurityToken token;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	UserSecurityManager.initUserSecurity();
	this.token = UserSecurityManager.getSecurityToken();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	UserSecurityManager mgr = new UserSecurityManager();
	mgr.resetSecurityToken();
	mgr = null;
	this.token = null;
    }

}
