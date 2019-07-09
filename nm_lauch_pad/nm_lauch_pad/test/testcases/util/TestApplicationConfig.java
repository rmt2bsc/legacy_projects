package testcases.util;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.nv.util.AppInitException;
import com.nv.util.AppUtil;

/**
 * @author rterrell
 *
 */
public class TestApplicationConfig {

    @Test
    public void confgLogger() {
	AppUtil util = new AppUtil();
	try {
	    util.initLogger();
	}
	catch (AppInitException e) {
	    e.printStackTrace();
	    return;
	}
	Logger logger = Logger.getLogger("TestLoggerConfig");
	logger.info("Application initialized");
    }
}
