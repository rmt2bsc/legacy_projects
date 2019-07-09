package com.bean.bindings;


import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.api.codes.CodesApi;
import com.api.codes.CodesFactory;
import com.api.codes.GeneralCodeException;
import com.api.postal.AddressComponentsFactory;
import com.api.postal.ZipcodeApi;
import com.api.postal.ZipcodeException;
import com.api.security.authentication.AuthenticationException;
import com.api.security.authentication.AuthenticationFactory;
import com.api.security.authentication.RMT2SessionBean;

import com.bean.Country;
import com.bean.GeneralCodes;
import com.bean.GeneralCodesGroup;
import com.bean.IpLocation;
import com.bean.RMT2Base;
import com.bean.TimeZone;
import com.bean.VwBusinessAddress;
import com.bean.VwCodes;
import com.bean.VwCommonContact;
import com.bean.VwPersonAddress;
import com.bean.VwStateCountry;
import com.bean.VwZipcode;
import com.bean.Zipcode;

import com.bean.criteria.ContactCriteria;
import com.bean.db.DatabaseConnectionBean;
import com.controller.Request;

import com.util.RMT2Date;
import com.util.SystemException;
import com.xml.schema.bindings.AddressType;
import com.xml.schema.bindings.AppRoleType;
import com.xml.schema.bindings.BusinessType;
import com.xml.schema.bindings.CitytypeType;
import com.xml.schema.bindings.CodeDetailType;
import com.xml.schema.bindings.CommonContactCriteria;
import com.xml.schema.bindings.CommonContactType;
import com.xml.schema.bindings.ContacttypeType;
import com.xml.schema.bindings.CountryType;
import com.xml.schema.bindings.GenerationType;
import com.xml.schema.bindings.IpDetails;
import com.xml.schema.bindings.LookupCodeType;
import com.xml.schema.bindings.ObjectFactory;
import com.xml.schema.bindings.PersonType;
import com.xml.schema.bindings.StateType;
import com.xml.schema.bindings.TimezoneType;
import com.xml.schema.bindings.UserSessionType;
import com.xml.schema.bindings.ZipcodeFullType;
import com.xml.schema.bindings.ZipcodeType;



/**
 * A factory for creating JAXB objects using contact related objects.
 * 
 * @author Roy Terrell
 *
 */
public class JaxbContactsFactory extends RMT2Base {
    
    private static Logger logger = Logger.getLogger(JaxbContactsFactory.class);
    
    private DatabaseConnectionBean con;

    /**
     * Creates a JaxbContactsFactory that is capable of functioning without the use of an 
     * external datasource.
     */
    public JaxbContactsFactory() {
	super();
    }
    
    /**
     * Creates a JaxbContactsFactory that is initialized with a database connection.
     * 
     * @param con
     *          {@link com.bean.db.DatabaseConnectionBean DatabaseConnectionBean}
     */
    public JaxbContactsFactory(DatabaseConnectionBean con) {
	super();
	this.con = con;
    }

    /**
     * Creates a new JAXB BusinessType instance.
     * 
     * @return
     *        {@link com.xml.schema.bindings.BusinessType BusinessType}
     */
    public BusinessType createBusinessTypeInstance() {
	ObjectFactory f = new ObjectFactory();
	BusinessType bt = f.createBusinessType();
	bt.setBusinessId(BigInteger.valueOf(0));
	
	try {
	    CodeDetailType cdt = this.getCodeDetail();
	    bt.setEntityType(cdt);
	    cdt = this.getCodeDetail();
	    bt.setServiceType(cdt);
	}
	catch (GeneralCodeException e) {
	    // Do nothing...
	}
	
	bt.setLongName(null);
	bt.setShortName(null);
	bt.setContactFirstname(null);
	bt.setContactLastname(null);
	bt.setContactPhone(null);
	bt.setContactExt(null);
	bt.setContactEmail(null);
	bt.setTaxId(null);
	bt.setWebsite(null);
	
	AddressType at = this.getAddress(); 
	bt.setAddress(at);
	return bt;
    }
    
    /**
     * Creates a new CodeDetailType instance.
     * 
     * @return
     *        {@link com.xml.schema.bindings.CodeDetailType CodeDetailType}
     * @throws GeneralCodeException
     */
    public CodeDetailType getCodeDetail() throws GeneralCodeException {
	ObjectFactory f = new ObjectFactory();
	CodeDetailType cdt = f.createCodeDetailType();
	cdt.setCodeId(BigInteger.valueOf(0));
//	cdt.setCodeGroupId(BigInteger.valueOf(0));
	cdt.setLongdesc(null);
	cdt.setShortdesc(null);
	return cdt;
    }
    
