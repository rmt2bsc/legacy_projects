package com.api;

import java.util.List;

import com.util.GeneralCodeException;

import com.bean.GeneralCodesGroup;

/**
 * Defines the General Code Group Manager interface
 * 
 * @author RTerrell
 *
 */
public interface GeneralCodeGroupManagerApi extends BaseDataSource {
   GeneralCodesGroup findGroupById(int value) throws GeneralCodeException;
   List findGroupByDescription(String value) throws GeneralCodeException;
   List findGroup(String _criteria) throws GeneralCodeException;
   int maintainGroup(GeneralCodesGroup _grp) throws GeneralCodeException;
   void deleteGroup(GeneralCodesGroup _grp) throws GeneralCodeException;
}