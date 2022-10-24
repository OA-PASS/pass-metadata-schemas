package org.eclipse.pass.schema;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SchemaServlet This class handles the web request
 * handling of GET and POST requests from the client. It interacts with the
 * SchemaService class, which handles the business logic
 */
@WebServlet("/schemaservice")
public class SchemaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Servlet constructor.
     */
    public SchemaServlet() {
        // TODO: Not implemented yet
    }

    /**
     * Handle GET requests by sending a HTML response back to the client, providing
     * a link to the PASS schema service documentation.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // setCommonHeaders()

        // Create HTML response with a redirect link to the PASS schema service
        // documentation

    }

    /**
     * Handle POST requests by invoking the SchemaService to handle the business
     * logic of generating a merged schema from the list of relevant repository
     * schemas to a PASS submission
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // setCommonHeaders()

        // Create SchemaService instance to handle business logic

        // Encode resulting schema(s) into a JSON response object

    }

    /**
     * Sets standard headers that are common to both GET and POST responses
     *
     * @param request
     * @param response
     */
    private void setCommonHeaders(HttpServletRequest request, HttpServletResponse response) {
        // TODO: Not implemented yet
    }

}
