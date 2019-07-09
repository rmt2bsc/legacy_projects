package com.nv.util;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

import modules.StoreListItem;
import modules.StoreListItemComparator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Utility class that provides common functionality for resource
 * allocation/deallocation and general management regarding the entire
 * application.
 * 
 * @author rterrell
 *
 */
public class AppUtil {

    private static Logger logger;

    private static Properties APP_PROPS;

    private static final String CONFIG_STORE_LIST = "config/storeList.properties";

    private static final String CONFIG_DIR = "configDir";

    private static final String CONFIG_SITE_FILE = "siteConfigFile";

    private static final String CONFIG_SERVER_NAME = "dbServerName";

    private static final String APP_ICON_KEY = "appIcon";

    /**
     * The properties file containing the application's configuration.
     */
    public static final String CONFIG_COMMON_FILE = "/application.properties";

    /**
     * The default font size that will be applied to every DataGrid and dialog
     * within the Launch Pad application.
     */
    public static final int DEFAULT_FONT_SIZE = 15;

    /**
     * The property key to locate the value of the application title from within
     * the application configuration.
     */
    public static final String PROP_APP_TITLE = "appTitle";

    private List<StoreListItem> storeList;

    /**
     * Creates a AppUtil object.
     */
    public AppUtil() {
        return;
    }