    /**
     * 
     * @return
     */
    public AddressType getAddress() {
	ObjectFactory f = new ObjectFactory();
	AddressType at = f.createAddressType();
	at.setAddrId(BigInteger.valueOf(0));
	at.setPersonId(BigInteger.valueOf(0));
	at.setBusinessId(BigInteger.valueOf(0));
	at.setAddr1(null);
	at.setAddr2(null);
	at.setAddr3(null);
	at.setAddr4(null);
	try {
	    ZipcodeType z = this.getZipcode();
	    at.setZip(z);
	}
	catch (ZipcodeException e) {
	    // Do nothing...
	}
	at.setZipExt(BigInteger.ZERO);
	at.setPhoneCell(null);
	at.setPhoneFax(null);
	at.setPhoneHome(null);
	at.setPhoneMain(null);
	at.setPhonePager(null);
	at.setPhoneWork(null);
	at.setPhoneWorkExt(null);
	return at;
    }
    
    /**
     * Create a new ZipcodeType instance and return the instance to the user.
     * 
     * @return
     *         {@link com.xml.schema.bindings.ZipcodeType ZipcodeType}
     * @throws ZipcodeException
     */
    public ZipcodeType getZipcode() throws ZipcodeException {
	ObjectFactory f = new ObjectFactory();
	Zipcode z = AddressComponentsFactory.createZipcode();
	ZipcodeType zip = f.createZipcodeType();
	zip.setZipId(BigInteger.valueOf(z.getZipId()));
	zip.setZipcode(BigInteger.valueOf(z.getZip()));
	zip.setAreaCode(z.getAreaCode());
	zip.setCity(z.getCity());
	zip.setCityAliasAbbr(z.getCityAliasAbbr());
	zip.setCityAliasName(z.getCityAliasName());
	zip.setCountyName(z.getCountyName());
	zip.setState(z.getState());
	return zip;
    }
    
    /**
     * Creates a BusinessType object from data contained in a VwBusinessAddress object.
     * 
     * @param contact
     *         {@link com.bean.VwBusinessAddress VwBusinessAddress}
     * @return
     *         {@link com.xml.schema.bindings.BusinessType BusinessType}
     */
    public BusinessType createBusinessTypeInstance(VwBusinessAddress contact) {
	ObjectFactory f = new ObjectFactory();
	BusinessType bt = f.createBusinessType();
	bt.setBusinessId(BigInteger.valueOf(contact.getBusinessId()));
	
	try {
	    CodeDetailType cdt = this.getCodeDetail(contact.getBusEntityTypeId());
	    bt.setEntityType(cdt);
	    cdt = this.getCodeDetail(contact.getBusServTypeId());
	    bt.setServiceType(cdt);
	}
	catch (GeneralCodeException e) {
	    // Do nothing...
	}
	
	bt.setLongName(contact.getBusLongname());
	bt.setShortName(contact.getBusShortname());
	bt.setContactFirstname(contact.getBusContactFirstname());
	bt.setContactLastname(contact.getBusContactLastname());
	bt.setContactPhone(contact.getBusContactPhone());
	bt.setContactExt(contact.getBusContactExt());
	bt.setContactEmail(contact.getContactEmail());
	bt.setTaxId(contact.getBusTaxId());
	bt.setWebsite(contact.getBusWebsite());
	
	AddressType at = this.getAddress(contact); 
	bt.setAddress(at);
	return bt;
    }


