package modules.report;

import java.awt.Dimension;
import java.awt.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Date;

import java.util.concurrent.ExecutionException;

import javax.net.SocketFactory;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import modules.model.ReportDetail;

import com.nv.util.AppUtil;

/**
 * A worker class for sending a report request to the UNIX server and displaying
 * the results of the report request via the ReportViewer.
 * <p>
 * The sending of the request is performed as a background task since the UNIX
 * server could take several minutes to come back with the results depending on
 * the nature of the report request. Once the UNIX server sends the results of
 * the report request, the results are loaded into the report viewer, which is
 * operating on the current thread or event dispatch thread.
 * <p>
 * Connection information regarding the UNIX server should be obtained from the
 * application.properties file
 * 
 * @author rterrell
 *
 */
public class ReportRequestWorker extends SwingWorker<String, Void> {

    private static final Logger logger = Logger
            .getLogger(ReportRequestWorker.class);

    private String serverName;

    private int port;

    private String request;

    private ReportDetail rptInfo;

    private String msg;

    /**
     * Created a ReportRequestWorker object which will initialize itself with
     * UNIX server connection data obtained from the application.properties
     * file.
     * <p>
     * This is when the UNIX server connection information is obtained. It is
     * required that server name exist in the properties file as
     * <i>report.socket.serverName</i> and the port number as
     * <i>report.socket.port</i>.
     */
    public ReportRequestWorker(String request, ReportDetail rptInfo) {
        this.serverName = AppUtil.getAppProperty("dbServerName");
        String portStr = AppUtil.getAppProperty("report.socket.port");
        this.port = Integer.parseInt(portStr);
        this.request = request;
        this.rptInfo = rptInfo;
    }

    /**
     * Contacts the UNIX server via socket for the purpose of sending the report
     * request and waiting for a response.
     * <p>
     * 
     * @throws Exception
     *             Host cannot be identified or genreal IO errors.
     */
    @Override
    protected String doInBackground() throws Exception {
        SocketFactory f = SocketFactory.getDefault();
        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;

        logger.info("Begin report socket creation: " + new Date());
        try {
            s = f.createSocket(this.serverName, this.port);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()),
                    10000);
        } catch (UnknownHostException e) {
            throw new Exception(
                    "Unable to send report request, because the report host is unknown",
                    e);
        } catch (IOException e) {
            throw new Exception(
                    "Unable to send report request, because of an IO error occured during the process of sending the report request",
                    e);
        }
        logger.info("End report socket creation: " + new Date());

        logger.info("Begin sending report request to UNIX server: "
                + new Date());
        out.println(this.request);
        out.flush();
        logger.info("End sending report request to UNIX server: " + new Date());

        logger.info("*********************** Begin receiving report response: "
                + new Date());
        StringBuffer rptBuf = new StringBuffer();
        int inVal = 0;
        int len = 0;
        while ((inVal = in.read()) != -1) {
            char c = (char) inVal;
            rptBuf.append(c);

            // For some reason the server is causing the client to block when
            // reading beyond the
            // last valid character of the report but before receiving the
            // @@@EndOfReport tag.
            // The server will need to be evaluated.
            //
            // This is a workaround the problem, but could be a potential
            // problem since the "ready"
            // method is designed to determine if the buffer is empty (not the
            // end of the input stream).
            // This could be misleading and yield unexpected results especially
            // if the server is sending
            // a continuous stream, pause, and then resume sending a stream.
            // Since this is not the case
            // on the server side, this workaround should be reliable.
            if (in.ready()) {
                continue;
            }
            else {
                logger.info("Report socket input blocking event started...");
                // This line of code will interrupt the reading of the input
                // buffer and force the
                // results to be returned much quickly.
                len = rptBuf.length();
                logger.info("The number of bytes read before blocking event: "
                        + len);
                boolean eor = (rptBuf.indexOf(ReportConstants.RPT_ACK_END) > -1);
                if (eor) {
                    logger.info("Report socket input completed.");
                    break;
                }
                logger.info("Report socket input blocking event ended; resume reading socket for more data...");
                continue;
            }
        }
        logger.info("*********************** End receiving report response: "
                + new Date());

        len = rptBuf.length();
        logger.info("Total number of bytes read for the report: " + len);

        logger.info("Begin report socket close: " + new Date());
        String results = rptBuf.toString();
        out.close();
        in.close();
        s.close();
        logger.info("End report socket close: " + new Date());

        return results;
    }

    /**
     * Receives and loads the report request results into the
     * {@link ReportViewer} instance for display.
     * 
     * @throws RuntimeException
     *             An execution error or general type error.
     */
    @Override
    protected void done() {
        super.done();
        boolean showViewer = true;
        try {
            String report = this.get();

            if (report.indexOf(ReportConstants.RPT_ACK_ERROR) != -1) {
                report = "The Report Server cannot generate the requested report.\n\nIf this continues please notify your supervisor.";
            }
            // The following condition may not ever be satisfied due to the
            // changes in doInBackground
            // method regarding the usage of input stream's "ready" method.
            if (report.indexOf(ReportConstants.RPT_ACK_END) != -1) {
                report = report.replaceAll(ReportConstants.RPT_ACK_END, "");
            }
            if (report.indexOf(ReportConstants.RPT_ACK_RECEIVED) != -1) {
                showViewer = false;
            }

            // Invoke the Report viewer which displays the report on the user's
            // screen.
            if (showViewer) {
                ReportViewer view = new ReportViewer(new Dimension(1200, 900),
                        new Point(500, 300), this.rptInfo, report,
                        "Report Viewer");
                view.setVisible(true);
                // GeneralUtil.outputFile(report,
                // "C:\\temp\\ReportWorkerResults.txt");
            }
        } catch (InterruptedException e) {
            this.msg = "Report Request Worker experienced a thread interruption while processing report request, "
                    + this.request;
            logger.warn(this.msg, e);
        } catch (ExecutionException e) {
            this.msg = "Report Request Worker experienced an execution error while processing report request, "
                    + this.request;
            logger.error(this.msg, e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            this.msg = "Report Request Worker experienced a general error while processing report request, "
                    + this.request;
            logger.error(this.msg, e);
            throw new RuntimeException(e);
        }
    }

}
