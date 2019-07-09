import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;

import com.api.DaoApi;
import com.api.config.ConfigAttributes;
import com.api.db.orm.DataSourceFactory;
import com.api.security.pool.AppPropertyPool;
import com.api.security.user.UserFactory;
import com.bean.OrmBean;
import com.bean.UserLogin;
import com.bean.db.DatabaseConnectionBean;

public class AuthTest implements ConfigAttributes {
    DatabaseConnectionBean con;
    Properties appProps;

    /**
     * @param args
     */
    public static void main(String[] args) {
        AuthTest test = new AuthTest();
//        String arg1 = args[0];
//        String arg2 = System.getProperty("progArg");
//        arg2 = System.getProperty("vmArg");
//        System.out.println("Prog Arg: " + arg1);
//        System.out.println("VM Arg: " + arg2);
        int rows;
        rows = test.delete(0);
        int key = test.add();
        rows = test.delete(key);
    }

    public AuthTest() {
        this.appProps = new Properties();

        Properties props = new Properties();
        props.put("User", "dba");
        props.put("Password", "sql");
        // props.put("DatabaseFile",
        // "C:\\MyStuff\\data\\sql\\authentication.db");
        props.put("RemotePWD", ",DatabaseName=authentication");

        this.setupConfig();

        // Open the connection. May throw a SQLException.
        try {
            // Use jConnect v6 driver
            DriverManager.registerDriver((Driver) Class.forName("com.sybase.jdbc3.jdbc.SybDriver").newInstance());
            //Connection connection = DriverManager.getConnection("jdbc:sybase:Tds:localhost:2638", props);
            Connection connection = DriverManager.getConnection("jdbc:sybase:Tds:localhost:2638", "dba", "sql");
            this.con = new DatabaseConnectionBean();
            this.con.setDbConn(connection);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public int add() {
        DaoApi dao = null;
        try {
            dao = DataSourceFactory.createDao(this.con);

            UserLogin user = UserFactory.createUserLogin();
            user.addCriteria(UserLogin.PROP_LOGINID, "admin");
            Object results[] = dao.retrieve(user);
            user = (UserLogin) results[0];
            System.out.println("First name: " + user.getFirstname());

            // Create a user
            user = UserFactory.createUserLogin();
            user.setUsername("testuser");
            user.setFirstname("Tank");
            user.setLastname("Johnson");
            user.setEmail("fdksladfksl@ test.com");
            user.setGrpId(1);
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
            UserLogin user = UserFactory.createUserLogin();
            user.addCriteria(UserLogin.PROP_LOGINID, key);
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
        this.appProps.setProperty("app_dir", "\\authentication");
        this.appProps.setProperty("datasource_dir", "\\src\\java\\com\\bean");
        this.appProps.setProperty(OrmBean.PACKAGE_PREFIX, "com.bean.");

        System.setProperty("webapps_drive", "c:");
        System.setProperty("webapps_dir", "\\MyStuff\\source");
        AppPropertyPool pool = AppPropertyPool.getInstance();
        pool.addProperties(this);
    }
}
