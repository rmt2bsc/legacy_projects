/***********************************************************************
 * Module:  GeneralCodeManagerApi
 * Author:  appdev
 * Purpose: Defines the Interface General Code Manager Api
 ***********************************************************************/

package com.api;

import java.util.List;

import com.util.GeneralCodeException;

import com.bean.GeneralCodes;

/**
 * Defines the General Code Manager interface
 * 
 * @author RTerrell
 *
 */
public interface GeneralCodeManagerApi extends BaseDataSource {
   GeneralCodes findCodeById(int value) throws GeneralCodeException;
   List findCodeByGroup(int value) throws GeneralCodeException;
   List findCodeByDescription(String value) throws GeneralCodeException;
   List findCode(String _criteria) throws GeneralCodeException;
   int maintainCode(GeneralCodes _code) throws GeneralCodeException;
   void deleteCode(GeneralCodes _grp) throws GeneralCodeException;
}