    /**
     * Creates a PersonType object from data contained in a VwPersonAddress object.
     * 
     * @param contact
     *         {@link com.bean.VwPersonAddress VwPersonAddress}
     * @return
     *         {@link com.xml.schema.bindings.PersonType PersonType}
     */
    public PersonType createPersonalTypeInstance(VwPersonAddress contact) {
        ObjectFactory f = new ObjectFactory();
        PersonType pt = f.createPersonType();
        
        // Bind primary key
        pt.setPersonId(BigInteger.valueOf(contact.getPersonId()));
        
        // Bind various CodeDetailType types.
        try {
            // Bind title
            CodeDetailType cdt = this.getCodeDetail(contact.getPerTitle());
            pt.setTitle(cdt);
            // Bind gender
            cdt = this.getCodeDetail(contact.getPerGenderId());
            pt.setGender(cdt);
            // Bind marital status
            cdt = this.getCodeDetail(contact.getPerMaritalStatus());
            pt.setMaritalStatus(cdt);
            // Bind race
            cdt = this.getCodeDetail(contact.getPerRaceId());
            pt.setRace(cdt);
        }
        catch (GeneralCodeException e) {
            // Do nothing...
        }
        
        pt.setFirstName(contact.getPerFirstname());
        pt.setMidName(contact.getPerMidname());
        pt.setLastName(contact.getPerLastname());
        pt.setMaidenName(contact.getPerMaidenname());
        pt.setShortName(contact.getPerShortname());
        pt.setBirthDate(RMT2Date.formatDate(contact.getPerBirthDate(), "MM/dd/yyyy"));
        pt.setEmail(contact.getPerEmail());
        pt.setSsn(contact.getPerSsn());
        
        // Bind generation
        try {
            GenerationType gt = GenerationType.fromValue(contact.getPerGeneration());
            pt.setGeneration(gt);
        }
        catch (Exception e) {
            // ...Do nothing
        }
        
        AddressType at = this.getAddress(contact); 
        pt.setAddress(at);
        return pt;
    }
   
    
    /**
     * Creates a CodeDetailType instance usning a GeneralCodes id, <i>code</i>.  The parameter, 
     * <i>code</i>, is used to fetch the record from the database and migrate the data to an 
     * instance of CodeDetailType, which is returned to the caller. 
     * 
     * @param code
     *         an integer value representing the primary key of the row to fetch from the 
     *         GenrealCodes table. 
     * @return
     *         {@link com.xml.schema.bindings.CodeDetailType CodeDetailType} or null when code = zero.
     * @throws GeneralCodeException
     */
    public CodeDetailType getCodeDetail(int code) throws GeneralCodeException {
        if (code == 0) {
            return null;
        }
	if (this.con == null) {
	    this.msg = "Unable to get code detail for general code, " + code + ", due to invalid database connection";
	    logger.error(msg);
	    logger.error("Ensure that JaxbContactsFactory is instantiated with a vaild database connection");
	    throw new GeneralCodeException(msg);
	}
	ObjectFactory f = new ObjectFactory();
	CodesApi codes = CodesFactory.createCodesApi(this.con);
	GeneralCodes gc = (GeneralCodes) codes.findCodeById(code);
	CodeDetailType cdt = f.createCodeDetailType();
	cdt.setCodeId(BigInteger.valueOf(gc.getCodeId()));
	cdt.setLongdesc(gc.getLongdesc());
	cdt.setShortdesc(gc.getShortdesc());
	return cdt;
    }
    
    
   
    
    /**
     * Creates a JAXB BusinessType instance that is initialized by <i>VwBusinessAddress</i> data.
     * 
     * @param addr
     *         {@link com.bean.VwBusinessAddress VwBusinessAddress}
     * @return
     *        {@link com.xml.schema.bindings.AddressType AddressType}
     */
    public AddressType getAddress(VwBusinessAddress addr) {
	ObjectFactory f = new ObjectFactory();
	AddressType at = f.createAddressType();
	at.setAddrId(BigInteger.valueOf(addr.getAddrId()));
	at.setPersonId(BigInteger.valueOf(addr.getAddrPersonId()));
	at.setBusinessId(BigInteger.valueOf(addr.getAddrBusinessId()));
	at.setAddr1(addr.getAddr1());
	at.setAddr2(addr.getAddr2());
	at.setAddr3(addr.getAddr3());
	at.setAddr4(addr.getAddr4());
	try {
	    ZipcodeType z = this.getZipcode(addr.getAddrZip());
	    at.setZip(z);
	}
	catch (ZipcodeException e) {
	    // Do nothing...
	}
        at.setZipExt(BigInteger.valueOf(addr.getAddrZipext()));
	at.setPhoneCell(addr.getAddrPhoneCell());
	at.setPhoneFax(addr.getAddrPhoneFax());
	at.setPhoneHome(addr.getAddrPhoneHome());
	at.setPhoneMain(addr.getAddrPhoneMain());
	at.setPhonePager(addr.getAddrPhonePager());
	at.setPhoneWork(addr.getAddrPhoneWork());
	at.setPhoneWorkExt(addr.getAddrPhoneExt());
	return at;
    }
    
