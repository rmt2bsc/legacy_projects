package com.api.bean;

import com.api.DaoApi;

/**
 * @author appdev
 *
 */
public interface BeanDao extends DaoApi {

    /**
     * Returns the java type of a bean property.
     * 
     * @param property the name of the bean property.
     * @return int
     */
    int getJavaType(String property);
}
