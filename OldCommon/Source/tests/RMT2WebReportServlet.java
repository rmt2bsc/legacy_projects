package com.servlet;

import java.io.IOException;
//import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IReportEngine;

import com.action.RMT2BirtEngine;
import com.util.SystemException;



/**
 * @deprecated
 * @author appdev
 *
 */
public class RMT2WebReportServlet extends RMT2Servlet {
	private static final long serialVersionUID = 1L;
	private IReportEngine birtReportEngine = null;
	protected static Logger logger = Logger.getLogger( "org.eclipse.birt" );

	public RMT2WebReportServlet() {
		super();
	}

	/**
	 * Destruction of the servlet.

	 */
	public void destroy() {
		super.destroy();
		 RMT2BirtEngine.destroyBirtEngine();
	}

	/**
	 * 
	 */
	public void initServlet() throws SystemException {
	}

	/**
	 * Process report request.
	 * 
	 * @see 
	 */
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

		//get report name and launch the engine
		resp.setContentType("text/html");
		//resp.setContentType( "application/pdf" );
		//resp.setHeader ("Content-Disposition","inline; filename=test.pdf");
		String reportName = req.getParameter("ReportName");
		ServletContext sc = req.getSession().getServletContext();
		this.birtReportEngine = RMT2BirtEngine.getBirtEngine(sc);

		//setup image directory
		HTMLRenderContext renderContext = new HTMLRenderContext();
		renderContext.setBaseImageURL(req.getContextPath()+"/images");
		renderContext.setImageDirectory(sc.getRealPath("/images"));

		logger.log( Level.FINE, "image directory " + sc.getRealPath("/images"));
		System.out.println("stdout image directory " + sc.getRealPath("/images"));

		HashMap<String, HTMLRenderContext> contextMap = new HashMap<String, HTMLRenderContext>();
		contextMap.put( EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext );

		IReportRunnable design;
		try  {
			//Open report design
			design = birtReportEngine.openReportDesign( sc.getRealPath("/Reports")+"/"+reportName );
			//create task to run and render report
			IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask( design );
			task.setAppContext( contextMap );

			//set output options
			HTMLRenderOption options = new HTMLRenderOption();
			options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);
			//options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);
			options.setOutputStream(resp.getOutputStream());
			task.setRenderOption(options);

			//run report
			task.run();
			task.close();
		 }
		catch (Exception e) {
			e.printStackTrace();
			throw new ServletException( e );
		}
	}

	/**
	 * Initialization of the servlet.

	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		RMT2BirtEngine.initBirtConfig();
	}
} // end class