    /**
     * Creates a JAXB PersonType instance that is initialized by <i>VwPersonAddress</i> data.
     * 
     * @param addr
     *         {@link com.bean.VwPersonAddress VwPersonAddress}
     * @return
     *        {@link com.xml.schema.bindings.AddressType AddressType}
     */
    public AddressType getAddress(VwPersonAddress addr) {
        ObjectFactory f = new ObjectFactory();
        AddressType at = f.createAddressType();
        at.setAddrId(BigInteger.valueOf(addr.getAddrId()));
        at.setPersonId(BigInteger.valueOf(addr.getAddrPersonId()));
        at.setBusinessId(BigInteger.valueOf(addr.getAddrBusinessId()));
        at.setAddr1(addr.getAddr1());
        at.setAddr2(addr.getAddr2());
        at.setAddr3(addr.getAddr3());
        at.setAddr4(addr.getAddr4());
        try {
            ZipcodeType z = this.getZipcode(addr.getAddrZip());
            at.setZip(z);
        }
        catch (ZipcodeException e) {
            // Do nothing...
        }
        at.setZipExt(BigInteger.valueOf(addr.getAddrZipext()));
        at.setPhoneCell(addr.getAddrPhoneCell());
        at.setPhoneFax(addr.getAddrPhoneFax());
        at.setPhoneHome(addr.getAddrPhoneHome());
        at.setPhoneMain(addr.getAddrPhoneMain());
        at.setPhonePager(addr.getAddrPhonePager());
        at.setPhoneWork(addr.getAddrPhoneWork());
        at.setPhoneWorkExt(addr.getAddrPhoneExt());
        return at;
    }
    
    
    
    /**
     * Creates a ZipcodeType instance from data obtained from the zipcode table using <i>zipId</i> as 
     * the primary key.  The parameter, <i>zipId</i>, is used to fetch the record from the database 
     * and migrate the data to an instance of ZipcodeType, which is returned to the caller. 
     * 
     * @param zipId
     *         the primary key value used to fetch a row from the zipcode table
     * @return
     *         {@link com.xml.schema.bindings.ZipcodeType ZipcodeType}
     * @throws ZipcodeException
     */
    public ZipcodeType getZipcode(int zipId) throws ZipcodeException {
	if (this.con == null) {
	    this.msg = "Unable to get zip code details, " + zipId + ", due to invalid database connection";
	    logger.error(msg);
	    logger.error("Ensure that JaxbContactsFactory is instantiated with a vaild database connection");
	    throw new ZipcodeException(msg);
	}
	
	ObjectFactory f = new ObjectFactory();
	ZipcodeApi api = AddressComponentsFactory.createZipcodeApi(this.con);
	List<Zipcode> zList = (List <Zipcode>)api.findZipByCode(zipId);
	if (zList == null || zList.size() == 0) {
	    return null;
	}
	Zipcode z = zList.get(0);
	ZipcodeType zip = f.createZipcodeType();
	
	BigInteger zipcodeVal = BigInteger.valueOf(z.getZip());
	zip.setZipId(zipcodeVal == BigInteger.ZERO ? null : zipcodeVal);
	zipcodeVal = BigInteger.valueOf(z.getZip());
	zip.setZipcode(zipcodeVal == BigInteger.ZERO ? null : zipcodeVal);
	
	zip.setAreaCode(z.getAreaCode());
	zip.setCity(zipcodeVal == BigInteger.ZERO ? "" : z.getCity());
	zip.setCityAliasAbbr(z.getCityAliasAbbr());
	zip.setCityAliasName(z.getCityAliasName());
	zip.setCountyName(z.getCountyName());
	zip.setState(zipcodeVal == BigInteger.ZERO ? "" : z.getState());
	return zip;
    }
    
    
    /**
     * Creates a CodeDetailType instance from an instance of GenrealCodes.
     * 
     * @param dbObj
     *         an instance of {@link com.bean.GeneralCodes GeneralCodes}
     * @return
     *         an instance of {@link com.xml.schema.bindings.CodeDetailType CodeDetailType}
     */
    public CodeDetailType createCodeDetailTypeInstance(GeneralCodes dbObj) {
	ObjectFactory f = new ObjectFactory();
	CodeDetailType cdt = f.createCodeDetailType();
	cdt.setCodeId(BigInteger.valueOf(dbObj.getCodeId()));
	cdt.setLongdesc(dbObj.getLongdesc());
	cdt.setShortdesc(dbObj.getShortdesc());
	return cdt;
    }
    
