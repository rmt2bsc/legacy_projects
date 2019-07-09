////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//           Class: RMT2WebDbMenuTag
//
//    Constructors: public RMT2WebDbMenuTag()

//         Lineage: RMT2TagSupportBase
//
//      Implements: none
//
//     Description: JSP Custom tag class for constructing and displaying
//                            navigational menus from data derived from the datatbase.
//
//  SCR          Date                          Developer                    Description
// =======   ==============  ===============   =====================================
//                    2/9/2003                  RMT                              Add changes to distinquish between   menus that are to be created at the
//                                                                                             top of a jsp page or on the side of a  jsp page as well as hiding and
//                                                                                            displaying the menu by default,  respectively.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.taglib.menu;

import com.taglib.RMT2TagSupportBase;
import com.taglib.menu.RMT2WebDbMenu;

import java.io.IOException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import com.util.SystemException;

import com.constants.RMT2ServletConst; //  RMT 02092003

/**
 * @deprecated Will be removed in future versions
 * @author roy.terrell
 *
 */
public class RMT2WebDbMenuTag extends RMT2TagSupportBase {

    protected String sql = null;

    protected String dataColumn = null;

    protected String hyperlink = null;

    protected String reqParms = null;

    protected String menuName;

    protected int menuType = 0; //  RMT 02092003

    public void setSql(String value) {
        this.sql = value;
    }

    public String getSql() {
        return this.sql;
    }

    public void setDataColumn(String value) {
        this.dataColumn = value;
    }

    public String getDataColumn() {
        return this.dataColumn;
    }

    public void setHyperlink(String value) {
        this.hyperlink = value;
    }

    public String getHyperlink() {
        return this.hyperlink;
    }

    public void setMenuName(String value) {
        this.menuName = value;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setReqParms(String value) {
        this.reqParms = value;
    }

    public String getReqParms() {
        return this.reqParms;
    }

    //  Begin RMT 02092003
    public void setMenuType(String value) {
        if (value == null) {
            this.menuType = RMT2ServletConst.MENUTYPE_SIDE;
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

        super.doStartTag();
        String value = null;

        try {
            RMT2WebDbMenu menu = new RMT2WebDbMenu(this.obj, this.dataColumn, this.hyperlink, this.reqParms);
            menu.setMenuType(this.menuType); //  RMT 02092003
            value = menu.createMenu(this.menuName);
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