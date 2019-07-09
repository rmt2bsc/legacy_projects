package com.audiovideo;


import java.util.Date;
import com.bean.RMT2BaseBean;
import com.util.SystemException;

/**
 * 
 * @author appdev
 * @deprecated refer to {@link com.bean.AvArtist}
 */
public class AudioVideoArtist extends RMT2BaseBean {

  private int id;
  private String name;
  private int artistTypeId;



	// Getter/Setter Methods

  public AudioVideoArtist() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setName(String value) {
    this.name = value;
  }
  public String getName() {
    return this.name;
  }
  public void setArtistTypeId(int value) {
    this.artistTypeId = value;
  }
  public int getArtistTypeId() {
    return this.artistTypeId;
  }
  public void initBean() throws SystemException {}
}