    /**
     * Creates a CodeDetailType instance using primitive data types.
     * 
     * @param codeId 
     *          the genreal code id
     * @param shortDesc
     *          the abbreviated description of <i>codeId</i>
     * @param longDesc
     *          the long description of <i>codeId</i>
     * @return
     *          an instance of {@link com.xml.schema.bindings.CodeDetailType CodeDetailType}
     */
    public CodeDetailType createCodeDetailTypeInstance(int codeId, String shortDesc, String longDesc) {
        ObjectFactory f = new ObjectFactory();
        CodeDetailType cdt = f.createCodeDetailType();
        cdt.setCodeId(BigInteger.valueOf(codeId));
        cdt.setLongdesc(longDesc);
        cdt.setShortdesc(shortDesc);
        return cdt;
    }
   
    /**
     * 
     * @param id
     * @param countryName
     * @param countryCode
     * @return
     */
    public CountryType createCountryTypeInstance(int id, String countryName, String countryCode) {
        ObjectFactory f = new ObjectFactory();
        CountryType c = f.createCountryType();
        c.setCountryCode(countryCode) ;
        c.setCountryName(countryName);
        c.setCountryId(BigInteger.valueOf(id));
        return c;
    }
    

    /**
     * 
     * @param dto
     * @return
     */
    public List <StateType> getStateType(List <VwStateCountry> dto) {
        if (dto == null) {
            return null;
        }
	List <StateType> stateList = new ArrayList<StateType>();
	for (VwStateCountry item : dto) {
	    StateType state = this.getStateType(item);
	    stateList.add(state);
	}
	return stateList;
    }
    
    /**
     * 
     * @param dto
     * @return
     */
    public StateType getStateType(VwStateCountry dto) {
	if (dto == null) {
	    return null;
	}
	StateType state = this.getStateType();
	state.setCountryId(BigInteger.valueOf(dto.getCountryId()));
	state.setCountryName(dto.getCountryName());
	state.setStateCode(dto.getStateCode());
	state.setStateId(BigInteger.valueOf(dto.getStateId()));
	state.setStateName(dto.getStateName());
	return state;
    }
    
    /**
     * 
     * @return
     */
    public StateType getStateType() {
	ObjectFactory f = new ObjectFactory();
	StateType state = f.createStateType();
	return state;
    }
    
    /**
     * 
     * @param loc
     * @return
     */
    public static IpDetails getIpDetailsInstance(IpLocation loc) {
        ObjectFactory f = new ObjectFactory();
        IpDetails ip = f.createIpDetails();
        ip.setIpId(String.valueOf(loc.getIpId()));
        ip.setIpFrom(String.valueOf(loc.getIpFrom()));
        ip.setIpTo(String.valueOf(loc.getIpTo()));
        ip.setCountryCode(loc.getCountryCode());
        ip.setCountryName(loc.getCountryName());
        ip.setRegion(loc.getRegion());
        ip.setCity(loc.getCity());
        ip.setLatitude(String.valueOf(loc.getLatitude()));
        ip.setLongitude(String.valueOf(loc.getLongitude()));
        ip.setZip(loc.getZipcode());
        ip.setTimezone(loc.getTimezone());
        return ip;
    }

    /**
     * 
     * @param c
     * @return
     */
    public static List <CountryType> getCountryTypeInstance(List <Country> c) {
        if (c == null) {
            return null;
        }
        JaxbContactsFactory cf = new JaxbContactsFactory();
        List <CountryType> list = new ArrayList<CountryType>();
        for (Country src : c) {
            CountryType ct = cf.createCountryTypeInstance(src.getCountryId(), src.getName(), src.getCode());
            list.add(ct);
        }
        return list;
    }

    /**
     * 
     * @param grp
     * @param codes
     * @return
     */
    public static LookupCodeType getLookupCodeTypeInstance(GeneralCodesGroup grp,  List <GeneralCodes> codes) {
        ObjectFactory f = new ObjectFactory();
        JaxbContactsFactory cf = new JaxbContactsFactory();
        LookupCodeType lct = null;
        
        lct = f.createLookupCodeType();
        lct.setGroupId(BigInteger.valueOf(grp.getCodeGrpId()));
        lct.setLabel(grp.getDescription());
        
        // Build code instances for current group
        if (codes != null) {
            try {
                for (int ndx = 0; ndx < codes.size(); ndx++) {
                    GeneralCodes code = codes.get(ndx);
                    CodeDetailType cdt = cf.createCodeDetailTypeInstance(code.getCodeId(), code.getShortdesc(), code.getLongdesc());
                    cdt.setLabel(code.getLongdesc());
                    lct.getCode().add(cdt);
                }
            }
            catch (Exception e) {
                return null;
            }            
        }
        return lct;
    }

