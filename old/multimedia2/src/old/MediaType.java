package com.audiovideo;


import java.util.Date;
import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * 
 * @author appdev
 *@deprecated refer to {@link com.bean.AvMediaType}
 */
public class MediaType extends RMT2BaseBean {

  private int id;
  private String description;



	// Getter/Setter Methods

  public MediaType() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setDescription(String value) {
    this.description = value;
  }
  public String getDescription() {
    return this.description;
  }
  public void initBean() throws SystemException {}
}