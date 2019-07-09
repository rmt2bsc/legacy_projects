package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_resource database table/view.
 *
 * @author auto generated.
 */
public class VwResource extends OrmBean {




	// Property name constants that belong to respective DataSource, VwResourceView

/** The property name constant equivalent to property, RsrcId, of respective DataSource view. */
  public static final String PROP_RSRCID = "RsrcId";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Url, of respective DataSource view. */
  public static final String PROP_URL = "Url";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, Secured, of respective DataSource view. */
  public static final String PROP_SECURED = "Secured";
/** The property name constant equivalent to property, RsrcTypeId, of respective DataSource view. */
  public static final String PROP_RSRCTYPEID = "RsrcTypeId";
/** The property name constant equivalent to property, TypeDescr, of respective DataSource view. */
  public static final String PROP_TYPEDESCR = "TypeDescr";
/** The property name constant equivalent to property, RsrcSubtypeId, of respective DataSource view. */
  public static final String PROP_RSRCSUBTYPEID = "RsrcSubtypeId";
/** The property name constant equivalent to property, SubtypeName, of respective DataSource view. */
  public static final String PROP_SUBTYPENAME = "SubtypeName";
/** The property name constant equivalent to property, SubtypeDesc, of respective DataSource view. */
  public static final String PROP_SUBTYPEDESC = "SubtypeDesc";



	/** The javabean property equivalent of database column vw_resource.rsrc_id */
  private int rsrcId;
/** The javabean property equivalent of database column vw_resource.name */
  private String name;
/** The javabean property equivalent of database column vw_resource.url */
  private String url;
/** The javabean property equivalent of database column vw_resource.description */
  private String description;
/** The javabean property equivalent of database column vw_resource.secured */
  private int secured;
/** The javabean property equivalent of database column vw_resource.rsrc_type_id */
  private int rsrcTypeId;
/** The javabean property equivalent of database column vw_resource.type_descr */
  private String typeDescr;
/** The javabean property equivalent of database column vw_resource.rsrc_subtype_id */
  private int rsrcSubtypeId;
/** The javabean property equivalent of database column vw_resource.subtype_name */
  private String subtypeName;
/** The javabean property equivalent of database column vw_resource.subtype_desc */
  private String subtypeDesc;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwResource() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable rsrcId
 *
 * @author auto generated.
 */
  public void setRsrcId(int value) {
    this.rsrcId = value;
  }
/**
 * Gets the value of member variable rsrcId
 *
 * @author atuo generated.
 */
  public int getRsrcId() {
    return this.rsrcId;
  }
/**
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(String value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public String getName() {
    return this.name;
  }
/**
 * Sets the value of member variable url
 *
 * @author auto generated.
 */
  public void setUrl(String value) {
    this.url = value;
  }
/**
 * Gets the value of member variable url
 *
 * @author atuo generated.
 */
  public String getUrl() {
    return this.url;
  }
/**
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(String value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public String getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable secured
 *
 * @author auto generated.
 */
  public void setSecured(int value) {
    this.secured = value;
  }
/**
 * Gets the value of member variable secured
 *
 * @author atuo generated.
 */
  public int getSecured() {
    return this.secured;
  }
/**
 * Sets the value of member variable rsrcTypeId
 *
 * @author auto generated.
 */
  public void setRsrcTypeId(int value) {
    this.rsrcTypeId = value;
  }
/**
 * Gets the value of member variable rsrcTypeId
 *
 * @author atuo generated.
 */
  public int getRsrcTypeId() {
    return this.rsrcTypeId;
  }
/**
 * Sets the value of member variable typeDescr
 *
 * @author auto generated.
 */
  public void setTypeDescr(String value) {
    this.typeDescr = value;
  }
/**
 * Gets the value of member variable typeDescr
 *
 * @author atuo generated.
 */
  public String getTypeDescr() {
    return this.typeDescr;
  }
/**
 * Sets the value of member variable rsrcSubtypeId
 *
 * @author auto generated.
 */
  public void setRsrcSubtypeId(int value) {
    this.rsrcSubtypeId = value;
  }
/**
 * Gets the value of member variable rsrcSubtypeId
 *
 * @author atuo generated.
 */
  public int getRsrcSubtypeId() {
    return this.rsrcSubtypeId;
  }
/**
 * Sets the value of member variable subtypeName
 *
 * @author auto generated.
 */
  public void setSubtypeName(String value) {
    this.subtypeName = value;
  }
/**
 * Gets the value of member variable subtypeName
 *
 * @author atuo generated.
 */
  public String getSubtypeName() {
    return this.subtypeName;
  }
/**
 * Sets the value of member variable subtypeDesc
 *
 * @author auto generated.
 */
  public void setSubtypeDesc(String value) {
    this.subtypeDesc = value;
  }
/**
 * Gets the value of member variable subtypeDesc
 *
 * @author atuo generated.
 */
  public String getSubtypeDesc() {
    return this.subtypeDesc;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}