
package com.api;

import java.util.List;

import com.util.ProjectException;

/**
 * 
 * @author RTerrell
 *
 */
public interface ProjectBillingApi extends BaseDataSource {
    List findTime(String _criteria) throws ProjectException;
    int maintainTime(Object _base) throws ProjectException;
}
