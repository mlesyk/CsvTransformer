package org.mlesyk.gwt.csvdashboard.server.form;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.mlesyk.Loggers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFileHandler extends HttpServlet {

    private final Logger log = LoggerFactory.getLogger(Loggers.CSV_WEB);
    private static final long serialVersionUID = 1L;

    // sizeMax - The maximum allowed size, in bytes. The default value of -1 indicates, that there is no limit.
    // 1048576 bytes = 1024 Kilobytes = 1 Megabyte
    private static final int MAX_FILE_SIZE = 1048576;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File uploadedFile;
        PrintWriter out = response.getWriter();
        response.setHeader("Content-Type", "text/html");

        // Create a factory for disk-based file items
        FileItemFactory factory = new DiskFileItemFactory();
        // Create a new file upload handler
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setSizeMax(MAX_FILE_SIZE);

        try {
            if (!ServletFileUpload.isMultipartContent(request)) {
                log.error("Error, multipart request not found.");
                response.getWriter().write("Error, multipart request not found.");
                return;
            }
            List<FileItem> items = fileUpload.parseRequest(request);
            if (items == null) {
                log.debug("UploadFileHandler failed: items is null");
                response.getWriter().write("File uploaded not correctly");
                return;
            }
            uploadedFile = new File("C:\\" + items.get(0).getName());
            for (FileItem item : items) {
                // http://commons.apache.org/fileupload/using.html
                int sizeInBytes = (int) item.getSize();
                log.info("FileItem size in bytes is : " + sizeInBytes);
                item.write(uploadedFile);
            }
            log.debug("UploadFileHandler doPost output: {}", uploadedFile.getAbsolutePath());
            out.println(uploadedFile.getAbsolutePath());
        } catch (SizeLimitExceededException e) {
            log.error("File size exceeds the limit {} bytes", MAX_FILE_SIZE);
        } catch (Exception e) {
            log.error("Error on file upload", e);
            out.println("Error occurs: " + e.getMessage());
        } finally {
            out.flush();
            out.close();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}