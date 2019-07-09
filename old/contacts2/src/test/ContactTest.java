
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;

import com.api.Contact;
import com.api.DaoApi;
import com.api.config.ConfigAttributes;
import com.api.db.orm.DataSourceFactory;
import com.api.security.pool.AppPropertyPool;
import com.api.personal.PersonFactory;
import com.api.postal.AddressComponentsFactory;
import com.api.postal.ZipcodeApi;
import com.api.postal.ZipcodeException;
import com.bean.OrmBean;
import com.bean.Person;
import com.bean.VwZipcode;
import com.bean.db.DatabaseConnectionBean;

public class ContactTest implements ConfigAttributes {
    DatabaseConnectionBean con;
    Properties appProps;

    /**
     * @param args
     */
    public static void main(String[] args) {
	
        ContactTest test;
	try {
	    test = new ContactTest();
	    test.memoryTest();
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
        
        
//        String arg1 = args[0];
//        String arg2 = System.getProperty("progArg");
//        arg2 = System.getProperty("vmArg");
//        System.out.println("Prog Arg: " + arg1);
//        System.out.println("VM Arg: " + arg2);
//        int rows;
//        rows = test.delete(0);
//        int key = test.add();
//        rows = test.delete(key);
    }

    public ContactTest() throws Exception {
        this.appProps = new Properties();

        Properties props = new Properties();
       // props.put("User", "dba");
        //props.put("Password", "sql");
        // props.put("DatabaseFile",
        // "C:\\MyStuff\\data\\sql\\authentication.db");
        //props.put("RemotePWD", ",DatabaseName=authentication");
       //props.put("RemotePWD", ",DatabaseName=contacts");
        //props.put("RemotePWD", ",DatabaseName=demo");

        this.setupConfig();

        // Open the connection. May throw a SQLException.
        try {
            // Use jConnect v6 driver
            DriverManager.registerDriver((Driver) Class.forName("com.sybase.jdbc3.jdbc.SybDriver").newInstance());
            //Connection connection = DriverManager.getConnection("jdbc:sybase:Tds:localhost:2638/contacts", props);
            Connection connection = DriverManager.getConnection("jdbc:sybase:Tds:localhost:2638?ServiceName=contacts", "dba", "sql");
            //Connection connection = DriverManager.getConnection("jdbc:sybase:Tds:localhost:2638", props);
            //Connection connection = DriverManager.getConnection("jdbc:sybase:Tds:DAL1-WXP-D317:2638", props);
            this.con = new DatabaseConnectionBean();
            this.con.setDbConn(connection);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e);
        }
    }
    
    public void memoryTest() {
        ZipcodeApi api = AddressComponentsFactory.createZipcodeApi(this.con);
        Runtime rt = Runtime.getRuntime();
        try {
            
            System.out.println("Free JVM memory: " + rt.freeMemory());
            System.out.println("Max JVM memory: " + rt.maxMemory());
            System.out.println("Total JVM memory: " + rt.totalMemory());
            Object results = api.findZipByCode(71106);
            results = api.findZip(" area_code = '318'");
            List list = (List) results;
            VwZipcode zip = (VwZipcode) list.get(0);
            System.out.println(zip.getCityAliasName());
        }
        catch (ZipcodeException e) {
            System.out.println("Free JVM memory after error: " + rt.freeMemory());
            System.out.println(e);
        }
    }
    
    public int add() {
        DaoApi dao = null;
        try {
            dao = DataSourceFactory.createDao(this.con);

            Person user = PersonFactory.createPerson();
            user.addCriteria(Person.PROP_PERSONID, "admin");
            Object results[] = dao.retrieve(user);
            user = (Person) results[0];
            System.out.println("First name: " + user.getFirstname());

            // Create a user
            user = PersonFactory.createPerson();
            user.setFirstname("Tank");
            user.setLastname("Johnson");
            user.setEmail("fdksladfksl@ test.com");
            int rc = dao.insertRow(user, true);
            dao.commitUOW();
            System.out.println("new key: " + rc);
            return rc;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            dao.rollbackUOW();
            return -1;
        }
    }
    
    
    public int delete(int key) {
        DaoApi dao = null;
        try {
            dao = DataSourceFactory.createDao(this.con);
            Person user = PersonFactory.createPerson();
            user.addCriteria(Person.PROP_PERSONID, key);
            int rc = dao.deleteRow(user);
            dao.commitUOW();
            System.out.println("Total rows deleted: " + rc);
            return rc;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            dao.rollbackUOW();
            return -1;
        }
    }
    
    

    public String getProperty(String key) {
        if (this.appProps == null) {
            return null;
        }
        String result = this.appProps.getProperty(key);
        if (result == null) {
            result = System.getProperty(key);
        }
        return result;
    }

    public void setupConfig() {
        this.appProps.setProperty("SAXDriver", "com.ibm.xml.parsers.SAXParser");
        this.appProps.setProperty("app_dir", "\\contacts");
        this.appProps.setProperty("datasource_dir", "\\src\\java\\com\\bean");
        this.appProps.setProperty(OrmBean.PACKAGE_PREFIX, "com.bean.");

        System.setProperty("webapps_drive", "c:");
        //System.setProperty("webapps_dir", "\\MyStuff\\source");
        System.setProperty("webapps_dir", "\\projects");
        AppPropertyPool pool = AppPropertyPool.getInstance();
        pool.addProperties(this);
    }
}
