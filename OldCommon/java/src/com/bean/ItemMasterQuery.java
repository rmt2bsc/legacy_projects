package com.bean;


import com.bean.OrmBean;
import com.util.SystemException;


public class ItemMasterQuery extends OrmBean {
      private static final long serialVersionUID = -4753283554476140980L;
      private String qry_Id;
      private String qry_Description;
      private String qry_VendorId;
      private String qry_ItemTypeId;
      private String qry_VendorItemNo;
      private String qry_ItemSerialNo;
      private String qry_UnitCost;
      private String qryRelOp_UnitCost;
      private String qry_QtyOnHand;
      private String qry_Active;
      private String qry_Inactive;
      private String qry_ItemStatusId;
    
    
      public ItemMasterQuery() throws SystemException {
      }
      
      public static ItemMasterQuery getInstance() {
          try {
              return new ItemMasterQuery();
          }
          catch (SystemException e) {
              return null;
          }
      }
      
      public void setQry_Id(String value) {
          this.qry_Id = value;
      }
      public String getQry_Id() {
          return this.qry_Id == null ? "" : this.qry_Id;
      }
      public void setQry_Description(String value) {
          this.qry_Description = value;
      }
      public String getQry_Description() {
          return this.qry_Description == null ? "" : this.qry_Description;
      }
      public void setQry_VendorId(String value) {
          this.qry_VendorId = value;
      }
      public String getQry_VendorId() {
          return this.qry_VendorId == null ? "" : this.qry_VendorId;
      }
      public void setQry_ItemTypeId(String value) {
          this.qry_ItemTypeId = value;
      }
      public String getQry_ItemTypeId() {
          return this.qry_ItemTypeId == null ? "" : this.qry_ItemTypeId;
      }  
      public void setQry_VendorItemNo(String value) {
          this.qry_VendorItemNo = value;
      }
      public String getQry_VendorItemNo() {
          return this.qry_VendorItemNo == null ? "" : this.qry_VendorItemNo;
      }
      public void setQry_ItemSerialNo(String value) {
          this.qry_ItemSerialNo = value;
      }
      public String getQry_ItemSerialNo() {
          return this.qry_ItemSerialNo == null ? "" : this.qry_ItemSerialNo;
      }
      public void setQry_UnitCost(String value) {
          this.qry_UnitCost = value;
      }
      public String getQry_UnitCost() {
          return this.qry_UnitCost == null ? "" : this.qry_UnitCost;
      }
      public void setQryRelOp_UnitCost(String value) {
          this.qryRelOp_UnitCost = value;
      }
      public String getQryRelOp_UnitCost() {
          return this.qryRelOp_UnitCost == null ? "" : this.qryRelOp_UnitCost;
      }
      public String getQry_QtyOnHand() {
          return this.qry_QtyOnHand == null ? "" : this.qry_QtyOnHand;
      }
      public void setQry_QtyOnHand(String value) {
          this.qry_QtyOnHand = value;
      }
      public void setQry_Active(String value) {
          this.qry_Active = value;
      }
      public String getQry_Active() {
          return this.qry_Active == null ? "" : this.qry_Active;
      }
      public void setQry_Inactive(String value) {
          this.qry_Inactive = value;
      }
      public String getQry_Inactive() {
          return this.qry_Inactive == null ? "" : this.qry_Inactive;
      }
      public void setQry_ItemStatusId(String value) {
          this.qry_ItemStatusId = value;
      }
      public String getQry_ItemStatusId() {
          return this.qry_ItemStatusId == null ? "" : this.qry_ItemStatusId;
      }
      
      
      public void initBean() throws SystemException {}
}