    /**
     * Globally sets the font for the application with values obtained from
     * application.properties.
     */
    public void setDefaultFont() {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                Font f = getDataGridRowFont();
                UIManager.put(key, f);
            }
        }
    }

    /**
     * Queries the internal application properties collection for a value based
     * on the <i>propName</i>.
     * 
     * @param propName
     *            The key used to obtain the property value.
     * @return the value of <i>propName</i> or null if property name does not
     *         exists.
     */
    public static final String getAppProperty(String propName) {
        if (AppUtil.APP_PROPS == null) {
            return null;
        }
        return AppUtil.APP_PROPS.getProperty(propName);
    }

    /**
     * Adds a property to the collection of application properties.
     * 
     * @param propName
     *            The key name of the property
     * @param value
     *            The value of the property
     * @return <i>value</i> when added successfully or null when the Properties
     *         collection has not been initialized.
     */
    public static final String addAppProperty(String propName, String value) {
        if (AppUtil.APP_PROPS == null) {
            return null;
        }
        AppUtil.APP_PROPS.setProperty(propName, value);
        return value;
    }

    /**
     * Uses the specified component, <i>comp</i>, to display a message anchored
     * by its parent to the user.
     * <p>
     * The HTML paragraph tag, &lt;P$gt;, is used to enclose the message text so
     * that the JLabel component is forced to wrap excessively long message
     * text.
     * 
     * @param comp
     *            an instance of {@link JLabel} that is used to display a
     *            message on its parent.
     * @param message
     *            The message to be displayed.
     * @param error
     *            When true, the font color of the message is RED. Otherwise,
     *            the font color is BLACK.
     */
    public static final void showMessage(JLabel comp, String message,
            boolean error) {
        if (comp == null) {
            logger.error("The intended component to display user message is invalid or null");
            return;
        }

        // Use HTML paragraph tags to make text wrap in the JLable component
        message = "<html><p>" + message + "</p></html>";
        comp.setText(message);
        comp.setVisible(true);

        // Set the font
        Font f = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        comp.setFont(f);

        if (error) {
            comp.setForeground(Color.RED);
        }
        else {
            comp.setForeground(Color.BLACK);
        }
    }

    /**
     * Sets up the logging environment that is to be used throughout the entire
     * application.
     * 
     * @throws AppInitException
     *             if the log4j.properties is not found or the occurrence of
     *             genreal IO errors
     */
    public void initLogger() throws AppInitException {
        String msg = null;
        try {
            Properties props = new Properties();
            props.load(AppUtil.class.getResourceAsStream("/log4j.properties"));
            PropertyConfigurator.configure(props);
            AppUtil.logger = Logger.getLogger(AppUtil.class);
        } catch (FileNotFoundException e) {
            msg = "Unable to load log4j configuration due to file not found";
            throw new AppInitException(msg, e);
        } catch (IOException e) {
            msg = "Unable to load log4j configuration due to a general IO error";
            throw new AppInitException(msg, e);
        }
    }

    /**
     * Retrieves the site's store list.
     * <p>
     * The source of this information is required to exist in the configuration
     * file, sotreList.properties, which is located in the <install_dir>/config
     * directory. This listing can contain one to many entries which each entry
     * is of the format, "<server name>,<store number>".
     * 
     * @throws AppInitException
     */
    public void initStoreList() throws AppInitException {
        Iterator<Object> stores = null;
        List<StoreListItem> list = new ArrayList<StoreListItem>();
        try {
            Properties props = GeneralUtil
                    .loadProperties(AppUtil.CONFIG_STORE_LIST);
            if (props.keySet().isEmpty()) {
                return;
            }

            // Get list of store/server names
            stores = props.keySet().iterator();
            while (stores.hasNext()) {
                String key = stores.next().toString();
                String store = props.getProperty(key);
                String parts[] = store.split(",");
                if (parts.length > 1) {
                    StoreListItem item = new StoreListItem();
                    item.setServer((parts[0] == null ? parts[0] : parts[0]
                            .toUpperCase()));
                    item.setStoreNo(parts[1]);
                    list.add(item);
                }
            }

            // Sort list by Store number
            Collections.sort(list, new StoreListItemComparator());
            this.storeList = list;
        } catch (MissingResourceException e) {
            String msg = "Unable to find Store List resource bundle in classpath: "
                    + AppUtil.CONFIG_STORE_LIST;
            logger.error(msg);
            throw new AppInitException(msg, e);
        } catch (Exception e) {
            String msg = "Error occurred reading the configuration for Store/Server list";
            logger.error(msg);
            throw new AppInitException(msg, e);
        }
    }

    /**
     * Loads the property file that contains configuration information about the
     * application as it pertains to a particular site.
     * <p>
     * This process replicates the common configuration,
     * <i>application.proerties</i>, into the site specifice configuration,
     * <i>site.properties</i>. Site configuration will contain both common and
     * specific information pertaining to the current site. An example of
     * specific information is the selected server name.
     * 
     * @param serverName
     *            The name of the server where the database resides
     * @throws AppInitException
     *             Unable to identify the server name or error persisting the
     *             site.properties file.
     */
    public void initSiteConfig(String serverName) throws AppInitException {
        String msg = null;
        // Determine the server name of the store in which the client wishes to
        // establish a database connection.
        if (serverName == null) {
            msg = "Launch Pad requires that a server in order to complete site configuration successfully";
            logger.error(msg);
            throw new AppInitException(msg);
        }

        // Set the name of the actual server the client will establish a
        // database connection.
        Properties prop = GeneralUtil
                .loadProperties(AppUtil.CONFIG_COMMON_FILE);
        prop.setProperty(AppUtil.CONFIG_SERVER_NAME, serverName);

        // Copy the application properties changes to the site specific
        // configuration
        String configDir = prop.getProperty(AppUtil.CONFIG_DIR);
        String siteConfigFile = prop.getProperty(AppUtil.CONFIG_SITE_FILE);
        String destSiteConfig = configDir + siteConfigFile;
        GeneralUtil.persistProperties(prop, destSiteConfig);
        AppUtil.APP_PROPS = prop;
    }

    /**
     * @return the storeList
     */
    public List<StoreListItem> getStoreList() {
        return storeList;
    }

    /**
     * Obtains a icon image for the application.
     * 
     * @return {@link ImageIcon}
     */
    public static ImageIcon getAppIcon() {
        URL imgURL;
        String iconPath = AppUtil.getAppProperty(AppUtil.APP_ICON_KEY);
        if (iconPath == null) {
            // Try obtaining from application.properties file directly, in the
            // event the app properties have not been loaded yet.
            Properties prop = GeneralUtil
                    .loadProperties(AppUtil.CONFIG_COMMON_FILE);
            iconPath = prop.getProperty(AppUtil.APP_ICON_KEY);
        }
        imgURL = AppUtil.class.getResource(iconPath);
        ImageIcon img = null;
        if (imgURL != null) {
            img = new ImageIcon(imgURL, "Application Icon");
        }
        return img;
    }

    /**
     * Return a Font instance that will be applied to each data row of the
     * DataGrid component.
     * <p>
     * The data used to create the Font instance is obtained from the
     * application.properties file. The keys for the font family, size, and
     * style are <i>ListDataFont</i>, <i>ListDataFontSize</i>, and
     * <i>ListDataFontStyle</i>, respectively.
     * 
     * @return An instance of {@link Font}
     */
    public static Font getDataGridRowFont() {
        String fontName = AppUtil.getAppProperty("ListDataFont");
        String fontSizeStr = AppUtil.getAppProperty("ListDataFontSize");
        String fontStyleStr = AppUtil.getAppProperty("ListDataFontStyle");
        int fontSize;
        int fontStyle;

        if (fontName == null) {
            fontName = Font.MONOSPACED;
        }
        try {
            fontSize = Integer.parseInt(fontSizeStr);
        } catch (NumberFormatException e) {
            fontSize = DEFAULT_FONT_SIZE;
        }

        try {
            fontStyle = Integer.parseInt(fontStyleStr);
        } catch (NumberFormatException e) {
            fontStyle = Font.PLAIN;
        }

        return new Font(fontName, fontStyle, fontSize);
    }

    /**
     * Return a Font instance that will be applied to table header of the
     * DataGrid component.
     * <p>
     * The data used to create the Font instance is obtained from the
     * application.properties file. The keys for the font family, size, and
     * style are <i>ListHeaderFont</i>, <i>ListHeaderFontSize</i>, and
     * <i>ListHeaderFontStyle</i>, respectively.
     * 
     * @return An instance of {@link Font}
     */
    public static Font getDataGridHeaderFont() {
        String fontName = AppUtil.getAppProperty("ListHeaderFont");
        String fontSizeStr = AppUtil.getAppProperty("ListHeaderFontSize");
        String fontStyleStr = AppUtil.getAppProperty("ListHeaderFontStyle");
        int fontSize;
        int fontStyle;

        if (fontName == null) {
            fontName = Font.MONOSPACED;
        }
        try {
            fontSize = Integer.parseInt(fontSizeStr);
        } catch (NumberFormatException e) {
            fontSize = DEFAULT_FONT_SIZE;
        }

        try {
            fontStyle = Integer.parseInt(fontStyleStr);
        } catch (NumberFormatException e) {
            fontStyle = Font.BOLD;
        }

        return new Font(fontName, fontStyle, fontSize);
    }
}
