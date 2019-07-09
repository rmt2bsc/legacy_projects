package com;

import java.sql.*;
import java.util.*;

import com.ibm.xml.parser.TXDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.PrintWriter;
import java.io.FileWriter;

public abstract class AbstractOrmDataSourceCreator extends AbstractOrmDataProvider implements ObjectSelector {

    public Document doc;
    public String docClass;
    public ResultSet rsSysColumn;
    public ResultSet rsSysDomain;
    public String tableName;
    public int tableId;
    public String formattedTableName;
    private Object source;

    
    
    public AbstractOrmDataSourceCreator(Object dataSource) throws Exception {
        super(dataSource);
    }

    public void produceDocuments() {

        try {
            List list = this.getSelectedObjects(this.source);
            for (Object data : list) {
                tableName = ((OrmObjectData) data).getObjectName().trim().toLowerCase();
                formattedTableName = this.formatDsName(tableName);
                tableId = ((OrmObjectData) data).getObjectId();

                // Create Document Object
                doc = (Document) Class.forName(docClass).newInstance();

                // Create root element
                Element root = doc.createElement("DataSource");
                root.setAttribute("Name", formattedTableName + "View");

                // Create Select Element
                String selectList = this.buildSelectList(tableId);
                Element selectItem = doc.createElement("Sql");
                selectItem.setAttribute("Select", selectList);
                selectItem.setAttribute("From", tableName);
                selectItem.setAttribute("Where", "");
                selectItem.setAttribute("GroupBy", "");
                selectItem.setAttribute("Having", "");
                selectItem.setAttribute("OrderBy", "");
                root.appendChild(selectItem);

                // Create Table Usage Element
                Element tableItem = doc.createElement("TableUsage");
                tableItem.setAttribute("Name", formattedTableName + "Table");
                tableItem.setAttribute("DBName", tableName.trim().toLowerCase());
                tableItem.setAttribute("Updateable", "true");
                root.appendChild(tableItem);

                // Create Column Attributes
                this.buildColAttribute(tableId, root);

                doc.appendChild(root);
                ((TXDocument) doc).setVersion("1.0");
                FileWriter file = new FileWriter(filename + formattedTableName + "View.xml");
                ((TXDocument) doc).printWithFormat(new PrintWriter(file));
                System.out.println(formattedTableName + "View - created successfully!");
            }
            System.out.println("DataSource Creation Process Completed!!!!!!!");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String buildSelectList(int _tableId) throws Exception {
	List list = this.getDataSourceQuery(new Integer(_tableId));
	StringBuffer selectList = new StringBuffer(200);
	int colCount = 0;
	for (Object data : list) {
	    String colName = ((OrmObjectData) data).getObjectName().trim().toLowerCase();
	    selectList.append((++colCount > 1 ? ", \n\t\t" + colName.toLowerCase() : colName.toLowerCase()));
	}
	return selectList.toString();
    }

    public int buildColAttribute(int _tableId, Element _root) throws Exception {

        String sql = "select * from syscolumn where table_id = " + _tableId;
        ResultSet rsCols = null;
        ResultSet rsType = null;
        int colCount = 0;
        String colName = null;
        String pKey = null;
        String nullable = null;
        String dataTypeName = null;
        String javaType = null;
        int dataType = 0;

        rsCols = this.con.createStatement().executeQuery(sql);
        try {
            while (rsCols.next()) {
                colName = rsCols.getString("column_name").trim();
                pKey = (rsCols.getString("pkey").equalsIgnoreCase("Y") ? "true" : "false");
                nullable = (rsCols.getString("nulls").equalsIgnoreCase("Y") ? "true" : "false");
                dataType = rsCols.getInt("domain_id");
                rsType = this.con.createStatement().executeQuery("select * from sysdomain where domain_id = " + dataType);
                rsType.next();
                dataTypeName = rsType.getString("domain_name");
                switch (dataType) {
                case 1:
                case 2:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                    javaType = "java.sql.Types.INTEGER";
                    break;

                case 3:
                case 4:
                case 5:
                case 27:
                    javaType = "java.sql.Types.DOUBLE";
                    break;
                case 7:
                case 8:
                case 33:
                case 34:
                    dataTypeName = "java.sql.Types.CHAR";
                    break;

                case 9:
                case 10:
                case 11:
                case 12:
                case 28:
                case 32:
                case 35:
                case 36:
                    javaType = "java.sql.Types.VARCHAR";
                    break;

                case 6:
                case 13:
                case 14:
                    javaType = "java.sql.Types.DATE";
                    break;

                default:
                    javaType = "";
                    dataTypeName = "";
                    break;
                } // end switch

                Element item = doc.createElement("ColAttr");
                if (pKey.trim().equalsIgnoreCase("true")) {
                    // item.setAttribute("Name", formattedTableName + "Id");
                    // item.setAttribute("Name", "Id");
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
                // rsKey.beforeFirst();
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

    public boolean isPrimaryKey(ResultSet rs, String colname)
            throws SQLException {
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

}
