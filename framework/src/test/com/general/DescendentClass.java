/**
 * 
 */
package com.general;

/**
 * @author SG0903711
 *
 */
public class DescendentClass extends AncestorClass {

    /**
     * 
     */
    public DescendentClass() {
	super();
    }

    public void init() {
	super.init();
	this.setFirstName("DFirstName");
	this.setLastName("DLastName");
	this.setPhone("2222222222");
    }
}
