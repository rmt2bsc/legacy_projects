package com;

import java.sql.*;
import java.util.*;

import java.io.PrintWriter;
import java.io.FileWriter;


public abstract class AbstractOrmClassCreator extends AbstractOrmDataProvider implements ObjectSelector {

  private String tableName;
  private int tableId;
  private String formBeanName;
  private StringBuffer header;
  private StringBuffer declaration;
  private StringBuffer methods;
  private StringBuffer constructor;
  private StringBuffer staticColNames;
  private Object source;
  
  
  public AbstractOrmClassCreator(Object dataSource) throws Exception {
      super(dataSource);
  }
  
  
  
  public void produceBeans() {
    String content;
      
    try {
        List list = this.getSelectedObjects(this.source);
      for (Object data : list) {
        tableName = ((OrmObjectData) data).getObjectName().trim().toLowerCase();
        formBeanName = this.formatClassMethodName(tableName);
        tableId = ((OrmObjectData) data).getObjectId();

          // Instantiate buffers
        this.header = new StringBuffer();
        this.declaration = new StringBuffer();
        this.methods = new StringBuffer();
        this.constructor = new StringBuffer();
        this.staticColNames = new StringBuffer();

           // Create class package statement
        String packageLoc = this.prop.getString("orm_bean_package");
        this.header.append("package " + packageLoc + ";\n\n\n");
        this.header.append("import java.util.Date;\n");
        this.header.append("import com.bean.OrmBean;\n");
        this.header.append("import com.util.SystemException;\n\n\n");

        // Add class description javadoc
        this.header.append("/**\n");
        this.header.append(" * Peer object that maps to the " + tableName + " database table/view.\n");
        this.header.append(" *\n");
        this.header.append(" * @author Roy Terrell.\n");
        this.header.append(" */\n");

        this.header.append("public class ");
        this.header.append(formBeanName);
        this.header.append(" extends OrmBean {\n\n");

        // Add javadoc for default constructor.
        this.constructor.append("/**\n");
        this.constructor.append(" * Default constructor.\n");
        this.constructor.append(" *\n");
        this.constructor.append(" * @author Roy Terrell.\n");
        this.constructor.append(" */\n");
        this.constructor.append("  public ");
        this.constructor.append(formBeanName);
        this.constructor.append("() throws SystemException {\n\tsuper();\n }\n");

        // Create Class data memeber declarations
        this.buildBeanAttributes(tableId);

        //  Build last method
        this.methods.append("/**\n");
        this.methods.append(" * Stubbed initialization method designed to implemented by developer.\n\n");
        this.methods.append(" *\n");
        this.methods.append(" * @author Roy Terrell.\n");
        this.methods.append(" */\n");
        this.methods.append("  public void initBean() throws SystemException {}\n}");

        // Assemble the javabean parts into a file.
        content = this.header.toString() + "\n\n\n\t// Property name constants that belong to respective DataSource, " + formBeanName + "View\n\n" +
                  this.staticColNames.toString() + "\n\n\n\t" +
                  this.declaration.toString() + "\n\n\n\t// Getter/Setter Methods\n\n" +
                  this.constructor.toString() +
                  this.methods.toString();
//                  this.methods.toString() + "  public void initBean() throws SystemException {}\n}";
        FileWriter file = new FileWriter(filename + formBeanName + ".java");
        PrintWriter pw = (new PrintWriter(file));
        pw.print(content);
        pw.flush();
        pw.close();
        System.out.println(formBeanName + "Bean - created successfully!");
      }
      System.out.println("Bean Creation Process Completed!!!!!!!");
    }
    catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }


 public int buildBeanAttributes(int _tableId) throws Exception {
    List list = this.getObjectAttributes(new Integer(_tableId)); 
    for (Object data : list) {
	String colName = ((OrmObjectData) data).getObjectName().trim().toLowerCase();
        int dataType = ((OrmObjectData) data).getObjectId();
      this.buildDataMember(colName, dataType);
    }
    return 1;
  }



