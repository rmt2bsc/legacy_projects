package testcases.db;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import modules.model.Assoc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.nv.db.IbaistEnvConfig;


/**
 * @author rterrell
 *
 */
public class TestIbatisApi {
    private SqlMapClient client;
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	try {
	    this.client = IbaistEnvConfig.getSqlMap();    
	}
	catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void authenticateUser() {
	Object level = null;
	Assoc vo = new Assoc();
	vo.setId(111113);
	vo.setPassword("111113");
	try {
	    level = this.client.queryForObject("UserAdmin.authenticate", vo);
	    Assert.assertNotNull(level);
	    return;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    
    @Test
    public void authenticateInvalidUser() {
	Object level = null;
	Assoc vo = new Assoc();
	vo.setId(111113);
	vo.setPassword("johndoe");
	try {
	    level = this.client.queryForObject("UserAdmin.authenticate", vo);
	    Assert.assertNull(level);
	    return;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    
    
    @Test
    public void getUsers() {
	List <Assoc> list = null;
	try {
	    list = this.client.queryForList("UserAdmin.list");
	    Assert.assertTrue(list.size() > 0);
	    return;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    
    @Test
    public void addUser() {
	Assoc vo = new Assoc();
	vo.setId(77777);
	vo.setFirstName("Roy");
	vo.setMidInit("M");
	vo.setLastName("Terrell");
	vo.setSecurityLevel(3);
	vo.setPassword("johndo");
	vo.setLastLogin(new Date());
	vo.setLastChange(new Date());
	try {
	    this.client.startTransaction();
	    this.client.insert("UserAdmin.add", vo);
	    this.client.commitTransaction();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
	finally {
	    try {
		this.client.endTransaction();
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }
    
    @Test
    public void updateUser() {
	Assoc vo = new Assoc();
	vo.setId(77777);
	vo.setNewId(123456);
	vo.setFirstName("Dennis");
	vo.setMidInit("M");
	vo.setLastName("Chambers");
	vo.setSecurityLevel(2);
	vo.setPassword("123456");
	vo.setLastChange(new Date());
	try {
	    this.client.update("UserAdmin.update", vo);
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
    
    @Test
    public void deleteUser()  {
	Assoc vo = new Assoc();
	vo.setId(123456);
	try {
	    int rc = this.client.delete("UserAdmin.delete", vo);
	    Assert.assertEquals(1, rc);
	    return;
	}
	catch (SQLException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }
}
