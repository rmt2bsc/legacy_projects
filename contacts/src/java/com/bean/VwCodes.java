package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_codes database table/view.
 *
 * @author auto generated.
 */
public class VwCodes extends OrmBean {




	// Property name constants that belong to respective DataSource, VwCodesView

/** The property name constant equivalent to property, GroupId, of respective DataSource view. */
  public static final String PROP_GROUPID = "GroupId";
/** The property name constant equivalent to property, GroupDesc, of respective DataSource view. */
  public static final String PROP_GROUPDESC = "GroupDesc";
/** The property name constant equivalent to property, CodeId, of respective DataSource view. */
  public static final String PROP_CODEID = "CodeId";
/** The property name constant equivalent to property, CodeShortdesc, of respective DataSource view. */
  public static final String PROP_CODESHORTDESC = "CodeShortdesc";
/** The property name constant equivalent to property, CodeLongdesc, of respective DataSource view. */
  public static final String PROP_CODELONGDESC = "CodeLongdesc";



	/** The javabean property equivalent of database column vw_codes.group_id */
  private int groupId;
/** The javabean property equivalent of database column vw_codes.group_desc */
  private String groupDesc;
/** The javabean property equivalent of database column vw_codes.code_id */
  private int codeId;
/** The javabean property equivalent of database column vw_codes.code_shortdesc */
  private String codeShortdesc;
/** The javabean property equivalent of database column vw_codes.code_longdesc */
  private String codeLongdesc;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwCodes() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable groupId
 *
 * @author auto generated.
 */
  public void setGroupId(int value) {
    this.groupId = value;
  }
/**
 * Gets the value of member variable groupId
 *
 * @author atuo generated.
 */
  public int getGroupId() {
    return this.groupId;
  }
/**
 * Sets the value of member variable groupDesc
 *
 * @author auto generated.
 */
  public void setGroupDesc(String value) {
    this.groupDesc = value;
  }
/**
 * Gets the value of member variable groupDesc
 *
 * @author atuo generated.
 */
  public String getGroupDesc() {
    return this.groupDesc;
  }
/**
 * Sets the value of member variable codeId
 *
 * @author auto generated.
 */
  public void setCodeId(int value) {
    this.codeId = value;
  }
/**
 * Gets the value of member variable codeId
 *
 * @author atuo generated.
 */
  public int getCodeId() {
    return this.codeId;
  }
/**
 * Sets the value of member variable codeShortdesc
 *
 * @author auto generated.
 */
  public void setCodeShortdesc(String value) {
    this.codeShortdesc = value;
  }
/**
 * Gets the value of member variable codeShortdesc
 *
 * @author atuo generated.
 */
  public String getCodeShortdesc() {
    return this.codeShortdesc;
  }
/**
 * Sets the value of member variable codeLongdesc
 *
 * @author auto generated.
 */
  public void setCodeLongdesc(String value) {
    this.codeLongdesc = value;
  }
/**
 * Gets the value of member variable codeLongdesc
 *
 * @author atuo generated.
 */
  public String getCodeLongdesc() {
    return this.codeLongdesc;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}