  public int buildDataMember(String _colName, int _dataType) throws Exception {

    String varName = this.formatVarName(_colName);
    String methodName = this.formatClassMethodName(_colName);
    String dataTypeName = null;

    switch (_dataType) {
      case 1:
      case 2:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
        dataTypeName = "int";
        break;

      case 3:
      case 4:
      case 5:
      case 27:
        dataTypeName = "double";
        break;

      case 7:
      case 8:
      case 33:
      case 34:
          dataTypeName = "char";
          
      case 9:
      case 10:
      case 11:
      case 12:
      case 28:
      case 32:
      case 35:
      case 36:
        dataTypeName = "String";
        break;

      case 6:
      case 13:
      case 14:
        dataTypeName = "java.util.Date";
        break;

      default:
        dataTypeName = "";
        break;
    } // end switch


      // Create Class Data Member and its javadoc
    this.declaration.append("/** The javabean property equivalent of database column " + this.tableName + "." + _colName + " */\n");
    this.declaration.append("  private ");
    this.declaration.append(dataTypeName);
    this.declaration.append(" ");
    this.declaration.append(varName);
    this.declaration.append(";\n");
    
    // Create DataSource property name constants and their javadoc
    this.staticColNames.append("/** The property name constant equivalent to property, " + methodName + ", of respective DataSource view. */\n");
    this.staticColNames.append("  public static final String PROP_");
    this.staticColNames.append(methodName.toUpperCase());
    this.staticColNames.append(" = \"");
    this.staticColNames.append(methodName);
    this.staticColNames.append("\";\n");

      // Create Class setter Method and its javadoc
    this.methods.append("/**\n");
    this.methods.append(" * Sets the value of member variable " + varName + "\n");
    this.methods.append(" *\n");
    this.methods.append(" * @author Roy Terrell.\n");
    this.methods.append(" */\n");
    this.methods.append("  public void set");
    this.methods.append(methodName);
    this.methods.append("(");
    this.methods.append(dataTypeName);
    this.methods.append(" value) {\n");
    this.methods.append("    this.");
    this.methods.append(varName);
    this.methods.append(" = value;\n");
    this.methods.append("  }\n");

      // Create Class getter Method
    this.methods.append("/**\n");
    this.methods.append(" * Gets the value of member variable " + varName + "\n");
    this.methods.append(" *\n");
    this.methods.append(" * @author Roy Terrell.\n");
    this.methods.append(" */\n");
    this.methods.append("  public ");
    this.methods.append(dataTypeName);
    this.methods.append(" get");
    this.methods.append(methodName);
    this.methods.append("() {\n");
    this.methods.append("    return this.");
    this.methods.append(varName);
    this.methods.append(";\n");
    this.methods.append("  }\n");

    return 1;
  }


  public String formatClassMethodName(String value) {

    String newValue = "";
    String token = "";

    StringTokenizer str = new StringTokenizer(value, "_");
    while (str.hasMoreTokens()) {
      token = str.nextToken();
      newValue += this.wordCap(token.toLowerCase());
    }
    return newValue;
  }

  public String formatVarName(String value) {

    int    wordCount = 0;
    String newValue = "";
    String token = "";

    StringTokenizer str = new StringTokenizer(value, "_");
    while (str.hasMoreTokens()) {
      token = str.nextToken();
      if (++wordCount == 1) {
        newValue = token.toLowerCase();
      }
      else {
        newValue += this.wordCap(token.toLowerCase());
      }
    }  //  end while

    return newValue;
  }



  public String wordCap(String value) {

    StringBuffer capValue = new StringBuffer(100);

    for (int ndx = 0; ndx < value.length(); ndx++) {
      if (ndx == 0) {
        Character ch = new Character(value.charAt(ndx));
        capValue.append(ch.toString().toUpperCase());
      }
      else {
        Character ch = new Character(value.charAt(ndx));
        capValue.append(ch.toString());
      }
    }
    return capValue.toString();
  }

}

