package modules;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * Launch Pad's main menu implementation of ListModel which extends
 * AbstarctLitModel.
 * <p>
 * This class provides the data needed to paint the main menu. The model manages
 * its list items as instances of {@link MainMenuItem}.
 * 
 * @author rterrell
 *
 */
public class MainMenuListModelImpl extends AbstractListModel {

    private static final long serialVersionUID = 8659567350752547834L;

    private static final Logger logger = Logger
            .getLogger(MainMenuListModelImpl.class);

    private static int COL_TOTAL = 4;

    public static final int IDT_PROCESS_ID = 110;

    private List<MainMenuItem> items;

    /**
     * Creates an MainMenuListModelImpl object initialized with a list of menu
     * items.
     * <p>
     * Each menu item's display value is derived from a value hard coded in the
     * logic and the image icons are obtained from the file system and directly
     * mapped to each display value.
     * <p>
     * The expected record layout of the hard-coded menu item data that must
     * follow the column order as specified below:
     * <ol>
     * <li>An alpha numeric String representing the menu item's display value.</li>
     * <li>A String representing the the path of the image file.</li>
     * <li>A boolean String representing the whether or not the menu item is
     * subject to security. Valid values are "true", "false", "yes", and "no"</li>
     * <li>A numeric String representing the menu item's process id which should
     * be unique.</li>
     * </ol>
     * <p>
     * The only image formats that are supported by Swing are: .jpg, .png, and
     * .gif.
     */
    public MainMenuListModelImpl() {
        String data[][] = {
                { "Login", "/images/id_card.png", "false", "100" },
                { "User Administration", "/images/dude3.png", "true", "101" },
                { "Price Change Administration", "/images/price_change.png",
                        "true", "102" },
                { "Price Audit Administration", "/images/price_audit.png",
                        "true", "103" },
                { "SI Transfers Administration", "/images/si_trans.png",
                        "true", "104" },
                { "BI Transfers Administration", "/images/bi_trans.png",
                        "true", "105" },
                { "SI RTV Administration", "/images/si_rtv.png", "true", "106" },
                { "BI RTV Administration", "/images/bi_rtv.png", "true", "107" },
                { "Print/Reprint Reports", "/images/reports.png", "false",
                        "108" },
                { "Count Administration", "/images/counts.png", "true", "109" },
                { "IDT Administration", "/images/idt.png", "true",
                        String.valueOf(MainMenuListModelImpl.IDT_PROCESS_ID) },
                // { "Load Telnet Hex File", "/images/telnet.png", "true", "111"
                // },
                { "Logout", "/images/id_card_error.png", "true", "112" },
                { "Exit", "/images/exit.png", "false", "-1" } };

        // Current directory information to include in log
        try {
            String dir = null;
            File file = new File(".");
            dir = file.getCanonicalPath();
            logger.info("The current directory where the retrieval of main menu images will begin: "
                    + dir);
        } catch (IOException e) {
            logger.warn("Unable to determine application current directory during main menu image loading");
        }

        // build menu structure
        this.items = this.createMenuStructure(data);
    }

    /**
     * 
     * @param data
     * @return
     */
    private List<MainMenuItem> createMenuStructure(String data[][]) {
        List<MainMenuItem> list = new ArrayList<MainMenuItem>();
        String name = null;
        String imgUrl = null;
        boolean secure = false;
        int processId = 0;
        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < MainMenuListModelImpl.COL_TOTAL; col++) {
                if (col == 0) {
                    name = data[row][col];
                }
                if (col == 1) {
                    imgUrl = data[row][col];
                }
                if (col == 2) {
                    secure = Boolean.parseBoolean(data[row][col]);
                }
                if (col == 3) {
                    try {
                        processId = Integer.parseInt(data[row][col]);
                    } catch (NumberFormatException e) {
                        processId = -1;
                    }
                }
            }
            MainMenuItem item = this.createMenuItem(name, imgUrl, secure,
                    processId);
            list.add(item);
        }
        return list;
    }

    /**
     * 
     * @param descr
     * @param imgPath
     * @param secure
     * @param processId
     * @return
     */
    private MainMenuItem createMenuItem(String descr, String imgPath,
            boolean secure, int processId) {
        MainMenuItem item = new MainMenuItem();
        item.setDisplayValue(descr);
        item.setSecure(secure);
        item.setProcessId(processId);
        URL imgURL = MainMenuListModelImpl.class.getResource(imgPath);
        ImageIcon img = null;
        if (imgURL == null) {
            logger.warn("Unable to locate image resource for menu item, "
                    + descr + ": " + imgPath);
            img = new ImageIcon();
        }
        else {
            img = new ImageIcon(imgURL, descr);
            logger.info("Main menu item loaded successfully: " + descr + " : "
                    + imgPath);
        }
        item.setImage(img);
        return item;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize() {
        return (this.items == null ? 0 : this.items.size());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index) {
        return (this.items == null ? null : this.items.get(index));
    }

}
