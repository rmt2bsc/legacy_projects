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
public class CommandlineDataSourceView extends AbstractOrmDataSourceCreator {
    /**
     * @param dataSource
     * @throws Exception
     */
    public CommandlineDataSourceView(Object dataSource) throws Exception {
        super(dataSource);
        this.docClass = this.prop.getString("docClass");
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

    
    
    public List getDataSourceQuery(Object source) throws Exception {
	String sql = this.prop.getString("ds_select_list");
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
            String colName = rsSysTable.getString("column_name").trim();
            data.setObjectName(colName);
            list.add(data);
        }
        return list;
    }
    
    
    
    public static void main(String[] args) {
        try {
            AbstractOrmDataSourceCreator bean = new CommandlineDataSourceView(args);
            bean.produceDocuments();    
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
