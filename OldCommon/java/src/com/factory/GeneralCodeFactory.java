package com.factory;

import java.util.ArrayList;

import com.api.GeneralCodeManagerApi;
import com.api.GeneralCodeGroupManagerApi;
import com.api.db.DatabaseException;
import com.api.db.orm.DataSourceAdapter;

import com.apiimpl.GeneralCodeManagerApiImpl;

import com.bean.GeneralCodesGroup;
import com.bean.GeneralCodes;
import com.bean.db.DatabaseConnectionBean;

import com.util.SystemException;


public class GeneralCodeFactory extends DataSourceAdapter   {

  public static GeneralCodeGroupManagerApi createGroupApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     GeneralCodeGroupManagerApi api = new GeneralCodeManagerApiImpl(_dbo);
     api.setBaseView("GeneralCodesGroupView");
     api.setBaseClass("com.bean.GeneralCodesGroup");
     return api;
  }

  public static GeneralCodeManagerApi createCodeApi(DatabaseConnectionBean _dbo) throws SystemException, DatabaseException {
     GeneralCodeManagerApi api = new GeneralCodeManagerApiImpl(_dbo);
     api.setBaseView("GeneralCodesView");
     api.setBaseClass("com.bean.GeneralCodes");
     return api;
  }

  public static GeneralCodesGroup createGroup() throws SystemException {
			return new GeneralCodesGroup();
	}

  public static GeneralCodes createCode() throws SystemException {
			return new GeneralCodes();
	}
}


