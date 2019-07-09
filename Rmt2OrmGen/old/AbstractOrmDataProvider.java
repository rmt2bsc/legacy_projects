package com;

import java.sql.*;
import java.util.*;

public abstract class AbstractOrmDataProvider {

    protected Connection con;
    protected String filename;
    protected Object source;

    protected ResourceBundle prop;

    public AbstractOrmDataProvider(Object dataSource) throws Exception {
        this.source = dataSource;
        this.init();
    }

    public void init() throws Exception {
        String userid;
        String password;
        String database;
        String dbPropName;
        String driver;
        String url;

        try {
            Properties dbParms = new Properties();

            // Be sure to include as a System param for command line
            // invocations:
            // -DOrmGenProfile=[class path to application profile for ORM
            // Generation]
            this.prop = ResourceBundle.getBundle(System.getProperty("OrmGenProfile"));

            // Get path for generated output
            filename = prop.getString("orm_generated_output");

            // Get configuration data
            userid = prop.getString("userid");
            password = prop.getString("password");
            database = prop.getString("database");
            dbPropName = prop.getString("dbPropertyName");
            driver = prop.getString("dbdriver");
            url = prop.getString("dburl");

            // Set Database Parameters
            dbParms.setProperty("user", userid);
            dbParms.setProperty("password", password);
            dbParms.setProperty(dbPropName, database);

            // Connect to database
            Driver dbDriver = (Driver) Class.forName(driver).newInstance();
            this.con = dbDriver.connect(url, dbParms);
            // this.stmt = this.con.createStatement();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception(e);
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            throw new Exception(e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e);
        }
    }

}
