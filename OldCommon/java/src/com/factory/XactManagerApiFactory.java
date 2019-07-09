package com.factory;

import java.util.ArrayList;


import com.api.XactManagerApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.bean.Xact;
import com.bean.XactXlatBean;
import com.bean.db.DatabaseConnectionBean;

import com.apiimpl.XactManagerApiImpl;

import com.util.SystemException;


/**
 * 
 * @author rterrell
 * @deprecated Use {@link XactFactory} to create transaction related objects.
 */
public class XactManagerApiFactory extends DataSourceAdapter   {

   public static XactManagerApi create(DatabaseConnectionBean _dbo, int _xactTypeId, double _amt) throws SystemException, DatabaseException    {
         XactManagerApi api = new XactManagerApiImpl(_dbo, _xactTypeId, _amt);
		     return api;
   }
   
   public static XactManagerApi create(DatabaseConnectionBean _dbo, Xact _xact, XactXlatBean _target, ArrayList _offsets) throws SystemException, DatabaseException    {
       XactManagerApi api = new XactManagerApiImpl(_dbo);
       api.setXactBean(_xact);
		 return api;
 }
}
