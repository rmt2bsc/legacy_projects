package com.bean;


import java.util.Date;
import java.io.*;
import com.bean.OrmBean;
import com.util.SystemException;


/**
 * Peer object that maps to the products database table/view.
 *
 * @author auto generated.
 */
public class Products extends OrmBean {




	// Property name constants that belong to respective DataSource, ProductsView

/** The property name constant equivalent to property, Id, of respective DataSource view. */
  public static final String PROP_ID = "Id";
/** The property name constant equivalent to property, Name, of respective DataSource view. */
  public static final String PROP_NAME = "Name";
/** The property name constant equivalent to property, Description, of respective DataSource view. */
  public static final String PROP_DESCRIPTION = "Description";
/** The property name constant equivalent to property, Size, of respective DataSource view. */
  public static final String PROP_SIZE = "Size";
/** The property name constant equivalent to property, Color, of respective DataSource view. */
  public static final String PROP_COLOR = "Color";
/** The property name constant equivalent to property, Quantity, of respective DataSource view. */
  public static final String PROP_QUANTITY = "Quantity";
/** The property name constant equivalent to property, Unitprice, of respective DataSource view. */
  public static final String PROP_UNITPRICE = "Unitprice";
/** The property name constant equivalent to property, Photo, of respective DataSource view. */
  public static final String PROP_PHOTO = "Photo";



	/** The javabean property equivalent of database column products.id */
  private int id;
/** The javabean property equivalent of database column products.name */
  private char name;
/** The javabean property equivalent of database column products.description */
  private char description;
/** The javabean property equivalent of database column products.size */
  private char size;
/** The javabean property equivalent of database column products.color */
  private char color;
/** The javabean property equivalent of database column products.quantity */
  private int quantity;
/** The javabean property equivalent of database column products.unitprice */
  private double unitprice;
/** The javabean property equivalent of database column products.photo */
  private InputStream photo;



	// Getter/Setter Methods

/**
 * Default constructor.
 *
 * @author auto generated.
 */
  public Products() throws SystemException {
	super();
 }
/**
 * Sets the value of member variable id
 *
 * @author auto generated.
 */
  public void setId(int value) {
    this.id = value;
  }
/**
 * Gets the value of member variable id
 *
 * @author atuo generated.
 */
  public int getId() {
    return this.id;
  }
/**
 * Sets the value of member variable name
 *
 * @author auto generated.
 */
  public void setName(char value) {
    this.name = value;
  }
/**
 * Gets the value of member variable name
 *
 * @author atuo generated.
 */
  public char getName() {
    return this.name;
  }
/**
 * Sets the value of member variable description
 *
 * @author auto generated.
 */
  public void setDescription(char value) {
    this.description = value;
  }
/**
 * Gets the value of member variable description
 *
 * @author atuo generated.
 */
  public char getDescription() {
    return this.description;
  }
/**
 * Sets the value of member variable size
 *
 * @author auto generated.
 */
  public void setSize(char value) {
    this.size = value;
  }
/**
 * Gets the value of member variable size
 *
 * @author atuo generated.
 */
  public char getSize() {
    return this.size;
  }
/**
 * Sets the value of member variable color
 *
 * @author auto generated.
 */
  public void setColor(char value) {
    this.color = value;
  }
/**
 * Gets the value of member variable color
 *
 * @author atuo generated.
 */
  public char getColor() {
    return this.color;
  }
/**
 * Sets the value of member variable quantity
 *
 * @author auto generated.
 */
  public void setQuantity(int value) {
    this.quantity = value;
  }
/**
 * Gets the value of member variable quantity
 *
 * @author atuo generated.
 */
  public int getQuantity() {
    return this.quantity;
  }
/**
 * Sets the value of member variable unitprice
 *
 * @author auto generated.
 */
  public void setUnitprice(double value) {
    this.unitprice = value;
  }
/**
 * Gets the value of member variable unitprice
 *
 * @author atuo generated.
 */
  public double getUnitprice() {
    return this.unitprice;
  }
/**
 * Sets the value of member variable photo
 *
 * @author auto generated.
 */
  public void setPhoto(InputStream value) {
    this.photo = value;
  }
/**
 * Gets the value of member variable photo
 *
 * @author atuo generated.
 */
  public InputStream getPhoto() {
    return this.photo;
  }
/**
 * Stubbed initialization method designed to implemented by developer.

 *
 * @author auto generated.
 */
  public void initBean() throws SystemException {}
}