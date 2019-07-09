package com.api.bean;

import com.api.DaoApi;

import com.api.db.orm.DataSourceAdapter;

import com.util.SystemException;

/**
 * Factory for creating java bean resources to be managed by the {@link com.api.DaoApi DaoApi} 
 * interface.
 * 
 * @author roy.terrell
 *
 */
public class BeanDaoFactory extends DataSourceAdapter {

    public static DaoApi createApi() {
        try {
            DaoApi api = new BeanDataSource();
            return api;
        }
        catch (SystemException e) {
            return null;
        }
    }

}