package testcases.dao;

import java.util.List;

import junit.framework.Assert;

import modules.model.Assoc;
import modules.useradmin.AssocDao;
import modules.useradmin.AssocDaoFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.nv.db.DatabaseException;

/**
 * @author rterrell
 *
 */
public class UserAdminDaoTest extends CommonDaoTest {

    private AssocDaoFactory f;

    private AssocDao dao;


    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	super.setUp();
	this.f = new AssocDaoFactory();
	this.dao = f.getDaoInstance(this.token);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
	this.dao.close();
	this.dao = null;
	this.f = null;
	super.tearDown();
    }

    @Test
    public void fetchAllUsers() {
	Object list = null;
	try {
	    list = this.dao.fetchAllUsers();
	    Assert.assertNotNull(list);
	    Assert.assertTrue(list instanceof List);
	    Assert.assertTrue(((List) list).size() > 0);
	}
	catch (Exception e) {
	    throw new RuntimeException(e);
	}
	finally {

	}
    }
    
    
    @Test
    public void createFetchDeleteTest() {
	Assoc user =  this.createAssocModel();
	try {
	    this.dao.addUser(user);
	    Assoc vo = this.dao.fetch(user.getId());
	    Assert.assertNotNull(vo);
	    Assert.assertEquals(999999, vo.getId());
	    int rc = this.dao.deleteUser(user);
	    Assert.assertEquals(1, rc);
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	}
    }
    
    @Test
    public void fetchNotFoundUserTest() {
	try {
	    Assoc vo = this.dao.fetch(999999);
	    Assert.assertNull(vo);
	}
	catch (DatabaseException e) {
	    e.printStackTrace();
	}
    }
    
    
    
    private Assoc createAssocModel() {
	Assoc user = new Assoc();
	user.setId(999999);
	user.setNewId(999999);
	user.setFirstName("TestFirstName");
	user.setMidInit("T");
	user.setLastName("TestLastName");
	user.setSecurityLevel(3);
	user.setPassword("123456");
	return user;
    }
}
