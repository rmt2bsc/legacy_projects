package com.general;

public class AncestorClass {
    private String firstName;
    private String lastName;
    private String phone;
    
    
    public AncestorClass() {
	this.init();
    }
    
    
    public void init() {
	this.firstName = "AFirstName";
	this.lastName = "ALastName";
	this.phone = "1111111111";
    }
    
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
