//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//           Class: RMT2WebMenuTag
//
//    Constructors: public RMT2WebMenuTag()

//         Lineage: RMT2TagSupportBase
//
//      Implements: none
//
//     Description: JSP Custom tag class for constructing and displaying
//                             navigational menus from a XML data source.
//
//  SCR          Date                          Developer                   Description
// =======   ==============  ===============   =====================================
//                    2/9/2003                   RMT                              Add changes to distinquish between  menus that are to be created at the
//                                                                                             top of a jsp page or on the side of a  jsp page as well as hiding and
//                                                                                             displaying the menu by default, respectively.
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.taglib.menu;

import java.io.IOException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import com.taglib.RMT2TagSupportBase;
import com.util.SystemException;

import com.constants.RMT2ServletConst; //  RMT 02092003

/**
 * @deprecated Will be removed in future versions
 * @author roy.terrell
 *
 */
public class RMT2WebMenuTag extends RMT2TagSupportBase {

    protected String menuName = null;

    protected String filename = null;

    protected String subMenuImage = null;

    protected int menuType = 0; //  RMT 02092003

    public void setMenuName(String value) {
        this.menuName = value;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setFilename(String value) {
        this.filename = value;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setSubMenuImage(String value) {
        this.subMenuImage = value;
    }

    public String getSubMenuImage() {
        return this.subMenuImage;
    }

    //  Begin RMT 02092003
    public void setMenuType(String value) {
        if (value == null) {
            this.menuType = RMT2ServletConst.MENUTYPE_TOP;
            return;
        }
        if (value == "0") {
            this.menuType = RMT2ServletConst.MENUTYPE_TOP;
            return;
        }
        if (value == "1") {
            this.menuType = RMT2ServletConst.MENUTYPE_TOP;
            return;
        }
        if (value == "2") {
            this.menuType = RMT2ServletConst.MENUTYPE_SIDE;
            return;
        }
    }

    //  End RMT 02092003

    public int doStartTag() throws JspException {

        String filePath = null;
        String value = null;

        try {
            //      super.doStartTag();

            filePath = "file:///" + pageContext.getServletContext().getRealPath("/data/xml/menus/" + this.getFilename() + ".xml");
            RMT2WebMenus menu = new RMT2WebMenus();
            menu.setSubMenuImage(this.subMenuImage);
            menu.setMenuType(this.menuType); //  RMT 02092003
            value = menu.createMenu(this.menuName, filePath);
            this.outputHtml(value);

            return SKIP_BODY;
        }
        catch (IOException e) {
            throw new JspException("Class: " + this.className + "  Method: " + this.methodName + "  Message (SystemException): " + e.getMessage());
        }
        catch (SystemException e) {
            throw new JspException("Class: " + this.className + "  Method: " + this.methodName + "  Message (SystemException): " + e.getMessage());
        }
    }

    protected void exportData() throws JspException {
        return;
    }

}