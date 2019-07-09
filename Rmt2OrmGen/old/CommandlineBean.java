/**
 * 
 */
package com;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RTerrell
 * 
 */
public class CommandlineBean extends AbstractOrmClassCreator {
    /**
     * @param dataSource
     * @throws Exception
     */
    public CommandlineBean(Object dataSource) throws Exception {
        super(dataSource);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ObjectSelector#getSelectedObjeects(java.lang.Object)
     */
    public List getSelectedObjects(Object source) throws Exception {
        ResultSet rsSysTable;
        String sql = this.prop.getString("table_list_query");

        // Get table names to create datasources
        Statement stmt = this.con.createStatement();
        rsSysTable = stmt.executeQuery(sql);

        List list = new ArrayList();
        while (rsSysTable.next()) {
            OrmObjectData data = new OrmObjectData();
            String tableName = rsSysTable.getString("table_name").trim().toLowerCase();
            int tableId = rsSysTable.getInt("table_id");
            data.setObjectId(tableId);
            data.setObjectName(tableName);
            list.add(data);
        }
        return list;
    }

    
    public List getObjectAttributes(Object source) throws Exception {
	String sql = this.prop.getString("column_list_query");
	int key;
	if (source != null && source instanceof Integer) {
	    key = ((Integer) source).intValue();
	    sql += key;
	}
	else {
	    return null;
	}
	
        // Get column names that belong to source
        Statement stmt = this.con.createStatement();
        ResultSet rsSysTable = stmt.executeQuery(sql);

        List list = new ArrayList();
        while (rsSysTable.next()) {
            OrmObjectData data = new OrmObjectData();
            String colName = rsSysTable.getString("name").trim();
            int dataType = rsSysTable.getInt("usertype");
            data.setObjectId(dataType);
            data.setObjectName(colName);
            list.add(data);
        }
        return list;

    }
    
    

    /**
     * No Action
     */
    public List getDataSourceQuery(Object source) throws Exception {
	return null;
    }

    public static void main(String[] args) {
        try {
            AbstractOrmClassCreator bean = new CommandlineBean(args);
            bean.produceBeans();    
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
