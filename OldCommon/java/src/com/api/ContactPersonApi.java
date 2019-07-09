package com.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.util.SystemException;
import com.util.ContactPersonException;
import com.util.ContactException;

import com.bean.Person;
import com.bean.GeneralCodes;

/**
 * 
 * @author RTerrell
 *
 */
public interface ContactPersonApi extends BaseDataSource {
    Person createPerson() throws SystemException;
    Person createPerson(HttpServletRequest req) throws SystemException;
    Person findPerById(int arg0) throws ContactPersonException;
    List findPerByFirstName(String arg0) throws ContactPersonException;
    List findPerByLastName(String arg0) throws ContactPersonException;
    List findPerByGender(int arg0) throws ContactPersonException;
    List findPerByMaritalStatus(int arg0) throws ContactPersonException;
    List findPerByRace(int arg0) throws ContactPersonException;
    List findPerBySSN(String arg0) throws ContactPersonException;
    List findPerByBirthDate(String arg0) throws ContactPersonException;
    List findPerByEMail(String arg0) throws ContactPersonException;
    List findContact(String arg0) throws ContactException;
    List findGeneralCodeGroups() throws ContactException;
    GeneralCodes findGeneralCode(int _codeId) throws ContactException;
    List findGeneralCodes(int _grpId) throws ContactException;
    int maintainPerson(Person contact) throws ContactPersonException;
    Person copyPerson(Person old) throws ContactPersonException;
    int deletePerson(int _id) throws ContactPersonException;
    void validatePerson(Person _obj) throws ContactPersonException;

}
