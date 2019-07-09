package com.api.filehandler;

import com.bean.RMT2Base;

/**
 * A bean representing the application's module configuration.
 * 
 * @author appdev
 *
 */
public class ModuleConfig extends RMT2Base {
    
    private int moduleCode;

    private String dbUrl;
    
    private String table;
    
    private String primaryKey;
    
    private String foreignKey;
    
    private String filePattern;
    
    /**
     * Creates an empty ModuleConfig object
     */
    public ModuleConfig() {
	return;
    }

    /**
     * Creates a ModuleConfig object containing the module code
     * 
     * @param moduleCode
     *         The module code
     */
    public ModuleConfig(int moduleCode) {
	this.moduleCode = moduleCode;
	return;
    }
    
    /**
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @return the primaryKey
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the foreignKey
     */
    public String getForeignKey() {
        return foreignKey;
    }

    /**
     * @param foreignKey the foreignKey to set
     */
    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * @return the filePattern
     */
    public String getFilePattern() {
        return filePattern;
    }

    /**
     * @param filePattern the filePattern to set
     */
    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    /**
     * @return the moduleCode
     */
    public int getModuleCode() {
        return moduleCode;
    }

    /**
     * @param moduleCode the moduleCode to set
     */
    public void setModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * @return the dbUrl
     */
    public String getDbUrl() {
        return dbUrl;
    }

    /**
     * @param dbUrl the dbUrl to set
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

}
