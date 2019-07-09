package com.api.mail;

import com.api.db.orm.DataSourceAdapter;
import com.bean.mail.EMailBean;

/**
 * Factory that creates new instances of EMailManagerApi and EMailBean classes.
 * 
 * @author roy.terrell
 * @deprecated Will be replaced by {@link com.api.messaging.email.smtp.SmtpFactory} in future versions.
 * 
 */
public class EMailFactory extends DataSourceAdapter {

    /**
     * Creates a instance of {@link EMailManagerApi}.
     * 
     * @return EMailManagerApi
     */
    public static EMailManagerApi createApi() {
        try {
            EMailManagerApi api = EMailManagerImpl.getInstance();
            return api;
        }
        catch (Exception e) {
            return null;
        }

    }

    /**
     * Creates an instance of EMailManagerApi using EMailBean.
     * 
     * @param _bean
     *            {@link EMailBean}
     * @return {@link EMailManagerApi}
     */
    public static EMailManagerApi createApi(EMailBean _bean) {
        try {
            if (_bean == null) {
                _bean = create();
            }
            EMailManagerApi api = EMailFactory.createApi();
            api.setEmailBean(_bean);
            return api;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates an instance of EMailBean class.
     * 
     * @return {@link EMailBean}
     */
    public static EMailBean create() {
        try {
            EMailBean obj = new EMailBean();
            return obj;
        }
        catch (Exception e) {
            return null;
        }
    }

}
