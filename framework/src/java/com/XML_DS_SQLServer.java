package com;

import com.util.RMT2Utility;
import java.sql.*;
import java.util.*;

//import com.ibm.xml.parser.TXDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.ProcessingInstruction;
import java.io.PrintWriter;
import java.io.FileWriter;

public class XML_DS_SQLServer {

    public Connection con;

    public Statement stmt;

    public Document doc;

    public String filename;

    public String docClass;

    public String userid;

    public String password;

    public String database;

    private String dbPropName;

    public String driver;

    public String url;

    public ResultSet rsSysTable;

    public ResultSet rsSysColumn;

    public ResultSet rsSysDomain;

    public ResourceBundle prop;

    public String tableName;

    public int tableId;

    public String formattedTableName;

    public XML_DS_SQLServer() {

        try {
            Properties dbParms = new Properties();
            prop = ResourceBundle.getBundle(RMT2Utility.CONFIG_SYSTEM);

            // Get path for generated output
            filename = prop.getString("orm_generated_output");

            // Get configuration data      
            docClass = prop.getString("docClass");
            userid = prop.getString("userid");
            password = prop.getString("password");
            database = prop.getString("database");
            dbPropName = prop.getString("dbPropertyName");
            driver = prop.getString("dbdriver");
            url = prop.getString("dburl");

            //Set Database Parameters      
            dbParms.setProperty("user", userid);
            dbParms.setProperty("password", password);
            dbParms.setProperty(dbPropName, database);

            // Connect to database
            Driver dbDriver = (Driver) Class.forName(driver).newInstance();
            this.con = dbDriver.connect(url, dbParms);
            this.stmt = this.con.createStatement();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void produceDocuments() {

        //    try {
        //         // Get table names to create datasources
        //      rsSysTable = stmt.executeQuery("select name \"table_name\", id \"table_id\" from sysobjects where type in ('V', 'U')");
        //
        //      while (rsSysTable.next()) {
        //        tableName = rsSysTable.getString("table_name").trim().toLowerCase();
        //        formattedTableName = this.formatDsName(tableName);
        //        tableId = rsSysTable.getInt("table_id");
        //
        //           // Create Document Object
        //        doc = (Document) Class.forName(docClass).newInstance();
        //
        //           // Create root element
        //        Element root = doc.createElement("DataSource");
        //        root.setAttribute("Name", formattedTableName + "View");
        //
        //          // Create Select Element
        //        String selectList = this.buildSelectList(tableId);
        //        Element selectItem = doc.createElement("Sql");
        //        selectItem.setAttribute("Select", selectList);
        //        selectItem.setAttribute("From", tableName);
        //        selectItem.setAttribute("Where", "");
        //        selectItem.setAttribute("GroupBy", "");
        //        selectItem.setAttribute("Having", "");
        //        selectItem.setAttribute("OrderBy", "");
        //        root.appendChild(selectItem);
        //
        //          // Create Table Usage Element
        //        Element tableItem = doc.createElement("TableUsage");
        //        tableItem.setAttribute("Name", formattedTableName  + "Table");
        //        tableItem.setAttribute("DBName", tableName.trim().toLowerCase());
        //        tableItem.setAttribute("Updateable", "true");
        //        root.appendChild(tableItem);
        //
        //          // Create Column Attributes
        //        int result = this.buildColAttribute(tableId, root);
        //
        //        doc.appendChild(root);
        //        ((TXDocument) doc).setVersion("1.0");
        //        FileWriter file = new FileWriter(filename + formattedTableName + "View.xml");
        //        ((TXDocument) doc).printWithFormat(new PrintWriter(file));
        //        System.out.println(formattedTableName + "View - created successfully!");
        //      }
        //      System.out.println("DataSource Creation Process Completed!!!!!!!");
        //    }
        //    catch (Exception e) {
        //      System.out.println("Error: " + e.getMessage());
        //    }
    }

    public String buildSelectList(int _tableId) throws Exception {

        String sql = "select name \"column_name\" from syscolumns where id = " + _tableId;
        StringBuffer selectList = new StringBuffer(200);
        ResultSet rsCols = null;
        int colCount = 0;
        String col = null;

        try {
            rsCols = this.con.createStatement().executeQuery(sql);
            while (rsCols.next()) {
                col = rsCols.getString("column_name").trim();
                selectList.append((++colCount > 1 ? ", \n\t\t" + col.toLowerCase() : col.toLowerCase()));
            }
            return selectList.toString();
        }
        catch (SQLException e) {
            throw e;
        }
    }

    public int buildColAttribute(int _tableId, Element _root) throws Exception {

        String sql = "select * from syscolumns where id = " + _tableId;
        StringBuffer selectList = new StringBuffer(200);
        ResultSet rsCols = null;
        ResultSet rsKey = null;
        ResultSet rsType = null;
        CallableStatement call;
        int colCount = 0;
        String colName = null;
        String pKey = null;
        String nullable = null;
        String dataTypeName = null;
        String javaType = null;
        int dataType = 0;
        String proc;

        try {
            proc = "sp_pkeys " + tableName;
            call = this.con.prepareCall(proc);
            rsKey = call.executeQuery();
            rsCols = this.con.createStatement().executeQuery(sql);
            while (rsCols.next()) {
                colName = rsCols.getString("name").trim();
                pKey = (this.isPrimaryKey(rsKey, colName) ? "true" : "false");
                nullable = (rsCols.getInt("status") == 8 ? "true" : "false");
                dataType = rsCols.getInt("usertype");
                rsType = this.con.createStatement().executeQuery("select name from systypes where usertype = " + dataType);
                rsType.next();
                dataTypeName = rsType.getString("name");
                switch (dataType) {
                case 5:
                case 6:
                case 7:
                case 13:
                case 16:
                    javaType = "java.sql.Types.INTEGER";
                    break;

                case 8:
                case 10:
                case 11:
                case 14:
                case 17:
                case 21:
                case 23:
                case 24:
                case 25:
                case 26:
                    javaType = "java.sql.Types.DOUBLE";
                    break;

                case 1:
                case 2:
                case 3:
                case 4:
                case 19:
                case 20:
                    javaType = "java.sql.Types.VARCHAR";
                    break;

                case 12:
                case 15:
                    javaType = "java.sql.Types.DATE";
                    break;

                default:
                    javaType = "";
                    dataTypeName = "";
                    break;
                } // end switch

                Element item = doc.createElement("ColAttr");
                if (pKey.trim().equalsIgnoreCase("true")) {
                    //        item.setAttribute("Name", formattedTableName + "Id");
                    //        item.setAttribute("Name", "Id");
                    item.setAttribute("Name", this.formatDsName(colName.trim()));
                }
                else {
                    item.setAttribute("Name", this.formatDsName(colName.trim()));
                }
                item.setAttribute("DBName", colName.toLowerCase().trim());
                item.setAttribute("SqlType", dataTypeName.trim());
                item.setAttribute("IsNull", nullable.trim());
                item.setAttribute("JavaType", javaType.trim());
                item.setAttribute("PrimaryKey", pKey.trim());
                item.setAttribute("TableName", formattedTableName + "Table");
                item.setAttribute("Updateable", "true");
                item.setAttribute("DisplayHeader", "");
                item.setAttribute("Computed", "false");
                item.setAttribute("DataValue", "");
                _root.appendChild(item);
                colCount++;
                //      rsKey.beforeFirst();
            } // end while

            return colCount;
        }
        catch (SQLException e) {
            throw e;
        }
    }

    public String formatDsName(String value) {

        String newValue = "";
        String token = "";

        StringTokenizer str = new StringTokenizer(value, "_");
        while (str.hasMoreTokens()) {
            token = str.nextToken();
            newValue += this.wordCap(token.toLowerCase());
        }
        return newValue;
    }

    public String wordCap(String value) {

        StringBuffer capValue = new StringBuffer(100);

        for (int ndx = 0; ndx < value.length(); ndx++) {
            if (ndx == 0) {
                Character ch = new Character(value.charAt(ndx));
                capValue.append(ch.toString().toUpperCase());
            }
            else {
                Character ch = new Character(value.charAt(ndx));
                capValue.append(ch.toString());
            }
        }
        return capValue.toString();
    }

    public boolean isPrimaryKey(ResultSet rs, String colname) throws SQLException {

        String ndx;

        try {
            while (rs.next()) {
                ndx = rs.getString("column_name");
                if (ndx.equalsIgnoreCase(colname)) {
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e) {
            throw e;
        }
    }

    public void start() {
        try {
            XML_DS_SQLServer ds = new XML_DS_SQLServer();
            ds.produceDocuments();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Main method
    public static void main(String[] args) {
        try {
            XML_DS_SQLServer ds = new XML_DS_SQLServer();
            ds.produceDocuments();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
