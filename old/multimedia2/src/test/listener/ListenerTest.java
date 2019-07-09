package listener;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.api.BatchFileException;
import com.api.filehandler.FileListenerConfig;
import com.api.filehandler.InboundFileFactory;
import com.api.filehandler.MimeFileProcessor;


/**
 * @author appdev
 *
 */
public class ListenerTest {
    private String mimeConfigFile;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	this.mimeConfigFile = MimeFileProcessor.CONFIG_CLASSPATH + "TEST_" + InboundFileFactory.configFile;
	System.setProperty("mail.host.smtp", "outgoing.verizon.net");
	System.setProperty("mail.authentication", "true");
	System.setProperty("mail.userId", "rmt2bsc2");
	System.setProperty("mail.password", "drum7777");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoadConfiguration() {
	FileListenerConfig config = InboundFileFactory.getConfigInstance(this.mimeConfigFile);
	Assert.assertEquals(10000, config.getPollFreq());
	Assert.assertEquals(168, config.getArchiveAge());
	Assert.assertEquals("com.api.db.DefaultSybASABinaryImpl", config.getHandlerClass());
	Assert.assertEquals("rmt2bsc2@verizon.net", config.getReportEmail());
	Assert.assertNotNull(config.getModules().get(0));
	Assert.assertNotNull(config.getModules().get(1));
	Assert.assertEquals("acct_*.*", config.getModules().get(0).getFilePattern());
	Assert.assertEquals("proj_*.*", config.getModules().get(1).getFilePattern());
	Assert.assertEquals("jdbc:sybase:Tds:rmtdaldev04:2638?ServiceName=mime", config.getDbUrl());
    }

    @Test
    public void testInboundProcessor() {
	FileListenerConfig config = InboundFileFactory.getConfigInstance(this.mimeConfigFile);
	MimeFileProcessor proc = InboundFileFactory.createCommonMimeFileProcessor(config);
	try {
	    proc.initConnection(this.mimeConfigFile);
	    proc.processFiles(null, null);
	}
	catch (BatchFileException e) {
	    e.printStackTrace();
	}
	finally {
	    proc.close();
	}

    }
}
