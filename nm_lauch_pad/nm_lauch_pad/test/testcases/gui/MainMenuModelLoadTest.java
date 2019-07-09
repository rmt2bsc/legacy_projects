/**
 * 
 */
package testcases.gui;


import junit.framework.Assert;

import modules.MainMenuListModelImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author rterrell
 *
 */
public class MainMenuModelLoadTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void load() {
	MainMenuListModelImpl obj = new MainMenuListModelImpl();
	int rc = obj.getSize();
	Assert.assertTrue(rc > 0);
    }
}
