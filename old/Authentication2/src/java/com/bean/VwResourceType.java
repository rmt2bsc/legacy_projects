package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the vw_resource_type database table/view.
 *
 * @author auto generated.
 */
public class VwResourceType extends OrmBean {




	// Property name constants that belong to respective DataSource, VwResourceTypeView

/** The property name constant equivalent to property, ResrcTypeId, of respective DataSource view. */
  public static final String PROP_RESRCTYPEID = "ResrcTypeId";
/** The property name constant equivalent to property, ResrcTypeName, of respective DataSource view. */
  public static final String PROP_RESRCTYPENAME = "ResrcTypeName";
/** The property name constant equivalent to property, ResrcSubtypeId, of respective DataSource view. */
  public static final String PROP_RESRCSUBTYPEID = "ResrcSubtypeId";
/** The property name constant equivalent to property, ResrcSubtypeName, of respective DataSource view. */
  public static final String PROP_RESRCSUBTYPENAME = "ResrcSubtypeName";
/** The property name constant equivalent to property, ResrcSubtypeDesc, of respective DataSource view. */
  public static final String PROP_RESRCSUBTYPEDESC = "ResrcSubtypeDesc";



	/** The javabean property equivalent of database column vw_resource_type.resrc_type_id */
  private int resrcTypeId;
/** The javabean property equivalent of database column vw_resource_type.resrc_type_name */
  private String resrcTypeName;
/** The javabean property equivalent of database column vw_resource_type.resrc_subtype_id */
  private int resrcSubtypeId;
/** The javabean property equivalent of database column vw_resource_type.resrc_subtype_name */
  private String resrcSubtypeName;
/** The javabean property equivalent of database column vw_resource_type.resrc_subtype_desc */
  private String resrcSubtypeDesc;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public VwResourceType() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable resrcTypeId
 *
 * @author auto generated.
 */
  public void setResrcTypeId(int value) {
    this.resrcTypeId = value;
  }
/**
 * Gets the value of member variable resrcTypeId
 *
 * @author atuo generated.
 */
  public int getResrcTypeId() {
    return this.resrcTypeId;
  }
/**
 * Sets the value of member variable resrcTypeName
 *
 * @author auto generated.
 */
  public void setResrcTypeName(String value) {
    this.resrcTypeName = value;
  }
/**
 * Gets the value of member variable resrcTypeName
 *
 * @author atuo generated.
 */
  public String getResrcTypeName() {
    return this.resrcTypeName;
  }
/**
 * Sets the value of member variable resrcSubtypeId
 *
 * @author auto generated.
 */
  public void setResrcSubtypeId(int value) {
    this.resrcSubtypeId = value;
  }
/**
 * Gets the value of member variable resrcSubtypeId
 *
 * @author atuo generated.
 */
  public int getResrcSubtypeId() {
    return this.resrcSubtypeId;
  }
/**
 * Sets the value of member variable resrcSubtypeName
 *
 * @author auto generated.
 */
  public void setResrcSubtypeName(String value) {
    this.resrcSubtypeName = value;
  }
/**
 * Gets the value of member variable resrcSubtypeName
 *
 * @author atuo generated.
 */
  public String getResrcSubtypeName() {
    return this.resrcSubtypeName;
  }
/**
 * Sets the value of member variable resrcSubtypeDesc
 *
 * @author auto generated.
 */
  public void setResrcSubtypeDesc(String value) {
    this.resrcSubtypeDesc = value;
  }
/**
 * Gets the value of member variable resrcSubtypeDesc
 *
 * @author atuo generated.
 */
  public String getResrcSubtypeDesc() {
    return this.resrcSubtypeDesc;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}