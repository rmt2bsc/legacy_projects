package com.bean;


import java.util.Date;
import com.bean.OrmBean;
import com.util.SystemException;

// TODO: Add properties: item name, qty on hand, cost, and ect.
public class SalesOrderItemsBySalesOrder extends OrmBean {

  private int id;
  private int salesOrderId;
  private int itemMasterId;
  private double orderQty;
  private double initUnitCost;
  private double initMarkup;
  private int customerId;
  private int invoiced;
  private int personId;
  private int businessId;
  private String customerName;



	// Getter/Setter Methods

  public SalesOrderItemsBySalesOrder() throws SystemException {
  }
  public void setId(int value) {
    this.id = value;
  }
  public int getId() {
    return this.id;
  }
  public void setSalesOrderId(int value) {
    this.salesOrderId = value;
  }
  public int getSalesOrderId() {
    return this.salesOrderId;
  }
  public void setItemMasterId(int value) {
    this.itemMasterId = value;
  }
  public int getItemMasterId() {
    return this.itemMasterId;
  }
  public void setOrderQty(double value) {
    this.orderQty = value;
  }
  public double getOrderQty() {
    return this.orderQty;
  }
  public void setInitUnitCost(double value) {
    this.initUnitCost = value;
  }
  public double getInitUnitCost() {
    return this.initUnitCost;
  }
  public void setInitMarkup(double value) {
    this.initMarkup = value;
  }
  public double getInitMarkup() {
    return this.initMarkup;
  }

  public void setCustomerId(int value) {
			this.customerId = value;
	}
	public int getCustomerId() {
			return this.customerId;
	}
	public void setInvoiced(int value) {
			this.invoiced = value;
	}
	public int getInvoiced() {
			return this.invoiced;
	}
	public void setPersonId(int value) {
			this.personId = value;
	}
	public int getPersonId() {
			return this.personId;
	}
	public void setBusinessId(int value) {
			this.businessId = value;
	}
	public int getBusinessId() {
			return this.businessId;
	}
	public void setCustomerName(String value) {
			this.customerName = value;
	}
	public String getCustomerName() {
			return this.customerName;
	}
  public void initBean() throws SystemException {}
}