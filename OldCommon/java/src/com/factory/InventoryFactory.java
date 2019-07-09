package com.factory;

import com.bean.ItemMaster;
import com.bean.VwItemMaster;
import com.bean.VendorItems;
import com.bean.ItemMasterStatusHist;
import com.bean.db.DatabaseConnectionBean;

import com.api.InventoryApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.InventoryApiImpl;

import com.util.SystemException;

import javax.servlet.http.HttpServletRequest;



public class InventoryFactory extends DataSourceAdapter {
	
  public static InventoryApi createInventoryApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
      InventoryApi api = new InventoryApiImpl(_dbo);
     api.setBaseView("ItemMasterView");
     api.setBaseClass("com.bean.ItemMaster");
     return api;
  }
  
  public static InventoryApi createInventoryApi(DatabaseConnectionBean _dbo, HttpServletRequest _req)  {
	  try {
		  InventoryApi api = new InventoryApiImpl(_dbo, _req);
		  api.setBaseView("ItemMasterView");
		  api.setBaseClass("com.bean.ItemMaster");
		  return api;
	  }
      catch (DatabaseException e) {
    	  return null;
      }
      catch (SystemException e) {
    	  return null;
      }
  }


   public static ItemMaster  createItemMaster()  throws SystemException{
      ItemMaster item = new ItemMaster();
      return item;
   }

   public static ItemMaster createItemMaster(HttpServletRequest _req)   throws SystemException {
       ItemMaster obj = createItemMaster();
       int rc = packageBean(_req, obj);
       return obj;      
    }
   
   
   public static VendorItems createVendorItem() {
	   VendorItems obj = null;
	   try {
		   obj = new VendorItems();
		   return obj;
	   }
	   catch (SystemException e) {
		   return null;
	   }
   }
   
   public static VendorItems createVendorItem(int _vendorId, int _itemId, String _serialNo, String _vendorItemNo, double _unitCost) {
	   VendorItems obj = null;
	   obj = InventoryFactory.createVendorItem();
	   obj.setVendorId(_vendorId);
	   obj.setItemMasterId(_itemId);
	   obj.setItemSerialNo(_serialNo);
	   obj.setVendorItemNo(_vendorItemNo);
	   obj.setUnitCost(_unitCost);
	   return obj;
   }
   
   public static VwItemMaster  createItemMasterView()  {
       try {
           VwItemMaster item = new VwItemMaster();
           return item;
       }
       catch (SystemException e) {
           return null;
       }
    }


   public static ItemMasterStatusHist  createItemMasterStatusHist()  {
       try {
           ItemMasterStatusHist item = new ItemMasterStatusHist();
           return item;
       }
       catch (SystemException e) {
           return null;
       }
    }
   
}