    /**
     * 
     * @param src
     * @return
     */
    public static List <LookupCodeType> getLookupCodeTypeInstance(List <VwCodes> src) {
        ObjectFactory f = new ObjectFactory();
        JaxbContactsFactory cf = new JaxbContactsFactory();
        List<LookupCodeType> lst = new ArrayList<LookupCodeType>();
        int prevGrpId = 0;
        LookupCodeType lct = null;
        try {
            for (int ndx = 0; ndx < src.size(); ndx++) {
                VwCodes code = src.get(ndx);
                if (prevGrpId != code.getGroupId()) {
                    // Setup new group
                    lct = f.createLookupCodeType();
                    lct.setGroupId(BigInteger.valueOf(code.getGroupId()));
                    lct.setLabel(code.getGroupDesc());
                    prevGrpId = code.getGroupId();
                }
    
                
                // Build code instances for current group
                if (code.getCodeId() > 0) {
                    CodeDetailType cdt = cf.createCodeDetailTypeInstance(code.getCodeId(), code.getCodeShortdesc(), code.getCodeLongdesc());
                    cdt.setLabel(code.getCodeLongdesc());
                    lct.getCode().add(cdt);
                }
                if ((ndx + 1) == src.size() || src.get(ndx + 1).getGroupId() != prevGrpId) {
                    lst.add(lct);
                }    
            }
            return lst;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 
     * @param item
     * @return
     */
    public static StateType getStateTypeInstance(VwStateCountry item) {
        ObjectFactory f = new ObjectFactory();
        StateType st = f.createStateType();
        st.setCountryId(BigInteger.valueOf(item.getCountryId()));
        st.setStateId(BigInteger.valueOf(item.getStateId()));
        st.setStateCode(item.getStateCode());
        st.setStateName(item.getStateName());
        return st;
    }

    /**
     * 
     * @param items
     * @return
     */
    public static List<StateType> getStateTypeInstance(List<VwStateCountry> items) {
        List<StateType> list = new ArrayList<StateType>();
        for (VwStateCountry item : items) {
            StateType st = JaxbContactsFactory.getStateTypeInstance(item);
            list.add(st);
        }
        return list;
    }

    /**
     * 
     * @param obj
     * @param request
     */
    public static void updateUserSessionType(UserSessionType obj, Request request) {
        if (request != null) {
            obj.setRemoteHost(request.getRemoteHost());
            obj.setServerName(request.getServerName());
            obj.setServerPort(BigInteger.valueOf(request.getServerPort()));
            obj.setServletContext(request.getContextPath());
            obj.setScheme(request.getScheme());
            obj.setSessionId(request.getSession().getId());
        }
        return;
    }

    public static RMT2SessionBean getUserSession(UserSessionType obj, Request request) {
        RMT2SessionBean session;
        try {
            session = AuthenticationFactory.getSessionBeanInstance(obj.getLoginId(), obj.getOrigAppId());
        }
        catch (AuthenticationException e) {
            throw new SystemException(e);
        }
        session.setLoginId(obj.getLoginId());
        
        session.setAuthSessionId(obj.getAuthSessionId());
        session.setFirstName(obj.getFname());
        session.setLastName(obj.getLname());
        session.setAccessLevel(obj.getAccessLevel().intValue());
        session.setGatewayInterface(obj.getGatewayInterface());
        session.setRemoteAppName(obj.getRemoteAppName());
        session.setSessionCreateTime(obj.getSessionCreate());
        session.setSessionLastAccessedTime(obj.getSessionLastAccessed());
        session.setSessionMaxInactSecs(obj.getSessionMax().intValue());
        session.setGroupId(obj.getGroupId() == null ? 0 : Integer.parseInt(obj.getGroupId()));
    
        if (request != null) {
            session.setRemoteHost(request.getRemoteHost());
            session.setRemoteAddress(request.getRemoteAddr());
            session.setServerName(request.getServerName());
            session.setServerPort(request.getServerPort());
            session.setServletContext(request.getContextPath());
            session.setScheme(request.getScheme());
            session.setSessionId(request.getSession().getId());
            
            // TODO:  change interface to recognize these attributes.
            session.setServerProtocol(obj.getServerProtocol());
            session.setServerInfo(obj.getServerInfo());
            session.setServerSoftware(obj.getServerSoftware());
            session.setUserAgent(obj.getUserAgent());
            session.setLocale(obj.getLocal());
            session.setAccept(obj.getAccept());
            session.setAcceptLanguage(obj.getAcceptLang());
            session.setAcceptEncoding(obj.getAcceptEncoding());
        }
        
        
        List<String> roles = new ArrayList<String>();
        for (AppRoleType role : obj.getRoles()) {
            roles.add(role.getAppRoleCode());
        }
        session.setRoles(roles);
        return session;
    }

    /**
     * 
     * @param ormList
     * @return
     */
    public static List<CommonContactType> toCommonContactTypeList(List<VwCommonContact> ormList, DatabaseConnectionBean con) {
        ObjectFactory f = new ObjectFactory();
        List<CommonContactType> list = new ArrayList<CommonContactType>();
        for (VwCommonContact orm : ormList) {
            CommonContactType c = f.createCommonContactType();
            c.setContactId(BigInteger.valueOf(orm.getContactId()));
            c.setContactName(orm.getContactName());
            ContacttypeType ctt = ContacttypeType.fromValue(String.valueOf(orm.getContactType()));
            c.setContactType(ctt);
            c.setContactEmail(orm.getEmail());
            
            AddressType at = f.createAddressType();
            at.setAddrId(BigInteger.valueOf(orm.getAddrId()));
            at.setAddr1(orm.getAddr1());
            at.setAddr2(orm.getAddr2());
            at.setAddr3(orm.getAddr3());
            at.setAddr4(orm.getAddr4());
            try {
                JaxbContactsFactory cf = new JaxbContactsFactory(con);
                ZipcodeType z = cf.getZipcode(orm.getAddrZip());
                at.setZip(z);    
            }
            catch (ZipcodeException e) {
                // Do nothing...
            }
            at.setZipExt(BigInteger.valueOf(orm.getAddrZipext()));
            at.setPhoneCell(orm.getAddrPhoneCell());
            at.setPhoneFax(orm.getAddrPhoneFax());
            at.setPhoneHome(orm.getAddrPhoneHome());
            at.setPhoneMain(orm.getAddrPhoneMain());
            at.setPhonePager(orm.getAddrPhonePager());
            at.setPhoneWork(orm.getAddrPhoneWork());
            at.setPhoneWorkExt(orm.getAddrPhoneExt());
            
            c.setAddress(at);
            list.add(c);
        }
        
        return list;
    }

    /**
     * Converts a single instance of VwZipcode to an instance of ZipcodeType.
     * 
     * @param item
     * @return
     */
    public static ZipcodeType getZipShortInstance(VwZipcode item) {
        ObjectFactory f = new ObjectFactory();
        ZipcodeType z = f.createZipcodeType();
        z.setZipId(BigInteger.valueOf(item.getZipId()));
        z.setZipcode(BigInteger.valueOf(item.getZip()));
        z.setAreaCode(item.getAreaCode());
        z.setCity(item.getCity());
        z.setState(item.getState());
        z.setCountyName(item.getCountyName());
        z.setCityAliasAbbr(item.getCityAliasAbbr());
        z.setCityAliasName(item.getCityAliasName());
        return z;
    }

    /**
     * Converts a List of VwZipcode instances to a List of ZipcodeType instances.
     * 
     * @param items
     * @return
     */
    public static List<ZipcodeType> getZipShortInstance(List<VwZipcode> items) {
        List<ZipcodeType> list = new ArrayList<ZipcodeType>();
        for (VwZipcode item : items) {
            ZipcodeType z = JaxbContactsFactory.getZipShortInstance(item);
            list.add(z);
        }
        return list;
    }

    /**
     * 
     * @param items
     * @return
     */
    public static List <ZipcodeFullType> getZipFullTypeInstance(List <VwZipcode> items) {
	List<ZipcodeFullType> list = new ArrayList<ZipcodeFullType>();
        for (VwZipcode item : items) {
            ZipcodeFullType z = JaxbContactsFactory.getZipFullTypeInstance(item);
            list.add(z);
        }
        return list;
    }
    
    /**
     * 
     * @param item
     * @return
     */
    public static ZipcodeFullType getZipFullTypeInstance(VwZipcode item) {
	 ObjectFactory f = new ObjectFactory();
	 ZipcodeFullType z = f.createZipcodeFullType();
	 z.setZipId(BigInteger.valueOf(item.getZipId()));
	 z.setZipcode(BigInteger.valueOf(item.getZip()));
	 z.setCity(item.getCity());
	 z.setState(item.getState());
	 z.setAreaCode(item.getAreaCode());
	 z.setCityAliasName(item.getCityAliasName());
	 z.setCityAliasAbbr(item.getCityAliasAbbr());
	 CitytypeType ctt = f.createCitytypeType();
	 ctt.setCityTypeId(item.getCityTypeId());
	 ctt.setCityTypeDesc(item.getCitytypeDescr());
	 z.setCityTypeId(ctt);
	 z.setCountyName(item.getCountyName());
	 z.setCountyFips(item.getCountyFips());
	 TimezoneType tt = f.createTimezoneType();
	 tt.setTimezoneId(BigInteger.valueOf(item.getTimeZone()));
	 tt.setTimeszoneDesc(item.getTimezoneDescr());
	 z.setTimeZoneId(tt);
	 z.setDayLightSaving(item.getDayLightSaving());
	 z.setLatitude(Double.valueOf(item.getLatitude()));
	 z.setLongitude(Double.valueOf(item.getLongitude()));
	 z.setElevation(Double.valueOf(item.getElevation()));
	 z.setMsa(Double.valueOf(item.getMsa()));
	 z.setPmsa(Double.valueOf(item.getPmsa()));
	 z.setCbsa(Double.valueOf(item.getCbsa()));
	 z.setCbsaDiv(Double.valueOf(item.getCbsaDiv()));
	 z.setPersonsPerHousehold(Double.valueOf(item.getPersonsPerHousehold()));
	 z.setZipcodePopulation(Double.valueOf(item.getZipcodePopulation()));
	 z.setCountiesArea(Double.valueOf(item.getCountiesArea()));
	 z.setHouseholdsPerZipcode(Double.valueOf(item.getHouseholdsPerZipcode()));
	 z.setWhitePopulation(Double.valueOf(item.getWhitePopulation()));
	 z.setBlackPopulation(Double.valueOf(item.getBlackPopulation()));
	 z.setHispanicPopulation(Double.valueOf(item.getHispanicPopulation()));
	 z.setIncomePerHousehold(Double.valueOf(item.getIncomePerHousehold()));
	 z.setAverageHouseValue(Double.valueOf(item.getAverageHouseValue()));
	 return z;
   }
    
    /**
     * 
     * @param client
     * @return
     */
    public static CommonContactCriteria createCommonSelectionCriteria(ContactCriteria client) {
        ObjectFactory f = new ObjectFactory();
        CommonContactCriteria c = f.createCommonContactCriteria();
        
       try {
            int contactId = Integer.parseInt(client.getQry_ContactId());
            c.getContactId().add(BigInteger.valueOf(contactId));
        }
       catch (NumberFormatException e) {
           // Ignore contact id...
       }
       
       c.setContactName(client.getQry_ContactName().equals("") ? null : client.getQry_ContactName());
       c.setMainPhone(client.getQry_AddrPhoneMain().equals("") ? null : client.getQry_AddrPhoneMain());
       c.setCity(client.getQry_ZipCity().equals("") ? null : client.getQry_ZipCity());
       c.setState(client.getQry_ZipState().equals("") ? null : client.getQry_ZipState());
       c.setZipcode(client.getQry_AddrZip().equals("") ? null : client.getQry_AddrZip());
       return c;
    }
    
    
    /**
     * Converts a List of TimeZone instances to a List of TimezoneType instances.
     * 
     * @param items
     * @return
     */
    public static List<TimezoneType> getTimezoneInstance(List<TimeZone> items) {
        List<TimezoneType> list = new ArrayList<TimezoneType>();
        for (TimeZone item : items) {
            TimezoneType z = JaxbContactsFactory.getTimezoneInstance(item);
            list.add(z);
        }
        return list;
    }
    
    /**
     * Converts a single instance of TimeZone to an instance of TimezoneType.
     * 
     * @param item
     * @return
     */
    public static TimezoneType getTimezoneInstance(TimeZone item) {
        ObjectFactory f = new ObjectFactory();
        TimezoneType z = f.createTimezoneType();
        z.setTimezoneId(BigInteger.valueOf(item.getTimeZoneId()));
        z.setTimeszoneDesc(item.getDescr());
        return z;
    }

    
    

}
