package com;

import java.util.List;
/**
 * @author RTerrell
 *
 */
public interface ObjectSelector {
    /**
     * Identify the list of objects that are to be used to produce ORM 
     * resoruces and return the results to the user.
     * 
     * @param source The of the data to examine.
     * @return A List of arbitrary objects as the results of the implementation.
     * @throws Exception
     */
    List getSelectedObjects(Object source) throws Exception;
    
    /**
     * 
     * @param source
     * @return
     * @throws Exception
     */
    List getObjectAttributes(Object source) throws Exception;
    
    /**
     * 
     * @param source
     * @return
     * @throws Exception
     */
    List getDataSourceQuery(Object source) throws Exception;
}
