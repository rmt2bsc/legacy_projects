package com.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.SystemException;
import com.util.ContactBusinessException;
import com.util.ContactException;

import com.bean.Business;
import com.bean.GeneralCodes;

/**
 * 
 * @author RTerrell
 *
 */
public interface ContactBusinessApi extends BaseDataSource {
    Business createBusiness() throws SystemException;
    Business createBusiness(HttpServletRequest req) throws SystemException;
    Business findBusById(int arg0) throws ContactBusinessException;
    List findBusByContactFirstName(String arg0) throws ContactBusinessException;
    List findBusByContactLastName(String arg0) throws ContactBusinessException;
    List findBusByBusType(int arg0) throws ContactBusinessException;
    List findBusByServType(int arg0) throws ContactBusinessException;
    List findBusByLongName(String arg0) throws ContactBusinessException;
    List findBusByShortName(String arg0) throws ContactBusinessException;
    List findBusByTaxId(String arg0) throws ContactBusinessException;
    List findBusByWebsite(String arg0) throws ContactBusinessException;
    List findContact(String arg0) throws ContactException;
    List findGeneralCodeGroups() throws ContactException;
    GeneralCodes findGeneralCode(int _codeId) throws ContactException;
    List findGeneralCodes(int _grpId) throws ContactException;
    int maintainBusiness(Business contact) throws ContactBusinessException;
    Business copyBusiness(Business old) throws ContactBusinessException;
    int deleteBusiness(int _id) throws ContactBusinessException;
    void validateBusiness(Business _base) throws ContactBusinessException;
}
